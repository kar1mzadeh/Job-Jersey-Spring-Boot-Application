package com.zambeyzz.Jobms.job.implementions;


import com.zambeyzz.Jobms.clients.CompanyClient;
import com.zambeyzz.Jobms.clients.ReviewClient;
import com.zambeyzz.Jobms.dto.JobDTO;
import com.zambeyzz.Jobms.external.Company;
import com.zambeyzz.Jobms.external.Review;
import com.zambeyzz.Jobms.job.Job;
import com.zambeyzz.Jobms.job.JobRepository;
import com.zambeyzz.Jobms.job.JobService;
import com.zambeyzz.Jobms.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImp implements JobService {
    private final JobRepository jobRepository;



    private CompanyClient companyClient;
    private ReviewClient reviewClient;
    int attempt=1;


    public JobServiceImp(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient=companyClient;
        this.reviewClient=reviewClient;
    }
//@CircuitBreaker(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
//
//    @Retry(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
@Override
    public List<JobDTO> findAll()
    {
        System.out.println("Attempt : " + ++attempt);
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    //fallback method

    public List<String> companyBreakerFallback(Exception e)
    {
        List<String> fallback = new ArrayList<>();
        fallback.add("Just try after some time");
        return fallback;
    }
private JobDTO convertToDto(Job job)
{
    Company company = companyClient.getClient(job.getCompanyId());
    List<Review> reviews = reviewClient.getReviews(job.getCompanyId());


    JobDTO jobDTO = JobMapper.jobMappingtoDTo(job,company,reviews);


    return jobDTO;
}
    @Override
    public Job createJob(Job job) {
jobRepository.save(job);

        return job;
    }

    @Override
    public JobDTO findById(Long id) {

       Job job=  jobRepository.findById(id).orElse(null);
       return convertToDto(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try
        {
            jobRepository.deleteById(id);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {

        Optional<Job> optionalJob= jobRepository.findById(id);
            if(optionalJob.isPresent())
            {
                Job job = optionalJob.get();
                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());
                job.setLocation(updatedJob.getLocation());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setMaxSalary(updatedJob.getMaxSalary());
                jobRepository.save(updatedJob);
                return true;
            }
      return false;
    }

}
