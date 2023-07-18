package com.prodyna.person.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

// userName and password should be saved in db not in memory db
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    private final AuthenticationEntryPoint authEntryPoint;

    public SecurityConfiguration(@Qualifier("delegatedAuthenticationEntryPoint") AuthenticationEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITE_LIST).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint);
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User
                .withUsername("admin")
                .password("$2a$12$KLVlMaNQ.WZYVFIZ1VbVzOp0F617E791DClMAvDWgq5zhEXT5uL46")//admin
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
