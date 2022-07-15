package com.bksoftwarevn.auction.security.jwt;

import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.constant.ClaimConstant;
import com.bksoftwarevn.auction.model.UserDetailCustomize;
import com.bksoftwarevn.auction.security.config.JwtConfiguration;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JWTProvider {


    private final long tokenValidityInMilliseconds;
    private final long tokenValidityInMillisecondsForRememberMe;

    private final Key secretKey;
    private final JwtParser jwtParser;

    public JWTProvider(JwtConfiguration jwtConfiguration) {
        byte[] keyBytes;
        String secret = jwtConfiguration.getBase64Secret();
        if (!ObjectUtils.isEmpty(secret)) {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Base64Utils.decodeFromString(secret);
        } else {
            log.warn("Warning: the JWT key used is not Base64-encoded. We recommend using the `security.jwt.base64-secret` key for optimum security.");
            secret = jwtConfiguration.getSecret();
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        secretKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.RS512.getJcaName());
        jwtParser = Jwts.parser().setSigningKey(secretKey);
        this.tokenValidityInMilliseconds = 1000 * jwtConfiguration.getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe = 1000 * jwtConfiguration.getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = System.currentTimeMillis();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        UserDetailCustomize user = null;
        if (authentication.getPrincipal() instanceof UserDetailCustomize) {
            user = (UserDetailCustomize) authentication.getPrincipal();
        }

        JwtBuilder jwtBuilder = Jwts
                .builder()
                .setIssuedAt(new Date(now))
                .setHeaderParam("typ", "JWT")
                .claim(ClaimConstant.AUTHORITIES_KEY, authorities)
                .setExpiration(validity);

        if (user != null) {
            jwtBuilder
                    .setSubject(user.getId())
                    .claim(ClaimConstant.USER_ID_KEY, user.getId())
                    .claim(ClaimConstant.USERNAME, user.getUsername())
                    .claim(ClaimConstant.ROLE, user.getRoles())
                    .claim(ClaimConstant.AVATAR, user.getAvatar())
                    .claim(ClaimConstant.PHONE, user.getPhone())
                    .claim(ClaimConstant.TYPE, "Bearer")
                    .claim(ClaimConstant.EMAIL_KEY, user.getEmail());
        }
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        return jwtBuilder.compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(ClaimConstant.AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetailCustomize principal = new UserDetailCustomize(claims.getSubject(), "", authorities,
                String.valueOf(claims.get(ClaimConstant.USER_ID_KEY)), String.valueOf(claims.get(ClaimConstant.EMAIL_KEY)));

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        boolean result = Boolean.FALSE;
        try {
            jwtParser.parseClaimsJws(authToken);
            result = Boolean.TRUE;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException e) {
            log.error(AucMessage.INVALID_JWT_TOKEN.getMessage(), e);
        } catch (Exception e) {
            log.error("Token validation error {}", e.getMessage());
        }

        return result;
    }


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, userDetails.getUsername());
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.tokenValidityInMilliseconds)).signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
