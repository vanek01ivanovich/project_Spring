package com.example.project.configSecurity;



import com.example.project.Exception.UserNameNotFoundException;
import com.example.project.controller.MainController;
import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import com.example.project.service.UserDetailsImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String userName) throws UserNameNotFoundException {
        User user = userRepository.findUserByUserName(userName)
                .orElseThrow(()->new UserNameNotFoundException("not found",userName));
        return new UserDetailsImpl(user);
    }


}

