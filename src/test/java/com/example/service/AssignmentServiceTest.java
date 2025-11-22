package com.example.service;

import com.example.entity.Assignment;
import com.example.entity.Lesson;
import com.example.entity.Submission;
import com.example.entity.User;
import com.example.repository.AssignmentRepository;
import com.example.repository.LessonRepository;
import com.example.repository.SubmissionRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    private Lesson lesson;
    private Assignment assignment;
    private User student;
    private Submission submission;

    @BeforeEach
    void setUp() {
        lesson = new Lesson();

        assignment = new Assignment(lesson, "Test Assignment", "Description",
                LocalDate.now().plusDays(7), 100);

        student = User.builder()
                .id(1L)
                .name("Student")
                .email("student@test.com")
                .role("STUDENT")
                .build();

        submission = Submission.builder()
                .id(1L)
                .assignment(assignment)
                .student(student)
                .content("Test")
                .build();
    }

    @Test
    void shouldCreateAssignment() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        Assignment created = assignmentService.createAssignment(1L, "Test", "Desc",
                LocalDate.now(), 100);

        assertThat(created).isNotNull();
        verify(assignmentRepository).save(any(Assignment.class));
    }

    @Test
    void shouldSubmitAssignment() {
        when(submissionRepository.existsByAssignmentIdAndStudentId(1L, 1L)).thenReturn(false);
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);

        Submission result = assignmentService.submitAssignment(1L, 1L, "Solution");

        assertThat(result).isNotNull();
        verify(submissionRepository).save(any(Submission.class));
    }

    @Test
    void shouldThrowExceptionWhenSubmissionAlreadyExists() {
        when(submissionRepository.existsByAssignmentIdAndStudentId(1L, 1L)).thenReturn(true);

        assertThatThrownBy(() -> assignmentService.submitAssignment(1L, 1L, "Solution"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Студент уже отправил это задание");
    }

    @Test
    void shouldGradeSubmission() {
        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);

        assignmentService.gradeSubmission(1L, 95, "Good work");

        verify(submissionRepository).save(submission);
        assertThat(submission.getScore()).isEqualTo(95);
        assertThat(submission.getFeedback()).isEqualTo("Good work");
    }
}