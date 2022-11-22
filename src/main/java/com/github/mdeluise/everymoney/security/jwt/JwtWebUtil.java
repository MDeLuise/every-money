package com.github.mdeluise.everymoney.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtWebUtil {
    private final JwtTokenUtil jwtTokenUtil;
    private final String jwtCookieName;


    @Autowired
    public JwtWebUtil(JwtTokenUtil jwtTokenUtil, @Value("jwt.cookie.name") String jwtCookieName) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtCookieName = jwtCookieName;
    }


    public ResponseCookie generateJwtCookie(UserDetails userDetails) {
        String jwt = jwtTokenUtil.generateAccessToken(userDetails).token();
        return ResponseCookie.from(jwtCookieName, jwt)
                             .path("/api")
                             .maxAge(24 * 60 * 60)
                             .httpOnly(true)
                             .build();
    }


    public ResponseCookie generateCleanJwtCookie() {
        return ResponseCookie.from(jwtCookieName, null).path("/api").build();
    }


    public String getJwtTokenFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }


    public String getAccessToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }
}
