package com.example.Car.Rental.System.dto;

import com.example.Car.Rental.System.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private UserRole userRole;
}
