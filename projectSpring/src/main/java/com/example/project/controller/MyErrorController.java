package com.example.project.controller;

import org.apache.log4j.Logger;
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

    private static final Logger log = Logger.getLogger(MyErrorController.class);

    @Override
    public String getErrorPath() {
        return "/error";
    }

    /**
     * Error method if user input wrong url
     * @param request needed for getting current request
     * @return page like a string
     */
    @RequestMapping(value = "/error")
    public String errorPage(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if ((Integer.parseInt(status.toString()) == HttpStatus.FORBIDDEN.value()) && request.isUserInRole("ROLE_USER")){
            log.error("IT`S FORBIDDEN TO REDIRECT TO ADMIN PAGE");
            return "redirect:/users";
        }else if ((Integer.parseInt(status.toString()) == HttpStatus.FORBIDDEN.value())  && request.isUserInRole("ROLE_ADMIN")){
            log.error("IT`S FORBIDDEN TO REDIRECT TO USER PAGE");
            return "redirect:/admin";
        }
        return "error";
    }
}
