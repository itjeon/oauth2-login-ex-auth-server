package com.itjeon.oauth.srv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.itjeon.oauth.srv.config.properties.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    AppProperties.class
})
public class Oauth2LoginExAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2LoginExAuthServerApplication.class, args);
	}

}
