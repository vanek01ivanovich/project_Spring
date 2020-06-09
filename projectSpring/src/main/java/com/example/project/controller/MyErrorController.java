package com.example.project.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error")
    public String errorPage(HttpServletRequest request){
        System.out.println(request.isUserInRole("ROLE_USER"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if ((Integer.parseInt(status.toString()) == HttpStatus.FORBIDDEN.value()) && request.isUserInRole("ROLE_USER")){
            return "redirect:/users";
        }else if ((Integer.parseInt(status.toString()) == HttpStatus.FORBIDDEN.value())  && request.isUserInRole("ROLE_ADMIN")){
            return "redirect:/admin";
        }
        return "error";
    }
}
