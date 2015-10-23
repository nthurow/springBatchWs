package springBatchDemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Configuration
@EnableBatchProcessing
public class ExpiredPaymentBatchConfiguration {

    @Bean
    public ItemReader<CustomerPayment> reader() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<CustomerPayment> customerPayments = new ArrayList();

        customerPayments.add(new CustomerPayment("nthurow@gmail.com", sdf.parse("2015-01-01")));
        customerPayments.add(new CustomerPayment("mwasowski@gmail.com", sdf.parse("2014-11-23")));
        customerPayments.add(new CustomerPayment("elephant@gmail.com", sdf.parse("2015-12-31")));

        return new ListItemReader(customerPayments);
    }

    @Bean
    public ItemProcessor<CustomerPayment, CustomerPayment> processor() {
        return new ExpiredPaymentItemProcessor();
    }

    @Bean
    public ItemWriter<CustomerPayment> writer(DataSource dataSource) {
        return new FakeItemWriter();
    }

    @Bean
    public Job importUserJob(JobBuilderFactory jobs, Step s1, JobExecutionListener listener) {
        return jobs.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(s1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<CustomerPayment> reader,
                      ItemWriter<CustomerPayment> writer, ItemProcessor<CustomerPayment, CustomerPayment> processor) {
        return stepBuilderFactory.get("step1")
                .<CustomerPayment, CustomerPayment>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
