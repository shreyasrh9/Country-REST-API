package com.itgma.shreyas.technicaltest;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfigurationBasicAuth extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .authorizeRequests().
        antMatchers(HttpMethod.OPTIONS,"/**", "/console/**").
        permitAll().anyRequest().authenticated()
        .and()
        .httpBasic();
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	
}