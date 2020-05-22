package com.example.project.service.serviceInterfaces;

import com.example.project.entity.Application;
import com.example.project.entity.DestinationProperty;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DestinationsPropertyService {


    List<DestinationProperty> findAllDestinationsByApplication(Application application);

}
