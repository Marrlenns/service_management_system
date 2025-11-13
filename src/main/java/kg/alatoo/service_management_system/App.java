package kg.alatoo.service_management_system;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override public void init() throws Exception {
    }

    @Override public void start(Stage stage) {
        StackPane root = new StackPane(new Label("Hi!"));
        stage.setScene(new Scene(root, 360, 220));

        stage.setFullScreenExitHint("Нажмите ESC, чтобы выйти");
        stage.setFullScreen(true);
        stage.show();
    }

    @Override public void stop() throws Exception {
        // закрыть ресурсы
    }

    public static void main(String[] args) {
        launch(args);
    }
}
