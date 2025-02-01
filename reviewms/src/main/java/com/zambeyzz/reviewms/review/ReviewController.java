package com.zambeyzz.reviewms.review;

import com.zambeyzz.reviewms.review.messagequeues.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
   private ReviewService reviewService;
   private ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer= reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getReviews(@RequestParam Long companyId)
    {
        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }
    @PostMapping
    public ResponseEntity<String> createReviews(@RequestParam Long companyId,@RequestBody Review review)
    {
        reviewService.createReviews(companyId,review);
        reviewMessageProducer.sendMessage(review);
        return new ResponseEntity<>("The review created successfully",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> findSingleReview(@PathVariable Long id) {
        Review review = reviewService.findById(id);
        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSingleReview(@PathVariable Long id)
    {
        Boolean review = reviewService.deleteSingleReview(id);
        if (review) {
            return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<String> updateReview(@PathVariable Long id,@RequestBody Review review)
    {
Boolean reviewCheking = reviewService.updateSingleReview(id,review);
if(reviewCheking)
{
    return new ResponseEntity<>("Updated successfully",HttpStatus.OK);
}
else{
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
    }
    @GetMapping("/averageRating")
    public double getAvarageRating(@RequestParam Long companyId)
    {
        List<Review> ratings = reviewService.getAllReviews(companyId);

        return ratings.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }


}
