package com.example.rate.limit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lengleng
 * @date 2022/6/21
 */
@RestController
@RequestMapping
public class RateLimitController {

	@GetMapping("/rate-limit")
	public String rateLimit() {
		return "success";
	}

}
