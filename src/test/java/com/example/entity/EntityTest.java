package com.example.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class EntityTest {

    @Test
    void shouldCreateUser() {
        User user = User.builder()
                .name("John Doe")
                .email("john@example.com")
                .role("STUDENT")
                .build();

        assertThat(user.getName()).isEqualTo("John Doe");
        assertThat(user.getEmail()).isEqualTo("john@example.com");
        assertThat(user.getRole()).isEqualTo("STUDENT");
    }

    @Test
    void shouldCreateCategory() {
        Category category = new Category("Programming");

        assertThat(category.getName()).isEqualTo("Programming");
    }

    @Test
    void shouldCreateCourse() {
        User teacher = User.builder()
                .name("Teacher")
                .email("teacher@test.com")
                .role("TEACHER")
                .build();

        Category category = new Category("Java");

        Course course = Course.builder()
                .title("Java Basics")
                .description("Learn Java")
                .category(category)
                .teacher(teacher)
                .build();

        assertThat(course.getTitle()).isEqualTo("Java Basics");
        assertThat(course.getDescription()).isEqualTo("Learn Java");
        assertThat(course.getCategory()).isEqualTo(category);
        assertThat(course.getTeacher()).isEqualTo(teacher);
    }

    @Test
    void shouldCreateEnrollment() {
        User student = User.builder()
                .name("Student")
                .email("student@test.com")
                .role("STUDENT")
                .build();

        Course course = Course.builder()
                .title("Test Course")
                .teacher(User.builder().name("Teacher").email("t@test.com").role("TEACHER").build())
                .build();

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrollDate(LocalDate.now())
                .status("Active")
                .build();

        assertThat(enrollment.getStudent()).isEqualTo(student);
        assertThat(enrollment.getCourse()).isEqualTo(course);
        assertThat(enrollment.getStatus()).isEqualTo("Active");
    }

    @Test
    void shouldCreateUserProfile() {
        User user = User.builder()
                .name("User")
                .email("user@test.com")
                .role("STUDENT")
                .build();

        UserProfile profile = UserProfile.builder()
                .user(user)
                .bio("Bio")
                .avatarUrl("avatar.jpg")
                .build();

        assertThat(profile.getUser()).isEqualTo(user);
        assertThat(profile.getBio()).isEqualTo("Bio");
        assertThat(profile.getAvatarUrl()).isEqualTo("avatar.jpg");
    }

    @Test
    void shouldCreateTag() {
        Tag tag = new Tag("Java");

        assertThat(tag.getName()).isEqualTo("Java");
    }

    @Test
    void shouldCreateCourseModule() {
        Course course = Course.builder()
                .title("Test Course")
                .teacher(User.builder().name("Teacher").email("t@test.com").role("TEACHER").build())
                .build();

        CourseModule module = CourseModule.builder()
                .course(course)
                .title("Module 1")
                .orderIndex(1)
                .build();

        assertThat(module.getCourse()).isEqualTo(course);
        assertThat(module.getTitle()).isEqualTo("Module 1");
        assertThat(module.getOrderIndex()).isEqualTo(1);
    }

    @Test
    void shouldCreateLesson() {
        CourseModule module = CourseModule.builder()
                .course(Course.builder().title("Course").teacher(User.builder().name("T").email("t@t.com").role("TEACHER").build()).build())
                .title("Module")
                .orderIndex(1)
                .build();

        Lesson lesson = Lesson.builder()
                .courseModule(module)
                .title("Lesson 1")
                .content("Content")
                .build();

        assertThat(lesson.getCourseModule()).isEqualTo(module);
        assertThat(lesson.getTitle()).isEqualTo("Lesson 1");
        assertThat(lesson.getContent()).isEqualTo("Content");
    }

    @Test
    void shouldCreateAssignment() {
        Lesson lesson = Lesson.builder()
                .courseModule(CourseModule.builder()
                        .course(Course.builder().title("C").teacher(User.builder().name("T").email("t@t.com").role("TEACHER").build()).build())
                        .title("M")
                        .orderIndex(1)
                        .build())
                .title("L")
                .build();

        Assignment assignment = new Assignment(lesson, "Assignment 1", "Do this",
                LocalDate.now(), 100);

        assertThat(assignment.getLesson()).isEqualTo(lesson);
        assertThat(assignment.getTitle()).isEqualTo("Assignment 1");
        assertThat(assignment.getMaxScore()).isEqualTo(100);
    }

    @Test
    void shouldCreateSubmission() {
        Assignment assignment = new Assignment();
        User student = User.builder()
                .name("Student")
                .email("student@test.com")
                .role("STUDENT")
                .build();

        Submission submission = Submission.builder()
                .assignment(assignment)
                .student(student)
                .content("Solution")
                .build();

        assertThat(submission.getAssignment()).isEqualTo(assignment);
        assertThat(submission.getStudent()).isEqualTo(student);
        assertThat(submission.getContent()).isEqualTo("Solution");
    }

    @Test
    void shouldCreatetest() {
        CourseModule module = CourseModule.builder()
                .course(Course.builder().title("C").teacher(User.builder().name("T").email("t@t.com").role("TEACHER").build()).build())
                .title("M")
                .orderIndex(1)
                .build();

        com.example.entity.Test test = com.example.entity.Test.builder()
                .courseModule(module)
                .title("test 1")
                .build();

        assertThat(test.getCourseModule()).isEqualTo(module);
        assertThat(test.getTitle()).isEqualTo("test 1");
    }

    @Test
    void shouldCreateQuestion() {
        com.example.entity.Test test = com.example.entity.Test.builder()
                .courseModule(CourseModule.builder()
                        .course(Course.builder().title("C").teacher(User.builder().name("T").email("t@t.com").role("TEACHER").build()).build())
                        .title("M")
                        .orderIndex(1)
                        .build())
                .title("Q")
                .build();

        Question question = new Question(test, "What is Java?", "SINGLE_CHOICE");

        assertThat(question.getTest()).isEqualTo(test);
        assertThat(question.getText()).isEqualTo("What is Java?");
        assertThat(question.getType()).isEqualTo("SINGLE_CHOICE");
    }

    @Test
    void shouldCreateAnswerOption() {
        Question question = new Question();
        AnswerOption option = new AnswerOption(question, "Programming language", true);

        assertThat(option.getQuestion()).isEqualTo(question);
        assertThat(option.getText()).isEqualTo("Programming language");
        assertThat(option.isCorrect()).isTrue();
    }

    @Test
    void shouldCreatetestSubmission() {
        com.example.entity.Test test = new com.example.entity.Test.Builder()
                .courseModule(CourseModule.builder()
                        .course(Course.builder().title("C").teacher(User.builder().name("T").email("t@t.com").role("TEACHER").build()).build())
                        .title("M")
                        .orderIndex(1)
                        .build())
                .title("Q")
                .build();

        User student = User.builder()
                .name("Student")
                .email("student@test.com")
                .role("STUDENT")
                .build();

        TestSubmission submission = TestSubmission.builder()
                .test(test)
                .student(student)
                .score(85)
                .build();

        assertThat(submission.getTest()).isEqualTo(test);
        assertThat(submission.getStudent()).isEqualTo(student);
        assertThat(submission.getScore()).isEqualTo(85);
    }

    @Test
    void shouldCreateCourseReview() {
        Course course = Course.builder()
                .title("Test Course")
                .teacher(User.builder().name("Teacher").email("t@test.com").role("TEACHER").build())
                .build();

        User student = User.builder()
                .name("Student")
                .email("student@test.com")
                .role("STUDENT")
                .build();

        CourseReview review = CourseReview.builder()
                .course(course)
                .student(student)
                .rating(5)
                .comment("Great course!")
                .build();

        assertThat(review.getCourse()).isEqualTo(course);
        assertThat(review.getStudent()).isEqualTo(student);
        assertThat(review.getRating()).isEqualTo(5);
        assertThat(review.getComment()).isEqualTo("Great course!");
    }
}