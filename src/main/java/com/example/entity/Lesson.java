package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.sql.ast.tree.update.Assignment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private CourseModule courseModule;

    @NotBlank
    private String title;

    private String content;
    private String videoUrl;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Assignment> assignments = new ArrayList<>();

    public Lesson(){}

    private Lesson(Builder builder) {
        this.id = builder.id;
        this.courseModule = builder.courseModule;
        this.title = builder.title;
        this.content = builder.content;
        this.videoUrl = builder.videoUrl;
        this.assignments = builder.assignments != null ? new ArrayList<>(builder.assignments) : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public CourseModule getCourseModule() {
        return courseModule;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public List<Assignment> getAssignments() {
        return new ArrayList<>(assignments);
    }

    public void addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
    }

    public static class Builder {
        private Long id;
        private CourseModule courseModule;
        private String title;
        private String content;
        private String videoUrl;
        private List<Assignment> assignments;

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

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder videoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        public Builder assignments(List<Assignment> assignments) {
            this.assignments = assignments;
            return this;
        }

        public Lesson build() {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalStateException("Title cannot be null or empty");
            }
            if (courseModule == null) {
                throw new IllegalStateException("CourseModule cannot be null");
            }

            return new Lesson(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
