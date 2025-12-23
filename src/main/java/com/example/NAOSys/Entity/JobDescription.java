package com.example.NAOSys.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "JobDescription")
public class JobDescription
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long job_id;
    private String headline;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String JobDescription;

    private String skills;
    private String date_of_posting;
    private String apply_before;
    private String companyName;
    private String desiredExperience_min;
    private String desiredExperience_max;
    private String salaryOffered_min;
    private String salaryOffered_max;
    private Long added_by;
    private String job_Status;
}
