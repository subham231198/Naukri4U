package com.example.NAOSys.Controller;

import com.example.NAOSys.Entity.*;
import com.example.NAOSys.POJO.API_StandardResponse;
import com.example.NAOSys.Service.UserService;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Naukri4U")
public class UserController
{
    @Autowired
    UserService userService;

    private static final Logger log = LogManager.getLogger(UserController.class);

    API_StandardResponse apiStandardResponse = new API_StandardResponse<>();

    @PostMapping("/addRecruiter")
    public ResponseEntity<API_StandardResponse<?>> registerRecruiter(@RequestBody RecruiterRegistration recruiter)
    {
        Boolean validation = userService.addRecruiter(recruiter.getUser(), recruiter.getRecruiter());
        if(validation)
        {
            apiStandardResponse.setCode(null);
            apiStandardResponse.setReason(null);
            apiStandardResponse.setMessage("Recruiter has been added successfully");
            return new ResponseEntity<>(apiStandardResponse, HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addCandidate")
    public ResponseEntity<API_StandardResponse<?>> registerCandidate(@RequestBody CandidateRegistration candidate)
    {
        String validation = userService.addCandidate(candidate.getUser(), candidate.getCandidate());
        if(validation != null)
        {
            apiStandardResponse.setCode(null);
            apiStandardResponse.setReason(null);
            apiStandardResponse.setMessage("Candidate has been added successfully");
            return new ResponseEntity<>(apiStandardResponse, HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllRecruiter")
    public ResponseEntity<List<Map<String, Object>>> viewAllRecruiter()
    {
        List<Map<String, Object>> validation = userService.getAllRecruiter();
        if(validation != null)
        {
            return new ResponseEntity<>(validation, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getRecruiterEmail/{email}")
    public ResponseEntity<Map<String, Object>> viewRecruiterByEmail(@PathVariable("email") String email)
    {
        Map<String, Object> validation = userService.getRecruiterByEmail(email);
        if(validation!=null)
        {
            return new ResponseEntity<>(validation, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getRecruiterPhone")
    public ResponseEntity<Map<String, Object>> viewRecruiterByPhone(@RequestHeader(name = "x-channel") String channel, @RequestHeader(name = "x-phone") String phone)
    {
        if(phone.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "x-phone cannot be empty or null!");
        }
        if(channel.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "x-channel cannot be empty or null!");
        }
        if(!channel.equals("WEB"))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid value passed against header x-channel!");
        }
        Map<String, Object> validation = userService.getRecruiterByPhone(phone);
        if(!validation.isEmpty())
        {
            log.info(validation);
            return new ResponseEntity<>(validation, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllCandidate")
    public ResponseEntity<String> viewAllCandidate()
    {
        String validation = userService.getAllCandidate();
        if(validation != null)
        {
            return new ResponseEntity<>(userService.getAllCandidate(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
