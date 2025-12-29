package com.example.NAOSys.Controller;

import com.example.NAOSys.Entity.JobDescription;
import com.example.NAOSys.Service.JDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Naukri4U")
public class JobDescriptionController
{
    @Autowired
    JDService jdService;

    @PostMapping("/addJobDescription/{REC_ID}")
    public ResponseEntity<Map<String, Object>> createNewJob(@RequestBody JobDescription jd, @PathVariable("REC_ID") Long rec_id)
    {
        Map<String, Object> validation = jdService.addJobDescription(jd, rec_id);
        if(validation!=null)
        {
            return new ResponseEntity<>(validation, HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllJobDescriptions")
    public ResponseEntity<List<Map<String, Object>>> getAllJobDescriptions()
    {
        List<Map<String, Object>> validation = jdService.getJobAllDescription();
        if(validation!=null)
        {
            return new ResponseEntity<>(validation, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getJob/{JOB_ID}")
    public ResponseEntity<Map<String, Object>> getJobDescriptionById(@PathVariable("JOB_ID") Long job_id)
    {
        Map<String, Object> validation = jdService.getJobDetailsByID(job_id);
        if(validation!=null)
        {
            return new ResponseEntity<>(validation, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
