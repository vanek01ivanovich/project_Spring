package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.service.implementation.UserServiceImpl;
import org.apache.log4j.Logger;
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

    private static final Logger log = Logger.getLogger(ManageAdminController.class);
    private UserServiceImpl userServiceImpl;
    private static String oldName;
    private static  User user;

    @Autowired
    public ManageAdminController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * GET method for updating user
     * Get parameter user from url /admin/allusers
     * @param model needed for setting attributes
     * @return modelAndView object
     */
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public ModelAndView editUser(Model model){
        ModelAndView modelAndView = new ModelAndView();
        user =  (User) (model.asMap().get("user") == null ? user:model.asMap().get("user"));
        oldName = user.getUserName();
        userServiceImpl.getLocale(model);
        model.addAttribute("user", user);
        modelAndView.setViewName("updateUser");
        return modelAndView;
    }

    /**
     * POST method for updating user
     * Method has checking on errors
     * @param user needed for previous info about user
     * @param bindingResult needed for checking regex errors in user`s input
     * @param model needed for setting attributes
     * @param redirectAttrs needed for redirecting attributes
     * @return page like a string if updating was successfully
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String registration(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs){
        if (bindingResult.hasErrors()) {
            userServiceImpl.getLocale(model);
            model.addAttribute("upd",1);
            log.error("USER " + oldName + " CAN`T BE UPDATED (HAS REGEX ERRORS)!");
            return "updateUser";
        }else{
            return updatePostMethod(user,redirectAttrs);
        }

    }

    /**
     * Delete User method
     * @param user needed for deleting user info from db
     * @return page like a string
     */
    @Transactional
    @GetMapping("/delete")
    public String  deleteUser(User user){
        userServiceImpl.deleteUser(user);
        log.info("USER " + user.getUserName() + " HAS BEEN DELETED SUCCESSFULLY!");
        return "redirect:/admin/allusers";

    }

    /**
     * Post method for updating
     * Method has realization of post method
     * @param user needed for updating user in db
     * @param redirectAttrs needed for redirecting attributes
     * @return page like a string
     */
    private String updatePostMethod(User user, RedirectAttributes redirectAttrs){
        if(userServiceImpl.existsUserByUserName(user.getUserName()) && !(oldName.equals(user.getUserName()))){
            user.setUserName(oldName);
            redirectAttrs.addFlashAttribute("upd",0);
            redirectAttrs.addFlashAttribute("user",user);
            redirectAttrs.addFlashAttribute("oldUserName",oldName);
            log.error("USER " + oldName + " CAN`T BE UPDATED (HAS ERRORS)!");
            return"redirect:/admin/allusers/update";
        }else {
            redirectAttrs.addFlashAttribute("upd",1);
            userServiceImpl.updateUser(user,oldName);
            log.info("USER " + oldName + " HAS BEEN UPDATED SUCCESSFULLY!");
            return "redirect:/admin/allusers";
        }
    }
}