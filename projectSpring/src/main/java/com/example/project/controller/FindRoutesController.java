package com.example.project.controller;

import com.example.project.entity.Application;
import com.example.project.entity.DestinationProperty;
import com.example.project.service.UserDetailsImpl;
import com.example.project.service.implementation.ApplicationServiceImpl;
import com.example.project.service.implementation.DestinationsPropertyServiceImpl;
import com.example.project.service.implementation.PageServiceImpl;
import com.example.project.service.implementation.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class FindRoutesController {

    private static final Logger log = Logger.getLogger(FindRoutesController.class);

    private static List<DestinationProperty> routes;
    private static Page<DestinationProperty> pages;
    private UserServiceImpl userServiceImpl;
    private UserDetailsImpl user;
    private ApplicationServiceImpl applicationServiceImpl;


    private DestinationsPropertyServiceImpl destinationsPropertyService;

    @Autowired
    public FindRoutesController(DestinationsPropertyServiceImpl destinationsPropertyService,
                                UserServiceImpl userServiceImpl, ApplicationServiceImpl applicationServiceImpl){
        this.destinationsPropertyService = destinationsPropertyService;
        this.userServiceImpl = userServiceImpl;
        this.applicationServiceImpl = applicationServiceImpl;
    }


    /**
     * GET method for FindRoutePage
     * @param model needed for setting attributes in html form
     * @return modelAndView object with attributes
     */
    @RequestMapping(value = "/findroute", method = RequestMethod.GET)
    public ModelAndView findRoutes(Model model){
        ModelAndView modelAndView = new ModelAndView();
        user = userServiceImpl.getUserContext();
        userServiceImpl.getLocale(model);
        model.addAttribute("user",user);
        modelAndView.addObject("findRoutesForm",new Application());
        modelAndView.setViewName("findroute");
        return modelAndView;
    }


    /**
     * POST method for FindRoutePage
     * Adding application to db and finding all the destination with application
     * Method has pagination
     * @param pageable object needed for pagination
     * @param application needed in order to find all routes from db with application
     * @param model needed for setting attributes in html
     * @return modelAndView object with attributes
     */
    @RequestMapping(value = "/findroute", method = RequestMethod.POST)
    public ModelAndView findRoutes(@PageableDefault(size = 3) Pageable pageable,
                                       @ModelAttribute Application application,
                                        Model model){
        ModelAndView modelAndView = new ModelAndView();

        user = userServiceImpl.getUserContext();
        userServiceImpl.getLocale(model);
        applicationServiceImpl.addApplication(application);
        routes = destinationsPropertyService.findAllDestinationsByApplication(application);

        model.addAttribute("user",user);
        return makePages(modelAndView,pageable,model,application);
    }

    /**
     * GET method for GetRoutesPage
     * Method has pagination
     * @param page needed for pagination realization
     * @param pageable needed for pagination realization
     * @param modelAndView needed for setting attributes such as pages
     * @param model needed for setting attributes
     * @return modelAndView object with all the destinations and attributes
     */
    @RequestMapping(value = "/getroute", method = RequestMethod.GET)
    public ModelAndView getRoutes(@RequestParam(name = "page",required = false) String page,
                                  @PageableDefault(size = 3) Pageable pageable,
                                  ModelAndView modelAndView,Model model){
        user = userServiceImpl.getUserContext();
        userServiceImpl.getLocale(model);
        PageServiceImpl pageService = new PageServiceImpl();
        pages = (Page<DestinationProperty>)pageService.pagination(page,pageable,routes);
        model.addAttribute("user",user);
        modelAndView.addObject("page",pages);
        modelAndView.setViewName("cities");
        return modelAndView;
    }

    /**
     * POST method for GetRoutesPage
     * Method redirects user to chosen TicketPage
     * @param idProperty needed for choosing ticket
     * @param redirectAttributes needed for setting redirected attributes such as ticket object
     * @param model needed for setting attributes
     * @return modelAndView object with all the attributes
     */
    @RequestMapping(value = "/getroute",method = RequestMethod.POST)
    public ModelAndView getRoutes(@RequestParam(name = "idProperty") String idProperty,RedirectAttributes redirectAttributes,Model model){
        DestinationProperty ticket = getDestinationForTicket(Integer.parseInt(idProperty));
        user = userServiceImpl.getUserContext();
        model.addAttribute("user",user);
        redirectAttributes.addFlashAttribute("ticket",ticket);
        return new ModelAndView("redirect:/users/getroute/ticket");
    }

    /**
     * Method that get needed object
     * @param idProperty needed for choosing right object
     * @return DestinationProperty object
     */
    private DestinationProperty getDestinationForTicket(int idProperty){
        for (DestinationProperty ticket:routes) {
            if (ticket.getIdProperty() == idProperty){
                return ticket;
            }
        }
        return null;
    }

    /**
     * Method that help realize pagination
     * @param modelAndView  needed for setting objects
     * @param pageable needed for pagination
     * @param model needed for setting objects
     * @param application needed for find routes by application
     * @return modelAndView object
     */
    private ModelAndView makePages(ModelAndView modelAndView, Pageable pageable, Model model, Application application){
        if (routes != null){
            pages = new PageImpl<>(routes.subList(0, Math.min(routes.size(),pageable.getPageSize())), pageable, routes.size());
            modelAndView.addObject("page", pages);
            return new ModelAndView("redirect:/users/getroute");
        }else{
            model.addAttribute("notFound", true);
            modelAndView.setViewName("findroute");
            log.error("ERROR LIST ROUTES ON " + application.getDate() +
                    " FROM " + application.getStationFrom() +
                    " TO " + application.getStationTo() + " IS EMPTY");
            return modelAndView;
        }
    }

}
