package com.example.app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.app.entity.User;
import com.example.app.error.exception.AuthorizationException;
import com.example.app.repository.UserRepository;
import com.example.app.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWT filter is working");
        String accessToken = request.getHeader("Authorization");
        System.out.println(accessToken);
        if (Strings.isEmpty(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            DecodedJWT jwt = JWT.decode(accessToken);
            String commonname = jwt.getClaims().get("commonname").asString();
            String email = jwt.getClaims().get("email").asString();
            if (commonname != null && email != null) {
                User user = userService.createUser(commonname, email);
                UserAndRole userAndRole = new UserAndRole(commonname, email, user.getId());
                List<String> roles = userRepository.findAllRoleById(userAndRole.getId());
                List<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userAndRole, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                throw new AuthorizationException("User is not defined");
            }
        } catch (JWTDecodeException exception) {
            filterChain.doFilter(request, response);
        }
    }
}
