package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    private LocalDateTime submittedAt;
    private String content;
    private Integer score;
    private String feedback;

    public Submission() {
    }

    private Submission(Builder builder) {
        this.id = builder.id;
        this.assignment = builder.assignment;
        this.student = builder.student;
        this.submittedAt = builder.submittedAt != null ? builder.submittedAt : LocalDateTime.now();
        this.content = builder.content;
        this.score = builder.score;
        this.feedback = builder.feedback;
    }


    public Long getId() { return id; }
    public Assignment getAssignment() { return assignment; }
    public User getStudent() { return student; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public String getContent() { return content; }
    public Integer getScore() { return score; }
    public String getFeedback() { return feedback; }

    public void setFeedback(String feedback) { this.feedback = feedback; }
    public void setScore(Integer score) { this.score = score; }

    public static class Builder {
        private Long id;
        private Assignment assignment;
        private User student;
        private LocalDateTime submittedAt;
        private String content;
        private Integer score;
        private String feedback;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder assignment(Assignment assignment) {
            this.assignment = assignment;
            return this;
        }

        public Builder student(User student) {
            this.student = student;
            return this;
        }

        public Builder submittedAt(LocalDateTime submittedAt) {
            this.submittedAt = submittedAt;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder score(Integer score) {
            this.score = score;
            return this;
        }

        public Builder feedback(String feedback) {
            this.feedback = feedback;
            return this;
        }

        public Submission build() {
            if (assignment == null) {
                throw new IllegalStateException("Assignment cannot be null");
            }
            if (student == null) {
                throw new IllegalStateException("Student cannot be null");
            }
            return new Submission(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
