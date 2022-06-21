package com.example.tencentdiscovery.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TencentRouterConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TencentRouterConsumerApplication.class, args);
	}

}
