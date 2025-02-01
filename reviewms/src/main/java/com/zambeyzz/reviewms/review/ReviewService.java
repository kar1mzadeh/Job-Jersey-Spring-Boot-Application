package com.zambeyzz.reviewms.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    Boolean createReviews(Long companyId,Review review);

    Review findById(Long id);


    Boolean deleteSingleReview(Long id);

    Boolean updateSingleReview(Long id, Review updatedReview);
}
