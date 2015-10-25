package springBatchDemoTests

import com.stripe.model.Card
import com.stripe.model.Customer
import groovy.mock.interceptor.MockFor
import groovy.mock.interceptor.StubFor
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import springBatchDemo.EmailService
import springBatchDemo.ExpiredPaymentItemProcessor
import springBatchDemo.StripeRepository

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ["classpath:/ExpiredPaymentBatchApp-context.xml"])
class ExpiredPaymentItemProcessorTest extends GroovyTestCase {

    @Test
    void testProcess_SendsEmailIfCardExpiresInLessThan60Days() {

        def mockStripeRepository = new MockFor(StripeRepository.class)
        def mockEmailService = new StubFor(EmailService.class)

        def mockCustomer = new Customer()
        mockCustomer.id = "123";

        def cardAboutToExpire = new Card();
        cardAboutToExpire.expMonth = Calendar.getInstance().get(Calendar.MONTH)
        cardAboutToExpire.expYear = Calendar.getInstance().get(Calendar.YEAR)

        mockStripeRepository.demand.with {
            getCustomerCard { stripeCustomerId ->
                assert stripeCustomerId == "123"
                return cardAboutToExpire
            }
        }

        mockEmailService.demand.with {
            sendMessage { stripeCustomerId, message ->
                assert stripeCustomerId == "123"
                assert message == 'Your credit card is about to expire'
            }
        }

        def stripeRespository = mockStripeRepository.proxyDelegateInstance()
        def emailService = mockEmailService.proxyDelegateInstance()

        def expiredPaymentItemProcessor = new ExpiredPaymentItemProcessor(stripeRespository, emailService)

        def processedMockCustomer = expiredPaymentItemProcessor.process(mockCustomer)

        assert processedMockCustomer == mockCustomer
        mockStripeRepository.verify(stripeRespository)
        mockEmailService.verify(emailService)
    }

    @Test
    void testProcess_NoEmailIfCardExpiresInMoreThan60Days() {

        def mockStripeRepository = new MockFor(StripeRepository.class)

        def mockCustomer = new Customer()
        mockCustomer.id = "123";

        def cardNotCloseToExpiring = new Card();
        cardNotCloseToExpiring.expMonth = Calendar.getInstance().get(Calendar.MONTH)
        cardNotCloseToExpiring.expYear = Calendar.getInstance().get(Calendar.YEAR ) + 1

        mockStripeRepository.demand.with {
            getCustomerCard { stripeCustomerId ->
                assert stripeCustomerId == "123"
                return cardNotCloseToExpiring
            }
        }

        def mockEmailService = [ sendMessage: { stripeCustomerId, message ->
            throw new Exception("SendMessage should not have been called")
        }] as EmailService

        def stripeRepository = mockStripeRepository.proxyDelegateInstance()

        def expiredPaymentItemProcessor = new ExpiredPaymentItemProcessor(stripeRepository, mockEmailService)

        def processedMockCustomer = expiredPaymentItemProcessor.process(mockCustomer)

        assert processedMockCustomer == null
        mockStripeRepository.verify(stripeRepository)
    }
}
