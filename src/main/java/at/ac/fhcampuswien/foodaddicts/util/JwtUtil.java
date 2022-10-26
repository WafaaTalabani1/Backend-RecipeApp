package at.ac.fhcampuswien.foodaddicts.util;

import at.ac.fhcampuswien.foodaddicts.model.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtUtil {

    public static final long DEFAULT_JWT_TOKEN_VALIDITY = 60 * 60 * 24;

    @Value("${jwt.secret.key}")
    private String jwtSecret;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUserId(String token) {
        return extractClaim(token, claim -> (String) claim.get("userId"));
    }

    public String extractEmail(String token) {
        return extractClaim(token, claim -> (String) claim.get("email"));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(AppUser user) {
        return doGenerateToken(user);
    }

    public GrantedAuthority jwtClaimsToGrantedAuthority(String token) {
        String role = extractClaim(token, claim -> (String) claim.get("role"));
        return new SimpleGrantedAuthority(role);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String doGenerateToken(AppUser user) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder().setSubject(user.getUsername())
                .claim("role", user.getAppUserRole())
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtUtil.DEFAULT_JWT_TOKEN_VALIDITY * 1000))
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public Boolean isTokenValid(String token) {
        try {
            // if this line does not throw an exception, the signature is valid
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (Exception exception) {
            //log.trace("received invalid token: {}", token, exception);
            return false;
        }
    }
}
