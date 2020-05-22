package com.example.project.service.implementation;

import com.example.project.entity.Application;
import com.example.project.entity.DestinationProperty;
import com.example.project.entity.Destinations;
import com.example.project.entity.Train;
import com.example.project.service.mapper.DestinationPropertyMapper;
import com.example.project.service.serviceInterfaces.DestinationsPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.print.attribute.standard.Destination;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class DestinationsPropertyServiceImpl implements DestinationsPropertyService {





    @PersistenceContext
    private EntityManager entityManager;

    final String sqlFindByStationsAndDate = "SELECT  destinations.*,property.*,train.* " +
            "FROM myrailwaydb.property join destinations on property.destinations_iddestinations = destinations.iddestinations " +
            "join train on property.train_idtrain = train.idtrain where departure = ? and " +
            "arrival = ? and date_departure = ?";

    final String sqlFindByUkrainianStationsAndDate = "SELECT  destinations.*,property.*,train.* " +
            "FROM myrailwaydb.property join destinations on property.destinations_iddestinations = destinations.iddestinations " +
            "join train on property.train_idtrain = train.idtrain where departureUA = ? and " +
            "arrivalUA = ? and date_departure = ?";

    @Override
    public List<DestinationProperty> findAllDestinationsByApplication(Application application) {
        Locale locale = LocaleContextHolder.getLocale();
        List<Object[]> results = entityManager.createNativeQuery(locale == Locale.ENGLISH ?
                                                                sqlFindByStationsAndDate:
                                                                sqlFindByUkrainianStationsAndDate)
                .setParameter(1,locale == Locale.ENGLISH ? application.getStationFrom():application.getStationFromUkr())
                .setParameter(2,locale == Locale.ENGLISH ? application.getStationTo():application.getStationToUkr())
                .setParameter(3,application.getDate())
                .getResultList();

        DestinationPropertyMapper destinationPropertyMapper = new DestinationPropertyMapper();

        try {
            if (results.size() != 0){
                return destinationPropertyMapper.extractFromResult(results);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
