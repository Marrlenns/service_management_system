package kg.alatoo.service_management_system;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import kg.alatoo.service_management_system.entities.Teacher;
import kg.alatoo.service_management_system.keyboards.NumberKeyboard;
import kg.alatoo.service_management_system.keyboards.TextKeyboard;
import kg.alatoo.service_management_system.services.StudentService;
import kg.alatoo.service_management_system.services.TeacherService;
import kg.alatoo.service_management_system.services.CategoryCodeService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

@SpringBootApplication
public class App extends Application {

    private ConfigurableApplicationContext context;

    // Текущий пользователь (студент или преподаватель)
    private Long   currentUserId;    // id студента или преподавателя
    private String currentUserName;  // "Имя Фамилия"
    /**
     * Для БД: "STUDENT" или "TEACHER"
     */
    private String currentUserRole;

    @Override
    public void init() {
        context = new SpringApplicationBuilder(App.class)
                .headless(false)
                .run();
    }

    @Override
    public void start(Stage stage) {
        // ----- Бины -----
        StudentService      studentService      = context.getBean(StudentService.class);
        TeacherService      teacherService      = context.getBean(TeacherService.class);
        CategoryCodeService categoryCodeService = context.getBean(CategoryCodeService.class);

        // ----- Общие стили -----
        final String DARK_BG = "-fx-background-color: #0B1E39;";
        final String btnStyle = String.join("",
                "-fx-background-color: #1976D2;",
                "-fx-text-fill: white;",
                "-fx-font-size: 16px;",
                "-fx-font-weight: bold;",
                "-fx-background-radius: 8px;"
        );
        final String backBtnStyle = String.join("",
                "-fx-background-color: #455A64;",
                "-fx-text-fill: white;",
                "-fx-font-weight: bold;",
                "-fx-background-radius: 8px;"
        );
        final double btnWidth = 200, btnHeight = 50;

        // ===== Главное меню =====
        Button btnStudent = new Button("Student");
        Button btnTeacher = new Button("Teacher");
        Button btnOther   = new Button("Other");
        for (Button b : new Button[]{btnStudent, btnTeacher, btnOther}) {
            b.setStyle(btnStyle);
            b.setPrefSize(btnWidth, btnHeight);
        }

        VBox menu = new VBox(15, btnStudent, btnTeacher, btnOther);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(24));

        StackPane mainRoot = new StackPane(menu);
        mainRoot.setStyle(DARK_BG);

        Scene scene = new Scene(mainRoot, 800, 600);
        stage.setTitle("Главное меню");
        stage.setScene(scene);
        stage.show();

        // ===== Экран STUDENT ID + цифровая клавиатура =====
        Label studentTitle = new Label("Student ID");
        studentTitle.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        TextField studentIdField = new TextField();
        studentIdField.setPromptText("Введите ваш ID");
        studentIdField.setEditable(false); // ввод только через NumberKeyboard
        studentIdField.setPrefColumnCount(12);
        studentIdField.setMaxWidth(260);
        studentIdField.setStyle("-fx-font-size: 14px;");

        NumberKeyboard studentKeypad = NumberKeyboard.forTextField(studentIdField, 12);
        studentKeypad.setKeyWidth(64);

        Button studentEnter = new Button("Enter");
        studentEnter.setStyle(btnStyle);
        studentEnter.setPrefSize(btnWidth, btnHeight);

        Button backToMenuFromStudent = new Button("Back");
        backToMenuFromStudent.setStyle(backBtnStyle);
        backToMenuFromStudent.setPrefSize(btnWidth, 40);

        VBox studentCenter = new VBox(18, studentTitle, studentIdField, studentKeypad, studentEnter);
        studentCenter.setAlignment(Pos.CENTER);

        BorderPane studentRoot = new BorderPane();
        studentRoot.setCenter(studentCenter);
        StackPane studentBottom = new StackPane(backToMenuFromStudent);
        studentBottom.setPadding(new Insets(20));
        studentBottom.setAlignment(Pos.CENTER);
        studentRoot.setBottom(studentBottom);
        studentRoot.setStyle(DARK_BG);

        // ===== Экран TEACHER email + текстовая клавиатура =====
        Label teacherTitle = new Label("Teacher email");
        teacherTitle.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        TextField teacherEmailField = new TextField();
        teacherEmailField.setPromptText("Введите ваш email");
        teacherEmailField.setEditable(false); // ввод через TextKeyboard
        teacherEmailField.setPrefColumnCount(20);
        teacherEmailField.setMaxWidth(320);
        teacherEmailField.setStyle("-fx-font-size: 14px;");

        TextKeyboard teacherKeyboard = TextKeyboard.forTextField(teacherEmailField, 40);
        teacherKeyboard.setKeyWidth(48);

        Button teacherEnter = new Button("Enter");
        teacherEnter.setStyle(btnStyle);
        teacherEnter.setPrefSize(btnWidth, btnHeight);

