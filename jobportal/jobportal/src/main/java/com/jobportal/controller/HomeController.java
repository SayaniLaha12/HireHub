package com.jobportal.controller;

import com.jobportal.model.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final JobService jobService;
    private final UserService userService;
    private final ApplicationService applicationService;

    public HomeController(JobService jobService, UserService userService,
                          ApplicationService applicationService) {
        this.jobService = jobService;
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("recentJobs", jobService.findRecentJobs());
        model.addAttribute("totalJobs", jobService.countActiveJobs());
        model.addAttribute("totalSeekers", userService.countJobSeekers());
        model.addAttribute("totalEmployers", userService.countEmployers());
        model.addAttribute("totalApplications", applicationService.countAll());
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        if (auth == null) return "redirect:/login";

        User user = userService.findByEmail(auth.getName()).orElse(null);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);

        if (user.getRole() == User.Role.EMPLOYER) {
            var postedJobs = jobService.findByEmployer(user);
            model.addAttribute("postedJobs", postedJobs);
            long totalApplications = postedJobs.stream()
                .mapToLong(j -> j.getApplications().size()).sum();
            model.addAttribute("totalApplications", totalApplications);
            return "dashboard/employer";
        } else if (user.getRole() == User.Role.JOB_SEEKER) {
            model.addAttribute("myApplications", applicationService.findByApplicant(user));
            model.addAttribute("recentJobs", jobService.findRecentJobs());
            return "dashboard/seeker";
        } else {
            model.addAttribute("totalJobs", jobService.countAllJobs());
            model.addAttribute("totalUsers", userService.countAll());
            model.addAttribute("totalApplications", applicationService.countAll());
            return "dashboard/admin";
        }
    }
}
