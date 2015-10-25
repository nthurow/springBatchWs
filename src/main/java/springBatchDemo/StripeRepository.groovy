package springBatchDemo

import com.stripe.Stripe
import com.stripe.model.Card
import com.stripe.model.Customer
import com.stripe.model.ExternalAccount

class StripeRepository {

    public StripeRepository() {

        Stripe.apiKey = "sk_test_1hjdKcaquUlHZcc6qVCaEeKo"
    }

    public List<Customer> getCustomers() {

        Map<String, Object> customerParams = new HashMap<String, Object>()
        return Customer.all(customerParams).data
    }

    public ExternalAccount getCustomerCard(stripeCustomerId) {

        return Customer.retrieve(stripeCustomerId).sources.data[0]
    }
}
