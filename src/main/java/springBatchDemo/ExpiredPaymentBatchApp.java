package springBatchDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpiredPaymentBatchApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExpiredPaymentBatchApp.class, args);
    }
}
