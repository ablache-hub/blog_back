package com.alex.blog_back.jwt;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
@NoArgsConstructor
@Getter
@Setter
public class JwtConfig {

    private  String secretKey;
    private  String tokenPrefix;
    private  int tokenDateExpirationDays;

    // Cryptage SecretKey
    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Envoyer le type de Header en response au client
    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
