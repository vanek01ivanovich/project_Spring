package com.example.project.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "property")
public class DestinationProperty {

    @Id
    @GeneratedValue
    @Column(name = "idproperty")
    private int idProperty;

    @Column(name = "destinations_iddestinations")
    private int idDestination;


    @Column(name = "train_idtrain")
    private int idTrain;

    @Column(name = "time_departure")
    private String timeDeparture;

    @Column(name = "time_arrival")
    private String timeArrival;

    @Column(name = "date_departure")
    private String dateDeparture;

    @Column(name = "date_arrival")
    private String dateArrival;

    @Column(name = "price")
    private int price;

    @Transient
    private List<Train> trains = new ArrayList<>();

    @Transient
    private List<Destinations> destinations = new ArrayList<>();

}
