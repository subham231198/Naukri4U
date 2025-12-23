package com.example.NAOSys.Controller;

import com.example.NAOSys.Entity.JobApplication;
import com.example.NAOSys.Service.JobAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Naukri4U")
public class JobApplicationController
{
    @Autowired
    JobAppService jobAppService;

    @PostMapping("/JobDescription/{job_id}/Candidate/{cid}/Apply")
    public ResponseEntity<String> apply(@PathVariable("job_id") Long job_id, @PathVariable("cid") Long candidate_id, JobApplication jobApplication)
    {
        String validataion = jobAppService.applyToJob(job_id, candidate_id, jobApplication);
        if(validataion!=null)
        {
            return new ResponseEntity<>("Successfully applied to jod id "+job_id, HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/JobDescription/{job_id}/Candidate/{cid}/Withdraw")
    public ResponseEntity<String> withdraw(@PathVariable("job_id") Long job_id, @PathVariable("cid") Long candidate_id)
    {
        String validataion = jobAppService.withdrawApplication(job_id, candidate_id);
        if(validataion!=null)
        {
            return new ResponseEntity<>(jobAppService.withdrawApplication(job_id, candidate_id), HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
