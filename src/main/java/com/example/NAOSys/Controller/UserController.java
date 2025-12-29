package com.example.NAOSys.Controller;

import com.example.NAOSys.Entity.*;
import com.example.NAOSys.POJO.API_StandardResponse;
import com.example.NAOSys.POJO.LogOffProvider;
import com.example.NAOSys.POJO.UserLogOn;
import com.example.NAOSys.Service.LogOnService;
import com.example.NAOSys.Service.UserService;
import com.sun.net.httpserver.HttpsServer;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    @Autowired
    LogOnService logOnService;

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
        Boolean validation = userService.addCandidate(candidate.getUser(), candidate.getCandidate());
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
    public ResponseEntity<List<Map<String, Object>>> viewAllCandidate()
    {
        List<Map<String, Object>> validation = userService.getAllCandidate();
        if(validation != null)
        {
            return new ResponseEntity<>(validation, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/userLogOn")
    public ResponseEntity<Map<String, Object>> userLogOn(@RequestBody UserLogOn userLogOn, HttpServletResponse response)
    {
        if(userLogOn.getEmail().isEmpty() || userLogOn.getPassword().isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credentials cannot be null or blank!");
        }
        else
        {
            Map<String, Object> validation = logOnService.userLogOn(userLogOn.getEmail(), userLogOn.getPassword());
            if(validation!=null)
            {
                Cookie cookie = new Cookie(
                        "SESSION_ID",
                        validation.get("user_sessionId").toString()
                );

                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                cookie.setMaxAge(600);
                response.addCookie(cookie);

                return new ResponseEntity<>(validation, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping(value = "/rest-sts/logout")
    public ResponseEntity<Map<String, Object>> userLogOut(@RequestBody LogOffProvider logOffProvider)
    {
        if(!logOffProvider.getInputTokenState().getToken_type().equals("SSOTOKEN"))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token-type in request");
        }
        else if(logOffProvider.getInputTokenState().getTokenId().isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tokenId cannot be null or blank!");
        }
        else if(!logOffProvider.getOutputTokenState().getSubject_confirmation().equals("Bearer"))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid value passed against subject confirmation!");
        }
        else
        {
            Map<String, Object> result = logOnService.userLogout(logOffProvider.getInputTokenState().getTokenId());
            if(result!=null)
            {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
    }

}
