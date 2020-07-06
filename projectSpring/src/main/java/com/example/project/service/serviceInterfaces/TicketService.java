package com.example.project.service.serviceInterfaces;

import com.example.project.entity.Application;
import com.example.project.entity.DestinationProperty;
import com.example.project.entity.Ticket;
import com.example.project.entity.User;

import java.util.List;

public interface TicketService {
    boolean addTicket(DestinationProperty ticket);
    List<User> findAllTickets();
}
