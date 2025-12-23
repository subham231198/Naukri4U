package com.example.NAOSys.Repository;

import com.example.NAOSys.Entity.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JDRepo extends JpaRepository<JobDescription, Long>
{

}
