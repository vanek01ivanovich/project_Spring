package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.service.implementation.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "")
public class RegistrationController {

    private static final Logger log = Logger.getLogger(RegistrationController.class);
    private UserServiceImpl userServiceImpl;

    @Autowired
    public RegistrationController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }


    /**
     * GET method
     * Registration page for anonymous users
     * @param model needed for setting attributes
     * @return registration page like a string
     */
    @RequestMapping(value = "/registration")
    public String reg(Model model){

        User user = new User();
        model.addAttribute("user",user);
        return "registration";
    }


    /**
     * POST method for registration method
     * @param user needed for create object user and add it to db
     * @param bindingResult needed for checking on wrogn user`s input
     * @param model needed for setting attributes
     * @param redirectAttrs needed for redirecting attributes
     * @return login string page if registration was successful
     */
    @RequestMapping(value = "/registration",method = RequestMethod.POST)
    public String mainPage(@Valid User user,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttrs){
        if (bindingResult.hasErrors()) {
            model.addAttribute("redir",0);
            model.addAttribute("alert", 1);
            log.error("ERROR REGEX REGISTRATION!");
            return "registration";
        }else{
            return registrationPostMethod(user, model, redirectAttrs);
        }
    }

    /**
     * Post method for registration realization
     * @param user needed for create object user and add it to db
     * @param model needed for setting attributes
     * @param redirectAttrs needed for redirecting attributes
     * @return login string page if registration was successful
     */
    private String registrationPostMethod(User user,Model model,RedirectAttributes redirectAttrs){
        if (userServiceImpl.addUser(user)){
            redirectAttrs.addFlashAttribute("alert",1);
            log.info("USER " + user.getUserName() + " HAS REGISTERED SUCCESSFULLY!");
            return "redirect:/login";
        }else {
            redirectAttrs.addFlashAttribute("alert", 0);
            model.addAttribute("alert", 0);
            log.error("ERROR SUCH USER IS ALREADY EXISTED!");
            return "registration";
        }
    }


}
