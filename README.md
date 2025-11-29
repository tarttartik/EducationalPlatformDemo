# Educational Platform Demo

Демонстрационный проект образовательной платформы, реализованный на Spring Boot. Система моделирует ключевые аспекты онлайн-обучения: курсы, модули, уроки, задания, тесты, пользователей и процесс обучения.

Проект организован так, чтобы его можно было легко расширять и использовать как основу для более сложной LMS-системы.

## Технологии
- Java 17+
- Spring Boot (Web, Data JPA)
- H2 (in-memory), PostgreSQL
- Maven
- JUnit 5
- GitHub Actions (CI)

## Установка и запуск
### 1. Клонирование репозитория
```bash
git clone https://github.com/tarttartik/educational-platform-demo.git
cd educational-platform-demo
```

### 2. Запуск приложения
```bash
mvn spring-boot:run
```
Сервер будет доступен по адресу:
```
http://localhost:8080
```

### 3. Профили приложения
В `src/main/resources` находятся профили:
- `application.properties` — базовый
- `application-h2.properties` — работа с H2
- `application-test.properties` — тестовый

Запуск с использованием H2:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

## Тестирование
Запуск тестов:
```bash
mvn test
```

### CI
GitHub Actions автоматически запускает тесты:
- при каждом push
- при открытии pull request

## Основная архитектура проекта
```
src/main/java/com/example
│
├── controller        # REST API
├── data              # Seeder — начальные данные
├── entity            # Модели данных (JPA)
├── repository        # Spring Data JPA
├── service           # Бизнес-логика
└── EducationalPlatformDemoApplication
```

### Основные модели данных
- User, UserProfile
- Course, CourseModule, Lesson
- Category, Tag
- Enrollment — запись студента на курс
- Assignment, Submission
- Test, Question, AnswerOption, TestSubmission

## REST API

### Courses API (`/api/courses`)
**Создать курс**
```
POST /api/courses
Params: title, description, categoryId, teacherId, start (LocalDate), duration (String)
```

**Получить курс**
```
GET /api/courses/{id}
GET /api/courses/{id}/with-modules
```

**Получить все курсы**
```
GET /api/courses
```

**Фильтрация**
```
GET /api/courses/category/{categoryId}
GET /api/courses/teacher/{teacherId}
```

**Обновление и удаление**
```
PUT /api/courses/{id}
DELETE /api/courses/{id}
```

**Добавление модуля**
```
POST /api/courses/{courseId}/modules
```

### Assignments API (`/api/assignments`)
**Создать задание**
```
POST /api/assignments
Params: lessonId, title, description, dueDate, maxScore
```

**Получить задание**
```
GET /api/assignments/{id}
```

**Задания по уроку / курсу**
```
GET /api/assignments/lesson/{lessonId}
GET /api/assignments/course/{courseId}
```

**Отправить решение**
```
POST /api/assignments/{assignmentId}/submit
Params: studentId, content
```

**Оценить решение**
```
PUT /api/assignments/submissions/{submissionId}/grade
Params: score, feedback
```

### Enrollment API (`/api/enrollments`)
**Записать студента на курс**
```
POST /api/enrollments
```

**Отписать студента от курса**
```
DELETE /api/enrollments
```

**Получить записи**
```
GET /api/enrollments/student/{studentId}
GET /api/enrollments/course/{courseId}
GET /api/enrollments/student/{studentId}/courses
```

**Проверить запись**
```
GET /api/enrollments/check
```

### Tests API (`/api/tests`)
**Создать тест**
```
POST /api/tests
```

**Добавить вопрос**
```
POST /api/tests/{TestId}/questions
```

**Добавить вариант ответа**
```
POST /api/tests/questions/{questionId}/options
```

**Пройти тест**
```
POST /api/tests/{TestId}/take
Body: { questionId: answerOptionId }
```

**Получить тест по модулю**
```
GET /api/tests/module/{courseModuleId}
```

## Пример запуска с тестами
```bash
git clone <repo>
cd educational-platform-demo
mvn clean install
mvn test
mvn spring-boot:run
```

После запуска API доступны по адресу:
```
http://localhost:8080/api/
```