        Button backToMenuFromTeacher = new Button("Back");
        backToMenuFromTeacher.setStyle(backBtnStyle);
        backToMenuFromTeacher.setPrefSize(btnWidth, 40);

        VBox teacherCenter = new VBox(18, teacherTitle, teacherEmailField, teacherKeyboard, teacherEnter);
        teacherCenter.setAlignment(Pos.CENTER);

        BorderPane teacherRoot = new BorderPane();
        teacherRoot.setCenter(teacherCenter);
        StackPane teacherBottom = new StackPane(backToMenuFromTeacher);
        teacherBottom.setPadding(new Insets(20));
        teacherBottom.setAlignment(Pos.CENTER);
        teacherRoot.setBottom(teacherBottom);
        teacherRoot.setStyle(DARK_BG);

        // ===== Экран приветствия + категории (общий) =====
        Label welcome = new Label();
        welcome.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");

        String catStyle = String.join("",
                "-fx-background-color: #1976D2;",
                "-fx-text-fill: white;",
                "-fx-font-size: 16px;",
                "-fx-font-weight: bold;",
                "-fx-background-radius: 12px;"
        );

        Button[] cat = new Button[5];
        for (int i = 0; i < 5; i++) {
            cat[i] = new Button("Category " + (i + 1));
            cat[i].setStyle(catStyle);
            cat[i].setPrefSize(260, 56);
        }

        VBox categoriesCol = new VBox(12, cat[0], cat[1], cat[2], cat[3], cat[4]);
        categoriesCol.setAlignment(Pos.CENTER);

        Button backFromWelcome = new Button("Back");
        backFromWelcome.setStyle(backBtnStyle);
        backFromWelcome.setPrefSize(btnWidth, 40);

        VBox welcomeCenter = new VBox(24, welcome, categoriesCol);
        welcomeCenter.setAlignment(Pos.CENTER);

        BorderPane welcomeRoot = new BorderPane();
        welcomeRoot.setCenter(welcomeCenter);
        StackPane welcomeBottom = new StackPane(backFromWelcome);
        welcomeBottom.setPadding(new Insets(20));
        welcomeBottom.setAlignment(Pos.CENTER);
        welcomeRoot.setBottom(welcomeBottom);
        welcomeRoot.setStyle(DARK_BG);

        // ===== Экран талончика =====
        Label ticketTitle = new Label("Ваш номер");
        ticketTitle.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        Label ticketUserLabel = new Label();
        ticketUserLabel.setStyle("-fx-text-fill: #CFD8DC; -fx-font-size: 16px;");

        Label ticketCodeLabel = new Label();
        ticketCodeLabel.setStyle(
                "-fx-text-fill: #FFEB3B;" +
                        "-fx-font-size: 72px;" +
                        "-fx-font-weight: bold;"
        );

        Label ticketHint = new Label("Пожалуйста, ожидайте своей очереди");
        ticketHint.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        VBox ticketCard = new VBox(10, ticketTitle, ticketUserLabel, ticketCodeLabel, ticketHint);
        ticketCard.setAlignment(Pos.CENTER);
        ticketCard.setPadding(new Insets(24));
        ticketCard.setMaxWidth(400);
        ticketCard.setStyle(
                "-fx-background-color: #102542;" +
                        "-fx-background-radius: 16px;" +
                        "-fx-border-color: #546E7A;" +
                        "-fx-border-radius: 16px;" +
                        "-fx-border-width: 2px;"
        );

        StackPane ticketCenter = new StackPane(ticketCard);
        ticketCenter.setPadding(new Insets(24));
        ticketCenter.setAlignment(Pos.CENTER);

        Button ticketDone = new Button("Готово");
        ticketDone.setStyle(backBtnStyle);
        ticketDone.setPrefSize(btnWidth, 40);

        StackPane ticketBottom = new StackPane(ticketDone);
        ticketBottom.setPadding(new Insets(20));
        ticketBottom.setAlignment(Pos.CENTER);

        BorderPane ticketRoot = new BorderPane();
        ticketRoot.setCenter(ticketCenter);
        ticketRoot.setBottom(ticketBottom);
        ticketRoot.setStyle(DARK_BG);

