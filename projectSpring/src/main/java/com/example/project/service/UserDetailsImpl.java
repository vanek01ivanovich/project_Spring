package com.example.project.service;

import com.example.project.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(User userByUserName){
        this.user = userByUserName;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));

    }

    public String getFirstName(){
        return user.getFirstName();
    }
    public String getLastName(){
        return user.getLastName();
    }
    public String getLastNameUkr(){
        return user.getLastNameUkr();
    }
    public String getFirstNameUkr(){
        return user.getFirstNameUkr();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getIdUser(){
        return user.getId();
    }

    public int getMoney(){return user.getMoney();}

    public int getCardNumber(){return user.getCardNumber();}

    public String getCard(){return user.getCard();}

    public void setMoney(int money){ user.setMoney(money);}

    public String getRole(){ return user.getRole().toString();}




}
