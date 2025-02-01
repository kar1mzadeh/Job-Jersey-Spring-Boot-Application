package com.zambeyzz.companyms.company.messagequeues;

import com.zambeyzz.companyms.company.CompanyService;
import com.zambeyzz.companyms.company.dto.ReviewMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {

    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService)
    {
        this.companyService=companyService;
    }
    @RabbitListener(queues="comapnyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage)
    {
        companyService.updateCompanyRating(reviewMessage);
    }
}
