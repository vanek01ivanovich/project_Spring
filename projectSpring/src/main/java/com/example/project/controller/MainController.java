package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.service.UserDetailsImpl;
import com.example.project.service.implementation.UserServiceImpl;
import org.apache.log4j.Logger;
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

    private static final Logger log = Logger.getLogger(MainController.class);
    private UserDetailsImpl user;

    @Autowired
    public UserServiceImpl userServiceImpl;

    /**
     * Method for guestPage
     * @return page like string for guest or anonymous users
     */
    @GetMapping("/")
    public String mainPage(){
        return "guestPage";
    }

    /**
     * Method that return page for authenticated users
     * @param model needed for setting attributes
     * @return page like string for authenticated users
     */
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public String users(Model model) {
        user = userServiceImpl.getUserContext();
        userServiceImpl.getLocale(model);
        model.addAttribute("user",user);
        return "user";
    }

    /**
     * Post method for top up user`s money
     * @param userInfo needed for getting user`s info
     * @param bindingResult needed for checking user`s input in top up form
     * @param model needed for setting attributes
     * @param redirectAttrs needed for setting redirected attributes
     *                      such as message for successful or wrong operation
     * @return page like string for authenticated users
     */
    @RequestMapping(value = "/users",method = RequestMethod.POST)
    public String users(@Valid User userInfo,BindingResult bindingResult,Model model,RedirectAttributes redirectAttrs){

        user = userServiceImpl.getUserContext();
        if (bindingResult.hasErrors()) {
            redirectAttrs.addFlashAttribute("alert",false);
            log.error("USER " + user.getUsername() + " can`t top up money,problem with user properties!");
        }else{
            redirectAttrs.addFlashAttribute("alert",userServiceImpl.topUpMoney(user,userInfo));
            log.info("USER " + user.getUsername() + " has topped up " +  userInfo.getMoney() + " money successfully!");
        }
        userServiceImpl.getLocale(model);
        return "redirect:/users";
    }

    /**
     * Method for adminPage
     * @param model needed for setting attributes
     * @return page like string for admin
     */
    @GetMapping("/admin")
    public String admin(Model model) {
        user = userServiceImpl.getUserContext();
        userServiceImpl.getLocale(model);
        model.addAttribute("user",user);
        return "admin";
    }

    /**
     * Method that return loginPage for anonymous users
     * @param error needed for message if user input wrong info
     * @param logout needed for message if authenticated user has logged out
     * @param model needed for setting attributes
     * @return page like string for login page
     */
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

    /**
     * Method return logout page
     * @return page like string for logout page
     */
    @RequestMapping(value = "/logout")
    public String logoutPage(){
        user = userServiceImpl.getUserContext();
        log.info("USER " + user.getUsername() + " has logged out successfully");
        return "redirect:/login?logout";
    }

}
