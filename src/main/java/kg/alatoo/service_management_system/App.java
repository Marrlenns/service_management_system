package kg.alatoo.service_management_system;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import kg.alatoo.service_management_system.entities.Teacher;
import kg.alatoo.service_management_system.i18n.I18n;
import kg.alatoo.service_management_system.i18n.Language;
import kg.alatoo.service_management_system.services.CategoryCodeService;
import kg.alatoo.service_management_system.services.StudentService;
import kg.alatoo.service_management_system.services.TeacherService;
import kg.alatoo.service_management_system.ui.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;
import java.util.Optional;

@SpringBootApplication
public class App extends Application {

    private ConfigurableApplicationContext context;

    // текущий пользователь (Student / Teacher / Other)
    private Long   currentUserId;
    private String currentUserName;
    // "STUDENT" / "TEACHER" / "OTHER"
    private String currentUserRole;

    // текущий язык
    private Language currentLanguage = Language.RU;

    // Вьюшки
    private MainMenuView     mainMenuView;
    private StudentLoginView studentLoginView;
    private TeacherLoginView teacherLoginView;
    private WelcomeView      welcomeView;
    private TicketView       ticketView;

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

        // ---- Вьюшки (с языком и колбэком смены языка) ----
        mainMenuView     = new MainMenuView(currentLanguage, this::onLanguageChange);
        studentLoginView = new StudentLoginView(currentLanguage, this::onLanguageChange);
        teacherLoginView = new TeacherLoginView(currentLanguage, this::onLanguageChange);
        welcomeView      = new WelcomeView(currentLanguage, this::onLanguageChange);
        ticketView       = new TicketView(currentLanguage, this::onLanguageChange);

        try {
            var is = getClass().getResourceAsStream("/logo.png");
            if (is != null) {
                stage.getIcons().add(new javafx.scene.image.Image(is));
            } else {
                System.err.println("[App] logo.png not found for stage icon");
            }
        } catch (Exception e) {
            System.err.println("[App] Failed to set stage icon: " + e.getMessage());
        }

        Scene scene = new Scene(mainMenuView.getRoot());
        stage.setTitle("Service Management System");
        stage.setScene(scene);
        stage.setFullScreen(true);
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
            currentUserName = "";
            updateWelcomeLabel();
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

        // ===== Логин STUDENT =====
        studentLoginView.getEnterButton().setOnAction(e -> {
            String raw = studentLoginView.getIdField().getText().trim();
            if (raw.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, I18n.alertEnterId(currentLanguage));
                return;
            }
            Long id;
            try {
                id = Long.parseLong(raw);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, I18n.alertIdMustBeNumber(currentLanguage));
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
                    updateWelcomeLabel();
                    scene.setRoot(welcomeView.getRoot());
                }, () -> showAlert(Alert.AlertType.INFORMATION,
                        I18n.alertStudentNotFound(currentLanguage)));
            });

            task.setOnFailed(ev -> {
                studentLoginView.getEnterButton().setDisable(false);
                Throwable ex = task.getException();
                showAlert(Alert.AlertType.ERROR,
                        I18n.alertDbErrorPrefix(currentLanguage) +
                                (ex != null && ex.getMessage() != null ? ex.getMessage() : "Unknown"));
            });

            new Thread(task, "db-student-lookup").start();
        });

        // ===== Логин TEACHER по email =====
        teacherLoginView.getEnterButton().setOnAction(e -> {
            String email = teacherLoginView.getEmailField().getText().trim();
            if (email.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, I18n.alertEnterEmail(currentLanguage));
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
                    updateWelcomeLabel();
                    scene.setRoot(welcomeView.getRoot());
                }, () -> showAlert(Alert.AlertType.INFORMATION,
                        I18n.alertTeacherNotFound(currentLanguage)));
            });

            task.setOnFailed(ev -> {
                teacherLoginView.getEnterButton().setDisable(false);
                Throwable ex = task.getException();
                showAlert(Alert.AlertType.ERROR,
                        I18n.alertDbErrorPrefix(currentLanguage) +
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
                    showAlert(Alert.AlertType.WARNING,
                            I18n.alertLoginOrOtherFirst(currentLanguage));
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
                    if ("TEACHER".equals(currentUserRole)) {
                        displayRole = I18n.roleTeacher(currentLanguage);
                    } else if ("STUDENT".equals(currentUserRole)) {
                        displayRole = I18n.roleStudent(currentLanguage);
                    } else if ("OTHER".equals(currentUserRole)) {
                        displayRole = I18n.roleOther(currentLanguage);
                    } else {
                        displayRole = currentUserRole;
                    }

                    ticketView.showTicket(displayRole, currentUserName, code);
                    scene.setRoot(ticketView.getRoot());
                });

                task.setOnFailed(ev -> {
                    sourceButton.setDisable(false);
                    Throwable ex = task.getException();
                    showAlert(Alert.AlertType.ERROR,
                            I18n.alertCategoryErrorPrefix(currentLanguage) +
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
        updateWelcomeLabel();
    }

    private void updateWelcomeLabel() {
        if (welcomeView == null) return;
        if (currentUserName != null && !currentUserName.isBlank()) {
            welcomeView.getWelcomeLabel()
                    .setText(I18n.welcomeWithName(currentLanguage, currentUserName));
        } else {
            welcomeView.getWelcomeLabel()
                    .setText(I18n.welcomePlain(currentLanguage));
        }
    }

    private void onLanguageChange(Language lang) {
        if (lang == null || lang == currentLanguage) return;

        currentLanguage = lang;

        mainMenuView.applyLanguage(lang);
        studentLoginView.applyLanguage(lang);
        teacherLoginView.applyLanguage(lang);
        welcomeView.applyLanguage(lang);
        ticketView.applyLanguage(lang);

        updateWelcomeLabel();
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
