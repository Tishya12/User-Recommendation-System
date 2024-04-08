package com.example.recommendation.service;

import com.example.recommendation.enums.RelevanceScore;
import com.example.recommendation.exception.MethodNotAllowedException;
import com.example.recommendation.exception.ReviewNotFoundException;
import com.example.recommendation.exception.UserNotFoundException;
import com.example.recommendation.model.ReviewDetails;
import com.example.recommendation.model.UserDetails;
import com.example.recommendation.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

import static com.example.recommendation.enums.RelevanceScore.*;

@Service(value = "reviewService")
public class ReviewServiceImpl implements IReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    @Qualifier("userService")
    IUserService userService;

    @Override
    public ReviewDetails addReview(ReviewDetails reviewDetails) throws UserNotFoundException, MethodNotAllowedException, ReviewNotFoundException {

        try {
            checkUserExists(reviewDetails);
            boolean isReviewSaved = reviewRepository.saveReview(reviewDetails);
            if (isReviewSaved) {
                System.out.println("Review saved successfully. ");
                calculateRelevanceScore(reviewDetails.getReviewId());
            }
            return reviewDetails;
        } catch (UserNotFoundException | MethodNotAllowedException | ReviewNotFoundException ex) {
            throw ex;
        }
    }


    public void calculateRelevanceScore(Long reviewId) throws ReviewNotFoundException {
        try {
            ReviewDetails reviewDetails = reviewRepository.getReviewDetails(reviewId);

            UserDetails reviewee = userService.getUser(reviewDetails.getRevieweeId());
            UserDetails reviewer = userService.getUser(reviewDetails.getReviewerId());
            int revieweeYoe = calculateYearsOfExperience(reviewee.getDateOfJoining());
            int reviewerYOE = calculateYearsOfExperience(reviewer.getDateOfJoining());
            RelevanceScore relevanceScore = null;

            if (reviewer.getSkill().equals(reviewee.getSkill())) {
                if (reviewDetails.getIsCoworker()) {
                    if (revieweeYoe == reviewerYOE || reviewerYOE > revieweeYoe) {
                        relevanceScore = HIGHLY_RELEVANT;
                    } else if (revieweeYoe - reviewerYOE >= 5) {
                        relevanceScore = MODERATELY_RELEVANT;
                    }
                } else if (revieweeYoe == reviewerYOE) {
                    relevanceScore = HIGHLY_RELEVANT;
                }
            } else if (reviewDetails.getIsCoworker() && revieweeYoe == reviewerYOE) {
                relevanceScore = MODERATELY_RELEVANT;
            } else {
                relevanceScore = LEAST_RELEVANT;
            }

            reviewDetails.setRelevanceScore(relevanceScore);
        } catch (ReviewNotFoundException e) {
            throw e;
        }
    }

    @Override
    public String getRelevanceScore(Long reviewId) throws ReviewNotFoundException {
        try {
            return reviewRepository.getRelevanceScore(reviewId).toString();
        } catch (ReviewNotFoundException e) {
            throw e;
        }
    }

    public void checkUserExists(ReviewDetails reviewDetails) throws UserNotFoundException {
        UserDetails reviewee = userService.getUser(reviewDetails.getRevieweeId());
        UserDetails reviewer = userService.getUser(reviewDetails.getReviewerId());

        if (reviewer == null) {
            throw new UserNotFoundException("Reviwer does not exists.");
        } else if (reviewee == null) {
            throw new UserNotFoundException("Reviwee does not exists.");
        }
    }

    public int calculateYearsOfExperience(LocalDate dateOfJoining) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfJoining, currentDate);
        return period.getYears();
    }


}

