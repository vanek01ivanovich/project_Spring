package com.example.project.service.implementation;

import com.example.project.configSecurity.BcryptEncoder;
import com.example.project.entity.User;
import com.example.project.entity.enums.RoleStatus;
import com.example.project.repository.UserRepository;
import com.example.project.service.UserDetailsImpl;
import com.example.project.service.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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


    /**
     * Method that finds all users from db
     * @return list of users
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    /**
     * Method that adds user to db
     * @param user needed for adding user to db
     * @return boolean value if adding was successful
     */
    @Override
    public boolean addUser(User user) {

        if (userRepository.existsUserByUserName(user.getUserName()) ||
            userRepository.existsUserByCardNumber(Integer.parseInt(user.getCard()))){
            return false;
        }
        user.setPassword(encoder.hashPassword(user.getPassword()));
        user.setRole(RoleStatus.ROLE_USER);
        userRepository.addUser(user.getFirstName(),user.getLastName(),
                               user.getFirstNameUkr(),user.getLastNameUkr(),user.getRole().toString(),
                               user.getPassword(),user.getUserName(),"0",user.getCard());
        return true;
    }

    /**
     * Method that checks if user exists by userName
     * @param userName needed for checking
     * @return boolean value if user exist or not
     */
    @Override
    public boolean existsUserByUserName(String userName) {
        return userRepository.existsUserByUserName(userName);
    }

    /**
     * Method that checks if user exists by cardNumber
     * @param cardNumber needed for checking
     * @return boolean value if card exist or not
     */
    @Override
    public boolean existsUserByCardNumber(int cardNumber) {
        return userRepository.existsUserByCardNumber(cardNumber);
    }


    /**
     * Method that updates user in db
     * @param user needed for updating
     * @param oldUserName needed for checking old user
     */
    @Override
    public void updateUser(User user, String oldUserName) {
        userRepository.updateUser(user.getUserName(),user.getFirstName(),
                user.getFirstNameUkr(),user.getLastName(),user.getLastNameUkr(),
                user.getRole().toString(),oldUserName);
    }

    /**
     * Method that delete user form db
     * @param user needed for deleting user from db
     */
    @Override
    public void deleteUser(User user) {
        userRepository.deleteUserById(user.getId());
    }

    /**
     * Method that allows user to top up money
     * @param user needed for topping up money
     * @param userInfo needed for checking user info while topping up
     * @return boolean value if topping up was successful or not
     */
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


    /**
     * Method that finds current user from list of all users
     * @param allUsers needed like a list of users
     * @param idUser needed for find user by id
     * @return user object
     */
    public User getCurrentUser(List<User> allUsers,int idUser){
        for (User user : allUsers) {
            if (user.getId() == idUser) {
                return user;
            }
        }
        return null;
    }

    /**
     * Method that sets locale in my program
     * @param model needed for setting locale attributes
     */
    public void getLocale(Model model) {
        Locale locale = LocaleContextHolder.getLocale();
        model = (locale == Locale.ENGLISH) ? model.addAttribute("type","hidden"):
                model.addAttribute("type","NotHidden");


    }

    /**
     * Method that returns user`s context
     * @return userDetailsImpl object
     */
    public UserDetailsImpl getUserContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

}
