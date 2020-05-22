package com.example.project.controller;

import com.example.project.entity.Application;
import com.example.project.entity.DestinationProperty;
import com.example.project.entity.User;
import com.example.project.service.UserDetailsImpl;
import com.example.project.service.implementation.ApplicationServiceImpl;
import com.example.project.service.implementation.DestinationsPropertyServiceImpl;
import com.example.project.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = {"/users","/admin"})
public class FindRoutesController {

    private static List<DestinationProperty> routes;
    private static Page<DestinationProperty> pages;
    private UserServiceImpl userServiceImpl;
    private ApplicationServiceImpl applicationServiceImpl;


    private DestinationsPropertyServiceImpl destinationsPropertyService;

    @Autowired
    public FindRoutesController(DestinationsPropertyServiceImpl destinationsPropertyService,
                                UserServiceImpl userServiceImpl,ApplicationServiceImpl applicationServiceImpl){
        this.destinationsPropertyService = destinationsPropertyService;
        this.userServiceImpl = userServiceImpl;
        this.applicationServiceImpl = applicationServiceImpl;
    }

    @RequestMapping(value = "/findroute", method = RequestMethod.GET)
    public ModelAndView findRoutes(Model model){
        userServiceImpl.getLocale(model);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("findRoutesForm",new Application());
        modelAndView.setViewName("findroute");
        return modelAndView;
    }

    @RequestMapping(value = "/findroute", method = RequestMethod.POST)
    public ModelAndView findRoutes(@PageableDefault(size = 3) Pageable pageable,
                                       @ModelAttribute Application application,Model model){

        //applicationServiceImpl.addApplication(application);
        userServiceImpl.getLocale(model);
        routes = destinationsPropertyService.findAllDestinationsByApplication(application);
        ModelAndView modelAndView = new ModelAndView();
        if (routes != null){
            pages = new PageImpl<>(routes.subList(0, pageable.getPageSize()), pageable, routes.size());
            modelAndView.addObject("page", pages);
            return new ModelAndView("redirect:/users/getroute");
        }else{
            model.addAttribute("notFound", true);
            modelAndView.setViewName("findroute");
            return modelAndView;
        }



    }

    @RequestMapping(value = "/getroute", method = RequestMethod.GET)
    public ModelAndView getRoutes(@RequestParam(name = "page",required = false) String page,
                                  @PageableDefault(size = 3) Pageable pageable,
                                  ModelAndView modelAndView,Model model){
        userServiceImpl.getLocale(model);
        if (page != null){
            int start = pageable.getPageSize()*Integer.parseInt(page);
            int end = pageable.getPageSize() == Integer.parseInt(page)+1 ? routes.size() : start+pageable.getPageSize();
            pages = new PageImpl<>(routes.subList(start,end), pageable, routes.size());
            modelAndView.addObject("page",pages);
            modelAndView.setViewName("cities");
            return modelAndView;
        }else{
            modelAndView.addObject("page",pages);
            modelAndView.setViewName("cities");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/getroute",method = RequestMethod.POST)
    public ModelAndView getRoutes(@RequestParam(name = "idProperty") String idProperty,RedirectAttributes redirectAttributes){
        DestinationProperty ticket = getDestinationForTicket(Integer.parseInt(idProperty));

        redirectAttributes.addFlashAttribute("ticket",ticket);
        return new ModelAndView("redirect:/users/getroute/ticket");
    }





    private DestinationProperty getDestinationForTicket(int idProperty){
        for (DestinationProperty ticket:routes) {
            if (ticket.getIdProperty() == idProperty){
                return ticket;
            }
        }
        return null;
    }


}
