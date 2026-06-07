package com.jobportal.service;

import com.jobportal.model.Job;
import com.jobportal.model.User;
import com.jobportal.repository.JobRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Transactional
    public Job postJob(Job job) {
        return jobRepository.save(job);
    }

    public Optional<Job> findById(Long id) {
        return jobRepository.findById(id);
    }

    public Page<Job> findActiveJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("postedAt").descending());
        return jobRepository.findByActiveTrue(pageable);
    }

    public Page<Job> searchJobs(String keyword, String location, String category,
                                 Job.JobType jobType, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("postedAt").descending());
        String kw  = (keyword  == null || keyword.isBlank())  ? null : keyword.trim();
        String loc = (location == null || location.isBlank()) ? null : location.trim();
        String cat = (category == null || category.isBlank()) ? null : category.trim();
        return jobRepository.searchJobs(kw, loc, cat, jobType, pageable);
    }

    public List<Job> findByEmployer(User employer) {
        return jobRepository.findByEmployerOrderByPostedAtDesc(employer);
    }

    public List<Job> findRecentJobs() {
        return jobRepository.findTop6ByActiveTrueOrderByPostedAtDesc();
    }

    @Transactional
    public void toggleActive(Long jobId, User employer) {
        jobRepository.findById(jobId).ifPresent(job -> {
            if (job.getEmployer().getId().equals(employer.getId())) {
                job.setActive(!job.isActive());
                jobRepository.save(job);
            }
        });
    }

    @Transactional
    public void deleteJob(Long jobId, User employer) {
        jobRepository.findById(jobId).ifPresent(job -> {
            if (job.getEmployer().getId().equals(employer.getId())) {
                jobRepository.delete(job);
            }
        });
    }

    public long countActiveJobs() {
        return jobRepository.countByActiveTrue();
    }

    public long countAllJobs() {
        return jobRepository.count();
    }
}
