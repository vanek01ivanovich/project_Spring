package com.example.project.service.serviceInterfaces;

import com.example.project.entity.Application;
import com.example.project.entity.Ticket;
import com.example.project.entity.User;

import java.util.List;

public interface TicketService {
    void addTicket(Integer idTicketProperty);
    List<User> findAllTickets();
}
