package pl.bilskik.lab34.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final static String SECRET= "566D597133743677397A24432646294A404E635166546A576E5A723475377821";

    public String removeBearerStartFromJwt(String authHeader) {
        return authHeader.substring(7);
    }

    public String extractUsername(String jwt) {
        return Jwts.parser()
                        .verifyWith(getSecretKey())
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload()
                        .getSubject();
    }
    private SecretKey getSecretKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(SECRET),
                SignatureAlgorithm.HS256.getJcaName());
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    public String generateJWT(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .signWith(getSecretKey())
                .expiration(Date.from(ZonedDateTime.now().plusMinutes(15).toInstant()))
                .compact();
    }
    private boolean isTokenExpired(String jwt) {
        Date date = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getExpiration();
        Date currDate = Date.from(ZonedDateTime.now().toInstant());
        return !currDate.before(date);
    }

}
