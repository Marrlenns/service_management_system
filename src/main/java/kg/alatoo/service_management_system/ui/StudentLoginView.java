package kg.alatoo.service_management_system.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import kg.alatoo.service_management_system.i18n.I18n;
import kg.alatoo.service_management_system.i18n.Language;
import kg.alatoo.service_management_system.keyboards.NumberKeyboard;

import java.util.function.Consumer;

public class StudentLoginView {

    private final BorderPane root;
    private final Label titleLabel;
    private final TextField idField;
    private final Button enterButton;
    private final Button backButton;

    private static final double ENTER_WIDTH_RATIO  = 0.25;
    private static final double ENTER_HEIGHT_RATIO = 0.07;
    private static final double BACK_WIDTH_RATIO   = 0.2;
    private static final double BACK_HEIGHT_RATIO  = 0.06;

    public StudentLoginView(Language initialLanguage,
                            Consumer<Language> onLanguageChange) {

        titleLabel = new Label();
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        idField = new TextField();
        idField.setEditable(false);
        idField.setPrefColumnCount(12);
        idField.setMaxWidth(260);
        idField.setStyle("-fx-font-size: 14px;");

        NumberKeyboard keypad = NumberKeyboard.forTextField(idField, 12);
        keypad.setKeyWidth(64);
        keypad.setMaxWidth(Region.USE_PREF_SIZE);

        HBox keypadBox = new HBox(keypad);
        keypadBox.setAlignment(Pos.CENTER);

        enterButton = new Button();
        enterButton.setStyle(UiStyles.PRIMARY_BUTTON);
        enterButton.setPrefSize(200, 50);

        backButton = new Button();
        backButton.setStyle(UiStyles.SECONDARY_BUTTON);
        backButton.setPrefSize(200, 40);

        VBox center = new VBox(18, titleLabel, idField, keypadBox, enterButton);
        center.setAlignment(Pos.CENTER);

        root = new BorderPane();
        root.setTop(HeaderBar.create(onLanguageChange));
        root.setCenter(center);

        StackPane bottom = new StackPane(backButton);
        bottom.setPadding(new Insets(20));
        bottom.setAlignment(Pos.CENTER);
        root.setBottom(bottom);
        root.setStyle(UiStyles.DARK_BG);

        // биндим размеры кнопок к размеру окна
        enterButton.prefWidthProperty().bind(root.widthProperty().multiply(ENTER_WIDTH_RATIO));
        enterButton.prefHeightProperty().bind(root.heightProperty().multiply(ENTER_HEIGHT_RATIO));

        backButton.prefWidthProperty().bind(root.widthProperty().multiply(BACK_WIDTH_RATIO));
        backButton.prefHeightProperty().bind(root.heightProperty().multiply(BACK_HEIGHT_RATIO));

        applyLanguage(initialLanguage);
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

    public void applyLanguage(Language lang) {
        titleLabel.setText(I18n.studentIdTitle(lang));
        idField.setPromptText(I18n.studentIdPrompt(lang));
        enterButton.setText(I18n.buttonEnter(lang));
        backButton.setText(I18n.buttonBack(lang));
    }
}
