package kg.alatoo.service_management_system.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import kg.alatoo.service_management_system.i18n.I18n;
import kg.alatoo.service_management_system.i18n.Language;

import java.util.function.Consumer;

public class WelcomeView {

    private final BorderPane root;
    private final Label welcomeLabel;
    private final Label hintLabel;          // 🔹 новая подсказка
    private final Button[] categoryButtons;
    private final Button backButton;

    private static final double CAT_WIDTH_RATIO   = 0.35;
    private static final double CAT_HEIGHT_RATIO  = 0.08;
    private static final double BACK_WIDTH_RATIO  = 0.20;
    private static final double BACK_HEIGHT_RATIO = 0.06;

    public WelcomeView(Language initialLanguage,
                       Consumer<Language> onLanguageChange) {

        welcomeLabel = new Label();
        welcomeLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 30px;" +
                        "-fx-font-weight: bold;"
        );

        // 🔹 текст-подсказка под приветствием
        hintLabel = new Label();
        hintLabel.setStyle(
                "-fx-text-fill: #CFD8DC;" +
                        "-fx-font-size: 18px;"
        );
        hintLabel.setWrapText(true);
        hintLabel.setMaxWidth(800);

        categoryButtons = new Button[5];
        for (int i = 0; i < 5; i++) {
            Button b = new Button();
            b.setStyle(UiStyles.CATEGORY_BUTTON);
            b.setPrefSize(260, 56);
            categoryButtons[i] = b;
        }

        VBox categoriesCol = new VBox(14,
                categoryButtons[0],
                categoryButtons[1],
                categoryButtons[2],
                categoryButtons[3],
                categoryButtons[4]
        );
        categoriesCol.setAlignment(Pos.CENTER);

        backButton = new Button();
        backButton.setStyle(UiStyles.SECONDARY_BUTTON);

        // 🔹 теперь: приветствие + подсказка + кнопки категорий
        VBox content = new VBox(16, welcomeLabel, hintLabel, categoriesCol);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(32));

        StackPane center = new StackPane(content);
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

        // Биндим размеры кнопок к размеру окна
        for (Button b : categoryButtons) {
            b.prefWidthProperty().bind(root.widthProperty().multiply(CAT_WIDTH_RATIO));
            b.prefHeightProperty().bind(root.heightProperty().multiply(CAT_HEIGHT_RATIO));
        }

        backButton.prefWidthProperty().bind(root.widthProperty().multiply(BACK_WIDTH_RATIO));
        backButton.prefHeightProperty().bind(root.heightProperty().multiply(BACK_HEIGHT_RATIO));

        applyLanguage(initialLanguage);
    }

    public Parent getRoot() {
        return root;
    }

    public Label getWelcomeLabel() {
        return welcomeLabel;
    }

    public Button[] getCategoryButtons() {
        return categoryButtons;
    }

    public Button getBackButton() {
        return backButton;
    }

    public void applyLanguage(Language lang) {
        for (int i = 0; i < categoryButtons.length; i++) {
            categoryButtons[i].setText(I18n.categoryLabel(lang, i + 1));
        }
        hintLabel.setText(I18n.categorySelectHint(lang));   // 🔹 используем новый текст
        backButton.setText(I18n.buttonBack(lang));
    }
}
