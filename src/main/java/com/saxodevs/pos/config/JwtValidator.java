package com.saxodevs.pos.config;

import java.util.List;

import javax.crypto.SecretKey;

import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, java.io.IOException {

        String jwt = request.getHeader(ConstantSecurity.JWT_HEADER);

        if (jwt != null) {
            jwt = jwt.substring(7);

            try {
                SecretKey key = Keys.hmacShaKeyFor(ConstantSecurity.JWT_SECRET.getBytes());

                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
                //
                String username = String.valueOf(claims.get("username"));

                String authorities = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, auths);

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid JWT Token....");
            }
        }

        filterChain.doFilter(request, response);

    }

}