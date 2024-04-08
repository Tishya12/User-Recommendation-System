package com.example.recommendation.repository;

import com.example.recommendation.enums.RelevanceScore;
import com.example.recommendation.exception.MethodNotAllowedException;
import com.example.recommendation.exception.ReviewNotFoundException;
import com.example.recommendation.model.ReviewDetails;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewRepository {
    static List<ReviewDetails> reviewDetailsList = new ArrayList<>();
    static int maxCapacity = 10;

    public boolean saveReview(ReviewDetails reviewDetails) throws MethodNotAllowedException {
        if (reviewDetailsList.size() < maxCapacity) {
            reviewDetails.setReviewId((long) reviewDetailsList.size() + 1);
            reviewDetailsList.add(reviewDetails);
            return true;
        } else {
            throw new MethodNotAllowedException("Not able to save review, please try after sometime. ");
        }
    }

    public ReviewDetails getReviewDetails(Long reviewId) throws ReviewNotFoundException {
        ReviewDetails reviewDetails = reviewDetailsList.get(Math.toIntExact(reviewId) - 1);
        if (reviewDetails == null) {
            throw new ReviewNotFoundException("Review does not exist. ");
        }
        return reviewDetails;
    }

    public RelevanceScore getRelevanceScore(Long reviewId) throws ReviewNotFoundException {
        ReviewDetails reviewDetails = reviewDetailsList.get(Math.toIntExact(reviewId) - 1);
        if (reviewDetails == null) {
            throw new ReviewNotFoundException("Review does not exist. ");
        }
        return reviewDetails.getRelevanceScore();
    }
}
