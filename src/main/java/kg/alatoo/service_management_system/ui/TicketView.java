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

public class TicketView {

    private final BorderPane root;
    private final Label titleLabel;
    private final Label userLabel;
    private final Label codeLabel;
    private final Label hintLabel;
    private final Label photoHintLabel;
    private final Button doneButton;

    // чуть шире и выше кнопку, чтобы по стилю подходила к большим текстам
    private static final double DONE_WIDTH_RATIO  = 0.25;
    private static final double DONE_HEIGHT_RATIO = 0.08;

    public TicketView(Language initialLanguage,
                      Consumer<Language> onLanguageChange) {

        // 🔹 Заголовок ("Ваш номер") — ОЧЕНЬ крупный
        titleLabel = new Label();
        titleLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 48px;" +          // было 36 → теперь ещё больше
                        "-fx-font-weight: bold;"
        );

        // 🔹 Роль + имя ("Студент: Имя")
        userLabel = new Label();
        userLabel.setStyle(
                "-fx-text-fill: #CFD8DC;" +
                        "-fx-font-size: 32px;"            // было 24 → увеличили
        );

        // 🔹 Сам номер талона — максимально жирный и огромный
        codeLabel = new Label();
        codeLabel.setStyle(
                "-fx-text-fill: #FFEB3B;" +
                        "-fx-font-size: 180px;" +         // было 120 → теперь реально огромный
                        "-fx-font-weight: bold;"
        );

        // 🔹 Основная подсказка ("Пожалуйста, ожидайте своей очереди")
        hintLabel = new Label();
        hintLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 32px;"            // было 24 → больше
        );

        // 🔹 Тонкий текст про фото талона
        photoHintLabel = new Label();
        photoHintLabel.setStyle(
                "-fx-text-fill: #B0BEC5;" +
                        "-fx-font-size: 26px;"            // было 20 → тоже заметнее
        );
        photoHintLabel.setWrapText(true);
        photoHintLabel.setMaxWidth(900);

        VBox content = new VBox(20, titleLabel, userLabel, codeLabel, hintLabel, photoHintLabel);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50));

        StackPane center = new StackPane(content);
        center.setPadding(new Insets(40));
        center.setAlignment(Pos.CENTER);

        doneButton = new Button();
        doneButton.setStyle(UiStyles.SECONDARY_BUTTON);

        StackPane bottom = new StackPane(doneButton);
        bottom.setPadding(new Insets(30));
        bottom.setAlignment(Pos.CENTER);

        root = new BorderPane();
        root.setTop(HeaderBar.create(onLanguageChange));
        root.setCenter(center);
        root.setBottom(bottom);
        root.setStyle(UiStyles.DARK_BG);

        doneButton.prefWidthProperty().bind(root.widthProperty().multiply(DONE_WIDTH_RATIO));
        doneButton.prefHeightProperty().bind(root.heightProperty().multiply(DONE_HEIGHT_RATIO));

        applyLanguage(initialLanguage);
    }

    public Parent getRoot() {
        return root;
    }

    public Button getDoneButton() {
        return doneButton;
    }

    public void showTicket(String displayRole, String name, String code) {
        codeLabel.setText(code);
        if (name != null && !name.isBlank()) {
            userLabel.setText(displayRole + ": " + name);
        } else {
            userLabel.setText(displayRole);
        }
    }

    public void applyLanguage(Language lang) {
        titleLabel.setText(I18n.ticketTitle(lang));
        hintLabel.setText(I18n.ticketHint(lang));
        photoHintLabel.setText(I18n.ticketPhotoHint(lang));
        doneButton.setText(I18n.buttonDone(lang));
    }
}
