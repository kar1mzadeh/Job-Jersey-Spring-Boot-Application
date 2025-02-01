package com.zambeyzz.Jobms.job;

import com.zambeyzz.Jobms.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @GetMapping("/jobs")
    public ResponseEntity<List<JobDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }
   @PostMapping("/jobs")
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job createdJob = jobService.createJob(job);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobDTO> findById(@PathVariable Long id)
    {
        JobDTO jobDTO = jobService.findById(id);
        if(jobDTO !=null) {
            return new ResponseEntity<>(jobDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id)
    {
        boolean delete= jobService.deleteJobById(id);
        if(delete)
        {
           return new ResponseEntity<>("Job deleted suc",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
@PutMapping("/jobs/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job updatedJob){
        boolean job = jobService.updateJob(id, updatedJob);
        if(job)
        {
            return new ResponseEntity<>("Job updated successfully",HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}

}
