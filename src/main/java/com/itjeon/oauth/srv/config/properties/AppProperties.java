package com.itjeon.oauth.srv.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

	public String getClientId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}