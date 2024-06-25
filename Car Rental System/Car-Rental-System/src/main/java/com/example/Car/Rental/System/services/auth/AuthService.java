package com.example.Car.Rental.System.services.auth;

import com.example.Car.Rental.System.dto.SignupRequest;
import com.example.Car.Rental.System.dto.UserDto;

public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);
    boolean hasCustomerWithEmail(String email);
    void createAdminAccount();
}
