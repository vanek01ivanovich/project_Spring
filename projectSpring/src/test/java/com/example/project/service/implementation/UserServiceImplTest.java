package com.example.project.service.implementation;

import com.example.project.configSecurity.BcryptEncoder;
import com.example.project.entity.User;
import com.example.project.entity.enums.RoleStatus;
import com.example.project.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BcryptEncoder bcryptEncoder;

    @Test
    void addUser() {
        User user = new User();
        boolean isUserCreated = userServiceImpl.addUser(user);

        Assert.assertTrue(isUserCreated);
        Assert.assertTrue(CoreMatchers.is(user.getRole()).matches(RoleStatus.ROLE_USER));

        Mockito.verify(userRepository,Mockito.times(1)).addUser(user.getFirstName(),user.getLastName(),
                user.getFirstNameUkr(),user.getLastNameUkr(),user.getRole().toString(),
                user.getPassword(),user.getUserName(),"0",user.getCard());
    }

    @Test
    public void addUserFailTest(){
        User user = new User();
        user.setUserName("ivan01");
        Mockito.when(userRepository.existsUserByUserName("ivan01"))
                .thenReturn(true);

        boolean isUserCreated = userServiceImpl.addUser(user);

        Assert.assertFalse(isUserCreated);

    }



    @Test
    void existsUserByUserName() {
        User user = new User();
        user.setUserName("ivan01");
        Mockito.when(userRepository.existsUserByUserName("ivan01"))
                .thenReturn(true);
        boolean isExistUserName = userServiceImpl.existsUserByUserName(user.getUserName());
        Assert.assertTrue(isExistUserName);
    }

    @Test
    void updateUser(){
        User user = new User();
        user.setUserName("ivan01");
        user.setFirstName("Ivan");
        user.setFirstNameUkr("Іван");
        user.setLastName("Ivanov");
        user.setLastNameUkr("Іванов");
        user.setRole(RoleStatus.ROLE_USER);
        String oldUserName = "ivan01";
        userRepository.updateUser(user.getUserName(),user.getFirstName(),
                user.getFirstNameUkr(),user.getLastName(),user.getLastNameUkr(),
                user.getRole().toString(),oldUserName);
        Mockito.verify(userRepository,Mockito.times(1)).updateUser(user.getUserName(),user.getFirstName(),
                user.getFirstNameUkr(),user.getLastName(),user.getLastNameUkr(),
                user.getRole().toString(),oldUserName);
    }
}