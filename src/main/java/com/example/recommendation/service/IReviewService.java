package com.example.recommendation.service;

import com.example.recommendation.exception.MethodNotAllowedException;
import com.example.recommendation.exception.ReviewNotFoundException;
import com.example.recommendation.exception.UserNotFoundException;
import com.example.recommendation.model.ReviewDetails;

public interface IReviewService {
    public ReviewDetails addReview(ReviewDetails reviewDetails) throws UserNotFoundException, MethodNotAllowedException, ReviewNotFoundException;

    public String getRelevanceScore(Long reviewId) throws ReviewNotFoundException;
}
