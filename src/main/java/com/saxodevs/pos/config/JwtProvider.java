package com.saxodevs.pos.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class  JwtProvider {

    static SecretKey key = Keys.hmacShaKeyFor(ConstantSecurity.JWT_SECRET.getBytes());

    public String generateToken(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = populateAuthorities(authorities);

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 86400000))
                .claim("username", authentication.getName())
                .claim("authorities", role)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String jwt) {
        jwt = jwt.substring(7);

        Claims claims = Jwts.parser()
                .verifyWith(key).build().parseSignedClaims(jwt)
                .getPayload();

        return String.valueOf(claims.get("username"));
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {

        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }

        return String.join(",", auths);

    }

}
