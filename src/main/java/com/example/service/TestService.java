package com.example.service;
import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @Autowired
    private TestSubmissionRepository testSubmissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    public Test createTest(Long courseModuleId, String title, Integer timeLimit) {
        CourseModule courseModule = moduleRepository.findById(courseModuleId)
                .orElseThrow(() -> new RuntimeException("Модуль не найден"));

        Test test = new Test.Builder()
                .timeLimit(timeLimit)
                .title(title)
                .build();

        return testRepository.save(test);
    }

    public Question addQuestionToTest(Long TestId, String text, String type) {
        Test test = testRepository.findById(TestId)
                .orElseThrow(() -> new RuntimeException("Тест не найден"));

        Question question = new Question(test, text, type);
        return questionRepository.save(question);
    }

    public AnswerOption addAnswerOption(Long questionId, String text, boolean isCorrect) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));

        AnswerOption option = new AnswerOption(question, text, isCorrect);
        return answerOptionRepository.save(option);
    }

    public TestSubmission takeTest(Long TestId, Long studentId, Map<Long, Long> answers) {
        if (testSubmissionRepository.existsByTestIdAndStudentId(TestId, studentId)) {
            throw new RuntimeException("Тест уже пройден");
        }

        Test test = testRepository.findById(TestId)
                .orElseThrow(() -> new RuntimeException("Тест не найден"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Студент не найден"));

        int correctAnswers = 0;
        for (Question question : test.getQuestions()) {
            Long selectedOptionId = answers.get(question.getId());
            if (selectedOptionId != null) {
                AnswerOption selectedOption = answerOptionRepository.findById(selectedOptionId)
                        .orElseThrow(() -> new RuntimeException("Вариант ответа не найден"));
                if (selectedOption.isCorrect()) {
                    correctAnswers++;
                }
            }
        }

        int score = !test.getQuestions().isEmpty() ?
                (correctAnswers * 100) / test.getQuestions().size() : 0;

        TestSubmission submission = new TestSubmission.Builder()
                .test(test)
                .student(student)
                .score(score)
                .build();

        return testSubmissionRepository.save(submission);
    }

    public List<TestSubmission> getTestSubmissions(Long TestId) {
        return testSubmissionRepository.findByTestId(TestId);
    }

    public List<TestSubmission> getStudentTestSubmissions(Long studentId) {
        return testSubmissionRepository.findByStudentId(studentId);
    }

    public Test getTestByModuleId(Long courseModuleId) {
        return testRepository.findByCourseModuleId(courseModuleId)
                .orElseThrow(() -> new RuntimeException("Тест не найден для этого модуля"));
    }
}
