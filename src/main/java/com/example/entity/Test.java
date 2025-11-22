package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private CourseModule courseModule;

    @NotBlank
    private String title;

    private Integer timeLimit;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private List<TestSubmission> submissions = new ArrayList<>();

    public Test() {}

    private Test(Builder builder) {
        this.id = builder.id;
        this.courseModule = builder.courseModule;
        this.title = builder.title;
        this.timeLimit = builder.timeLimit;
        this.questions = builder.questions != null ? new ArrayList<>(builder.questions) : new ArrayList<>();
        this.submissions = builder.submissions != null ? new ArrayList<>(builder.submissions) : new ArrayList<>();
    }

    public Long getId() { return id; }
    public CourseModule getCourseModule() { return courseModule; }
    public String getTitle() { return title; }
    public Integer getTimeLimit() { return timeLimit; }
    public List<Question> getQuestions() { return questions; }
    public List<TestSubmission> getSubmissions() { return submissions; }

    public static class Builder {
        private Long id;
        private CourseModule courseModule;
        private String title;
        private Integer timeLimit;
        private List<Question> questions;
        private List<TestSubmission> submissions;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder courseModule(CourseModule courseModule) {
            this.courseModule = courseModule;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder timeLimit(Integer timeLimit) {
            this.timeLimit = timeLimit;
            return this;
        }

        public Builder questions(List<Question> questions) {
            this.questions = questions;
            return this;
        }

        public Builder submissions(List<TestSubmission> submissions) {
            this.submissions = submissions;
            return this;
        }

        public Test build() {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalStateException("Title cannot be null or empty");
            }
            if (timeLimit != null && timeLimit <= 0) {
                throw new IllegalStateException("Time limit must be positive");
            }

            return new Test(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
