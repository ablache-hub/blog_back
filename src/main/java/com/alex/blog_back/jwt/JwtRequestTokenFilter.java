package com.alex.blog_back.jwt;

import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JwtRequestTokenFilter extends OncePerRequestFilter {
    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Récupération du Token qui est dans le header de la request envoyée par le client grâce au getAuthorizationHeader() qui comprend AUTHORIZATION pour savoir quel truc prendre dans le header
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Si le token est null/vide OU si le prefix du token ne commence pas par "Bearer " ALORS bloque la request
        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            throw new ServletException(String.format("Le token %s n'est pas bon", authorizationHeader));
        }

        // Récupération du token seul sans "Bearer "
        String tokenAlone = authorizationHeader.replace("Bearer ", "");

        try{
            // Extraction / Analyse du token récupéré. "Tu me redécodes mon token" Alex.2021
            Jws<Claims> tokenExtract = Jwts.parserBuilder()
                                        .setSigningKey(jwtConfig.getSecretKey())
                                        .build()
                                        .parseClaimsJws(tokenAlone);

            // Récupération du body du token
            Claims bodyToken = tokenExtract.getBody();

            // Récupération du subject du token qui est intégré dans le body du token (d'ou le bodyToken.getSubject())
            String usernameToken = bodyToken.getSubject();

            // On récupère les authorities liées a l'user dans le Token (format spéciale vu que c'est une List clé:valeur dans une variable)
            var authorities = (List<Map<String, String>>) bodyToken.get("authorities"); // "authorities" correspond à la liste qui comprend nos permissions dans le token

            // Notre UsernamePasswordAuthenticationToken attend une collection de grantedAuthorities donc on doit map() sur notre va authorities pour avoir une vraie liste de SimpleGrantedAuthority au lieu d'une liste simple
            Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = authorities.stream()
                    .map(item -> new SimpleGrantedAuthority(item.get("authority"))) //
                    .collect(Collectors.toSet());

            // On recréer une tentative d'auth avec le username/password/permissions de l'user
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    usernameToken,
                    null,
                    simpleGrantedAuthoritySet
            );

            // On envoie notre user validé/authentifié à notre SecurityContext pour qu'il gère les autorisation/uri qu'a le user
            SecurityContextHolder.getContext().setAuthentication(authentication);


        } catch (JwtException exception){
            throw new IllegalStateException(String.format("Le token %s n'est pas valide", tokenAlone));
        }

        // On renvoie la request/response au prochain filtre ou à l'API si c'est le dernier le filtre afin que la requête soit exécutée et que le résultat s'affiche
        filterChain.doFilter(request, response);
    }
}
