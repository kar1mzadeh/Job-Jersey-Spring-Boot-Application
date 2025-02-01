package com.zambeyzz.Jobms.job;

import com.zambeyzz.Jobms.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    Job createJob(Job job);
    JobDTO findById(Long id);
    boolean deleteJobById(Long id);
    boolean updateJob(Long id, Job updatedJob);
}
