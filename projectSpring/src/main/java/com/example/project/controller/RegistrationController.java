package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.service.implementation.UserServiceImpl;
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

    private UserServiceImpl userServiceImpl;

    @Autowired
    public RegistrationController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }


    @RequestMapping(value = "/registration")
    public String reg(Model model){

        User user = new User();
        model.addAttribute("user",user);
        return "registration";
    }


    @RequestMapping(value = "/registration",method = RequestMethod.POST)
    public String mainPage(@Valid User user,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttrs){
        if (bindingResult.hasErrors()) {
            model.addAttribute("redir",0);
            model.addAttribute("alert", 1);
            return "registration";
        }else if (userServiceImpl.addUser(user)){
            redirectAttrs.addFlashAttribute("alert",1);
            return "redirect:/login";
        }else {
          /*  model.addAttribute("user",user);*/
            redirectAttrs.addFlashAttribute("alert", 0);
            model.addAttribute("alert", 0);
            return "registration";
        }
    }


}
