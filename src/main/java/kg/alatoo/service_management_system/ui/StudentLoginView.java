package kg.alatoo.service_management_system.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import kg.alatoo.service_management_system.keyboards.NumberKeyboard;

public class StudentLoginView {

    private final BorderPane root;
    private final TextField idField;
    private final Button enterButton;
    private final Button backButton;

    public StudentLoginView() {
        Label title = new Label("Student ID");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        idField = new TextField();
        idField.setPromptText("Введите ваш ID");
        idField.setEditable(false);
        idField.setPrefColumnCount(12);
        idField.setMaxWidth(260);
        idField.setStyle("-fx-font-size: 14px;");

        NumberKeyboard keypad = NumberKeyboard.forTextField(idField, 12);
        keypad.setKeyWidth(64);
        // ВАЖНО: не растягиваем клавиатуру на всю ширину
        keypad.setMaxWidth(Region.USE_PREF_SIZE);

        // Оборачиваем в HBox, чтобы центрировать
        HBox keypadBox = new HBox(keypad);
        keypadBox.setAlignment(Pos.CENTER);

        enterButton = new Button("Enter");
        enterButton.setStyle(UiStyles.PRIMARY_BUTTON);
        enterButton.setPrefSize(200, 50);

        backButton = new Button("Back");
        backButton.setStyle(UiStyles.SECONDARY_BUTTON);
        backButton.setPrefSize(200, 40);

        VBox center = new VBox(18, title, idField, keypadBox, enterButton);
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

    public TextField getIdField() {
        return idField;
    }

    public Button getEnterButton() {
        return enterButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public void reset() {
        idField.clear();
    }
}
