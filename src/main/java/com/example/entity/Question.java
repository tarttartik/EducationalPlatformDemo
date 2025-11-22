package com.example.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question_options")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    @NotBlank
    private String text;

    private String type;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AnswerOption> options = new ArrayList<>();

    public Question() {
    }

    public Question(Test test, String text, String type) {
        this.test = test;
        this.text = text;
        this.type = type;
    }

    public Long getId() { return id; }
    public Test getTest() { return test; }
    public String getText() { return text; }
    public String getType() { return type; }
    public List<AnswerOption> getOptions() { return options; }
}
