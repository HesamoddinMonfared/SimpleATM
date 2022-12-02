package com.sampledomain.bank.security;

import com.sampledomain.bank.security.jwt.AuthEntryPointJwt;
import com.sampledomain.bank.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig   {
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/V1/banks/cards/enterCard/**").permitAll()
                .antMatchers("/api/V1/banks/cards/loginByCardNumberAndPinCode/**").permitAll()
                .antMatchers("/api/V1/banks/cards/loginByCardNumberAndFingerprint/**").permitAll()
                .antMatchers("/api/V1/banks/cards/exitCard/**").authenticated()
                .antMatchers("/api/V1/banks/cards/balance/**").authenticated()
                .antMatchers("/api/V1/banks/cards/deposit/**").authenticated()
                .antMatchers("/api/V1/banks/cards/withdrawal/**").authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
