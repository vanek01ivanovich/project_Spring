package com.example.project.controller;


import com.example.project.entity.DestinationProperty;
import com.example.project.entity.User;
import com.example.project.service.implementation.PageServiceImpl;
import com.example.project.service.implementation.TicketServiceImpl;
import com.example.project.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AllTicketsController {

    private TicketServiceImpl ticketServiceImpl;
    private UserServiceImpl userServiceImpl;
    private static Page<User> pages;
    private  PageServiceImpl pageService = new PageServiceImpl();

    @Autowired
    public AllTicketsController(TicketServiceImpl ticketServiceImpl,UserServiceImpl userServiceImpl){
        this.ticketServiceImpl = ticketServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * GET method for getting all users` tickets
     * Methods has pagination
     * @param page needed for pagination
     * @param pageable needed for pagination
     * @param modelAndView needed for setting attributes
     * @param model needed for setting attributes
     * @return modelAndView object
     */
    @RequestMapping(value = "/alltickets",method = RequestMethod.GET)
    public @ResponseBody ModelAndView getAllTickets(@RequestParam(name = "page",required = false) String page,
                                                    @PageableDefault(size = 3) Pageable pageable,
                                                    ModelAndView modelAndView,Model model){

        userServiceImpl.getLocale(model);
        List<User> allTickets = ticketServiceImpl.findAllTickets();
        pages = (Page<User>) pageService.pagination(page,pageable,allTickets);
        modelAndView.addObject("page",pages);
        modelAndView.setViewName("all_tickets");
        return modelAndView;

    }
}
