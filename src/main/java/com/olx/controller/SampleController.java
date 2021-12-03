package com.olx.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RefreshScope
public class SampleController {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@GetMapping(value = "/read-config")
	public String getConfig() {

		return "DB_URL" + dbUrl;
	}

}
