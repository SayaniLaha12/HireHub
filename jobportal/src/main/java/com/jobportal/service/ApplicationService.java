package com.jobportal.service;

import com.jobportal.model.Application;
import com.jobportal.model.Job;
import com.jobportal.model.User;
import com.jobportal.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Transactional
    public Application apply(Job job, User applicant, String coverLetter, String resumeLink) {
        if (applicationRepository.existsByJobAndApplicant(job, applicant)) {
            throw new RuntimeException("You have already applied for this job.");
        }
        Application application = new Application();
        application.setJob(job);
        application.setApplicant(applicant);
        application.setCoverLetter(coverLetter);
        application.setResumeLink(resumeLink);
        application.setStatus(Application.Status.PENDING);
        return applicationRepository.save(application);
    }

    public boolean hasApplied(Job job, User applicant) {
        return applicationRepository.existsByJobAndApplicant(job, applicant);
    }

    public List<Application> findByApplicant(User applicant) {
        return applicationRepository.findByApplicantOrderByAppliedAtDesc(applicant);
    }

    public List<Application> findByJob(Job job) {
        return applicationRepository.findByJobOrderByAppliedAtDesc(job);
    }

    public Optional<Application> findById(Long id) {
        return applicationRepository.findById(id);
    }

    @Transactional
    public Application updateStatus(Long applicationId, Application.Status status, String note) {
        Application app = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));
        app.setStatus(status);
        app.setEmployerNote(note);
        return applicationRepository.save(app);
    }

    public long countByApplicant(User applicant) {
        return applicationRepository.countByApplicant(applicant);
    }

    public long countAll() {
        return applicationRepository.count();
    }
}
