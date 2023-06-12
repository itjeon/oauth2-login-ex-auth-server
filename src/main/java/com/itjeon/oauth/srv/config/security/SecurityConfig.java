package com.itjeon.oauth.srv.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

import com.itjeon.oauth.srv.config.properties.AppProperties;
import com.itjeon.oauth.srv.handler.OAuth2AuthenticationSuccessHandler;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final AppProperties appProperties;

    @Bean
	protected SecurityFilterChain fileterChain(HttpSecurity http) throws Exception {
		http
			.sessionManagement( (sm) -> sm
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.formLogin( (frm) -> frm
					.disable()
			)
			.httpBasic( (basic) -> basic
					.disable()
			)
			.authorizeHttpRequests((authz) -> authz
					.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
					.requestMatchers("/login").permitAll()
					.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()	// view 로 forward 허용
					.anyRequest().authenticated()
			)
			.oauth2Login(login -> login
//					.loginProcessingUrl("/oauth2/authorization")
					.clientRegistrationRepository(clientRegistrationRepository())
					.successHandler(oAuth2AuthenticationSuccessHandler())
			)
		;
		
		return http.build();
	}

    @Bean
    protected ClientRegistrationRepository clientRegistrationRepository() {
//    	client-id: 791176758978-evnnmhtm9uvdm515qfbi855beqlmn75g.apps.googleusercontent.com
//        client-secret: CoyCdi-dHr0TJHybCUyD7oFf
//        authorization-grant-type: authorization_code
//        redirect-uri: http://localhost:8080/login/oauth2/code/google
//        scope:
//          - email
//          - profile
    	ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("google")
    			.clientId("")
    			.clientSecret("")
    			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//    			.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
    			.redirectUri("http://localhost:8080/login/oauth2/code/google")
    			.scope("openid", "profile", "email", "address", "phone")
    			.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
    			.tokenUri("https://www.googleapis.com/oauth2/v4/token")
    			.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
    			.userNameAttributeName(IdTokenClaimNames.SUB)
    			.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
    			.build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }
    
    /*
     * Oauth 인증 성공 핸들러
     * */
     @Bean
     public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
//         return new OAuth2AuthenticationSuccessHandler("http://localhost:8082/success");
    	 return new OAuth2AuthenticationSuccessHandler();
     }
}
