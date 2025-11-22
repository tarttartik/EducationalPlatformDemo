package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "course_reviews")
public class CourseReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;
    private LocalDateTime createdAt;

    public CourseReview() {
    }

    private CourseReview(Builder builder) {
        this.id = builder.id;
        this.course = builder.course;
        this.student = builder.student;
        this.rating = builder.rating;
        this.comment = builder.comment;
        this.createdAt = builder.createdAt != null ? builder.createdAt : LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Course getCourse() { return course; }
    public User getStudent() { return student; }
    public Integer getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public static class Builder {
        private Long id;
        private Course course;
        private User student;
        private Integer rating;
        private String comment;
        private LocalDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder course(Course course) {
            this.course = course;
            return this;
        }

        public Builder student(User student) {
            this.student = student;
            return this;
        }

        public Builder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CourseReview build() {
            if (course == null) {
                throw new IllegalStateException("Course cannot be null");
            }
            if (student == null) {
                throw new IllegalStateException("Student cannot be null");
            }
            if (rating == null) {
                throw new IllegalStateException("Rating cannot be null");
            }
            if (rating < 1 || rating > 5) {
                throw new IllegalStateException("Rating must be between 1 and 5");
            }

            return new CourseReview(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
