package com.example.fon_classroommanagment.Filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.fon_classroommanagment.Configuration.UserProfileDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import static com.example.fon_classroommanagment.Configuration.Constants.*;

public class UserFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager manager;
    public UserFilter(AuthenticationManager manager){
        this.manager=manager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username=request.getParameter("username");
        String password=request.getParameter("password");

        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,password);
        return manager.authenticate(token);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)  {
        UserProfileDetails userProfileDetails=(UserProfileDetails)  authResult.getPrincipal();

        String validationToken= CreateValidationToken(userProfileDetails);
        String refreshToken= CreateRefreshToken(userProfileDetails);
        response.setHeader(VALIDATION_TOKEN_HEDER_NAME,validationToken);
        response.setHeader(REFRESH_TOKEN_HEDER_NAME,refreshToken);

        Cookie cookie = new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public String CreateValidationToken(UserProfileDetails userProfileDetails) {
        Algorithm algorithm=Algorithm.HMAC256(SECRET.getBytes());
        return    JWT.create()
                .withSubject(userProfileDetails.getUsername())
                .withExpiresAt(new Date( Calendar.getInstance().getTimeInMillis() + (VALIDATION_TOKEN_EXPIRATION)))
                .withClaim("roles",userProfileDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()) )
                .sign(algorithm);
    }

    private String CreateRefreshToken(UserProfileDetails userProfileDetails) {
        Algorithm algorithm=Algorithm.HMAC256(SECRET.getBytes());
        return    JWT.create()
                .withSubject(userProfileDetails.getUsername())
                .withExpiresAt(new Date( Calendar.getInstance().getTimeInMillis() + (REFRESH_TOKEN_EXPIRATION)))
                .sign(algorithm);
    }
}
