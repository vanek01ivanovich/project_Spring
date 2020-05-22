package com.example.project.repository;

import com.example.project.entity.Ticket;
import com.example.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Ticket,Long> {


    @Modifying
    @Query(value = "INSERT INTO ticket(users_idusers,property_idproperty)" +
            "VALUES(?1,?2)",nativeQuery = true)
    void addApplication(Integer idUser,Integer idProperty);



}
