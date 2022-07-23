package com.aojun.user.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "login")
@Data
public class LoginConfig {
	
	public static final String AUTH_PREFIX = "Basic ";
	
	private String clientId;
	
	private String clientSecret;
	
}
