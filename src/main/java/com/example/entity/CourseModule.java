package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course_modules")
public class CourseModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @NotBlank
    private String title;

    private Integer orderIndex;
    private String description;

    @OneToMany(mappedBy = "courseModule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lesson> lessons = new ArrayList<>();

    @OneToOne(mappedBy = "courseModule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Test test;

    public CourseModule() {
    }

    private CourseModule(Builder builder) {
        this.id = builder.id;
        this.course = builder.course;
        this.title = builder.title;
        this.orderIndex = builder.orderIndex;
        this.description = builder.description;
        this.lessons = builder.lessons != null ? new ArrayList<>(builder.lessons) : new ArrayList<>();
        this.test = builder.test;
    }

    public Long getId() { return id; }
    public Course getCourse() { return course; }
    public String getTitle() { return title; }
    public Integer getOrderIndex() { return orderIndex; }
    public String getDescription() { return description; }
    public List<Lesson> getLessons() { return new ArrayList<>(lessons); }
    public Test getTest() { return test; }

    public Builder toBuilder() {
        return new Builder()
                .id(this.id)
                .course(this.course)
                .title(this.title)
                .orderIndex(this.orderIndex)
                .description(this.description)
                .lessons(this.lessons != null ? new ArrayList<>(this.lessons) : null)
                .test(this.test);
    }

    public static class Builder {
        private Long id;
        private Course course;
        private String title;
        private Integer orderIndex;
        private String description;
        private List<Lesson> lessons;
        private Test test;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder course(Course course) {
            this.course = course;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder orderIndex(Integer orderIndex) {
            this.orderIndex = orderIndex;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder lessons(List<Lesson> lessons) {
            this.lessons = lessons;
            return this;
        }

        public Builder test(Test test) {
            this.test = test;
            return this;
        }

        public CourseModule build() {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalStateException("Title cannot be null or empty");
            }
            if (course == null) {
                throw new IllegalStateException("Course cannot be null");
            }

            return new CourseModule(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
