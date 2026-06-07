package com.jobportal.repository;

import com.jobportal.model.Job;
import com.jobportal.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByActiveTrue(Pageable pageable);

    List<Job> findByEmployerOrderByPostedAtDesc(User employer);

    @Query("SELECT j FROM Job j WHERE j.active = true AND " +
           "(:keyword IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
           "LOWER(j.description) LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
           "LOWER(j.company) LIKE LOWER(CONCAT('%',:keyword,'%'))) AND " +
           "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%',:location,'%'))) AND " +
           "(:category IS NULL OR LOWER(j.category) LIKE LOWER(CONCAT('%',:category,'%'))) AND " +
           "(:jobType IS NULL OR j.jobType = :jobType)")
    Page<Job> searchJobs(@Param("keyword") String keyword,
                         @Param("location") String location,
                         @Param("category") String category,
                         @Param("jobType") Job.JobType jobType,
                         Pageable pageable);

    long countByActiveTrue();

    List<Job> findTop6ByActiveTrueOrderByPostedAtDesc();
}
