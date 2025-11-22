package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    private String duration;
    private LocalDate startDate;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CourseModule> modules = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<CourseReview> reviews = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_tags",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Course() {
    }

    private Course(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.category = builder.category;
        this.teacher = builder.teacher;
        this.duration = builder.duration;
        this.startDate = builder.startDate;
        this.modules = builder.modules != null ? new ArrayList<>(builder.modules) : new ArrayList<>();
        this.enrollments = builder.enrollments != null ? new ArrayList<>(builder.enrollments) : new ArrayList<>();
        this.reviews = builder.reviews != null ? new ArrayList<>(builder.reviews) : new ArrayList<>();
        this.tags = builder.tags != null ? new HashSet<>(builder.tags) : new HashSet<>();
    }

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Category getCategory() {
        return category;
    }
    public User getTeacher() {
        return teacher;
    }
    public String getDuration() {
        return duration;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public Set<Tag> getTags() {return tags; }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private Category category = null;
        private User teacher;
        private String duration;
        private LocalDate startDate;
        private List<CourseModule> modules;
        private List<Enrollment> enrollments;
        private List<CourseReview> reviews;
        private Set<Tag> tags;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder teacher(User teacher) {
            this.teacher = teacher;
            return this;
        }

        public Builder duration(String duration) {
            this.duration = duration;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder modules(List<CourseModule> modules) {
            this.modules = modules;
            return this;
        }

        public Builder enrollments(List<Enrollment> enrollments) {
            this.enrollments = enrollments;
            return this;
        }

        public Builder reviews(List<CourseReview> reviews) {
            this.reviews = reviews;
            return this;
        }

        public Builder tags(Set<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Course build() {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalStateException("Title cannot be null or empty");
            }
            if (teacher == null) {
                throw new IllegalStateException("Teacher cannot be null");
            }

            return new Course(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .category(this.category)
                .teacher(this.teacher)
                .duration(this.duration)
                .startDate(this.startDate)
                .modules(this.modules != null ? new ArrayList<>(this.modules) : null)
                .enrollments(this.enrollments != null ? new ArrayList<>(this.enrollments) : null)
                .reviews(this.reviews != null ? new ArrayList<>(this.reviews) : null)
                .tags(this.tags != null ? new HashSet<>(this.tags) : null);
    }

}