package com.example.project.service.mapper;

import com.example.project.entity.DestinationProperty;
import com.example.project.entity.Destinations;
import com.example.project.entity.Train;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DestinationPropertyMapper implements ObjectMapper<DestinationProperty> {

    @Override
    public List<DestinationProperty> extractFromResult(List<Object[]> results)throws SQLException {
        Train train;
        Destinations destination ;
        DestinationProperty destinationProperty ;
        List<DestinationProperty> destinationPropertyList = new ArrayList<>();
        for (Object[] record: results) {

            train = new Train();
            destination = new Destinations();
            destinationProperty = new DestinationProperty();

            destination.setIdDestinations((Integer) record[0]);
            destinationProperty.setIdDestination((Integer)record[0]);
            destination.setDeparture((String) record[1]);
            destination.setArrival((String) record[2]);
            destination.setDepartureUA((String) record[3]);
            destination.setArrivalUA((String) record[4]);
            destinationProperty.setIdProperty((Integer)record[5]);
            destinationProperty.setDateDeparture((String) record[6]);
            destinationProperty.setDateArrival((String)record[7]);
            destinationProperty.setTimeDeparture((String)record[8]);
            destinationProperty.setTimeArrival((String)record[9]);
            destinationProperty.setPrice(Integer.parseInt((String) record[10]));
            destinationProperty.setIdTrain((Integer) record[13]);
            train.setId((Integer) record[13]);
            train.setTrainName((String)record[14]);
            train.setTrainNameUkr((String)record[15]);

            destinationProperty.getDestinations().add(destination);
            destinationProperty.getTrains().add(train);

            destinationPropertyList.add(destinationProperty);
        }
        return destinationPropertyList;
    }


}
