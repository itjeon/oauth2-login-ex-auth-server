package com.itjeon.oauth.srv.util;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    public static String serialize(Object obj) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	String serializeStr = null;
    	
        try {
        	serializeStr = Base64.getUrlEncoder()
			        .encodeToString(objectMapper.writeValueAsBytes(obj));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
        return serializeStr;
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	Object deserializeObj = null;
    	try {
    		deserializeObj = objectMapper.reader().readValue(Base64.getUrlDecoder().decode(cookie.getValue()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return cls.cast(deserializeObj);
    }

}
