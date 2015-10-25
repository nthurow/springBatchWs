package springBatchDemo

import com.stripe.model.Customer
import groovy.time.TimeCategory
import org.springframework.batch.item.ItemProcessor
import org.springframework.beans.factory.annotation.Autowired

class ExpiredPaymentItemProcessor implements ItemProcessor<Customer, Customer> {

    private stripeRepository
    private emailService

    @Autowired
    public ExpiredPaymentItemProcessor(StripeRepository stripeRepository, EmailService emailService) {
        this.stripeRepository = stripeRepository
        this.emailService = emailService
    }

    @Override
    public Customer process(Customer customer) {

        def currentDate = new Date()
        def customerCard = stripeRepository.getCustomerCard(customer.id)

        def calendar = Calendar.getInstance()
        calendar.clear()
        calendar.set(Calendar.MONTH, customerCard.expMonth)
        calendar.set(Calendar.YEAR, customerCard.expYear)
        def cardExpirationDate = calendar.getTime()

        def timeDifference

        use(TimeCategory) {
            timeDifference = cardExpirationDate - currentDate

            if (timeDifference.days <= 60) {
                emailService.sendMessage(customer.id, 'Your credit card is about to expire')
                return customer
            } else {
                return null
            }
        }
    }
}
