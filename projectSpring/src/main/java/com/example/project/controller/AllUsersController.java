package com.example.project.controller;


import com.example.project.entity.User;
import com.example.project.service.UserDetailsImpl;
import com.example.project.service.implementation.PageServiceImpl;
import com.example.project.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private UserDetailsImpl user;
    private static Page<User> pages;
    private  PageServiceImpl pageService = new PageServiceImpl();

    @Autowired
    public AllUsersController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * GET method for getting all users
     * Method has pagination
     * @param model needed for setting attributes
     * @return modelAndView object
     */
    @RequestMapping(value = "/allusers",method = RequestMethod.GET)
    public ModelAndView lookAllUsers(@RequestParam(name = "page",required = false) String page,
                                     @PageableDefault(size = 10) Pageable pageable,
                                     ModelAndView modelAndView,Model model){

        userServiceImpl.getLocale(model);
        user = userServiceImpl.getUserContext();
        allUsers = userServiceImpl.getAllUsers();
        allUsers.removeIf(u -> u.getUserName().equals(user.getUsername()));
        allUsers.removeIf(u -> u.getUserName().equals("admin01"));
        pages = (Page<User>) pageService.pagination(page,pageable,allUsers);
        modelAndView.addObject("page",pages);
        modelAndView.setViewName("all_users");
        return modelAndView;
    }

    /**
     * POST method for allUsers page
     * Method can choose the user that you need
     * @param id needed for choosing user from list of all users
     * @param redirectAttributes needed for setting redirecting attributes
     * @param model needed for setting attributes
     * @return modelAndView object
     */
    @RequestMapping(value = "/allusers",method = RequestMethod.POST)
    public ModelAndView lookAllUsers(@RequestParam(name = "id") String id,RedirectAttributes redirectAttributes,Model model){
        User user = userServiceImpl.getCurrentUser(allUsers,Integer.parseInt(id));
        userServiceImpl.getLocale(model);
        redirectAttributes.addFlashAttribute("user",user);
        return new ModelAndView("redirect:/admin/allusers/update");
    }

}
