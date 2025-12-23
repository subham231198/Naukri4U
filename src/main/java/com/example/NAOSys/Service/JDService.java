package com.example.NAOSys.Service;

import com.example.NAOSys.CustomMethods.Validator;
import com.example.NAOSys.Entity.JobDescription;
import com.example.NAOSys.Entity.Recruiter;
import com.example.NAOSys.Repository.JDRepo;
import com.example.NAOSys.Repository.RecruiterRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JDService
{
    @Autowired
    JDRepo jdRepo;

    @Autowired
    RecruiterRepo recruiterRepo;

    public String addJobDescription(JobDescription jd, Long rec_id)
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

            return "Job description submitted successfully";
        }
        return "Not able to submit job details!";
    }

    public String getJobAllDescription()
    {
        List<JobDescription> getAllJobs = jdRepo.findAll();
        if(getAllJobs.size()>0)
        {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < getAllJobs.size(); i++)
            {
                Long rec_id = getAllJobs.get(i).getAdded_by();
                Optional<Recruiter> getRec = recruiterRepo.findById(rec_id);
                if (getRec.isPresent())
                {
                    sb.append("|").append("Job ID: ").append(getAllJobs.get(i).getJob_id());
                    sb.append("|").append("Headline: ").append(getAllJobs.get(i).getHeadline());
                    sb.append("|").append("Job Description: ").append(getAllJobs.get(i).getJobDescription());
                    sb.append("|").append("Company Name: ").append(getAllJobs.get(i).getCompanyName());
                    sb.append("|").append("Experience Range: ").append(getAllJobs.get(i).getDesiredExperience_min()).append("-").append(getAllJobs.get(i).getDesiredExperience_max()).append(" years");
                    sb.append("|").append("Salary range: INR ").append(getAllJobs.get(i).getSalaryOffered_min()).append("-").append(getAllJobs.get(i).getSalaryOffered_max()).append(" Lakhs per anum");
                    sb.append("|").append("Recruiter Name: ").append(getRec.get().getUser().getFirstName()).append(" ").append(getRec.get().getUser().getLastName());
                    sb.append("|").append("Recruiter Designation: ").append(getRec.get().getDesignation());
                    sb.append("|").append("Skills Required: ").append(getAllJobs.get(i).getSkills());
                    sb.append("|").append("Date of posting: ").append(getAllJobs.get(i).getDate_of_posting());
                    sb.append("|").append("Last of submission: ").append(getAllJobs.get(i).getApply_before()).append("|");
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
        else
        {
            return "No job details found in record";
        }
    }

    public String getJobDetailsByID(Long jd_id)
    {
        Optional<JobDescription> getJob = jdRepo.findById(jd_id);
        if(getJob.isPresent())
        {
            Long rec_id = getJob.get().getAdded_by();
            Optional<Recruiter> getRec = recruiterRepo.findById(rec_id);
            if(getRec.isPresent()) {
                StringBuilder sb = new StringBuilder();
                sb.append("|").append("Job ID: ").append(getJob.get().getJob_id());
                sb.append("|").append("Headline: ").append(getJob.get().getHeadline());
                sb.append("|").append("Job Description: ").append(getJob.get().getJobDescription());
                sb.append("|").append("Company Name: ").append(getJob.get().getCompanyName());
                sb.append("|").append("Experience Range: ").append(getJob.get().getDesiredExperience_min()).append("-").append(getJob.get().getDesiredExperience_max()).append(" years");
                sb.append("|").append("Salary range: INR ").append(getJob.get().getSalaryOffered_min()).append("-").append(getJob.get().getSalaryOffered_max()).append(" Lakhs per anum");
                sb.append("|").append("Recruiter Name: ").append(getRec.get().getUser().getFirstName()).append(" ").append(getRec.get().getUser().getLastName());
                sb.append("|").append("Recruiter Designation: ").append(getRec.get().getDesignation());
                sb.append("|").append("Skills Required: ").append(getJob.get().getSkills());
                sb.append("|").append("Date of posting: ").append(getJob.get().getDate_of_posting());
                sb.append("|").append("Last of submission: ").append(getJob.get().getApply_before()).append("|");

                return sb.toString();
            }
        }
        return "No record found with the given job id";
    }


}
