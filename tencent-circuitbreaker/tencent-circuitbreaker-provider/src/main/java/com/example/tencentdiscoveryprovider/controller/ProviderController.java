package com.example.tencentdiscoveryprovider.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author lengleng
 * @date 2022/6/20
 */
@RestController
@RequestMapping
public class ProviderController {

	@GetMapping("/provider")
	public String provider(@RequestParam String name) {
		int i = 1 / 0;
		return name;
	}

}
