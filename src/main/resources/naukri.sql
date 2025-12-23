-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 23, 2025 at 10:39 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `naukri`
--

-- --------------------------------------------------------

--
-- Table structure for table `candidate`
--

CREATE TABLE `candidate` (
  `cid` bigint(20) NOT NULL,
  `currentctc` varchar(255) DEFAULT NULL,
  `current_company` varchar(255) DEFAULT NULL,
  `expectedctc` varchar(255) DEFAULT NULL,
  `primary_skill` varchar(255) DEFAULT NULL,
  `relevant_years_of_experience` varchar(255) DEFAULT NULL,
  `secondary_skill` varchar(255) DEFAULT NULL,
  `tertiary_skill` varchar(255) DEFAULT NULL,
  `total_years_of_experience` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `candidate`
--

INSERT INTO `candidate` (`cid`, `currentctc`, `current_company`, `expectedctc`, `primary_skill`, `relevant_years_of_experience`, `secondary_skill`, `tertiary_skill`, `total_years_of_experience`, `user_id`) VALUES
(1, '4,70,000', 'Capgemini Technology Services India Limited', '8,50,000', 'Java', '2.11', 'SpringBoot', 'MySQL', '3', 7);

-- --------------------------------------------------------

--
-- Table structure for table `job_application`
--

CREATE TABLE `job_application` (
  `id` bigint(20) NOT NULL,
  `candidate_id` bigint(20) DEFAULT NULL,
  `job_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `job_application`
--

INSERT INTO `job_application` (`id`, `candidate_id`, `job_id`) VALUES
(1, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `job_description`
--

CREATE TABLE `job_description` (
  `job_id` bigint(20) NOT NULL,
  `job_description` text DEFAULT NULL,
  `added_by` bigint(20) DEFAULT NULL,
  `apply_before` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `date_of_posting` varchar(255) DEFAULT NULL,
  `desired_experience_max` varchar(255) DEFAULT NULL,
  `desired_experience_min` varchar(255) DEFAULT NULL,
  `headline` varchar(255) DEFAULT NULL,
  `job_status` varchar(255) DEFAULT NULL,
  `salary_offered_max` varchar(255) DEFAULT NULL,
  `salary_offered_min` varchar(255) DEFAULT NULL,
  `skills` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `job_description`
--

INSERT INTO `job_description` (`job_id`, `job_description`, `added_by`, `apply_before`, `company_name`, `date_of_posting`, `desired_experience_max`, `desired_experience_min`, `headline`, `job_status`, `salary_offered_max`, `salary_offered_min`, `skills`) VALUES
(1, 'As a Full Stack Developer, candidates should be comfortable around both front-end and back-end coding languages, development frameworks and third-party libraries. One should also be a team player and familiar with Agile methodologies. Primary Skills - Java, Spring Boot, Microservices, React.JS, MySQL Secondary Skills - CI/CD, Docker, AWS, Kafka Experience - 3 to 5 years', 2, '15/09/2024', 'Capgemini Technology Services India Limited', '09/07/2024', '5', '3', 'Java Full-Stack Developer', 'Accepting Job Applications', '1500000', '850000', 'Java, SpringBoot, Kafka, MySQL, React.JS'),
(2, 'Skilled in Java, J2EE, Spring, Hibernate, Springboot, MySQL, Webservices REST & SOAP, Apache Kafka, ActiveMQ, JUnit, Git, Maven, Linux shell script , Hazlecast cache, JBOSS/Weblogic/tomcat, Docker, Azure Kubernetes Service (AKS), SonarQube, Datastructures & Algorithms. Should have worked in development project with development experience as Backend Developer. Should have worked in Jenkins or similar CI/CD and must be familiar with branching strategies.  Excellent problem solving/troubleshooting skills on advanced Java/J2EE technologies and SpringBoot framework. Knowledge of No SQL database and Redis will be an added advantage. Should be motivated, self-organizing and able to work independently', 3, '23/07/2024', 'Tata Consultancy Services', '09/07/2024', '10', '5', 'Advanced Java Developer (Full Stack Developer)', 'Accepting Job Applications', '2000000', '1500000', 'Java, SpringBoot, Kafka, IBM DB2, Angular');

-- --------------------------------------------------------

--
-- Table structure for table `recruiter`
--

CREATE TABLE `recruiter` (
  `id` bigint(20) NOT NULL,
  `company` varchar(255) DEFAULT NULL,
  `designation` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `recruiter`
--

INSERT INTO `recruiter` (`id`, `company`, `designation`, `user_id`) VALUES
(2, 'Capgemini Technology Services India Limited', 'Talent Acquisition Head HR', 2),
(3, 'Tata Consultancy Services', 'Talent Acquisition Team', 3),
(4, 'Morgan Stanley', 'Talent Acquisition Team', 4),
(5, 'Morgan Stanley', 'Talent Acquisition Team', 5),
(6, 'Accenture', 'Talent Acquisition Team (Human Resources)', 6),
(7, 'HSBC Technology India Pvt Ltd', 'Talent Acquisition Manager', 8),
(8, 'Amdocs Pvt Ltd', 'Talent Acquisition Specialist', 9);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `pincode` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `address`, `city`, `email`, `first_name`, `last_name`, `password`, `phone`, `pincode`, `role`, `state`) VALUES
(2, '117 Besides Green Glen Layour, Bellandur', 'Bengaluru', 'amit@capgemini.com', 'Amit', 'Bhatia', 'Amit@123', '7523758092', '560103', 'Recruiter', 'Karnataka'),
(3, '126 Besides Green Glen Layour, Bellandur', 'Bengaluru', 'simran@tcs.com', 'Simran', 'Sharma', 'Simran@123', '9867098103', '560103', 'Recruiter', 'Karnataka'),
(4, '1120 N ST', 'Mumbai', 'ambika@morganstanley.com', 'Ambika', 'Das', 'Ambika@123', '9641209678', '400012', 'Recruiter', 'Maharashtra'),
(5, '12A Besides Sakra Hospiral, Bellandur', 'Bengaluru', 'ganeshan@morganstanley.com', 'Ganeshan', 'Ramachandran', 'Ganeshan@123', '9845023412', '560104', 'Recruiter', 'Karnataka'),
(6, '111 BTM Layout', 'Bengaluru', 'tanisha@accenture.com', 'Tanisha', 'Baluja', 'Tanisha@123', '7645098124', '560123', 'Recruiter', 'Karnataka'),
(7, '120 BTM Layout', 'Bengaluru', 's.shukla@capgemini.com', 'Siddharth', 'Shukla', 'Shukla@123', '7699098137', '560123', 'Candidate', 'Karnataka'),
(8, '12B, Hinjewadi, near Blue Ridge Society', 'Pune', 'aishwarya.salivati@hsbc.co.in', 'Aishwarya', 'Salivati', 'aishwarya@1197', '9714279033', '411039', 'Recruiter', 'Maharashtra'),
(9, '13A, Hinjewadi, near Blue Ridge Society', 'Pune', 'rohan.joshi@amdocs.com', 'Rohan', 'Joshi', 'rohan111@amdocs', '9898903467', '411039', 'Recruiter', 'Maharashtra');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `candidate`
--
ALTER TABLE `candidate`
  ADD PRIMARY KEY (`cid`),
  ADD UNIQUE KEY `UK67uyxu00tx9l55fptjvodc0gl` (`user_id`);

--
-- Indexes for table `job_application`
--
ALTER TABLE `job_application`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `job_description`
--
ALTER TABLE `job_description`
  ADD PRIMARY KEY (`job_id`);

--
-- Indexes for table `recruiter`
--
ALTER TABLE `recruiter`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK5r95851hmt8lx6l5u8mtsko3i` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
  ADD UNIQUE KEY `UK589idila9li6a4arw1t8ht1gx` (`phone`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `candidate`
--
ALTER TABLE `candidate`
  MODIFY `cid` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `job_application`
--
ALTER TABLE `job_application`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `job_description`
--
ALTER TABLE `job_description`
  MODIFY `job_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `recruiter`
--
ALTER TABLE `recruiter`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `candidate`
--
ALTER TABLE `candidate`
  ADD CONSTRAINT `FKj9h7beyp5gsdtdb20km82b4fl` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `recruiter`
--
ALTER TABLE `recruiter`
  ADD CONSTRAINT `FK63kq3uyt2p3i32pjo1nfin63a` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
