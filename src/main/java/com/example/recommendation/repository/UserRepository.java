package com.example.recommendation.repository;

import com.example.recommendation.exception.MethodNotAllowedException;
import com.example.recommendation.model.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository {

    static List<UserDetails> userDetailsList = new ArrayList<>();
    static int maxCapacity = 10;

    public boolean saveUser(UserDetails userDetails) throws MethodNotAllowedException {
        if (userDetailsList.size() < maxCapacity) {
            userDetails.setId((long) userDetailsList.size() + 1);
            userDetailsList.add(userDetails);
            return true;
        } else {
            throw new MethodNotAllowedException("Not able to save user, please try after sometime.");
        }
    }

    public UserDetails getUser(Long userid) {
        return userDetailsList.get(Math.toIntExact(userid) - 1);
    }

    public boolean findUserByMobile(String mobileNumber) {
        for (UserDetails userDetails : userDetailsList) {
            if (userDetails.getMobile().equals(mobileNumber)) {
                return true;
            }
        }
        return false;
    }
}
