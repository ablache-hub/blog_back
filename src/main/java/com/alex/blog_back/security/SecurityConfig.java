package com.alex.blog_back.security;

import com.alex.blog_back.jwt.JwtAuthFilter;
import com.alex.blog_back.jwt.JwtConfig;
import com.alex.blog_back.jwt.JwtRequestTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //Permet l'utilisation de PreAuthorize dans le controller
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //TODO Tester avec cette variable plutôt que le bean
//    private final AuthenticationManager authenticationManager;
//    private final UserDetailsService userDetailsService;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtConfig jwtConfig;


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilter(new JwtAuthFilter(authenticationManager(), jwtConfig))
                .addFilterAfter(new JwtRequestTokenFilter(jwtConfig), JwtAuthFilter.class)
                .authorizeRequests()
//              .antMatchers(/*"/", "/index",*/ "/registration").permitAll().
                .anyRequest()
                .authenticated();
        http.cors().configurationSource(corsConfigurationSource());
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> allowOrigins = Arrays.asList("*");
        configuration.setAllowedOriginPatterns(allowOrigins);
        configuration.setAllowedMethods(singletonList("*"));
        configuration.setAllowedHeaders(singletonList("*"));
        configuration.setExposedHeaders(List.of("Authorization", "Role", "Username"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/api/user/subscribe",
                "/error",
                "/api/role/save",
                "/api/role/addroleuser",
                "/article/get/**",
                "/api/user/get/**",
                "/api/categorie/**",
                "/file/**");
    }
}
