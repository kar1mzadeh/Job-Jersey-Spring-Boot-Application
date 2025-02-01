package com.zambeyzz.Jobms.mapper;

import com.zambeyzz.Jobms.dto.JobDTO;
import com.zambeyzz.Jobms.external.Company;
import com.zambeyzz.Jobms.external.Review;
import com.zambeyzz.Jobms.job.Job;

import java.util.List;

public class JobMapper {

    public static JobDTO jobMappingtoDTo(Job job, Company company, List<Review> reviews)
    {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setCompany(company);
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setReviews(reviews);
        return jobDTO;
    }
}
