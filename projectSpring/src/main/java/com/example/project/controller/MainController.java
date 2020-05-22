package com.example.project.controller;


import com.example.project.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(){
        return "guestPage";
    }

    @GetMapping("/users")
    public String users(Model model) {
        return "user";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
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

    @RequestMapping(value = "/logoutt",method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
       /* Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails user = (UsersDetails) auth.getPrincipal();

        redirectAttributes.addFlashAttribute("username",user.getUsername());
*/

        return "redirect:/login?logout";
    }




}
