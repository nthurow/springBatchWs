Spring Batch Demo 
=================

This repository is a simple example of using the Spring Batch framework to read data, process that data, and then record the results.  Specifically, this code will connect to the payment processing service [Stripe](https://stripe.com) to retrieve a list of customers; examine the credit card information stored for that customer and determine if the card is about to expire; send an email to the customer if the card is about to expire; and finally, log to the console the number of customers that 
were notified in each chunk.

## Notes
* The code uses the Stripe Java libraries to get customer information, and also uses the Stripe Customer and ExternalAccount Java classes
* Constructor injection was used to inject the StripeRepository and EmailService objects into the processor
* Some simple unit tests were written to verify the basic functionality of the processor, utilizing Groovy language features like mocking and closures to stub out calls to external services


