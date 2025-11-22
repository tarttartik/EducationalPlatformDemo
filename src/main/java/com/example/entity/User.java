package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile profile;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Course> taughtCourses = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Submission> submissions = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<TestSubmission> testSubmissions = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<CourseReview> reviews = new ArrayList<>();

    protected User() {
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.role = builder.role;
        this.profile = builder.profile;
        this.taughtCourses = builder.taughtCourses != null ? builder.taughtCourses : new ArrayList<>();
        this.enrollments = builder.enrollments != null ? builder.enrollments : new ArrayList<>();
        this.submissions = builder.submissions != null ? builder.submissions : new ArrayList<>();
        this.testSubmissions = builder.testSubmissions != null ? builder.testSubmissions : new ArrayList<>();
        this.reviews = builder.reviews != null ? builder.reviews : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getRole() {
        return role;
    }
    public UserProfile getProfile() {
        return profile;
    }
    public List<Course> getTaughtCourses() {
        return taughtCourses;
    }
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }
    public List<Submission> getSubmissions() {
        return submissions;
    }
    public List<TestSubmission> getTestSubmissions() {
        return testSubmissions;
    }
    public List<CourseReview> getReviews() {
        return reviews;
    }


    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private String role;
        private UserProfile profile;
        private List<Course> taughtCourses;
        private List<Enrollment> enrollments;
        private List<Submission> submissions;
        private List<TestSubmission> testSubmissions;
        private List<CourseReview> reviews;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder profile(UserProfile profile) {
            this.profile = profile;
            return this;
        }

        public Builder taughtCourses(List<Course> taughtCourses) {
            this.taughtCourses = taughtCourses;
            return this;
        }

        public Builder enrollments(List<Enrollment> enrollments) {
            this.enrollments = enrollments;
            return this;
        }

        public Builder submissions(List<Submission> submissions) {
            this.submissions = submissions;
            return this;
        }

        public Builder quizSubmissions(List<TestSubmission> quizSubmissions) {
            this.testSubmissions = quizSubmissions;
            return this;
        }

        public Builder reviews(List<CourseReview> reviews) {
            this.reviews = reviews;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}