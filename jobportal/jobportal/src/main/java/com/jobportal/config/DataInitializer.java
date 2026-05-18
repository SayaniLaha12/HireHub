package com.jobportal.config;

import com.jobportal.model.Application;
import com.jobportal.model.Job;
import com.jobportal.model.User;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = Logger.getLogger(DataInitializer.class.getName());

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, JobRepository jobRepository,
                           ApplicationRepository applicationRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        // Admin
        User admin = new User();
        admin.setFirstName("Admin"); admin.setLastName("User");
        admin.setEmail("admin@jobportal.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(User.Role.ADMIN); admin.setEnabled(true);
        userRepository.save(admin);

        // Employer 1
        User employer1 = new User();
        employer1.setFirstName("Sarah"); employer1.setLastName("Johnson");
        employer1.setEmail("employer@techcorp.com");
        employer1.setPassword(passwordEncoder.encode("employer123"));
        employer1.setRole(User.Role.EMPLOYER);
        employer1.setCompanyName("TechCorp Solutions");
        employer1.setCompanyWebsite("https://techcorp.example.com");
        employer1.setCompanyDescription("Leading software development company");
        employer1.setLocation("Mumbai, India"); employer1.setEnabled(true);
        userRepository.save(employer1);

        // Employer 2
        User employer2 = new User();
        employer2.setFirstName("Raj"); employer2.setLastName("Patel");
        employer2.setEmail("hr@innovatex.com");
        employer2.setPassword(passwordEncoder.encode("employer123"));
        employer2.setRole(User.Role.EMPLOYER);
        employer2.setCompanyName("InnovateX");
        employer2.setLocation("Bangalore, India"); employer2.setEnabled(true);
        userRepository.save(employer2);

        // Job Seeker 1
        User seeker1 = new User();
        seeker1.setFirstName("Priya"); seeker1.setLastName("Sharma");
        seeker1.setEmail("priya@gmail.com");
        seeker1.setPassword(passwordEncoder.encode("seeker123"));
        seeker1.setRole(User.Role.JOB_SEEKER);
        seeker1.setPhone("+91-9876543210");
        seeker1.setLocation("Pune, India");
        seeker1.setSkills("Java, Spring Boot, React, MySQL");
        seeker1.setProfileSummary("Full-stack developer with 3 years of experience.");
        seeker1.setEnabled(true);
        userRepository.save(seeker1);

        // Job Seeker 2
        User seeker2 = new User();
        seeker2.setFirstName("Amit"); seeker2.setLastName("Kumar");
        seeker2.setEmail("amit@gmail.com");
        seeker2.setPassword(passwordEncoder.encode("seeker123"));
        seeker2.setRole(User.Role.JOB_SEEKER);
        seeker2.setLocation("Delhi, India");
        seeker2.setSkills("Python, Django, Machine Learning, TensorFlow");
        seeker2.setEnabled(true);
        userRepository.save(seeker2);

        // Job 1
        Job job1 = new Job();
        job1.setTitle("Senior Java Developer");
        job1.setDescription("We are looking for an experienced Java developer with Spring Boot expertise. You will design and build scalable backend services, collaborate with the frontend team, and mentor junior developers.");
        job1.setCompany("TechCorp Solutions"); job1.setLocation("Mumbai, India");
        job1.setJobType(Job.JobType.FULL_TIME); job1.setExperienceLevel(Job.ExperienceLevel.SENIOR);
        job1.setSalaryRange("12 LPA - 20 LPA");
        job1.setSkills("Java, Spring Boot, Microservices, AWS, MySQL");
        job1.setCategory("IT"); job1.setVacancies(2);
        job1.setDeadline(LocalDateTime.now().plusMonths(1));
        job1.setEmployer(employer1); job1.setActive(true);
        jobRepository.save(job1);

        // Job 2
        Job job2 = new Job();
        job2.setTitle("React Frontend Developer");
        job2.setDescription("Join our team as a React developer to build beautiful and performant web applications. You'll work with designers to implement pixel-perfect UIs.");
        job2.setCompany("TechCorp Solutions"); job2.setLocation("Remote");
        job2.setJobType(Job.JobType.REMOTE); job2.setExperienceLevel(Job.ExperienceLevel.MID);
        job2.setSalaryRange("8 LPA - 14 LPA");
        job2.setSkills("React, JavaScript, TypeScript, CSS, REST APIs");
        job2.setCategory("IT"); job2.setVacancies(3);
        job2.setDeadline(LocalDateTime.now().plusMonths(2));
        job2.setEmployer(employer1); job2.setActive(true);
        jobRepository.save(job2);

        // Job 3
        Job job3 = new Job();
        job3.setTitle("Machine Learning Engineer");
        job3.setDescription("InnovateX is hiring an ML Engineer to build and deploy AI models. You'll work on NLP, computer vision, and recommendation systems.");
        job3.setCompany("InnovateX"); job3.setLocation("Bangalore, India");
        job3.setJobType(Job.JobType.FULL_TIME); job3.setExperienceLevel(Job.ExperienceLevel.MID);
        job3.setSalaryRange("15 LPA - 25 LPA");
        job3.setSkills("Python, TensorFlow, PyTorch, ML, NLP");
        job3.setCategory("IT"); job3.setVacancies(1);
        job3.setDeadline(LocalDateTime.now().plusMonths(1));
        job3.setEmployer(employer2); job3.setActive(true);
        jobRepository.save(job3);

        // Job 4
        Job job4 = new Job();
        job4.setTitle("Digital Marketing Specialist");
        job4.setDescription("We need a creative Digital Marketing Specialist. Manage SEO, SEM, social media campaigns, and content strategy to grow brand awareness.");
        job4.setCompany("InnovateX"); job4.setLocation("Delhi, India");
        job4.setJobType(Job.JobType.FULL_TIME); job4.setExperienceLevel(Job.ExperienceLevel.ENTRY);
        job4.setSalaryRange("4 LPA - 7 LPA");
        job4.setSkills("SEO, Google Ads, Social Media, Analytics, Content Writing");
        job4.setCategory("Marketing"); job4.setVacancies(2);
        job4.setEmployer(employer2); job4.setActive(true);
        jobRepository.save(job4);

        // Job 5
        Job job5 = new Job();
        job5.setTitle("DevOps Engineer");
        job5.setDescription("We are looking for a DevOps Engineer to streamline our CI/CD pipelines. Strong knowledge of Docker, Kubernetes, and AWS required.");
        job5.setCompany("TechCorp Solutions"); job5.setLocation("Mumbai, India");
        job5.setJobType(Job.JobType.FULL_TIME); job5.setExperienceLevel(Job.ExperienceLevel.SENIOR);
        job5.setSalaryRange("14 LPA - 22 LPA");
        job5.setSkills("Docker, Kubernetes, AWS, Jenkins, Linux, Terraform");
        job5.setCategory("IT"); job5.setVacancies(1);
        job5.setEmployer(employer1); job5.setActive(true);
        jobRepository.save(job5);

        // Job 6
        Job job6 = new Job();
        job6.setTitle("Java Intern");
        job6.setDescription("Summer internship for Java enthusiasts. Learn Spring Boot, REST APIs, and database design while working on real projects.");
        job6.setCompany("TechCorp Solutions"); job6.setLocation("Mumbai, India");
        job6.setJobType(Job.JobType.INTERNSHIP); job6.setExperienceLevel(Job.ExperienceLevel.ENTRY);
        job6.setSalaryRange("15,000/month stipend");
        job6.setSkills("Java, OOP, SQL, Basic Spring");
        job6.setCategory("IT"); job6.setVacancies(5);
        job6.setEmployer(employer1); job6.setActive(true);
        jobRepository.save(job6);

        // Applications
        Application app1 = new Application();
        app1.setJob(job1); app1.setApplicant(seeker1);
        app1.setCoverLetter("I have 3 years of Java and Spring Boot experience.");
        app1.setStatus(Application.Status.REVIEWING);
        applicationRepository.save(app1);

        Application app2 = new Application();
        app2.setJob(job3); app2.setApplicant(seeker2);
        app2.setCoverLetter("My ML background makes me a great fit.");
        app2.setStatus(Application.Status.SHORTLISTED);
        applicationRepository.save(app2);

        log.info("Demo data seeded! Open http://localhost:8080");
        log.info("Employer: employer@techcorp.com / employer123");
        log.info("Seeker:   priya@gmail.com / seeker123");
        log.info("Admin:    admin@jobportal.com / admin123");
    }
}
