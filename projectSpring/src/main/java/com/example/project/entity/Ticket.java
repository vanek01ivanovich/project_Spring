package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue
    @Column(name = "idticket")
    private Integer idTicket;

    @Column(name = "users_idusers")
    private Integer idUser;

    @Column(name = "property_idproperty")
    private Integer idProperty;






}
