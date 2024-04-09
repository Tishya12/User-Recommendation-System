package com.example.recommendation.service;

import com.example.recommendation.exception.MethodNotAllowedException;
import com.example.recommendation.exception.UserAlreadyExistsException;
import com.example.recommendation.model.UserDetails;
import com.example.recommendation.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.recommendation.enums.Skill.JAVA_DEVELOPERS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testAddUser_Success() throws UserAlreadyExistsException, MethodNotAllowedException {
        UserDetails userDetails = new UserDetails("TestName", "2007-10-12", JAVA_DEVELOPERS, "9876543212");
        when(userRepository.saveUser(userDetails)).thenReturn(true);

        UserDetails addedUser = userService.addUser(userDetails);

        assertNotNull(addedUser);
        assertEquals(userDetails, addedUser);
    }

    @Test
    public void testAddUser_UserAlreadyExists() throws MethodNotAllowedException {

        UserDetails userDetails = new UserDetails("TestName", "2007-10-12", JAVA_DEVELOPERS, "9876543212");
        when(userRepository.findUserByMobile(userDetails.getMobile())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.addUser(userDetails));
    }

    @Test
    public void testAddUser_MethodNotAllowed() throws MethodNotAllowedException {

        UserDetails userDetails = new UserDetails("TestName", "2007-10-12", JAVA_DEVELOPERS, "9876543212");
        when(userRepository.saveUser(userDetails)).thenThrow(MethodNotAllowedException.class);

        assertThrows(MethodNotAllowedException.class, () -> userService.addUser(userDetails));
    }
}
