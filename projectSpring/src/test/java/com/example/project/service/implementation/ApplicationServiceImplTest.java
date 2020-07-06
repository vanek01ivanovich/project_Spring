package com.example.project.service.implementation;

import com.example.project.entity.Application;
import com.example.project.entity.User;
import com.example.project.repository.ApplicationRepository;
import com.example.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApplicationServiceImplTest {


    @MockBean
    private ApplicationRepository applicationRepository;

    @MockBean
    private Authentication authentication;

    @Test
    void addApplication() {
        User user = new User();
        user.setId(1);
        Application application = new Application();
        applicationRepository.addApplication(Mockito.anyInt(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString());
        Mockito.verify(applicationRepository,Mockito.times(1))
                .addApplication(Mockito.anyInt(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString());
    }
}