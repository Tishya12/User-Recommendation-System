package com.example.recommendation.model;

import com.example.recommendation.enums.RelevanceScore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class ReviewDetails {

    @Id
    Long reviewId;

    @NotNull(message = "IsCoWorker relationship is mandatory field. ")
    Boolean isCoworker;

    @NotNull(message = "ReviewerId can not be null. ")
    Long reviewerId;

    @NotNull(message = "RevieweeId can not be null. ")
    Long revieweeId;

    String reviewMsg;
    RelevanceScore relevanceScore;


}