        // ----- Обработчики категорий -----
        for (int i = 0; i < 5; i++) {
            final int catIndex = i + 1; // 1..5

            cat[i].setOnAction(ev -> {
                if (currentUserName == null || currentUserRole == null) {
                    new Alert(Alert.AlertType.WARNING, "Сначала выполните вход").showAndWait();
                    return;
                }

                Button sourceButton = (Button) ev.getSource();
                sourceButton.setDisable(true);

                Task<String> task = new Task<>() {
                    @Override
                    protected String call() {
                        return categoryCodeService.registerClickAndGetCode(
                                currentUserId,
                                currentUserRole,  // "STUDENT" или "TEACHER"
                                catIndex
                        );
                    }
                };

                task.setOnSucceeded(e -> {
                    sourceButton.setDisable(false);
                    String code = task.getValue();

                    ticketCodeLabel.setText(code);

                    String displayRole;
                    if ("TEACHER".equals(currentUserRole)) {
                        displayRole = "Преподаватель";
                    } else if ("STUDENT".equals(currentUserRole)) {
                        displayRole = "Студент";
                    } else {
                        displayRole = currentUserRole;
                    }

                    ticketUserLabel.setText(displayRole + ": " + currentUserName);

                    scene.setRoot(ticketRoot);
                });

                task.setOnFailed(e -> {
                    sourceButton.setDisable(false);
                    Throwable ex = task.getException();
                    new Alert(Alert.AlertType.ERROR, "Ошибка при выдаче кода категории:\n" +
                            (ex != null && ex.getMessage() != null ? ex.getMessage() : "Unknown")).showAndWait();
                });

                new Thread(task, "db-category-click").start();
            });
        }

        // ----- Навигация -----
        btnStudent.setOnAction(e -> {
            clearCurrentUser();
            studentIdField.clear();
            scene.setRoot(studentRoot);
        });

        btnTeacher.setOnAction(e -> {
            clearCurrentUser();
            teacherEmailField.clear();
            scene.setRoot(teacherRoot);
        });

        backToMenuFromStudent.setOnAction(e -> {
            clearCurrentUser();
            studentIdField.clear();
            scene.setRoot(mainRoot);
        });

        backToMenuFromTeacher.setOnAction(e -> {
            clearCurrentUser();
            teacherEmailField.clear();
            scene.setRoot(mainRoot);
        });

        backFromWelcome.setOnAction(e -> {
            clearCurrentUser();
            studentIdField.clear();
            teacherEmailField.clear();
            scene.setRoot(mainRoot);
        });

        ticketDone.setOnAction(e -> {
            clearCurrentUser();
            studentIdField.clear();
            teacherEmailField.clear();
            scene.setRoot(mainRoot);
        });

        // ----- Логин STUDENT по ID -----
        studentEnter.setOnAction(e -> {
            String raw = studentIdField.getText().trim();
            if (raw.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Введите ID").showAndWait();
                return;
            }
            Long id;
            try {
                id = Long.parseLong(raw);
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "ID должен быть числом").showAndWait();
                return;
            }

            Task<Optional<String>> task = new Task<>() {
                @Override
                protected Optional<String> call() {
                    return studentService.getNameById(id);
                }
            };

            studentEnter.setDisable(true);

            task.setOnSucceeded(ev -> {
                studentEnter.setDisable(false);
                task.getValue().ifPresentOrElse(name -> {
                    currentUserId   = id;
                    currentUserName = name;
                    currentUserRole = "STUDENT";

                    welcome.setText("Welcome " + name);
                    scene.setRoot(welcomeRoot);
                }, () -> new Alert(Alert.AlertType.INFORMATION,
                        "Студент с таким ID не найден").showAndWait());
            });

            task.setOnFailed(ev -> {
                studentEnter.setDisable(false);
                Throwable ex = task.getException();
                new Alert(Alert.AlertType.ERROR, "Ошибка доступа к БД:\n" +
                        (ex != null && ex.getMessage() != null ? ex.getMessage() : "Unknown")).showAndWait();
            });

            new Thread(task, "db-student-lookup").start();
        });

        // ----- Логин TEACHER по email -----
        teacherEnter.setOnAction(e -> {
            String email = teacherEmailField.getText().trim();
            if (email.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Введите email").showAndWait();
                return;
            }

            Task<Optional<Teacher>> task = new Task<>() {
                @Override
                protected Optional<Teacher> call() {
                    return teacherService.findByEmail(email);
                }
            };

            teacherEnter.setDisable(true);

            task.setOnSucceeded(ev -> {
                teacherEnter.setDisable(false);
                task.getValue().ifPresentOrElse(teacher -> {
                    currentUserId   = teacher.getId();
                    currentUserName = teacher.getFirstname() + " " + teacher.getLastname();
                    currentUserRole = "TEACHER";

                    welcome.setText("Welcome " + currentUserName);
                    scene.setRoot(welcomeRoot);
                }, () -> new Alert(Alert.AlertType.INFORMATION,
                        "Преподаватель с таким email не найден").showAndWait());
            });

            task.setOnFailed(ev -> {
                teacherEnter.setDisable(false);
                Throwable ex = task.getException();
                new Alert(Alert.AlertType.ERROR, "Ошибка доступа к БД:\n" +
                        (ex != null && ex.getMessage() != null ? ex.getMessage() : "Unknown")).showAndWait();
            });

            new Thread(task, "db-teacher-lookup").start();
        });
    }

    private void clearCurrentUser() {
        currentUserId   = null;
        currentUserName = null;
        currentUserRole = null;
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
