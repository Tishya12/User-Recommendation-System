package com.example.recommendation.service;

import com.example.recommendation.exception.MethodNotAllowedException;
import com.example.recommendation.exception.UserAlreadyExistsException;
import com.example.recommendation.model.UserDetails;
import com.example.recommendation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.recommendation.enums.Domain.DEVELOPERS;
import static com.example.recommendation.enums.Domain.MARKETING;
import static com.example.recommendation.enums.Skill.MARKETING_PROFESSIONALS;

@Service(value = "userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails addUser(UserDetails userDetails) throws UserAlreadyExistsException, MethodNotAllowedException {
        if (userAlreadyExist(userDetails.getMobile())) {
            throw new UserAlreadyExistsException("User already exists.");
        }
        setDomain(userDetails);
        try {
            boolean isUserSaved = userRepository.saveUser(userDetails);
            if (isUserSaved) {
                System.out.println("User saved successfully. ");
            }
            return userDetails;
        } catch (MethodNotAllowedException exception) {
            throw exception;
        }
    }


    @Override
    public UserDetails getUser(Long userId) {
        return userRepository.getUser(userId);
    }

    public boolean userAlreadyExist(String mobileNumber) {
        return userRepository.findUserByMobile(mobileNumber);

    }

    public void setDomain(UserDetails userDetails) {
        if (userDetails.getSkill().equals(MARKETING_PROFESSIONALS)) {
            userDetails.setDomain(MARKETING);
        } else {
            userDetails.setDomain(DEVELOPERS);
        }
    }

}
