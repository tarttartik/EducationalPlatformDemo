package com.example.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.example.entity.*;
import com.example.repository.*;
import com.example.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Profile("!test")
public class DataSeeder implements CommandLineRunner {

    @Autowired  private UserRepository userRepository;
    @Autowired  private CategoryRepository categoryRepository;
    @Autowired  private CourseService courseService;
    @Autowired  private EnrollmentService enrollmentService;
    @Autowired  private AssignmentService assignmentService;
    @Autowired  private TestService testService;
    @Autowired  private TagRepository tagRepository;
    @Autowired  private ProfileRepository profileRepository;
    @Autowired private CourseReviewRepository courseReviewRepository;
    @Autowired private LessonRepository lessonRepository;

    @Override
    public void run(String... args) {
        seedData();
    }

    private void seedData() {

        //ПОЛЬЗОВАТЕЛИ

        User teacher = userRepository.save(User.builder()
                .name("Федор Корочкин")
                .email("f.korochkin@platform.edu")
                .role("TEACHER")
                .build());
        User studentKatya = userRepository.save(User.builder()
                .name("Катерина Горохова")
                .email("k.gorohova@platform.edu")
                .role("STUDENT")
                .build());
        User studentArtem= userRepository.save(User.builder()
                .name("Артем Воробьев")
                .email("a.vorobiev@platform.edu")
                .role("STUDENT")
                .build());
        User studentDenis = userRepository.save(User.builder()
                .name("Денис Добрынин")
                .email("d.dobrynin@platform.edu")
                .role("STUDENT")
                .build());

        UserProfile teacherProfile = profileRepository.save(UserProfile.builder()
                .user(teacher)
                .bio("К.т.н., разработчик со стажем более 20 лет")
                .avatarUrl("f_korochkin.jpg")
                .build());
        UserProfile studentKatyaProfile = profileRepository.save(UserProfile.builder()
                .user(teacher)
                .bio("Студент первого курса факультета информационных технологий")
                .avatarUrl("k_gorohova.jpg")
                .build());
        UserProfile studentArtemProfile = profileRepository.save(UserProfile.builder()
                .user(teacher)
                .bio("QA-инженер")
                .avatarUrl("a_vorobiev.jpg")
                .build());
        UserProfile studentDenisProfile = profileRepository.save(UserProfile.builder()
                .user(teacher)
                .bio("Начинающий разработчик")
                .avatarUrl("d_dobrynin.jpg")
                .build());

        //КАТЕГОРИИ И ТЕГИ

        Category programming = categoryRepository.save(new Category("Программирование"));
        Category fundamentalComputerScience = categoryRepository.save(new Category("Фундаментальная информатика"));

        Tag systemsTag = tagRepository.save(new Tag("Операционные системы"));
        Tag algorithmsTag = tagRepository.save(new Tag("Алгоритмы"));
        Tag pythonTag = tagRepository.save(new Tag("Python"));

        //ПЕРВЫЙ КУРС - ИМПЕРАТИВНОЕ ПРОГРАММИРОВАНИЕ

        Course imperativeProgrammingCourse = courseService.createCourse(
                "Введение в императивное программирование",
                "Основы императивного программирования и алгоритмов для начинающих разработчиков",
                programming.getId(),
                teacher.getId(),
                LocalDate.now().minusDays(10),
                "4 месяца"
        );

        courseService.addTagToCourse(imperativeProgrammingCourse.getId(), pythonTag.getId());
        courseService.addTagToCourse(imperativeProgrammingCourse.getId(), algorithmsTag.getId());

        enrollmentService.enrollStudent(imperativeProgrammingCourse.getId(), studentKatya.getId());
        enrollmentService.enrollStudent(imperativeProgrammingCourse.getId(), studentDenis.getId());

        CourseModule introModule = courseService.addModuleToCourse(imperativeProgrammingCourse.getId(), "Введение в императивное программирование", 1);
        CourseModule variablesModule = courseService.addModuleToCourse(imperativeProgrammingCourse.getId(), "Переменные и типы данных", 2);
        CourseModule functionsModule = courseService.addModuleToCourse(imperativeProgrammingCourse.getId(), "Функции", 3);
        CourseModule dataStructuresModule = courseService.addModuleToCourse(imperativeProgrammingCourse.getId(), "Базовые структуры данных", 4);
        CourseModule algorithmsModule = courseService.addModuleToCourse(imperativeProgrammingCourse.getId(), "Алгоритмы. Оценка сложности алгоритмов", 5);

        Lesson bigOLesson = lessonRepository.save(Lesson.builder()
                .courseModule(algorithmsModule)
                .title("Нотация большого О")
                .content("Учимся оценивать сложность алгоритмов")
                .videoUrl("https://platforn/bigO_lesson")
                .build());

        Assignment assignment1 = assignmentService.createAssignment(
                bigOLesson.getId(),
                "Оценить сложность алгоритмов",
                "Приведено 5 алгоритмов на языке Python. Оцените их сложность",
                LocalDate.now().plusDays(7),
                100
        );

        Submission submission1 = assignmentService.submitAssignment(assignment1.getId(), studentKatya.getId(),
                "1) линейная, 2) константная, 3) логарифмическая, 4) линейная, 5) квадратичная");

        assignmentService.gradeSubmission(submission1.getId(), 80, "Алгоритм 4 оценен неверно. Оценка - хорошо");

        //ВТОРОЙ КУРС - ОПЕРАЦИОННЫЕ СИСТЕМЫ

        Course osCourse = courseService.createCourse(
                "Операционные системы",
                "Глубокое погружение в операционные системы для профессиональных разработчиков",
                fundamentalComputerScience.getId(),
                teacher.getId(),
                LocalDate.now().minusDays(20),
                "6 месяцев"
        );

        courseService.addTagToCourse(osCourse.getId(), systemsTag.getId());

        enrollmentService.enrollStudent(imperativeProgrammingCourse.getId(), studentKatya.getId());
        enrollmentService.enrollStudent(imperativeProgrammingCourse.getId(), studentArtem.getId());

        CourseModule introOSModule = courseService.addModuleToCourse(osCourse.getId(), "Введение в императивное программирование", 1);
        CourseModule kernelModule = courseService.addModuleToCourse(osCourse.getId(), "Kernel Space и системные вызовы", 2);
        CourseModule processesModule = courseService.addModuleToCourse(osCourse.getId(), "Процессы", 3);
        CourseModule threadsModule = courseService.addModuleToCourse(osCourse.getId(), "Потоки", 4);
        CourseModule filesModule = courseService.addModuleToCourse(osCourse.getId(), "Файловая система", 5);

        Lesson systemCallsLesson = lessonRepository.save(Lesson.builder()
                .courseModule(kernelModule)
                .title("Системные вызовы")
                .content("Разбираем, что такое пользовательское и пространство ядра.")
                .videoUrl("https://platforn/kernel_lesson")
                .build());

        enrollmentService.enrollStudent(osCourse.getId(), studentKatya.getId());
        enrollmentService.enrollStudent(osCourse.getId(), studentDenis.getId());

        Test test = testService.createTest(systemCallsLesson.getId(), "Тест по ядру ОС", 30);

        Question question1 = testService.addQuestionToTest(test.getId(),
                "Что НЕ является системным вызовом в POSIX?", "SINGLE_CHOICE");

        testService.addAnswerOption(question1.getId(), "fork()", false);
        testService.addAnswerOption(question1.getId(), "read()", false);
        testService.addAnswerOption(question1.getId(), "malloc()", true);
        testService.addAnswerOption(question1.getId(), "kill()", false);

        CourseReview review1 =  courseReviewRepository.save(CourseReview.builder()
                .course(osCourse)
                .student(studentKatya)
                .comment("Очень полезный курс для любого разработчика! Спасибо!")
                .rating(5)
                .createdAt(LocalDateTime.now())
                .build());

        CourseReview review2 =  courseReviewRepository.save(CourseReview.builder()
                .course(osCourse)
                .student(studentDenis)
                .comment("Ничего не понятно, но очень интересно...")
                .rating(4)
                .createdAt(LocalDateTime.now())
                .build());

    }
}