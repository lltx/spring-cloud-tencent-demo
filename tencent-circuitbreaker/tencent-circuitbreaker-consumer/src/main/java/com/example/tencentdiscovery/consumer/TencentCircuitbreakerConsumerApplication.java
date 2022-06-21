package com.example.tencentdiscovery.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TencentCircuitbreakerConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TencentCircuitbreakerConsumerApplication.class, args);
	}

}
