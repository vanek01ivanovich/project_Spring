package com.example.project.controller;


import com.example.project.entity.User;
import com.example.project.service.UserDetailsImpl;
import com.example.project.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    public UserServiceImpl userServiceImpl;

    @GetMapping("/")
    public String mainPage(Principal principal){

        return "guestPage";
    }




    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public String users(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        userServiceImpl.getLocale(model);
        model.addAttribute("user",user);
        return "user";
    }

    @RequestMapping(value = "/users",method = RequestMethod.POST)
    public String users(@Valid User userInfo,BindingResult bindingResult,Model model,RedirectAttributes redirectAttrs){
        if (bindingResult.hasErrors()) {
            redirectAttrs.addFlashAttribute("alert",false);
        }else{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            redirectAttrs.addFlashAttribute("alert",userServiceImpl.topUpMoney(user,userInfo));
        }
        userServiceImpl.getLocale(model);
        return "redirect:/users";
    }



    @GetMapping("/admin")
    public String admin(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        userServiceImpl.getLocale(model);
        model.addAttribute("user",user);

        return "admin";
    }

    @GetMapping("/login")
    public String getLogin(@RequestParam(value = "error",required = false) String error,
                           @RequestParam(value = "logout",required = false) String logout,
                           Model model){


        String userNameLogOut = (String)model.asMap().get("userName");
        if (userNameLogOut != null){
            model.addAttribute("userNameLogOut",userNameLogOut);
        }


        model.addAttribute("error",error != null);
        model.addAttribute("logout",logout != null);
        return "login";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logoutPage(){
        return "redirect:/login?logout";
    }




}
