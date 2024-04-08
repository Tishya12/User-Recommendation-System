package com.example.recommendation.controller;

import com.example.recommendation.exception.MethodNotAllowedException;
import com.example.recommendation.exception.ReviewNotFoundException;
import com.example.recommendation.exception.UserNotFoundException;
import com.example.recommendation.model.ReviewDetails;
import com.example.recommendation.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {
    @Autowired
    IReviewService reviewService;

    @PostMapping(value = "/addReview")
    public ResponseEntity<ReviewDetails> addReview(@RequestBody ReviewDetails reviewDetails) throws UserNotFoundException, MethodNotAllowedException, ReviewNotFoundException {
        return new ResponseEntity<>(reviewService.addReview(reviewDetails), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getRelevanceScore")
    public ResponseEntity<String> getRelevanceScore(Long reviewId) throws ReviewNotFoundException {
        return new ResponseEntity<>(reviewService.getRelevanceScore(reviewId), HttpStatus.OK);
    }
}
