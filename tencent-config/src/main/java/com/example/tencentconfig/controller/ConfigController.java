package com.example.tencentconfig.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 动态配置文件
 *
 * @author lengleng
 * @date 2022/6/20
 */
@RefreshScope
@RestController
@RequestMapping
public class ConfigController {

	@Value("${name:}")
	private String name;

	@GetMapping
	public String get() {
		return name;
	}

}
