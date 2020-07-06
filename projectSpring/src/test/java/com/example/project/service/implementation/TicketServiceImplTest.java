package com.example.project.service.implementation;

import com.example.project.entity.User;
import com.example.project.repository.TicketRepository;
import com.example.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TicketServiceImplTest {

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private TicketServiceImpl ticketService;


    @Test
    void addTicket(){
        User userAdmin = new User();
        userAdmin.setUserName("admin01d");
        userAdmin.setMoney(8000);

        User user = new User();
        user.setUserName("ivan01");
        user.setMoney(5000);
        Optional<User> admin = Optional.of(userAdmin);


        Mockito.when(userRepository.findUserByUserName(Mockito.anyString())).thenReturn(admin);
        Mockito.doNothing().when(userRepository).updateUserMoney(100,admin.get().getUserName());
        Mockito.doNothing().when(userRepository).updateUserMoney(user.getMoney()-100,user.getUserName());
        userRepository.updateUserMoney(100,admin.get().getUserName());
        userRepository.updateUserMoney(user.getMoney()-100,user.getUserName());
        Mockito.verify(userRepository,Mockito.times(1)).updateUserMoney(100,admin.get().getUserName());
        Mockito.verify(userRepository,Mockito.times(1)).updateUserMoney(user.getMoney()-100,user.getUserName());
    }


}