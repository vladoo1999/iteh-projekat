package com.example.fon_classroommanagment.Filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.example.fon_classroommanagment.Configuration.Constants.BEARER_STRING;
import static com.example.fon_classroommanagment.Configuration.Constants.SECRET;
import static com.example.fon_classroommanagment.Configuration.Routes.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class BeforeRequestTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    List<String> routesAllowed= Arrays.asList(
            COMMON_PREFIX+COMMON_ALL_CLASSROOM_TYPES,
            COMMON_PREFIX+COMMON_ALL_EMPLOYEE_TYPES,
            COMMON_PREFIX+COMMON_ALL_EDUCATION_TITLES,
            COMMON_PREFIX+COMMON_ALL_EMPLOYEE_DEPARTMENTS,
            COMMON_PREFIX+COMMON_ALL_CLASSROOM_NAMES,
            LOGIN,
            REGISTER,
            LOGOUT);
    List<String> beginsWith= List.of(REGISTER+"/");
        if(routesAllowed.contains(request.getServletPath()) || request.getServletPath().startsWith(beginsWith.get(0))) filterChain.doFilter(request,response);
        else{
            String authHeader=request.getHeader(AUTHORIZATION);

            if(authHeader!=null && authHeader.startsWith(BEARER_STRING)){
                String token=authHeader.substring(BEARER_STRING.length());
                Algorithm algorithm=Algorithm.HMAC256(SECRET.getBytes());
                JWTVerifier verifier= JWT.require(algorithm).build();
                if(  JWT.decode(token).getExpiresAt().before(new Date())) {

                    TokenInvalid(response, "Token expired", HttpServletResponse.SC_NOT_ACCEPTABLE);
                }else {
                    TokenValid(request, response, filterChain, token, verifier);
                }
            }else{

                TokenInvalid(response, "Not logged in", HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    private void TokenValid(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String token, JWTVerifier verifier) throws IOException, ServletException {
        DecodedJWT decodedJWT= verifier.verify(token);

        String username = decodedJWT.getSubject();
        List<SimpleGrantedAuthority> authorities = new LinkedList<>();
        String[] tempAuthorities = decodedJWT.getClaim("roles").asArray(String.class);
        Arrays.stream(tempAuthorities).forEach(x -> authorities.add(new SimpleGrantedAuthority(x)));
        UsernamePasswordAuthenticationToken token1 = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(token1);
        filterChain.doFilter(request, response);
    }

    private void TokenInvalid(HttpServletResponse response, String x, int scMethodNotAllowed) throws IOException {
        response.resetBuffer();
        response.getOutputStream().write(x.getBytes());
        response.setStatus(scMethodNotAllowed);
    }
}
