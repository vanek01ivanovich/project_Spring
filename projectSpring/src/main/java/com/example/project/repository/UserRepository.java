package com.example.project.repository;

import com.example.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUserName(String userName);
    boolean existsUserByUserName(String userName);

    @Modifying
    @Query(value = "insert into users(first_name,last_name," +
            "first_name_ukr,last_name_ukr,users.role," +
            "users.password,user_name,money,card_number)"+
            "values(?1,?2,?3,?4,?5,?6,?7,?8,?9)",nativeQuery = true)
    void addUser(String firstName,String lastName,String firstNameUkr,
                 String lastNameUkr,String role,String password,String userName,String money,String cardNumber);


    @Modifying
    @Query(value = "update users set users.user_name=:userName," +
            "users.first_name=:firstName,users.first_name_ukr=:firstNameUkr," +
            "users.last_name=:lastName,users.last_name_ukr=:lastNameUkr,users.role=:role " +
            "where users.user_name=:oldUserName",nativeQuery = true)
    void updateUser(@Param("userName") String userName,
                    @Param("firstName") String firstName,
                    @Param("firstNameUkr") String firstNameUkr,
                    @Param("lastName") String lastName,
                    @Param("lastNameUkr") String lastNameUkr,
                    @Param("role") String role,
                    @Param("oldUserName") String oldUserName);


    @Modifying
    @Query(value = "update users set users.money=:money where users.user_name=:userName",nativeQuery = true)
    void updateUserMoney(@Param("money") int money,
                         @Param("userName") String userName);

    void deleteUserById(@Param("id") Integer id);
}
