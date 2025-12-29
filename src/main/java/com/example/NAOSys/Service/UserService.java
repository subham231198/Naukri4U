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

    public String addCandidate(User user, Candidate candidate)
    {
        Optional<User> validateEmailExists = userRepo.findByEmail(user.getEmail());
        Optional<User> validatePhoneExists = userRepo.findByPhone(user.getPhone());
        if(validateEmailExists.isPresent())
        {
            return "Candidate profile already exists with the provided email address "+user.getEmail();
        }
        else if(validatePhoneExists.isPresent())
        {
            return "Candidate profile already exists with the provided phone number "+user.getPhone();
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
                        return "Candidate has been added successfully";
                    }
                    else
                    {
                        return "Invalid pincode!";
                    }
                }
                else
                {
                    return "Invalid phone number";
                }
            }
            else
            {
                return "Invalid email address";
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
                map.put("id", getAllRec.get(j).getUser().getUser_id());
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

    public Map<String, Object> getRecruiterByEmail(String email)
    {
        if(Validator.validateEmail(email, "Recruiter"))
        {
            Optional<User> getUser = userRepo.findByEmail(email);
            if(getUser.isPresent())
            {
                Long user_id = getUser.get().getUser_id();
                Optional<Recruiter> getRec = recruiterRepo.findById(user_id);
                if (getRec.isPresent())
                {
                    if(getUser.get().getRole().equalsIgnoreCase("Recruiter"))
                    {
                        String name = getRec.get().getUser().getFirstName()+" "+getRec.get().getUser().getLastName();
                        return Map.of(
                                "id", getRec.get().getUser().getUser_id(),
                                "name", name,
                                "email", getRec.get().getUser().getEmail(),
                                "phone", getRec.get().getUser().getPhone(),
                                "company", getRec.get().getCompany(),
                                "designation", getRec.get().getDesignation()
                        );
                    }
                    else
                    {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No record available with the provided email "+email);
                    }
                }
            }
            else
            {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No record available with the provided email "+email);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email address. Please check your email and try again!");
    }

    public Map<String, Object> getRecruiterByPhone(String phone)
    {
        if(Validator.validatePhone(phone))
        {
            Optional<User> getUser = userRepo.findByPhone(phone);
            if(getUser.isPresent())
            {
                Long user_id = getUser.get().getUser_id();
                Optional<Recruiter> getRec = recruiterRepo.findById(user_id);
                if (getRec.isPresent())
                {
                    String name = getRec.get().getUser().getFirstName()+" "+getRec.get().getUser().getLastName();
                    return Map.of(
                            "id", getRec.get().getUser().getUser_id(),
                            "name", name,
                            "email", getRec.get().getUser().getEmail(),
                            "phone", getRec.get().getUser().getPhone(),
                            "company", getRec.get().getCompany(),
                            "designation", getRec.get().getDesignation()
                    );
                }
            }
            else
            {
                log.error("No record available with the provided phone number +91-"+phone);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No record available with the provided phone number +91-"+phone);
            }
        }
        else
        {
            log.error("Invalid phone number. Please check your phone number and try again!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid phone number. Please check your phone number and try again!");
        }
        return Map.of();
    }
    public String getAllCandidate()
    {
        List<Candidate> getAllCandidate = candidateRepo.findAll();
        if(getAllCandidate.size()>0)
        {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < getAllCandidate.size(); i++)
            {
                sb.append("|").append("CID: ").append(getAllCandidate.get(i).getCID());
                sb.append("|").append("Name: ").append(getAllCandidate.get(i).getUser().getFirstName());
                sb.append(" ").append(getAllCandidate.get(i).getUser().getLastName());
                sb.append("|").append("Email: ").append(getAllCandidate.get(i).getUser().getEmail());
                sb.append("|").append("Phone: +91-").append(getAllCandidate.get(i).getUser().getPhone());
                sb.append("|").append("Current Company: ").append(getAllCandidate.get(i).getCurrentCompany());
                sb.append("|").append("Total years of experience: ").append(getAllCandidate.get(i).getTotalYearsOfExperience());
                sb.append("|").append("Relevant years of experience: ").append(getAllCandidate.get(i).getRelevantYearsOfExperience());
                sb.append("|").append("Primary Skill").append(getAllCandidate.get(i).getPrimarySkill());
                sb.append("|").append("Secondary Skill: ").append(getAllCandidate.get(i).getSecondarySkill());
                sb.append("\n");
            }
            return sb.toString();
        }
        else
        {
            return "No candidate record available in database.";
        }
    }
}
