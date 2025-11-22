package com.example.service;

import com.example.entity.Category;
import com.example.entity.Course;
import com.example.entity.User;
import com.example.repository.CategoryRepository;
import com.example.repository.CourseRepository;
import com.example.repository.ModuleRepository;
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
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModuleRepository moduleRepository;

    @InjectMocks
    private CourseService courseService;

    private Category category;
    private User teacher;
    private Course course;

    @BeforeEach
    void setUp() {
        category = new Category("Programming");

        teacher = User.builder()
                .id(1L)
                .name("Teacher")
                .email("student@test.com")
                .role("TEACHER")
                .build();

        course = Course.builder()
                .id(1L)
                .title("Test Course")
                .description("Test")
                .teacher(teacher)
                .build();
    }

    @Test
    void shouldCreateCourse() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(userRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course created = courseService.createCourse("Test Course", "Test", 1L, 1L, LocalDate.now(), "5 months");

        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo("Test Course");
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.createCourse("Java", "Learn", 999L, 1L, LocalDate.now(), "5 months"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Категория не найдена");
    }

    @Test
    void shouldThrowExceptionWhenTeacherNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.createCourse("Java", "Learn", 1L, 999L, LocalDate.now(), "5 months"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Преподаватель не найден");
    }

    @Test
    void shouldGetCourseById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course found = courseService.getCourseById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getTitle()).isEqualTo("Test Course");
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.getCourseById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Курс не найден");
    }

    @Test
    void shouldDeleteCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        courseService.deleteCourse(1L);

        verify(courseRepository).delete(course);
    }
}
