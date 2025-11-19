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
import kg.alatoo.service_management_system.services.CategoryCodeService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

@SpringBootApplication
public class App extends Application {

    private ConfigurableApplicationContext context;

    private Long currentStudentId;
    private String currentStudentName;

    @Override
    public void init() {
        context = new SpringApplicationBuilder(App.class)
                .headless(false)
                .run();
    }

    @Override
    public void start(Stage stage) {

        StudentService studentService = context.getBean(StudentService.class);
        CategoryCodeService categoryCodeService = context.getBean(CategoryCodeService.class);


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


        Label title = new Label("Student ID");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("Введите ваш ID");
        idField.setEditable(false); // ввод только через экранную клавиатуру
        idField.setPrefColumnCount(12);
        idField.setMaxWidth(260);
        idField.setStyle("-fx-font-size: 14px;");

        NumberKeyboard keypad = NumberKeyboard.forTextField(idField, 12);
        keypad.setKeyWidth(64);

        Button enter = new Button("Enter");
        enter.setStyle(btnStyle);
        enter.setPrefSize(btnWidth, btnHeight);

        Button backToMenu = new Button("Back");
        backToMenu.setStyle(backBtnStyle);
        backToMenu.setPrefSize(btnWidth, 40);

        VBox studentCenter = new VBox(18, title, idField, keypad, enter);
        studentCenter.setAlignment(Pos.CENTER);

        BorderPane studentRoot = new BorderPane();
        studentRoot.setCenter(studentCenter);
        StackPane studentBottom = new StackPane(backToMenu);
        studentBottom.setPadding(new Insets(20));
        studentBottom.setAlignment(Pos.CENTER);
        studentRoot.setBottom(studentBottom);
        studentRoot.setStyle(DARK_BG);


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


        Label ticketTitle = new Label("Ваш номер");
        ticketTitle.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        Label ticketStudentLabel = new Label();
        ticketStudentLabel.setStyle("-fx-text-fill: #CFD8DC; -fx-font-size: 16px;");

        Label ticketCodeLabel = new Label();
        ticketCodeLabel.setStyle(
                "-fx-text-fill: #FFEB3B;" +
                        "-fx-font-size: 72px;" +
                        "-fx-font-weight: bold;"
        );

        Label ticketHint = new Label("Пожалуйста, ожидайте своей очереди");
        ticketHint.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        VBox ticketCard = new VBox(10, ticketTitle, ticketStudentLabel, ticketCodeLabel, ticketHint);
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


        for (int i = 0; i < 5; i++) {
            final int catIndex = i + 1;

            cat[i].setOnAction(ev -> {
                if (currentStudentId == null) {
                    new Alert(Alert.AlertType.WARNING, "Сначала введите Student ID").showAndWait();
                    return;
                }

                Button sourceButton = (Button) ev.getSource();
                sourceButton.setDisable(true);

                Task<String> task = new Task<>() {
                    @Override
                    protected String call() {
                        return categoryCodeService.registerClickAndGetCode(currentStudentId, catIndex);
                    }
                };

                task.setOnSucceeded(e -> {
                    sourceButton.setDisable(false);
                    String code = task.getValue();

                    // заполняем данные талончика
                    ticketCodeLabel.setText(code);
                    if (currentStudentName != null) {
                        ticketStudentLabel.setText("Студент: " + currentStudentName);
                    } else {
                        ticketStudentLabel.setText("");
                    }

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


        btnStudent.setOnAction(e -> {
            idField.clear();
            currentStudentId = null;
            currentStudentName = null;
            scene.setRoot(studentRoot);
        });

        backToMenu.setOnAction(e -> {
            currentStudentId = null;
            currentStudentName = null;
            idField.clear();
            scene.setRoot(mainRoot);
        });

        backFromWelcome.setOnAction(e -> {
            currentStudentId = null;
            currentStudentName = null;
            idField.clear();
            scene.setRoot(mainRoot);
        });

        ticketDone.setOnAction(e -> {

            currentStudentId = null;
            currentStudentName = null;
            idField.clear();
            scene.setRoot(mainRoot);
        });


        enter.setOnAction(e -> {
            String raw = idField.getText().trim();
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
                    return studentService.getNameById(id); // ожидается Optional<String> name
                }
            };

            enter.setDisable(true);

            task.setOnSucceeded(ev -> {
                enter.setDisable(false);
                task.getValue().ifPresentOrElse(name -> {
                    currentStudentId = id;
                    currentStudentName = name;
                    welcome.setText("Welcome " + name);
                    scene.setRoot(welcomeRoot);
                }, () -> new Alert(Alert.AlertType.INFORMATION, "Студент с таким ID не найден").showAndWait());
            });

            task.setOnFailed(ev -> {
                enter.setDisable(false);
                Throwable ex = task.getException();
                new Alert(Alert.AlertType.ERROR, "Ошибка доступа к БД:\n" +
                        (ex != null && ex.getMessage() != null ? ex.getMessage() : "Unknown")).showAndWait();
            });

            new Thread(task, "db-student-lookup").start();
        });
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
