package com.ibm.insite.kafkasender;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * A Sample application written for sending sample messages to Kafka topic. It also provides a UI for sending messages.
 * The UI provided for POC is simple on wherein uses can order electronic products. 
 * Here I am sending the order message to a simple Kafka partition but in production, we will have multiple partitions.
 * 
 * @author sw088d
 *
 */
@SpringBootApplication
public class KafkaSparkSenderApplication  extends SpringBootServletInitializer implements CommandLineRunner {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(KafkaSparkSenderApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaSparkSenderApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
