package com.example.project.entity;


import com.example.project.entity.enums.RoleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "idusers", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp = "[A-Za-z_0-9.]{2,20}")
    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Pattern(regexp = "[A-Z][a-z]{2,20}")
    @Column(name = "first_name")
    private String firstName;

    @Pattern(regexp = "[А-ЯІЩЄҐЇ][а-ящєґ'ії]+")
    @Column(name = "first_name_ukr")
    private String firstNameUkr;

    @Pattern(regexp = "[A-Z][a-z]{2,20}")
    @Column(name = "last_name")
    private String lastName;

    @Pattern(regexp = "[А-ЯІЩЄҐЇ][а-ящєґ'ії]+")
    @Column(name = "last_name_ukr")
    private String lastNameUkr;

    @Column(name = "money")
    private int money;

    @Column(name = "card_number")
    private int cardNumber;

    @Pattern(regexp = "[0-9]{8}")
    @Transient
    private String card;

    @Enumerated(EnumType.STRING)
    private RoleStatus role;

    @Transient
    private List<DestinationProperty> destinationProperties = new ArrayList<>();

    @Transient
    private List<Train> trains = new ArrayList<>();

    @Transient
    private List<Destinations> destinations = new ArrayList<>();

}
