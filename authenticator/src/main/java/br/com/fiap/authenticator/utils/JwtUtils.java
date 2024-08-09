package br.com.fiap.authenticator.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "+qCnj4esV0sX+pBpsrHSI3Imy6WIcNeEn79KdP/P76w=";

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)

                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()
                        + 120000)) // 2 minutos
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


}
