package com.example.project.controller;


import com.example.project.entity.User;
import com.example.project.service.implementation.TicketServiceImpl;
import com.example.project.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AllTicketsController {

    private TicketServiceImpl ticketServiceImpl;
    private UserServiceImpl userServiceImpl;

    @Autowired
    public AllTicketsController(TicketServiceImpl ticketServiceImpl,UserServiceImpl userServiceImpl){
        this.ticketServiceImpl = ticketServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @RequestMapping("/alltickets")
    public @ResponseBody
    ModelAndView getAllTickets(Model model){
        userServiceImpl.getLocale(model);
        List<User> allTickets = ticketServiceImpl.findAllTickets();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allTickets",allTickets);
        modelAndView.setViewName("all_tickets");
        return modelAndView;
    }

}
