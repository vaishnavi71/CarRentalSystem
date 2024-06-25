package com.example.Car.Rental.System.controller;

import com.example.Car.Rental.System.Utils.JWTUtil;
import com.example.Car.Rental.System.dao.UserDao;
import com.example.Car.Rental.System.dto.AuthenticationRequest;
import com.example.Car.Rental.System.dto.AuthenticationResponse;
import com.example.Car.Rental.System.dto.SignupRequest;
import com.example.Car.Rental.System.dto.UserDto;
import com.example.Car.Rental.System.model.User;
import com.example.Car.Rental.System.services.auth.AuthService;
import com.example.Car.Rental.System.services.auth.Jwt.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final UserDao userDao;

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest){
        if(authService.hasCustomerWithEmail(signupRequest.getEmail()))
            return new ResponseEntity<>("Customer already Exists",HttpStatus.NOT_ACCEPTABLE);
        UserDto createdCustomerDto=authService.createCustomer(signupRequest);
        if(createdCustomerDto==null)
            return new ResponseEntity<>("Customer not created", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(createdCustomerDto,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws BadCredentialsException, DisabledException, UsernameNotFoundException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));

        }
        catch(BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");

        }
        final UserDetails userDetails=userService.userDetailsService().loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser=userDao.findFirstByEmail(userDetails.getUsername());
        final String jwt=jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());

        }
        return authenticationResponse;

    }
}
