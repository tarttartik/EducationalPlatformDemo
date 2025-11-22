package com.example.repository;

import com.example.entity.TestSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestSubmissionRepository extends JpaRepository<TestSubmission, Long> {
    List<TestSubmission> findByTestId(Long testId);

    List<TestSubmission> findByStudentId(Long studentId);

    Optional<TestSubmission> findByTestIdAndStudentId(Long testId, Long studentId);

    boolean existsByTestIdAndStudentId(Long testId, Long studentId);
}
