package kg.alatoo.service_management_system;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import kg.alatoo.service_management_system.entities.Teacher;
import kg.alatoo.service_management_system.services.CategoryCodeService;
import kg.alatoo.service_management_system.services.StudentService;
import kg.alatoo.service_management_system.services.TeacherService;
import kg.alatoo.service_management_system.ui.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

@SpringBootApplication
public class App extends Application {

    private ConfigurableApplicationContext context;

    // текущий пользователь (Student / Teacher / Other)
    private Long   currentUserId;
    private String currentUserName;
    // "STUDENT" / "TEACHER" / "OTHER"
    private String currentUserRole;

    @Override
    public void init() {
        context = new SpringApplicationBuilder(App.class)
                .headless(false)
                .run();
    }

    @Override
    public void start(Stage stage) {
        // ---- Бины ----
        StudentService      studentService      = context.getBean(StudentService.class);
        TeacherService      teacherService      = context.getBean(TeacherService.class);
        CategoryCodeService categoryCodeService = context.getBean(CategoryCodeService.class);

        // ---- Вьюшки ----
        MainMenuView      mainMenuView      = new MainMenuView();
        StudentLoginView  studentLoginView  = new StudentLoginView();
        TeacherLoginView  teacherLoginView  = new TeacherLoginView();
        WelcomeView       welcomeView       = new WelcomeView();
        TicketView        ticketView        = new TicketView();

        Scene scene = new Scene(mainMenuView.getRoot(), 800, 600);
        stage.setTitle("Service Management Kiosk");
        stage.setScene(scene);
        stage.show();

        // ===== Навигация из главного меню =====
        mainMenuView.getStudentButton().setOnAction(e -> {
            clearCurrentUser();
            studentLoginView.reset();
            scene.setRoot(studentLoginView.getRoot());
        });

        mainMenuView.getTeacherButton().setOnAction(e -> {
            clearCurrentUser();
            teacherLoginView.reset();
            scene.setRoot(teacherLoginView.getRoot());
        });

        mainMenuView.getOtherButton().setOnAction(e -> {
            clearCurrentUser();
            currentUserId   = null;
            currentUserRole = "OTHER";
            currentUserName = ""; // имя не нужно
            welcomeView.getWelcomeLabel().setText("Welcome");
            scene.setRoot(welcomeView.getRoot());
        });

        // ===== Back-кнопки =====
        studentLoginView.getBackButton().setOnAction(e -> {
            clearCurrentUser();
            studentLoginView.reset();
            scene.setRoot(mainMenuView.getRoot());
        });

        teacherLoginView.getBackButton().setOnAction(e -> {
            clearCurrentUser();
            teacherLoginView.reset();
            scene.setRoot(mainMenuView.getRoot());
        });

        welcomeView.getBackButton().setOnAction(e -> {
            clearCurrentUser();
            studentLoginView.reset();
            teacherLoginView.reset();
            scene.setRoot(mainMenuView.getRoot());
        });

        ticketView.getDoneButton().setOnAction(e -> {
            clearCurrentUser();
            studentLoginView.reset();
            teacherLoginView.reset();
            scene.setRoot(mainMenuView.getRoot());
        });

        // ===== Логин студента =====
        studentLoginView.getEnterButton().setOnAction(e -> {
            String raw = studentLoginView.getIdField().getText().trim();
            if (raw.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Введите ID");
                return;
            }
            Long id;
            try {
                id = Long.parseLong(raw);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "ID должен быть числом");
                return;
            }

            Task<Optional<String>> task = new Task<>() {
                @Override
                protected Optional<String> call() {
                    return studentService.getNameById(id);
                }
            };

            studentLoginView.getEnterButton().setDisable(true);

            task.setOnSucceeded(ev -> {
                studentLoginView.getEnterButton().setDisable(false);
                task.getValue().ifPresentOrElse(name -> {
                    currentUserId   = id;
                    currentUserName = name;
                    currentUserRole = "STUDENT";
                    welcomeView.getWelcomeLabel().setText("Welcome " + name);
                    scene.setRoot(welcomeView.getRoot());
                }, () -> showAlert(Alert.AlertType.INFORMATION,
                        "Студент с таким ID не найден"));
            });

            task.setOnFailed(ev -> {
                studentLoginView.getEnterButton().setDisable(false);
                Throwable ex = task.getException();
                showAlert(Alert.AlertType.ERROR,
                        "Ошибка доступа к БД:\n" +
                                (ex != null && ex.getMessage() != null ? ex.getMessage() : "Unknown"));
            });

            new Thread(task, "db-student-lookup").start();
        });

