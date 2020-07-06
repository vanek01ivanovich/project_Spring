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
@Table(name = "destinations")
public class Destinations {

    @Id
    @GeneratedValue
    @Column(name = "iddestinations")
    private int idDestinations;

    @Column(name = "departure")
    private String departure;

    @Column(name = "arrival")
    private String arrival;

    @Column(name = "departureUA")
    private String departureUA;

    @Column(name = "arrivalUA")
    private String arrivalUA;
}
