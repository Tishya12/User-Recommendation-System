package com.example.recommendation.service;

import com.example.recommendation.exception.MethodNotAllowedException;
import com.example.recommendation.exception.UserAlreadyExistsException;
import com.example.recommendation.model.UserDetails;

public interface IUserService {
    public UserDetails addUser(UserDetails userDetails) throws UserAlreadyExistsException, MethodNotAllowedException;

    public UserDetails getUser(Long userId);
}
