package com.example.NAOSys.Service;

import com.example.NAOSys.Controller.UserController;
import com.example.NAOSys.CustomMethods.Validator;
import com.example.NAOSys.Entity.Candidate;
import com.example.NAOSys.Entity.Recruiter;
import com.example.NAOSys.Entity.User;
import com.example.NAOSys.Repository.CandidateRepo;
import com.example.NAOSys.Repository.RecruiterRepo;
import com.example.NAOSys.Repository.UserRepo;
import jakarta.transaction.Transactional;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Transactional
public class UserService
{
    @Autowired
    UserRepo userRepo;

    @Autowired
    RecruiterRepo recruiterRepo;

    @Autowired
    CandidateRepo candidateRepo;

    private static final Logger log = LogManager.getLogger(UserService.class);

    public Boolean addRecruiter(User user, Recruiter recruiter)
    {
        Optional<User> validateEmailExists = userRepo.findByEmail(user.getEmail());
        Optional<User> validatePhoneExists = userRepo.findByPhone(user.getPhone());
        if(validateEmailExists.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Recruiter profile already exists with the provided email address "+user.getEmail());
        }
        else if(validatePhoneExists.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Recruiter profile already exists with the provided phone number "+user.getPhone());
        }
        else
        {
            if(Validator.validateEmail(user.getEmail(), "Recruiter"))
            {
                if(Validator.validatePhone(user.getPhone()))
                {
                    if(Validator.validatePinCode(user.getPincode()))
                    {
                        user.setRole("Recruiter");
                        user.setRecruiter(recruiter);
                        recruiter.setUser(user);
                        userRepo.save(user);
                        recruiterRepo.save(recruiter);
                        return true;
                    }
                    else
                    {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid pincode!");
                    }
                }
                else
                {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid phone number");
                }
            }
            else
            {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email address");
            }
        }
    }

    public Boolean addCandidate(User user, Candidate candidate)
    {
        Optional<User> validateEmailExists = userRepo.findByEmail(user.getEmail());
        Optional<User> validatePhoneExists = userRepo.findByPhone(user.getPhone());
        if(validateEmailExists.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Candidate profile already exists with the provided email address "+user.getEmail());
        }
        else if(validatePhoneExists.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Candidate profile already exists with the provided phone number "+user.getPhone());
        }
        else
        {
            if(Validator.validateEmail(user.getEmail(), "Candidate"))
            {
                if(Validator.validatePhone(user.getPhone()))
                {
                    if(Validator.validatePinCode(user.getPincode()))
                    {
                        user.setRole("Candidate");
                        user.setCandidate(candidate);
                        candidate.setUser(user);
                        userRepo.save(user);
                        candidateRepo.save(candidate);
                        return true;
                    }
                    else
                    {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid pincode!");
                    }
                }
                else
                {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid phone number");
                }
            }
            else
            {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email address");
            }
        }
    }

    public List<Map<String, Object>> getAllRecruiter()
    {
        List<User> getAllUser = userRepo.findAll();
        List<Recruiter> getAllRec = recruiterRepo.findAll();
        List<Map<String, Object>> result = new LinkedList<>();

        if(getAllUser.size()>0 && getAllRec.size()>0)
        {
            for(int j = 0; j<getAllRec.size(); j++)
            {
                Map<String, Object> map = new LinkedHashMap<>();
                String name = getAllRec.get(j).getUser().getFirstName()+" "+getAllRec.get(j).getUser().getLastName();
                map.put("id", getAllRec.get(j).getUser().getUserId());
                map.put("name", name);
                map.put("email", getAllRec.get(j).getUser().getEmail());
                map.put("phone", getAllRec.get(j).getUser().getPhone());
                map.put("company", getAllRec.get(j).getCompany());
                map.put("designation", getAllRec.get(j).getDesignation());
                result.add(map);
            }
            return result;
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No recruiter record available in database");
        }
    }

    public Map<String, Object> getRecruiterByEmail(String email) {

        if (!Validator.validateEmail(email, "Recruiter")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid email address. Please check your email and try again!"
            );
        }

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No user found with email: " + email
                ));

        if (!"Recruiter".equalsIgnoreCase(user.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "User is not a recruiter"
            );
        }

        Recruiter recruiter = recruiterRepo.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recruiter profile not found for email: " + email
                ));

        String name = user.getFirstName() + " " + user.getLastName();

        return Map.of(
                "id", user.getUserId(),
                "name", name,
                "email", user.getEmail(),
                "phone", user.getPhone(),
                "company", recruiter.getCompany(),
                "designation", recruiter.getDesignation()
        );
    }


    public Map<String, Object> getRecruiterByPhone(String phone) {

        if (!Validator.validatePhone(phone)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid phone number. Please check your phone number and try again!"
            );
        }

        User user = userRepo.findByPhone(phone)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No user found with phone number: +91-" + phone
                ));

        if (!"Recruiter".equalsIgnoreCase(user.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "User is not a recruiter"
            );
        }

        Recruiter recruiter = recruiterRepo.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recruiter profile not found for phone number: +91-" + phone
                ));

        String name = user.getFirstName() + " " + user.getLastName();

        return Map.of(
                "id", user.getUserId(),
                "name", name,
                "email", user.getEmail(),
                "phone", user.getPhone(),
                "company", recruiter.getCompany(),
                "designation", recruiter.getDesignation()
        );
    }


    public List<Map<String, Object>> getAllCandidate()
    {
        List<Candidate> getAllCandidate = candidateRepo.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        if(getAllCandidate.size()>0)
        {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < getAllCandidate.size(); i++)
            {

                Map<String, Object> map = new LinkedHashMap<>();
                String name = getAllCandidate.get(i).getUser().getFirstName()+" "+getAllCandidate.get(i).getUser().getLastName();
                map.put("cid", getAllCandidate.get(i).getUser().getUserId());
                map.put("name", name);
                map.put("email", getAllCandidate.get(i).getUser().getEmail());
                map.put("phone", getAllCandidate.get(i).getUser().getPhone());
                map.put("company", getAllCandidate.get(i).getCurrentCompany());
                map.put("yoe", getAllCandidate.get(i).getTotalYearsOfExperience());
                map.put("skill1", getAllCandidate.get(i).getPrimarySkill());
                map.put("skill2", getAllCandidate.get(i).getSecondarySkill());
                result.add(map);


            }
            return result;
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No candidate record available in database.");
        }
    }
}
