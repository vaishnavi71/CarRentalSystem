package com.example.Car.Rental.System.services.auth;

import com.example.Car.Rental.System.dao.UserDao;
import com.example.Car.Rental.System.dto.SignupRequest;
import com.example.Car.Rental.System.dto.UserDto;
import com.example.Car.Rental.System.enums.UserRole;
import com.example.Car.Rental.System.model.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserDao userDao;

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
        User user=new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        User createdUser=userDao.save(user);
        UserDto userDto=new UserDto();
        userDto.setId(createdUser.getId());
        return userDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userDao.findFirstByEmail(email).isPresent();
    }
@PostConstruct
    @Override
    public void createAdminAccount() {
        User adminAccount= userDao.findByUserRole(UserRole.ADMIN);
        if(adminAccount==null){
            User newAdminAccount=new User();
            newAdminAccount.setName("Admin");
            newAdminAccount.setEmail("admin@test.com");
            newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
            newAdminAccount.setUserRole(UserRole.ADMIN);
            userDao.save(newAdminAccount);
            System.out.println("Admin account created");
        }
    }
}
