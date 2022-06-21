package com.example.tencentdiscovery.consumer.feign;

import org.springframework.stereotype.Service;

/**
 * @author lengleng
 * @date 2022/6/21
 */
@Service
public class DemoFeignFallback implements DemoFeign {

	@Override
	public String get(String name) {
		return null;
	}

}
