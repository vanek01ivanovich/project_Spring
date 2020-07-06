package com.example.project.configSecurity;

import com.example.project.controller.MainController;
import com.example.project.service.UserDetailsImpl;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Service
public class AuthenticationConfig implements AuthenticationSuccessHandler{
    private static final Logger log = Logger.getLogger(AuthenticationConfig.class);


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        if (roles.contains("ROLE_USER")){
            log.info("USER " + user.getUsername() + " has logged in successfully");
            httpServletResponse.sendRedirect("/users");
        }else {
            log.info("ADMIN " + user.getUsername() + " has logged in successfully");
            httpServletResponse.sendRedirect("/admin");
        }
    }
}
