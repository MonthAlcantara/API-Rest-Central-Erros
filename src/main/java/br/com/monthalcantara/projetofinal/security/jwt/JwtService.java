package br.com.monthalcantara.projetofinal.security.jwt;

import br.com.monthalcantara.projetofinal.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Profile({"prod","test"})
public class JwtService {
    @Value("${security.jwt.expiration}")
    private String expiration;
    @Value("${security.jwt.key-signature}")
    private String signatureKey;

    public String createToken(Usuario usuario) {
        long expirationInMinutes = Long.valueOf(expiration);
        LocalDateTime dateHourExpiration = LocalDateTime.now().plusMinutes(expirationInMinutes);
        Instant instant = dateHourExpiration.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, signatureKey)
                .compact();
    }

    public Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isValid(String token) throws ExpiredJwtException {
        try {
            Claims claims = getClaims(token);
            Date date = claims.getExpiration();
            LocalDateTime localDateTime = date
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            return !LocalDateTime.now().isAfter(localDateTime);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserLogin(String token) throws ExpiredJwtException {
        return (String) getClaims(token).getSubject();
    }
}
