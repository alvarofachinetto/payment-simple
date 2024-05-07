package com.paymentsimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PaymentSimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentSimpleApplication.class, args);
	}

}
