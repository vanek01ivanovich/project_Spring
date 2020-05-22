package com.example.project.repository;

import com.example.project.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ApplicationRepository  extends JpaRepository<Application,Long> {

    @Modifying
    @Query(value = "INSERT INTO applications(users_idusers," +
            "departure,arrival,date_departure)" +
            "VALUES(?1,?2,?3,?4)",nativeQuery = true)
    void addApplication(int idUser,String departure,String arrival,
                        String dateDeparture);

    @Modifying
    @Query(value = "INSERT INTO applications(users_idusers," +
            "departureUA,arrivalUA,date_departure)" +
            "VALUES(?1,?2,?3,?4)",nativeQuery = true)
    void addUkrApplication(int idUser,String departureUA,String arrivalUA,
                        String dateDeparture);

}
