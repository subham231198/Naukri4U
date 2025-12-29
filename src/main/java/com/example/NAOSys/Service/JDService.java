package com.example.NAOSys.Service;

import com.example.NAOSys.CustomMethods.Validator;
import com.example.NAOSys.Entity.JobDescription;
import com.example.NAOSys.Entity.Recruiter;
import com.example.NAOSys.Repository.JDRepo;
import com.example.NAOSys.Repository.RecruiterRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Transactional
public class JDService
{
    @Autowired
    JDRepo jdRepo;

    @Autowired
    RecruiterRepo recruiterRepo;

    public Map<String, Object> addJobDescription(JobDescription jd, Long rec_id)
    {
        Optional<Recruiter> getRec = recruiterRepo.findById(rec_id);
        if (getRec.isPresent())
        {
            String companyName = getRec.get().getCompany();
            jd.setDate_of_posting(Validator.locatDate());
            jd.setAdded_by(rec_id);
            jd.setCompanyName(companyName);
            jd.setJob_Status("Accepting Job Applications");
            jdRepo.save(jd);

            return Map.of("message","Job description submitted successfully");
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Not able to submit job details!");
    }

    public List<Map<String, Object>> getJobAllDescription()
    {
        List<JobDescription> getAllJobs = jdRepo.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        if(getAllJobs.size()>0)
        {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < getAllJobs.size(); i++)
            {
                Long rec_id = getAllJobs.get(i).getAdded_by();
                Optional<Recruiter> getRec = recruiterRepo.findById(rec_id);
                if (getRec.isPresent()) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("jid", getAllJobs.get(i).getJob_id());
                    map.put("headline", getAllJobs.get(i).getHeadline());
                    map.put("job_description", getAllJobs.get(i).getJobDescription());
                    map.put("company_name", getAllJobs.get(i).getCompanyName());
                    map.put("exp_range", getAllJobs.get(i).getDesiredExperience_min() + "-" + getAllJobs.get(i).getDesiredExperience_max());
                    map.put("salary_range", getAllJobs.get(i).getSalaryOffered_min() + "-" + getAllJobs.get(i).getSalaryOffered_max());
                    map.put("recruiter", getRec.get().getUser().getFirstName() + " " + getRec.get().getUser().getLastName());
                    map.put("recruiter_designation", getRec.get().getDesignation());
                    map.put("skill_required", getAllJobs.get(i).getSkills());
                    map.put("dop", getAllJobs.get(i).getDate_of_posting());
                    map.put("ldos", getAllJobs.get(i).getApply_before());
                    result.add(map);
                }

            }
            return result;
        }
        else
        {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No job details found in record");
        }
    }

    public Map<String, Object> getJobDetailsByID(Long jd_id)
    {
        Optional<JobDescription> getJob = jdRepo.findById(jd_id);
        if(getJob.isPresent())
        {
            Long rec_id = getJob.get().getAdded_by();
            Optional<Recruiter> getRec = recruiterRepo.findById(rec_id);
            Map<String, Object> map = new LinkedHashMap<>();
            if(getRec.isPresent()) {
                map.put("jid", getJob.get().getJob_id());
                map.put("headline", getJob.get().getHeadline());
                map.put("job_description", getJob.get().getJobDescription());
                map.put("company_name", getJob.get().getCompanyName());
                map.put("exp_range", getJob.get().getDesiredExperience_min() + "-" + getJob.get().getDesiredExperience_max());
                map.put("salary_range", getJob.get().getSalaryOffered_min() + "-" + getJob.get().getSalaryOffered_max());
                map.put("recruiter", getRec.get().getUser().getFirstName() + " " + getRec.get().getUser().getLastName());
                map.put("recruiter_designation", getRec.get().getDesignation());
                map.put("skill_required", getJob.get().getSkills());
                map.put("dop", getJob.get().getDate_of_posting());
                map.put("ldos", getJob.get().getApply_before());

                return map;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No record found with the given job id");
    }


}
