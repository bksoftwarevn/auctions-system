package com.bksoftwarevn.auction.security.config;

import com.bksoftwarevn.auction.security.authorization.AuthoritiesConstants;
import com.bksoftwarevn.auction.security.jwt.AuthJWTEntryPoint;
import com.bksoftwarevn.auction.security.jwt.JWTConfigurer;
import com.bksoftwarevn.auction.security.jwt.JWTProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

    private final AuthJWTEntryPoint jwtEntryPoint;
    private final CorsFilter corsFilter;
    private final JwtConfiguration jwtConfiguration;
    private final JWTProvider jwtProvider;

    public SecurityConfiguration(JWTProvider tokenProvider, CorsFilter corsFilter, JwtConfiguration jwtConfiguration, AuthJWTEntryPoint jwtEntryPoint) {
        this.jwtProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtEntryPoint = jwtEntryPoint;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .accessDeniedHandler(jwtEntryPoint)
                .and()
                .headers()
                .contentSecurityPolicy(jwtConfiguration.getContentSecurityPolicy())
                .and()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .permissionsPolicy().policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")
                .and()
                .frameOptions()
                .deny()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // guest
                .antMatchers("/images/**", "/js/**", "/swagger-ui/**", "/docs.html", "/docs.json","/docs/**", "/test/**", "/templates/**", "/check").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                //admin
                .antMatchers("/api/admin/**", "/api/role/**", "/api/**/management/**").hasAuthority(AuthoritiesConstants.ROLE_ADMIN.name())
                //user
                .anyRequest().authenticated()
                .and().httpBasic()
                .and()
                .apply(securityConfigurerAdapter())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(jwtProvider);
    }


}
