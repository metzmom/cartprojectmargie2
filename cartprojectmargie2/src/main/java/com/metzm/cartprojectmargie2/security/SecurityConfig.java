package com.metzm.cartprojectmargie2.security;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Service;

@Service
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .authorizeRequests()

               .antMatchers("/**").hasAnyRole("USER")//allows only home page
               .antMatchers("/").permitAll();//allows everyone everywhere

    }
}