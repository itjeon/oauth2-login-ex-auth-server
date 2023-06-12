package com.itjeon.oauth.srv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.itjeon.oauth.srv.util.CookieUtil;
import com.nimbusds.oauth2.sdk.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
	
	@GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
		String redirectUriAfterLogin = request.getParameter("redirect_uri");
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtil.addCookie(response, "redirect_uri", redirectUriAfterLogin, 180);
        }
        
        return "login";
    }
	
}
