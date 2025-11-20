package kg.alatoo.service_management_system.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import kg.alatoo.service_management_system.keyboards.TextKeyboard;

public class TeacherLoginView {

    private final BorderPane root;
    private final TextField emailField;
    private final Button enterButton;
    private final Button backButton;

    public TeacherLoginView() {
        Label title = new Label("Teacher email");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        emailField = new TextField();
        emailField.setPromptText("Введите ваш email");
        emailField.setEditable(false);
        emailField.setPrefColumnCount(20);
        emailField.setMaxWidth(320);
        emailField.setStyle("-fx-font-size: 14px;");

        TextKeyboard keyboard = TextKeyboard.forTextField(emailField, 40);
        keyboard.setKeyWidth(48);
        // Не растягиваем
        keyboard.setMaxWidth(Region.USE_PREF_SIZE);

        // Центрируем клавиатуру
        HBox keyboardBox = new HBox(keyboard);
        keyboardBox.setAlignment(Pos.CENTER);

        enterButton = new Button("Enter");
        enterButton.setStyle(UiStyles.PRIMARY_BUTTON);
        enterButton.setPrefSize(200, 50);

        backButton = new Button("Back");
        backButton.setStyle(UiStyles.SECONDARY_BUTTON);
        backButton.setPrefSize(200, 40);

        VBox center = new VBox(18, title, emailField, keyboardBox, enterButton);
        center.setAlignment(Pos.CENTER);

        root = new BorderPane();
        root.setCenter(center);

        StackPane bottom = new StackPane(backButton);
        bottom.setPadding(new Insets(20));
        bottom.setAlignment(Pos.CENTER);
        root.setBottom(bottom);
        root.setStyle(UiStyles.DARK_BG);
    }

    public Parent getRoot() {
        return root;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public Button getEnterButton() {
        return enterButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public void reset() {
        emailField.clear();
    }
}
