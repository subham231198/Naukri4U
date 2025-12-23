package com.example.NAOSys.Service;

import com.example.NAOSys.Entity.Candidate;
import com.example.NAOSys.Entity.JobApplication;
import com.example.NAOSys.Entity.JobDescription;
import com.example.NAOSys.Repository.CandidateRepo;
import com.example.NAOSys.Repository.JDRepo;
import com.example.NAOSys.Repository.JobAppRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JobAppService
{
    @Autowired
    private JobAppRepo jobAppRepo;

    @Autowired
    private CandidateRepo candidateRepo;

    @Autowired
    private JDRepo jdRepo;

    public String applyToJob(Long job_id, Long candidate_id, JobApplication jobApp) {
        Optional<Candidate> getCandidate = candidateRepo.findById(candidate_id);
        Optional<JobDescription> getJob = jdRepo.findById(job_id);

        if (getCandidate.isPresent() && getJob.isPresent()) {
            List<JobApplication> existingApplications = jobAppRepo.findAll();
            boolean isDuplicate = existingApplications.stream()
                    .anyMatch(app -> app.getCandidateId().equals(candidate_id) && app.getJd_id().equals(job_id));

            if (isDuplicate)
            {
                return "Candidate has already applied to this job!";
            }
            else
            {
                jobApp.setCandidateId(candidate_id);
                jobApp.setJd_id(job_id);
                JobApplication savedApp = jobAppRepo.save(jobApp);
                System.out.println("Saved JobApplication: {}" + savedApp);
                return "Successfully applied to job id " + jobApp.getJd_id();
            }
        }
        else
        {
            return "Invalid candidate or job description!";
        }
    }

    public String withdrawApplication(Long candidate_id, Long job_id)
    {
        List<JobApplication> applications = jobAppRepo.findAll();
        for (JobApplication application : applications) {
            if (application.getJd_id().equals(job_id) && application.getCandidateId().equals(candidate_id)) {
                jobAppRepo.deleteById(job_id);
                return "Job application has been successfully withdrawn by candidate!";
            }
        }
        return "No application exists in record";
    }
}
