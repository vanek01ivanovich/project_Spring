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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AllUsersController {


    private UserServiceImpl userServiceImpl;
    private static List<User> allUsers;

    @Autowired
    public AllUsersController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @RequestMapping(value = "/allusers",method = RequestMethod.GET)
    public ModelAndView lookAllUsers(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        userServiceImpl.getLocale(model);
        allUsers = userServiceImpl.getAllUsers();
        allUsers.removeIf(u -> u.getUserName().equals(user.getUsername()));
        allUsers.removeIf(u -> u.getUserName().equals("admin01"));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allUsers",allUsers);
        modelAndView.setViewName("all_users");
        return modelAndView;
    }

    @RequestMapping(value = "/allusers",method = RequestMethod.POST)
    public ModelAndView lookAllUsers(@RequestParam(name = "id") String id,RedirectAttributes redirectAttributes,Model model){
        User user = userServiceImpl.getCurrentUser(allUsers,Integer.parseInt(id));
        userServiceImpl.getLocale(model);
        redirectAttributes.addFlashAttribute("user",user);
        return new ModelAndView("redirect:/admin/allusers/update");
    }

}
