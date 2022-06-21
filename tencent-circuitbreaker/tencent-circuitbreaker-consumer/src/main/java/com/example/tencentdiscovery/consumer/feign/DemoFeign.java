package com.example.tencentdiscovery.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lengleng
 * @date 2022/6/21
 */
@FeignClient(contextId = "demoFeign", value = "lengleng-circuitbreaker-tencent-circuitbreaker-provider")
public interface DemoFeign {

	@GetMapping("/provider")
	String get(@RequestParam String name);

}
