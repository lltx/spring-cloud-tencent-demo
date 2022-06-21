package com.example.tencentdiscovery.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author lengleng
 * @date 2022/6/20
 */
@RestController
@RequestMapping
public class ConsumerController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/consumer")
	public String consumer() {
		return restTemplate.getForObject("http://lengleng-tencent-discovery-provider/provider/lengleng", String.class);
	}

}
