package com.jobportal.controller;

import org.springframework.web.multipart.MultipartFile;
import com.jobportal.model.Application;
import com.jobportal.model.Job;
import com.jobportal.model.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class JobController {

    private final JobService jobService;
    private final UserService userService;
    private final ApplicationService applicationService;

    public JobController(JobService jobService, UserService userService,
                         ApplicationService applicationService) {
        this.jobService = jobService;
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @GetMapping("/jobs")
    public String listJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Job.JobType jobType,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Page<Job> jobPage = jobService.searchJobs(keyword, location, category, jobType, page, 9);
        model.addAttribute("jobs", jobPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", jobPage.getTotalPages());
        model.addAttribute("totalJobs", jobPage.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("location", location);
        model.addAttribute("category", category);
        model.addAttribute("jobType", jobType);
        model.addAttribute("jobTypes", Job.JobType.values());
        return "jobs/list";
    }

    @GetMapping("/jobs/{id}")
    public String viewJob(@PathVariable Long id, Authentication auth, Model model) {
        Job job = jobService.findById(id).orElse(null);
        if (job == null) return "redirect:/jobs";

        model.addAttribute("job", job);

        if (auth != null) {
            User user = userService.findByEmail(auth.getName()).orElse(null);
            model.addAttribute("currentUser", user);
            if (user != null && user.getRole() == User.Role.JOB_SEEKER) {
                model.addAttribute("hasApplied", applicationService.hasApplied(job, user));
            }
        }
        return "jobs/detail";
    }

    @GetMapping("/employer/jobs/new")
    public String newJobForm(Model model) {
        model.addAttribute("job", new Job());
        model.addAttribute("jobTypes", Job.JobType.values());
        model.addAttribute("experienceLevels", Job.ExperienceLevel.values());
        return "employer/job-form";
    }

    @PostMapping("/employer/jobs/new")
    public String postJob(@ModelAttribute Job job,
                          Authentication auth,
                          RedirectAttributes redirectAttributes) {
        User employer = userService.findByEmail(auth.getName()).orElseThrow();
        job.setEmployer(employer);
        job.setCompany(employer.getCompanyName() != null ? employer.getCompanyName() : employer.getFullName());
        jobService.postJob(job);
        redirectAttributes.addFlashAttribute("success", "Job posted successfully!");
        return "redirect:/dashboard";
    }

    @PostMapping("/employer/jobs/{id}/toggle")
    public String toggleJob(@PathVariable Long id, Authentication auth, RedirectAttributes ra) {
        User employer = userService.findByEmail(auth.getName()).orElseThrow();
        jobService.toggleActive(id, employer);
        ra.addFlashAttribute("success", "Job status updated.");
        return "redirect:/dashboard";
    }

    @PostMapping("/employer/jobs/{id}/delete")
    public String deleteJob(@PathVariable Long id, Authentication auth, RedirectAttributes ra) {
        User employer = userService.findByEmail(auth.getName()).orElseThrow();
        jobService.deleteJob(id, employer);
        ra.addFlashAttribute("success", "Job deleted.");
        return "redirect:/dashboard";
    }

    @GetMapping("/employer/jobs/{id}/applications")
    public String viewApplications(@PathVariable Long id, Authentication auth, Model model) {
        Job job = jobService.findById(id).orElse(null);
        if (job == null) return "redirect:/dashboard";
        model.addAttribute("job", job);
        model.addAttribute("applications", applicationService.findByJob(job));
        model.addAttribute("statuses", Application.Status.values());
        return "employer/applications";
    }

    @PostMapping("/employer/applications/{id}/status")
    public String updateApplicationStatus(@PathVariable Long id,
                                          @RequestParam Application.Status status,
                                          @RequestParam(required = false) String note,
                                          RedirectAttributes ra) {
        Application app = applicationService.updateStatus(id, status, note);
        ra.addFlashAttribute("success", "Status updated to: " + status.getDisplayName());
        return "redirect:/employer/jobs/" + app.getJob().getId() + "/applications";
    }

    @PostMapping("/jobs/{id}/apply")
    public String applyForJob(@PathVariable Long id,
                              @RequestParam(required = false) String coverLetter,
                              @RequestParam(required = false) MultipartFile resumeFile,
                              Authentication auth,
                              RedirectAttributes ra) {
    Job job = jobService.findById(id).orElse(null);
    if (job == null) return "redirect:/jobs";
    User applicant = userService.findByEmail(auth.getName()).orElseThrow();
    try {
        String resumeFileName = null;
        if (resumeFile != null && !resumeFile.isEmpty()) {
            String uploadDir = System.getProperty("user.home") + "/uploads/";
            java.io.File dir = new java.io.File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            resumeFileName = System.currentTimeMillis() + "_" + resumeFile.getOriginalFilename();
            resumeFile.transferTo(new java.io.File(uploadDir + resumeFileName));
        }
        applicationService.apply(job, applicant, coverLetter, resumeFileName);
        ra.addFlashAttribute("success", "Application submitted successfully!");
    } catch (Exception e) {
        ra.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/jobs/" + id;   
    }

    @GetMapping("/seeker/applications")
    public String myApplications(Authentication auth, Model model) {
        User seeker = userService.findByEmail(auth.getName()).orElseThrow();
        model.addAttribute("applications", applicationService.findByApplicant(seeker));
        return "seeker/applications";
    }

    @GetMapping("/profile")
    public String viewProfile(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName()).orElseThrow();
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute User updatedUser,
                                Authentication auth,
                                RedirectAttributes ra) {
        User user = userService.findByEmail(auth.getName()).orElseThrow();
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhone(updatedUser.getPhone());
        user.setLocation(updatedUser.getLocation());
        user.setProfileSummary(updatedUser.getProfileSummary());
        user.setSkills(updatedUser.getSkills());
        user.setCompanyName(updatedUser.getCompanyName());
        user.setCompanyWebsite(updatedUser.getCompanyWebsite());
        user.setCompanyDescription(updatedUser.getCompanyDescription());
        userService.updateProfile(user);
        ra.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/profile";
    }
    @GetMapping("/resume/{filename}")
    public void downloadResume(@PathVariable String filename,
                           jakarta.servlet.http.HttpServletResponse response) throws Exception {
        String uploadDir = System.getProperty("user.home") + "/uploads/";
        java.io.File file = new java.io.File(uploadDir + filename);
        if (file.exists()) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            java.nio.file.Files.copy(file.toPath(), response.getOutputStream());
         }
    }
}
