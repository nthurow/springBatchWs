package springBatchDemo

import com.stripe.model.Customer
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.beans.factory.annotation.Autowired

class CustomerRepository implements ItemReader<Customer>, ItemWriter<Customer> {

    private customerReader

    @Autowired
    public CustomerRepository(StripeRepository stripeRepository) {
        customerReader = new ListItemReader<Customer>(stripeRepository.getCustomers())
    }

    @Override
    public Customer read() {
        return customerReader.read()
    }

    @Override
    public void write(List customersThatWereNotified) throws Exception {
        System.out.println(customersThatWereNotified.size() + " were just notified of expiring credit cards");
    }
}
