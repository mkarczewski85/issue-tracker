package pl.wsb.issuetracker.authentication.component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.authentication.model.UserAuthentication;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JWTUtilityComponent implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String ID_KEY = "id";
    private static final String FIRST_NAME_KEY = "first_name";
    private static final String LAST_NAME_KEY = "last_name";

    @Value("${jwt.base64-secret}")
    private String base64Secret;
    @Value("${jwt.expiration-in-seconds}")
    private long tokenValidityInSeconds;

    private SecretKey jwtSecretKey;
    private JwtParser jwtParser;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.jwtSecretKey = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parser().verifyWith(jwtSecretKey).build();
    }

    public String createJWT(final UserAuthentication authentication) {
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = System.currentTimeMillis();
        final Date validity = new Date(now + this.tokenValidityInSeconds * 1000);
        return Jwts.builder()
                .subject(authentication.getName())
                .claim(ID_KEY, authentication.getId())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(FIRST_NAME_KEY, authentication.getFirstName())
                .claim(LAST_NAME_KEY, authentication.getLastName())
                .signWith(jwtSecretKey)
                .expiration(validity)
                .compact();
    }

    public Authentication getAuthentication(final String token) {
        final Claims claims = jwtParser.parseSignedClaims(token).getPayload();
        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        final User principal = new User(claims.getSubject(), "", authorities);
        final UserAuthentication authentication = new UserAuthentication(principal, token, authorities);
        final String id = claims.get(ID_KEY).toString();
        final String firstName = claims.get(FIRST_NAME_KEY).toString();
        final String lastName = claims.get(LAST_NAME_KEY).toString();
        authentication.setId(id);
        authentication.setFirstName(firstName);
        authentication.setLastName(lastName);
        return authentication;
    }

    public boolean isTokenValid(final String authToken) {
        if (authToken.isEmpty()) {
            return false;
        }
        try {
            jwtParser.parse(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException | io.jsonwebtoken.security.SignatureException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return false;
    }

    public Long getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }

}
