package kg.alatoo.service_management_system;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import kg.alatoo.service_management_system.entities.CategoryClick;
import kg.alatoo.service_management_system.services.QueueBoardService;
import kg.alatoo.service_management_system.ui.UiStyles;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class QueueBoardApp extends Application {

    private ConfigurableApplicationContext context;

    private QueueBoardService boardService;

    // слева 10 строк
    private static final int LEFT_ROWS = 10;
    // справа 4 строки
    private static final int RIGHT_ROWS = 4;

    private Label[] waitingLabels  = new Label[LEFT_ROWS];
    private Label[] progressLabels = new Label[RIGHT_ROWS];

    @Override
    public void init() {
        context = new SpringApplicationBuilder(QueueBoardApp.class)
                .headless(false)
                .run();
    }

    @Override
    public void start(Stage stage) {
        boardService = context.getBean(QueueBoardService.class);

        // ----- Заголовок сверху -----
        Label header = new Label("ЭЛЕКТРОННАЯ ОЧЕРЕДЬ");
        header.setStyle(
                "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-size: 40px;" +
                        "-fx-font-weight: bold;"
        );
        StackPane headerPane = new StackPane(header);
        headerPane.setPadding(new Insets(16));
        headerPane.setStyle(
                "-fx-background-color: rgba(6,12,24,0.95);" +
                        "-fx-border-color: rgba(255,255,255,0.1);" +
                        "-fx-border-width: 0 0 1 0;"
        );

        // ----- Левая часть (ОЖИДАЮТ) -----
        Label waitingTitle = new Label("ОЖИДАЮТ");
        waitingTitle.setStyle(
                "-fx-text-fill: #FFEB3B;" +
                        "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;"
        );

        VBox waitingBox = new VBox(8);
        waitingBox.getChildren().add(waitingTitle);
        waitingBox.setAlignment(Pos.TOP_CENTER);
        waitingBox.setPadding(new Insets(24));

        for (int i = 0; i < LEFT_ROWS; i++) {
            Label row = createRowLabel();
            waitingLabels[i] = row;
            waitingBox.getChildren().add(row);
            // ВАЖНО: каждая строка растягивается, чтобы заполнить колонку
            VBox.setVgrow(row, Priority.ALWAYS);
        }

        // ----- Правая часть (ОБСЛУЖИВАЮТСЯ) -----
        Label progressTitle = new Label("ОБСЛУЖИВАЮТСЯ");
        progressTitle.setStyle(
                "-fx-text-fill: #4CAF50;" +
                        "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;"
        );

        VBox progressBox = new VBox(8);
        progressBox.getChildren().add(progressTitle);
        progressBox.setAlignment(Pos.TOP_CENTER);
        progressBox.setPadding(new Insets(24));

        for (int i = 0; i < RIGHT_ROWS; i++) {
            Label row = createRowLabel();
            progressLabels[i] = row;
            progressBox.getChildren().add(row);
            // Тоже растягиваем строки по высоте
            VBox.setVgrow(row, Priority.ALWAYS);
        }

        // ----- Вертикальная линия посередине -----
        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPrefWidth(3);
        separator.setOpacity(0.6);
        separator.setStyle(
                "-fx-background-color: rgba(255,255,255,0.25);"
        );
        VBox sepBox = new VBox(separator);
        sepBox.setAlignment(Pos.CENTER);
        sepBox.setPadding(new Insets(24, 8, 24, 8));

        // ----- Центр: левая колонка | линия | правая колонка -----
        HBox columns = new HBox(16, waitingBox, sepBox, progressBox);
        columns.setAlignment(Pos.CENTER);
        HBox.setHgrow(waitingBox, Priority.ALWAYS);
        HBox.setHgrow(progressBox, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setTop(headerPane);
        root.setCenter(columns);
        root.setStyle(UiStyles.DARK_BG);

        Scene scene = new Scene(root);
        stage.setTitle("Queue Board");
        stage.setScene(scene);

        // Развернуть на весь экран
        stage.setMaximized(true);
        // Если нужен настоящий fullscreen:
        // stage.setFullScreen(true);
        // stage.setFullScreenExitHint("");

        stage.show();

        // Первый запрос сразу
        refreshData();

        // Обновление каждые 2 секунды
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> refreshData())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Одна строка табло (большой текст, красивый фон).
     * Высота не фиксирована — Label будет растягиваться по Vgrow.
     */
    private Label createRowLabel() {
        Label label = new Label();
        label.setMaxWidth(Double.MAX_VALUE);
        label.setStyle(
                "-fx-text-fill: #ECEFF1;" +
                        "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 20 8 20;" +
                        "-fx-background-color: rgba(13, 32, 57, 0.9);" +
                        "-fx-background-radius: 12;"
        );
        return label;
    }

    /**
     * Обновляет данные в отдельном потоке, UI не блокируется.
     */
    private void refreshData() {
        Task<QueueData> task = new Task<>() {
            @Override
            protected QueueData call() {
                List<CategoryClick> waiting = boardService.getTodayWaiting();
                List<CategoryClick> inProgress = boardService.getTodayInProgress();
                return new QueueData(waiting, inProgress);
            }
        };

        task.setOnSucceeded(e -> {
            QueueData data = task.getValue();
            updateColumn(waitingLabels, data.waiting());
            updateColumn(progressLabels, data.inProgress());
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            System.err.println("[QueueBoard] Failed to refresh data: " +
                    (ex != null ? ex.getMessage() : "unknown error"));
        });

        new Thread(task, "queue-board-refresh").start();
    }

    /**
     * Заполняем массив лейблов данными: если данных меньше, просто очищаем текст.
     */
    private void updateColumn(Label[] labels, List<CategoryClick> data) {
        for (int i = 0; i < labels.length; i++) {
            if (i < data.size()) {
                labels[i].setText(formatTicket(data.get(i)));
            } else {
                labels[i].setText(""); // пустая строка
            }
        }
    }

    /**
     * Как отображается один талон.
     * Например: "A12   ·   Студент"
     */
    private static String formatTicket(CategoryClick click) {
        String code = click.getCode();    // "A12"
        String role = click.getRole();    // "STUDENT" / "TEACHER" / "OTHER"

        String roleLabel;
        if ("STUDENT".equalsIgnoreCase(role)) {
            roleLabel = "Студент";
        } else if ("TEACHER".equalsIgnoreCase(role)) {
            roleLabel = "Преподаватель";
        } else if ("OTHER".equalsIgnoreCase(role)) {
            roleLabel = "Гость";
        } else {
            roleLabel = role;
        }

        return code + "   ·   " + roleLabel;
    }

    private record QueueData(
            List<CategoryClick> waiting,
            List<CategoryClick> inProgress
    ) {}

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
