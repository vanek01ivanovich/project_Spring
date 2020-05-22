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
@Table(name = "train")
public class Train {

    @Id
    @GeneratedValue
    @Column(name = "idtrain")
    private Integer id;

    @Column(name = "trainName")
    private String trainName;

    @Column(name = "trainNameUA")
    private String trainNameUkr;

}


