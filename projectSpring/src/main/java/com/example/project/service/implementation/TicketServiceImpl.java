package com.example.project.service.implementation;

import com.example.project.entity.DestinationProperty;
import com.example.project.entity.Ticket;
import com.example.project.entity.User;
import com.example.project.repository.TicketRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.UserDetailsImpl;
import com.example.project.service.mapper.UserTicketMapper;
import com.example.project.service.serviceInterfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.*;

@Service
public class TicketServiceImpl implements TicketService {

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("databaseRequest");

    private TicketRepository ticketRepository;
    private UserRepository userRepository;
    private Authentication authentication;

    @PersistenceContext
    private EntityManager entityManager;

    private final String FIND_ALL_TICKETS = "find.all.tickets";

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,UserRepository userRepository){
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    /**
     * Method that add ticket to db
     * @param ticket needed for adding
     * @return boolean value if adding was successful
     */
    @Override
    public boolean addTicket(DestinationProperty ticket) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user  = (UserDetailsImpl) authentication.getPrincipal();
        if (user.getMoney() < ticket.getPrice()){
            return false;
        }else{
            Optional<User> admin = userRepository.findUserByUserName("admin01");
            userRepository.updateUserMoney(admin.get().getMoney()+ticket.getPrice(),"admin01");
            userRepository.updateUserMoney(user.getMoney()-ticket.getPrice(),user.getUsername());
            ticketRepository.addApplication(user.getIdUser(),ticket.getIdProperty());
            user.setMoney(user.getMoney()-ticket.getPrice());
            return true;
        }
    }


    /**
     * Method that find all tickets from db
     * @return list of ticket object
     */
    @Override
    public List<User> findAllTickets() {
        List<Object[]> results = entityManager.createNativeQuery(resourceBundle.getString(FIND_ALL_TICKETS)).getResultList();
        UserTicketMapper userTicketMapper = new UserTicketMapper();

        try {
            if (results.size() != 0){
                return userTicketMapper.extractFromResult(results);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
