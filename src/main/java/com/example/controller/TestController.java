package com.example.controller;

import com.example.entity.AnswerOption;
import com.example.entity.Question;
import com.example.entity.Test;
import com.example.entity.TestSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.TestService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Tests")
public class TestController {

    @Autowired
    private TestService TestService;

    @PostMapping
    public ResponseEntity<Test> createTest(
            @RequestParam Long courseModuleId,
            @RequestParam String title,
            @RequestParam(required = false) Integer timeLimit) {
        Test Test = TestService.createTest(courseModuleId, title, timeLimit);
        return ResponseEntity.ok(Test);
    }

    @PostMapping("/{TestId}/questions")
    public ResponseEntity<Question> addQuestion(
            @PathVariable Long TestId,
            @RequestParam String text,
            @RequestParam String type) {
        Question question = TestService.addQuestionToTest(TestId, text, type);
        return ResponseEntity.ok(question);
    }

    @PostMapping("/questions/{questionId}/options")
    public ResponseEntity<AnswerOption> addAnswerOption(
            @PathVariable Long questionId,
            @RequestParam String text,
            @RequestParam boolean isCorrect) {
        AnswerOption option = TestService.addAnswerOption(questionId, text, isCorrect);
        return ResponseEntity.ok(option);
    }

    @PostMapping("/{TestId}/take")
    public ResponseEntity<TestSubmission> takeTest(
            @PathVariable Long TestId,
            @RequestParam Long studentId,
            @RequestBody Map<Long, Long> answers) {
        TestSubmission submission = TestService.takeTest(TestId, studentId, answers);
        return ResponseEntity.ok(submission);
    }

    @GetMapping("/module/{courseModuleId}")
    public ResponseEntity<Test> getTestByModule(@PathVariable Long courseModuleId) {
        Test Test = TestService.getTestByModuleId(courseModuleId);
        return ResponseEntity.ok(Test);
    }

    @GetMapping("/{TestId}/submissions")
    public ResponseEntity<List<TestSubmission>> getTestSubmissions(@PathVariable Long TestId) {
        List<TestSubmission> submissions = TestService.getTestSubmissions(TestId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/submissions/student/{studentId}")
    public ResponseEntity<List<TestSubmission>> getStudentTestSubmissions(@PathVariable Long studentId) {
        List<TestSubmission> submissions = TestService.getStudentTestSubmissions(studentId);
        return ResponseEntity.ok(submissions);
    }
}
