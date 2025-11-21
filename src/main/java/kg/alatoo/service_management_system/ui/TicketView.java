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
    private final Button doneButton;

    private static final double DONE_WIDTH_RATIO  = 0.2;
    private static final double DONE_HEIGHT_RATIO = 0.06;

    public TicketView(Language initialLanguage,
                      Consumer<Language> onLanguageChange) {

        titleLabel = new Label();
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        userLabel = new Label();
        userLabel.setStyle("-fx-text-fill: #CFD8DC; -fx-font-size: 16px;");

        codeLabel = new Label();
        codeLabel.setStyle(
                "-fx-text-fill: #FFEB3B;" +
                        "-fx-font-size: 72px;" +
                        "-fx-font-weight: bold;"
        );

        hintLabel = new Label();
        hintLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        VBox card = new VBox(10, titleLabel, userLabel, codeLabel, hintLabel);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(24));
        card.setMaxWidth(400);
        card.setStyle(
                "-fx-background-color: #102542;" +
                        "-fx-background-radius: 16px;" +
                        "-fx-border-color: #546E7A;" +
                        "-fx-border-radius: 16px;" +
                        "-fx-border-width: 2px;"
        );

        StackPane center = new StackPane(card);
        center.setPadding(new Insets(24));
        center.setAlignment(Pos.CENTER);

        doneButton = new Button();
        doneButton.setStyle(UiStyles.SECONDARY_BUTTON);
        doneButton.setPrefSize(200, 40);

        StackPane bottom = new StackPane(doneButton);
        bottom.setPadding(new Insets(20));
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
        doneButton.setText(I18n.buttonDone(lang));
    }
}
