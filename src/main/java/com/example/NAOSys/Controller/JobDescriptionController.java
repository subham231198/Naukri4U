package com.example.NAOSys.Controller;

import com.example.NAOSys.Entity.JobDescription;
import com.example.NAOSys.Service.JDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Naukri4U")
public class JobDescriptionController
{
    @Autowired
    JDService jdService;

    @PostMapping("/addJobDescription/{REC_ID}")
    public ResponseEntity<String> createNewJob(@RequestBody JobDescription jd, @PathVariable("REC_ID") Long rec_id)
    {
        String validation = jdService.addJobDescription(jd, rec_id);
        if(validation!=null)
        {
            return new ResponseEntity<>("Job details added successfully", HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllJobDescriptions")
    public ResponseEntity<String> getAllJobDescriptions()
    {
        String validation = jdService.getJobAllDescription();
        if(validation!=null)
        {
            return new ResponseEntity<>(jdService.getJobAllDescription(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getJob/{JOB_ID}")
    public ResponseEntity<String> getJobDescriptionById(@PathVariable("JOB_ID") Long job_id)
    {
        String validation = jdService.getJobDetailsByID(job_id);
        if(validation!=null)
        {
            return new ResponseEntity<>(jdService.getJobDetailsByID(job_id), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
