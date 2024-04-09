package com.example.recommendation.service;

import com.example.recommendation.exception.MethodNotAllowedException;
import com.example.recommendation.exception.ReviewNotFoundException;
import com.example.recommendation.exception.UserNotFoundException;
import com.example.recommendation.model.ReviewDetails;
import com.example.recommendation.model.UserDetails;
import com.example.recommendation.repository.ReviewRepository;
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
public class ReviewServiceTest {
    @Autowired
    private ReviewServiceImpl reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private UserServiceImpl userService;

    @Test
    public void testAddReview_Success() throws UserNotFoundException, MethodNotAllowedException, ReviewNotFoundException {
        ReviewDetails reviewDetails = new ReviewDetails(true, 1L, 2L, "Good performer");
        UserDetails reviewer = new UserDetails("TestName", "2007-10-12", JAVA_DEVELOPERS, "9876543212");
        UserDetails reviewee = new UserDetails("TestName2", "2007-10-12", JAVA_DEVELOPERS, "9876543211");
        when(userService.getUser(reviewDetails.getReviewerId())).thenReturn(reviewer);
        when(userService.getUser(reviewDetails.getRevieweeId())).thenReturn(reviewee);
        when(reviewRepository.saveReview(reviewDetails)).thenReturn(true);
        when(reviewRepository.getReviewDetails(reviewDetails.getReviewId())).thenReturn(reviewDetails);

        ReviewDetails addedReview = reviewService.addReview(reviewDetails);

        assertNotNull(addedReview);
        assertEquals(reviewDetails, addedReview);
    }

    @Test
    public void testAddReview_UserNotFoundException() {

        ReviewDetails reviewDetails = new ReviewDetails(true, 1L, 2L, "Good performer");
        when(userService.getUser(reviewDetails.getReviewerId())).thenReturn(null);
        when(userService.getUser(reviewDetails.getRevieweeId())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> reviewService.addReview(reviewDetails));
    }

    @Test
    public void testAddReview_MethodNotAllowedException() throws MethodNotAllowedException {

        ReviewDetails reviewDetails = new ReviewDetails(true, 1L, 2L, "Good performer");
        UserDetails reviewer = new UserDetails("TestName", "2007-10-12", JAVA_DEVELOPERS, "9876543212");
        UserDetails reviewee = new UserDetails("TestName2", "2007-10-12", JAVA_DEVELOPERS, "9876543211");
        when(userService.getUser(reviewDetails.getReviewerId())).thenReturn(reviewer);
        when(userService.getUser(reviewDetails.getRevieweeId())).thenReturn(reviewee);
        when(reviewRepository.saveReview(reviewDetails)).thenThrow(MethodNotAllowedException.class);

        assertThrows(MethodNotAllowedException.class, () -> reviewService.addReview(reviewDetails));
    }

}


