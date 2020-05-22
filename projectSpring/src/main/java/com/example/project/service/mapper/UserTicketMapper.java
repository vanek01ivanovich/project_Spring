package com.example.project.service.mapper;

import com.example.project.entity.DestinationProperty;
import com.example.project.entity.Destinations;
import com.example.project.entity.Train;
import com.example.project.entity.User;

import javax.print.attribute.standard.Destination;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserTicketMapper implements ObjectMapper<User>{

    @Override
    public List<User> extractFromResult(List<Object[]> result) throws SQLException {
        Train train;
        DestinationProperty destinationProperty;
        Destinations destinations;
        User user;
        List<User> tickets = new ArrayList<>();

        for (Object[] res: result) {
            train = new Train();
            destinations = new Destinations();
            destinationProperty = new DestinationProperty();
            user = new User();

            destinationProperty.setIdProperty((Integer)res[0]);
            user.setUserName((String)res[1]);
            user.setFirstName((String)res[2]);
            user.setLastName((String)res[3]);
            user.setFirstNameUkr((String)res[4]);
            user.setLastNameUkr((String)res[5]);
            destinations.setDeparture((String)res[6]);
            destinations.setDepartureUA((String)res[7]);
            destinations.setArrival((String)res[8]);
            destinations.setArrivalUA((String)res[9]);
            destinationProperty.setDateDeparture((String)res[10]);
            destinationProperty.setDateArrival((String)res[11]);
            destinationProperty.setTimeDeparture((String)res[12]);
            destinationProperty.setTimeArrival((String)res[13]);
            destinationProperty.setPrice(Integer.parseInt((String)res[14]));
            train.setTrainName((String)res[15]);
            train.setTrainNameUkr((String)res[16]);

            user.getTrains().add(train);
            user.getDestinationProperties().add(destinationProperty);
            user.getDestinations().add(destinations);

            tickets.add(user);
        }

        return tickets;
    }
}
