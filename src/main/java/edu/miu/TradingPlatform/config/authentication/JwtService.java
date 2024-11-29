package edu.miu.TradingPlatform.config.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jjt.secreteKey}")
    private String SECRETE_KEY;
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .issuedAt(new Date())
                .issuer("edu.miu.cs489.trading_app")
                .expiration(new Date(System.currentTimeMillis()+ 60 * 60 * 24 * 1000))
                .subject(userDetails.getUsername())
                .signWith(signKey())
                .compact();
    }

    private SecretKey signKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRETE_KEY));
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(signKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

  public String getEmailFromJwtToken(String jwtToken) {

    if (jwtToken.startsWith("Bearer ")) {
      jwtToken = jwtToken.substring(7);
    }
    Claims claims = getClaims(jwtToken);
    String email = claims.getSubject();
    return email;
  }
}
