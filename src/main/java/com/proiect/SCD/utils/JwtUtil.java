package com.proiect.SCD.utils;

import com.proiect.SCD.domain.AppUser;
import com.proiect.SCD.domain.AppUserRole;
import com.proiect.SCD.dto.AppUserDto;
import com.proiect.SCD.exception.AppException;
import com.proiect.SCD.service.AppUserService;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
public class JwtUtil {

    private String secret_key = "secret";

    public String extractEmail(Claims claims) {
        return claims.get("email").toString();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Date extractCreationDate(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public Integer extractId(Claims claims) {
        return Integer.parseInt(claims.get("id").toString());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Boolean isJwtNull(String token) throws AppException {
        if (token != null) {
            return true;
        } else {
            log.error("Jwt is null!");
            throw new AppException(HttpStatus.UNAUTHORIZED, "Jwt is null!");
        }
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException
                | UnsupportedJwtException
                | MalformedJwtException
                | SignatureException
                | IllegalArgumentException e) {
            throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid Jwt");
        }
    }

    private Boolean isTokenExpired(String token) throws HttpServerErrorException {
        if (!extractExpiration(token).before(new Date())) {
            return false;
        } else {
            log.error("Token is expired!");
            throw new AppException(HttpStatus.UNAUTHORIZED, "Token is expired!");
        }
    }

    public String createToken(Map<String, Object> claims) {
        long timeUntillExpiration = 1000 * 60 * 60 * 10; // 10hours
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + timeUntillExpiration))
                .signWith(SignatureAlgorithm.HS384, secret_key)
                .compact();
    }

    public Boolean validateEmail(String email, Claims claims) throws HttpServerErrorException {
        if (this.extractEmail(claims).equals(email)) {
            return true;
        } else {
            log.error("Invalid email!");
            throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid email!");
        }
    }


    private Boolean validateClaims(Claims claims, AppUser appUser) throws AppException {
        Boolean validateEmail = this.validateEmail(appUser.getEmail(), claims);
        return true;
    }

    public Boolean validateToken(String token, AppUser appUser) throws HttpServerErrorException {
        try {
            Claims claims =
                    Jwts.parser()
                            .setSigningKey(DatatypeConverter.parseBase64Binary(secret_key))
                            .parseClaimsJws(token)
                            .getBody();
            validateClaims(claims, appUser);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
