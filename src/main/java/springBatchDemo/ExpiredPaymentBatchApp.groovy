package springBatchDemo

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

@SpringBootApplication
public class ExpiredPaymentBatchApp {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("ExpiredPaymentBatchApp-context.xml")
        JobLauncher jobLauncher = context.getBean(JobLauncher.class)

        Job job = context.getBean("springBatchJob", Job.class)

        JobParametersBuilder builder = new JobParametersBuilder()
        jobLauncher.run(job, builder.toJobParameters())
    }
}
