package com.example.demo.security;

import com.example.demo.Constant;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private String secretKey;
    private int exp = Constant.JWT_EXP;
    private long exp_rem = Constant.JWT_EXP_REMEMBER_ME;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(Constant.JWT_SECRET.getBytes());
    }

    public String createToken(String username,boolean rememberme) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        long eexxpp = 0;
        if (rememberme == true)
            eexxpp += exp_rem;
        else
            eexxpp += exp * 1000;
        Date validity = new Date((now.getTime() + eexxpp));

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername((token)));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(Constant.HEADER_AUTH);
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7, bearer.length());
        }
        return null;
    }
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
