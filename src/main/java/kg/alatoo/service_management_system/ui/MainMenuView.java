package kg.alatoo.service_management_system.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;

public class MainMenuView {

    private final StackPane root;
    private final Button studentButton;
    private final Button teacherButton;
    private final Button otherButton;

    public MainMenuView() {
        studentButton = new Button("Student");
        teacherButton = new Button("Teacher");
        otherButton   = new Button("Other");

        for (Button b : new Button[]{studentButton, teacherButton, otherButton}) {
            b.setStyle(UiStyles.PRIMARY_BUTTON);
            b.setPrefSize(200, 50);
        }

        VBox menu = new VBox(15, studentButton, teacherButton, otherButton);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(24));

        root = new StackPane(menu);
        root.setStyle(UiStyles.DARK_BG);
    }

    public Parent getRoot() {
        return root;
    }

    public Button getStudentButton() {
        return studentButton;
    }

    public Button getTeacherButton() {
        return teacherButton;
    }

    public Button getOtherButton() {
        return otherButton;
    }
}
