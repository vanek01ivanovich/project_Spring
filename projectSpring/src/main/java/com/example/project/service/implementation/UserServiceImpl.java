package com.example.project.service.implementation;

import com.example.project.configSecurity.BcryptEncoder;
import com.example.project.entity.User;
import com.example.project.entity.enums.RoleStatus;
import com.example.project.repository.UserRepository;
import com.example.project.service.UserDetailsImpl;
import com.example.project.service.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private BcryptEncoder encoder = new BcryptEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }



    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    @Override
    public boolean addUser(User user) {

        if (userRepository.existsUserByUserName(user.getUserName())){
            return false;
        }
        user.setPassword(encoder.hashPassword(user.getPassword()));
        user.setRole(RoleStatus.ROLE_USER);
        userRepository.addUser(user.getFirstName(),user.getLastName(),
                               user.getFirstNameUkr(),user.getLastNameUkr(),user.getRole().toString(),
                               user.getPassword(),user.getUserName(),"0",user.getCard());
        return true;
    }

    @Override
    public boolean existsUserByUserName(String userName) {
        return userRepository.existsUserByUserName(userName);
    }

    @Override
    public void updateUser(User user, String oldUserName) {
        userRepository.updateUser(user.getUserName(),user.getFirstName(),
                user.getFirstNameUkr(),user.getLastName(),user.getLastNameUkr(),
                user.getRole().toString(),oldUserName);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.deleteUserById(user.getId());
    }

    @Override
    public boolean topUpMoney(UserDetailsImpl user, User userInfo) {
        if (encoder.checkPass(userInfo.getPassword(),user.getPassword()) &&
                Integer.toString(user.getCardNumber()).equals(userInfo.getCard())){
            userRepository.updateUserMoney(userInfo.getMoney()+user.getMoney(),user.getUsername());
            user.setMoney(userInfo.getMoney()+user.getMoney());
            return true;
        }
        return false;
    }


    public User getCurrentUser(List<User> allUsers,int idUser){
        for (User user : allUsers) {
            if (user.getId() == idUser) {
                return user;
            }
        }
        return null;
    }

    public void getLocale(Model model) {
        Locale locale = LocaleContextHolder.getLocale();
        model = (locale == Locale.ENGLISH) ? model.addAttribute("type","hidden"):
                model.addAttribute("type","NotHidden");


    }
}
