package com.example.project.controller;

import com.example.project.entity.DestinationProperty;
import com.example.project.service.UserDetailsImpl;
import com.example.project.service.implementation.TicketServiceImpl;
import com.example.project.service.implementation.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping(value = {"/users/getroute","/admin/getroute"})
public class TicketController {

    private static final Logger log = Logger.getLogger(TicketController.class);

    private TicketServiceImpl ticketServiceImpl;
    private static DestinationProperty ticket;
    private UserServiceImpl userServiceImpl;
    private UserDetailsImpl user;

    @Autowired
    public TicketController(TicketServiceImpl ticketServiceImpl,UserServiceImpl userServiceImpl){
        this.ticketServiceImpl = ticketServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }


    /**
     * GET method for ticket page
     * Method gets parameter ticket
     * @param model needed for setting attributes
     * @return modelAndView object page
     */
    @RequestMapping(value = "/ticket",method = RequestMethod.GET)
    public ModelAndView ticket(Model model){
        ModelAndView modelAndView = new ModelAndView();
        user = userServiceImpl.getUserContext();
        userServiceImpl.getLocale(model);

        if (model.asMap().get("ticket") != null){
            ticket = (DestinationProperty) model.asMap().get("ticket");
        }

        modelAndView.addObject("user",user);
        modelAndView.addObject("ticket",ticket);
        modelAndView.setViewName("ticket");
        return modelAndView;
    }

    /**
     * POST method for ticket page
     * realization of transaction in ticket
     * @param idProperty needed for setting ticket in db
     * @param redirectAttrs needed for redirecting attributes
     * @param model needed for setting attributes
     * @return modelAndView object page
     */
    @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    public ModelAndView ticket(@RequestParam(name = "idProperty") String idProperty,RedirectAttributes redirectAttrs,Model model){
        userServiceImpl.getLocale(model);
        if(ticketServiceImpl.addTicket(ticket)){
            redirectAttrs.addFlashAttribute("alert",true);
            return new ModelAndView("redirect:/users/findroute");
        }else{
            redirectAttrs.addFlashAttribute("ticket",ticket);
            redirectAttrs.addFlashAttribute("alert",false);
            log.error("USER " + user.getUsername() + "CAN`T BUY TICKET,NOT ENOUGH MONEY");
            return new ModelAndView("redirect:/users/getroute/ticket");
        }
    }
}