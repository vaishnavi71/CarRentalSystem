package com.example.Car.Rental.System.dto;


import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;

    private String password;

    private String Username;
}
