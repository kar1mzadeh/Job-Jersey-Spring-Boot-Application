package com.zambeyzz.reviewms.review.messagequeues;

import com.zambeyzz.reviewms.review.dto.ReviewMessage;
import com.zambeyzz.reviewms.review.Review;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {

    final RabbitTemplate rabbitTemplate;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate)

    {
        this.rabbitTemplate= rabbitTemplate;
    }

    public void sendMessage(Review review)
    {
        ReviewMessage reviewMessage = new ReviewMessage();
        reviewMessage.setId(Math.toIntExact((Long) review.getId()));
        reviewMessage.setTitle(review.getTitle());
        reviewMessage.setDescription(review.getDescription());
        reviewMessage.setRating(review.getRating());
        reviewMessage.setCompanyId(Math.toIntExact((Long) review.getCompanyId()));
        rabbitTemplate.convertAndSend("comapnyRatingQueue", reviewMessage);
    }
}
