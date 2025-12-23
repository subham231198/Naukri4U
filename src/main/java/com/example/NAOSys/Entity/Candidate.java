package com.example.NAOSys.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "candidate")
public class Candidate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CID;
    private String totalYearsOfExperience;
    private String relevantYearsOfExperience;
    private String currentCTC;
    private String expectedCTC;
    private String currentCompany;
    private String primarySkill;
    private String secondarySkill;
    private String tertiarySkill;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
