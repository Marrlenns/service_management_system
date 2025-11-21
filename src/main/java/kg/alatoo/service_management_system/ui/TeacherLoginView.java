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
import kg.alatoo.service_management_system.keyboards.TextKeyboard;

import java.util.function.Consumer;

public class TeacherLoginView {

    private final BorderPane root;
    private final Label titleLabel;
    private final TextField emailField;
    private final Button enterButton;
    private final Button backButton;

    private static final double ENTER_WIDTH_RATIO  = 0.25;
    private static final double ENTER_HEIGHT_RATIO = 0.07;
    private static final double BACK_WIDTH_RATIO   = 0.2;
    private static final double BACK_HEIGHT_RATIO  = 0.06;

    public TeacherLoginView(Language initialLanguage,
                            Consumer<Language> onLanguageChange) {

        titleLabel = new Label();
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");

        emailField = new TextField();
        emailField.setEditable(false);
        emailField.setPrefColumnCount(20);
        emailField.setMaxWidth(320);
        emailField.setStyle("-fx-font-size: 16px;");

        TextKeyboard keyboard = TextKeyboard.forTextField(emailField, 40);
        keyboard.setKeyWidth(52);
        keyboard.setMaxWidth(Region.USE_PREF_SIZE);

        HBox keyboardBox = new HBox(keyboard);
        keyboardBox.setAlignment(Pos.CENTER);

        enterButton = new Button();
        enterButton.setStyle(UiStyles.PRIMARY_BUTTON);

        backButton = new Button();
        backButton.setStyle(UiStyles.SECONDARY_BUTTON);

        VBox content = new VBox(18, titleLabel, emailField, keyboardBox, enterButton);
        content.setAlignment(Pos.CENTER);

        StackPane card = new StackPane(content);
        card.setStyle(UiStyles.CARD);
        card.setPadding(new Insets(32));
        card.setMaxWidth(560);

        StackPane center = new StackPane(card);
        center.setPadding(new Insets(32));
        center.setAlignment(Pos.CENTER);

        root = new BorderPane();
        root.setTop(HeaderBar.create(onLanguageChange));
        root.setCenter(center);

        StackPane bottom = new StackPane(backButton);
        bottom.setPadding(new Insets(20));
        bottom.setAlignment(Pos.CENTER);
        root.setBottom(bottom);
        root.setStyle(UiStyles.DARK_BG);

        // биндим размеры кнопок
        enterButton.prefWidthProperty().bind(root.widthProperty().multiply(ENTER_WIDTH_RATIO));
        enterButton.prefHeightProperty().bind(root.heightProperty().multiply(ENTER_HEIGHT_RATIO));

        backButton.prefWidthProperty().bind(root.widthProperty().multiply(BACK_WIDTH_RATIO));
        backButton.prefHeightProperty().bind(root.heightProperty().multiply(BACK_HEIGHT_RATIO));

        applyLanguage(initialLanguage);
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

    public void applyLanguage(Language lang) {
        titleLabel.setText(I18n.teacherEmailTitle(lang));
        emailField.setPromptText(I18n.teacherEmailPrompt(lang));
        enterButton.setText(I18n.buttonEnter(lang));
        backButton.setText(I18n.buttonBack(lang));
    }
}
