package com.example.tencentdiscovery.consumer.controller;

import com.example.tencentdiscovery.consumer.feign.DemoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lengleng
 * @date 2022/6/20
 */
@RestController
@RequestMapping
public class ConsumerController {

	@Autowired
	private DemoFeign demoFeign;

	@GetMapping("/consumer")
	public String consumer() {
		return demoFeign.get("lengleng");
	}

}
