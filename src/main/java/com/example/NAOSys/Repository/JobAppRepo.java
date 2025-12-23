package com.example.NAOSys.Repository;

import com.example.NAOSys.Entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobAppRepo extends JpaRepository<JobApplication, Long>
{

}