        // ===== Логин преподавателя по email =====
        teacherLoginView.getEnterButton().setOnAction(e -> {
            String email = teacherLoginView.getEmailField().getText().trim();
            if (email.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Введите email");
                return;
            }

            Task<Optional<Teacher>> task = new Task<>() {
                @Override
                protected Optional<Teacher> call() {
                    return teacherService.findByEmail(email);
                }
            };

            teacherLoginView.getEnterButton().setDisable(true);

            task.setOnSucceeded(ev -> {
                teacherLoginView.getEnterButton().setDisable(false);
                task.getValue().ifPresentOrElse(teacher -> {
                    currentUserId   = teacher.getId();
                    currentUserName = teacher.getFirstname() + " " + teacher.getLastname();
                    currentUserRole = "TEACHER";

                    welcomeView.getWelcomeLabel().setText("Welcome " + currentUserName);
                    scene.setRoot(welcomeView.getRoot());
                }, () -> showAlert(Alert.AlertType.INFORMATION,
                        "Преподаватель с таким email не найден"));
            });

            task.setOnFailed(ev -> {
                teacherLoginView.getEnterButton().setDisable(false);
                Throwable ex = task.getException();
                showAlert(Alert.AlertType.ERROR,
                        "Ошибка доступа к БД:\n" +
                                (ex != null && ex.getMessage() != null ? ex.getMessage() : "Unknown"));
            });

            new Thread(task, "db-teacher-lookup").start();
        });

        // ===== Обработчики категорий =====
        var categoryButtons = welcomeView.getCategoryButtons();
        for (int i = 0; i < categoryButtons.length; i++) {
            final int catIndex = i + 1;
            categoryButtons[i].setOnAction(e -> {
                if (currentUserRole == null) {
                    showAlert(Alert.AlertType.WARNING, "Сначала выполните вход или выберите Other");
                    return;
                }

                var sourceButton = categoryButtons[catIndex - 1];
                sourceButton.setDisable(true);

                Task<String> task = new Task<>() {
                    @Override
                    protected String call() {
                        return categoryCodeService.registerClickAndGetCode(
                                currentUserId,
                                currentUserRole,
                                catIndex
                        );
                    }
                };

                task.setOnSucceeded(ev -> {
                    sourceButton.setDisable(false);
                    String code = task.getValue();

                    String displayRole;
                    switch (currentUserRole) {
                        case "TEACHER" -> displayRole = "Преподаватель";
                        case "STUDENT" -> displayRole = "Студент";
                        case "OTHER"   -> displayRole = "Гость";
                        default        -> displayRole = currentUserRole;
                    }

                    ticketView.showTicket(displayRole, currentUserName, code);
                    scene.setRoot(ticketView.getRoot());
                });

                task.setOnFailed(ev -> {
                    sourceButton.setDisable(false);
                    Throwable ex = task.getException();
                    showAlert(Alert.AlertType.ERROR,
                            "Ошибка при выдаче кода категории:\n" +
                                    (ex != null && ex.getMessage() != null ? ex.getMessage() : "Unknown"));
                });

                new Thread(task, "db-category-click").start();
            });
        }
    }

    private void clearCurrentUser() {
        currentUserId   = null;
        currentUserName = null;
        currentUserRole = null;
    }

    private void showAlert(Alert.AlertType type, String text) {
        new Alert(type, text).showAndWait();
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
