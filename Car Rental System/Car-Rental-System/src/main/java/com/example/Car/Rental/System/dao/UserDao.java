package com.example.Car.Rental.System.dao;

import com.example.Car.Rental.System.enums.UserRole;
import com.example.Car.Rental.System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,Long> {

    Optional<User> findFirstByEmail(String email);

    User findByUserRole(UserRole userRole);
}
