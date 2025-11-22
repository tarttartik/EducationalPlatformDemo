package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDate enrollDate;
    private String status;

    public Enrollment() {
    }

    private Enrollment(Builder builder) {
        this.id = builder.id;
        this.student = builder.student;
        this.course = builder.course;
        this.enrollDate = builder.enrollDate != null ? builder.enrollDate : LocalDate.now();
        this.status = builder.status != null ? builder.status : "ACTIVE";
    }


    public Long getId() { return id; }
    public User getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDate getEnrollDate() { return enrollDate; }
    public String getStatus() { return status; }
    public boolean isActive() { return "ACTIVE".equals(status); }
    public boolean isCompleted() { return "COMPLETED".equals(status); }
    public boolean isCancelled() { return "CANCELLED".equals(status); }
    public boolean isExpired() { return "EXPIRED".equals(status); }

    public static class Builder {
        private Long id;
        private User student;
        private Course course;
        private LocalDate enrollDate;
        private String status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder student(User student) {
            this.student = student;
            return this;
        }

        public Builder course(Course course) {
            this.course = course;
            return this;
        }

        public Builder enrollDate(LocalDate enrollDate) {
            this.enrollDate = enrollDate;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Enrollment build() {
            if (student == null) {
                throw new IllegalStateException("Student cannot be null");
            }
            if (course == null) {
                throw new IllegalStateException("Course cannot be null");
            }

            return new Enrollment(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
