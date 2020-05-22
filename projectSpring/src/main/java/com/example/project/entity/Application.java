package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue
    @Column(name = "idapplications")
    private Integer id;

    @Column(name = "users_idusers")
    private int idUser;

    @Column(name = "departure")
    private String stationFrom;

    @Column(name = "departureUA")
    private String stationFromUkr;

    @Column(name = "arrival")
    private String stationTo;

    @Column(name = "arrivalUA")
    private String stationToUkr;

    @Column(name = "date_departure")
    private String date;

}
