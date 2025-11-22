package com.example.service;

import com.example.entity.Category;
import com.example.entity.Course;
import com.example.entity.Enrollment;
import com.example.entity.User;
import com.example.repository.CourseRepository;
import com.example.repository.EnrollmentRepository;
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
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private User student;
    private User teacher;
    private Course course;
    private Enrollment enrollment;

    @BeforeEach
    void setUp() {

        teacher = User.builder()
                .id(1L)
                .name("Teacher")
                .email("student@test.com")
                .role("TEACHER")
                .build();

        student = User.builder()
                .id(1L)
                .name("Student")
                .email("student@test.com")
                .role("STUDENT")
                .build();

        course = Course.builder()
                .id(1L)
                .title("Test Course")
                .teacher(teacher)
                .build();

        enrollment = Enrollment.builder().id(1L).student(student).course(course).enrollDate(LocalDate.now()).status("Active").build();
    }

    @Test
    void shouldEnrollStudent() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        Enrollment result = enrollmentService.enrollStudent(1L, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getStudent()).isEqualTo(student);
        assertThat(result.getCourse()).isEqualTo(course);
        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void shouldThrowExceptionWhenAlreadyEnrolled() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(true);

        assertThatThrownBy(() -> enrollmentService.enrollStudent(1L, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Студент уже записан на этот курс");

        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void shouldUnenrollStudent() {
        when(enrollmentRepository.findByStudentIdAndCourseId(1L, 1L))
                .thenReturn(Optional.of(enrollment));

        enrollmentService.unenrollStudent(1L, 1L);

        verify(enrollmentRepository).delete(enrollment);
    }

    @Test
    void shouldCheckIfStudentEnrolled() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(true);

        boolean enrolled = enrollmentService.isStudentEnrolled(1L, 1L);

        assertThat(enrolled).isTrue();
    }
}
