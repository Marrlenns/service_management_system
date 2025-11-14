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
import kg.alatoo.service_management_system.keyboards.NumberKeyboard;
import kg.alatoo.service_management_system.services.StudentService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

@SpringBootApplication
public class App extends Application {

    private ConfigurableApplicationContext context;

    @Override public void init() {
        context = new SpringApplicationBuilder(App.class)
                .headless(false)
                .run();
    }

    @Override public void start(Stage stage) {
        StudentService studentService = context.getBean(StudentService.class);

        Button btnStudent = new Button("Student");
        Button btnTeacher = new Button("Teacher");
        Button btnOther   = new Button("Other");

        String btnStyle = String.join("",
                "-fx-background-color: #1976D2;",
                "-fx-text-fill: white;",
                "-fx-font-size: 16px;",
                "-fx-font-weight: bold;",
                "-fx-background-radius: 8px;"
        );
        double btnWidth = 200, btnHeight = 50;
        for (Button b : new Button[]{btnStudent, btnTeacher, btnOther}) {
            b.setStyle(btnStyle);
            b.setPrefSize(btnWidth, btnHeight);
        }

        VBox menu = new VBox(15, btnStudent, btnTeacher, btnOther);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(24));

        StackPane mainRoot = new StackPane(menu);
        mainRoot.setStyle("-fx-background-color: #0B1E39;");

        Scene scene = new Scene(mainRoot, 800, 600);
        stage.setTitle("Главное меню");
        stage.setScene(scene);
        stage.show();

        Label title = new Label("Student ID");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("Введите ваш ID");
        idField.setEditable(false);
        idField.setPrefColumnCount(12);
        idField.setMaxWidth(260);
        idField.setStyle("-fx-font-size: 14px;");

        NumberKeyboard keypad = NumberKeyboard.forTextField(idField, 12);
        keypad.setKeyWidth(64);

        Button enter = new Button("Enter");
        enter.setStyle(btnStyle);
        enter.setPrefSize(btnWidth, btnHeight);

        Button backToMenu = new Button("Back");
        backToMenu.setStyle("-fx-background-color: #455A64; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px;");
        backToMenu.setPrefSize(btnWidth, 40);

        VBox centerBox = new VBox(18, title, idField, keypad, enter);
        centerBox.setAlignment(Pos.CENTER);

        BorderPane studentRoot = new BorderPane();
        studentRoot.setCenter(centerBox);
        BorderPane.setAlignment(centerBox, Pos.CENTER);
        StackPane bottom = new StackPane(backToMenu);
        bottom.setPadding(new Insets(20));
        bottom.setAlignment(Pos.CENTER);
        studentRoot.setBottom(bottom);
        studentRoot.setStyle("-fx-background-color: #0B1E39;");

        // ===== Экран приветствия =====
        Label welcome = new Label();
        welcome.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");

        Button backFromWelcome = new Button("Back");
        backFromWelcome.setStyle("-fx-background-color: #455A64; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px;");
        backFromWelcome.setPrefSize(btnWidth, 40);

        VBox welcomeCenter = new VBox(20, welcome);
        welcomeCenter.setAlignment(Pos.CENTER);

        BorderPane welcomeRoot = new BorderPane();
        welcomeRoot.setCenter(welcomeCenter);
        StackPane welcomeBottom = new StackPane(backFromWelcome);
        welcomeBottom.setPadding(new Insets(20));
        welcomeBottom.setAlignment(Pos.CENTER);
        welcomeRoot.setBottom(welcomeBottom);
        welcomeRoot.setStyle("-fx-background-color: #0B1E39;");

        btnStudent.setOnAction(e -> {
            idField.clear();
            scene.setRoot(studentRoot);
        });
        backToMenu.setOnAction(e -> scene.setRoot(mainRoot));
        backFromWelcome.setOnAction(e -> scene.setRoot(mainRoot));

        enter.setOnAction(e -> {
            String raw = idField.getText().trim();
            if (raw.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Введите ID").showAndWait();
                return;
            }
            Long id;
            id = Long.parseLong(raw);

            Task<Optional<String>> task = new Task<>() {
                @Override protected Optional<String> call() {
                    return studentService.getNameById(id);
                }
            };

            enter.setDisable(true);

            task.setOnSucceeded(ev -> {
                enter.setDisable(false);
                Optional<String> nameOpt = task.getValue();
                if (nameOpt.isPresent()) {
                    welcome.setText("Welcome " + nameOpt.get());
                    scene.setRoot(welcomeRoot);
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Студент с таким ID не найден").showAndWait();
                }
            });

            task.setOnFailed(ev -> {
                enter.setDisable(false);
                Throwable ex = task.getException();
                new Alert(Alert.AlertType.ERROR, "Ошибка доступа к БД:\n" +
                        (ex != null ? ex.getMessage() : "Unknown")).showAndWait();
            });

            new Thread(task, "db-student-lookup").start();
        });
    }

    @Override public void stop() {
        context.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
