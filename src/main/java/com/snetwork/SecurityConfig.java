package com.snetwork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private WebSecurityConfig webSecurityConfig;

}
