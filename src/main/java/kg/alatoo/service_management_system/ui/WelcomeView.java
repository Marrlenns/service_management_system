package kg.alatoo.service_management_system.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class WelcomeView {

    private final BorderPane root;
    private final Label welcomeLabel;
    private final Button[] categoryButtons;
    private final Button backButton;

    public WelcomeView() {
        welcomeLabel = new Label();
        welcomeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");

        categoryButtons = new Button[5];
        for (int i = 0; i < 5; i++) {
            Button b = new Button("Category " + (i + 1));
            b.setStyle(UiStyles.CATEGORY_BUTTON);
            b.setPrefSize(260, 56);
            categoryButtons[i] = b;
        }

        VBox categoriesCol = new VBox(12,
                categoryButtons[0], categoryButtons[1],
                categoryButtons[2], categoryButtons[3],
                categoryButtons[4]);
        categoriesCol.setAlignment(Pos.CENTER);

        backButton = new Button("Back");
        backButton.setStyle(UiStyles.SECONDARY_BUTTON);
        backButton.setPrefSize(200, 40);

        VBox center = new VBox(24, welcomeLabel, categoriesCol);
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

    public Label getWelcomeLabel() {
        return welcomeLabel;
    }

    public Button[] getCategoryButtons() {
        return categoryButtons;
    }

    public Button getBackButton() {
        return backButton;
    }
}
