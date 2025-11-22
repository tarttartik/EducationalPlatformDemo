package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public class TestSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Test test;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    private Integer score;
    private LocalDateTime takenAt;

    public TestSubmission() {
    }

    private TestSubmission(Builder builder) {
        this.id = builder.id;
        this.test = builder.test;
        this.student = builder.student;
        this.score = builder.score;
        this.takenAt = builder.takenAt;
    }

    public Long getId() { return id; }
    public Test getTest() { return test; }
    public User getStudent() { return student; }
    public Integer getScore() { return score; }
    public LocalDateTime getTakenAt() { return takenAt; }

    public static class Builder {
        private Long id;
        private Test test;
        private User student;
        private Integer score;
        private LocalDateTime takenAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder test(Test test) {
            this.test = test;
            return this;
        }

        public Builder student(User student) {
            this.student = student;
            return this;
        }

        public Builder score(Integer score) {
            this.score = score;
            return this;
        }

        public Builder takenAt(LocalDateTime takenAt) {
            this.takenAt = takenAt;
            return this;
        }

        public TestSubmission build() {
            if (test == null) {
                throw new IllegalStateException("Test cannot be null");
            }
            if (student == null) {
                throw new IllegalStateException("Student cannot be null");
            }
            return new TestSubmission(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
