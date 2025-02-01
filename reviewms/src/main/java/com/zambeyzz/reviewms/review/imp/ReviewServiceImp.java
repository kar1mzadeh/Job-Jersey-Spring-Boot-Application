package com.zambeyzz.reviewms.review.imp;

import com.zambeyzz.reviewms.review.Review;
import com.zambeyzz.reviewms.review.ReviewRepository;
import com.zambeyzz.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImp implements ReviewService {


    private ReviewRepository reviewRepository;

//    private Long nextId=1L;

    public ReviewServiceImp(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;

    }
    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId); // Fetch reviews for a specific company
    }

    @Override
    public Boolean createReviews(Long companyId,Review review) {



        if(companyId != null)
        {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review findById(Long id) {
        return  reviewRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean deleteSingleReview(Long id) {
       Review deletedReview = reviewRepository.findById(id).orElse(null);
        if(deletedReview !=null){
            reviewRepository.delete(deletedReview);
            return true;
        }
        return false;


    }

    @Override
    public Boolean updateSingleReview(Long id, Review updatedReview) {

        Review review= reviewRepository.findById(id).orElse(null);
        if(review !=null)
        {
review.setTitle(updatedReview.getTitle());
review.setDescription(updatedReview.getDescription());
review.setRating(updatedReview.getRating());
reviewRepository.save(review);
return true;
        }
        return false;
    }

}
