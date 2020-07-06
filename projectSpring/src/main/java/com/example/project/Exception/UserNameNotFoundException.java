package com.example.project.Exception;

import com.example.project.configSecurity.MyUserDetailsService;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserNameNotFoundException extends UsernameNotFoundException {
    private static final Logger log = Logger.getLogger(MyUserDetailsService.class);

    /**
     * Custom exception if login is incorrect
     * @param errorMessage needed for message error
     * @param userName needed for understand what login is incorrect
     */
    public UserNameNotFoundException(String errorMessage,String userName) {
        super(errorMessage);
        log.error("ERROR LOGIN WITH [ userName: " + userName + " ]");
    }
}

