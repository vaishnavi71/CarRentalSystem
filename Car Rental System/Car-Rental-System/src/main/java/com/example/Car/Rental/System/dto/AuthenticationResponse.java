package com.example.Car.Rental.System.dto;

import com.example.Car.Rental.System.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private UserRole userRole;
    private Long userId;
}
