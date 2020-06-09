package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin/allusers")
public class ManageAdminController {

    private UserServiceImpl userServiceImpl;
    private static String oldName;
    @Autowired
    public ManageAdminController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public ModelAndView editUser(Model model){
        ModelAndView modelAndView = new ModelAndView();
        User user =  (User) model.asMap().get("user");
        System.out.println("ID USER = " + user.toString());
        oldName = user.getUserName();
        userServiceImpl.getLocale(model);
        model.addAttribute("user", user);
        modelAndView.setViewName("updateUser");
        return modelAndView;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String registration(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs){
        if (bindingResult.hasErrors()) {
            userServiceImpl.getLocale(model);
            model.addAttribute("upd",1);
            return "updateUser";
        }else if(userServiceImpl.existsUserByUserName(user.getUserName()) && !(oldName.equals(user.getUserName()))){
            user.setUserName(oldName);
            redirectAttrs.addFlashAttribute("upd",0);
            redirectAttrs.addFlashAttribute("user",user);
            redirectAttrs.addFlashAttribute("oldUserName",oldName);
            return"redirect:/admin/allusers/update";
        }else {

            redirectAttrs.addFlashAttribute("upd",1);
            System.out.println("UPDATE success = " + user.toString());
            userServiceImpl.updateUser(user,oldName);
            return "redirect:/admin/allusers";
        }

    }

    @Transactional
    @GetMapping("/delete")
    public String  deleteUser(User user){
        userServiceImpl.deleteUser(user);
        return "redirect:/admin/allusers";

    }

}
