package com.example.project.service.implementation;

import com.example.project.entity.Application;
import com.example.project.repository.ApplicationRepository;
import com.example.project.service.UserDetailsImpl;
import com.example.project.service.serviceInterfaces.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private ApplicationRepository applicationRepository;
    private Authentication authentication;


    @Autowired
    private ApplicationServiceImpl(ApplicationRepository applicationRepository){
        this.applicationRepository = applicationRepository;
    }

    @Override
    public void addApplication(Application application) {
        Locale locale = LocaleContextHolder.getLocale();
        authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user  = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println("USER id = " + user.getIdUser());
        System.out.println("App = " + application.toString());
        if (locale == Locale.ENGLISH){
            applicationRepository.addApplication(user.getIdUser(),
                                                 application.getStationFrom(),
                                                 application.getStationTo(),
                                                 application.getDate());
        }else{
            applicationRepository.addUkrApplication(user.getIdUser(),
                                                    application.getStationFromUkr(),
                                                    application.getStationToUkr(),
                                                    application.getDate());
        }
    }
}
