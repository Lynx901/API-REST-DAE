/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.service;

import static javafx.scene.input.KeyCode.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author juanf
 */

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SeguridadService extends  WebSecurityConfigurerAdapter {
    @Autowired
    SistemaService sistemaService;
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((T) sistemaService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests().antMatchers("/**").hasRole("USUARIO");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/**").hasRole("ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN");
      }
         
}
