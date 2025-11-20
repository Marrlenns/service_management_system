package kg.alatoo.service_management_system.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class TicketView {

    private final BorderPane root;
    private final Label userLabel;
    private final Label codeLabel;
    private final Button doneButton;

    public TicketView() {
        Label title = new Label("Ваш номер");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        userLabel = new Label();
        userLabel.setStyle("-fx-text-fill: #CFD8DC; -fx-font-size: 16px;");

        codeLabel = new Label();
        codeLabel.setStyle(
                "-fx-text-fill: #FFEB3B;" +
                        "-fx-font-size: 72px;" +
                        "-fx-font-weight: bold;"
        );

        Label hint = new Label("Пожалуйста, ожидайте своей очереди");
        hint.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        VBox card = new VBox(10, title, userLabel, codeLabel, hint);
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

        doneButton = new Button("Готово");
        doneButton.setStyle(UiStyles.SECONDARY_BUTTON);
        doneButton.setPrefSize(200, 40);

        StackPane bottom = new StackPane(doneButton);
        bottom.setPadding(new Insets(20));
        bottom.setAlignment(Pos.CENTER);

        root = new BorderPane();
        root.setCenter(center);
        root.setBottom(bottom);
        root.setStyle(UiStyles.DARK_BG);
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
}
