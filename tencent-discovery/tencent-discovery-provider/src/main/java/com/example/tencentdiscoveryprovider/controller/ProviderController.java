package com.example.tencentdiscoveryprovider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lengleng
 * @date 2022/6/20
 */
@RestController
@RequestMapping
public class ProviderController {

	@GetMapping("/provider/{name}")
	public String provider(@PathVariable String name) {
		return name;
	}

}
