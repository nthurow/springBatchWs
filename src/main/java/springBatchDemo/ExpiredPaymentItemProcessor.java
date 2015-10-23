package springBatchDemo;

import org.springframework.batch.item.ItemProcessor;
import springBatchDemo.CustomerPayment;

public class ExpiredPaymentItemProcessor implements ItemProcessor<CustomerPayment, CustomerPayment> {

    @Override
    public CustomerPayment process(final CustomerPayment customerPayment) {
        customerPayment.setNotifiedOfUpcomingExpiration(true);
        return customerPayment;
    }
}