package kg.alatoo.service_management_system.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import kg.alatoo.service_management_system.i18n.I18n;
import kg.alatoo.service_management_system.i18n.Language;

import java.util.function.Consumer;

public class MainMenuView {

    private final BorderPane root;
    private final Button studentButton;
    private final Button teacherButton;
    private final Button otherButton;

    private static final double BUTTON_WIDTH_RATIO  = 0.3;  // 30% ширины окна
    private static final double BUTTON_HEIGHT_RATIO = 0.08; // 8% высоты окна

    public MainMenuView(Language initialLanguage,
                        Consumer<Language> onLanguageChange) {

        studentButton = new Button();
        teacherButton = new Button();
        otherButton   = new Button();

        for (Button b : new Button[]{studentButton, teacherButton, otherButton}) {
            b.setStyle(UiStyles.PRIMARY_BUTTON);
            // начальные значения, дальше будут переопределены биндингами
            b.setPrefSize(200, 50);
        }

        VBox menu = new VBox(15, studentButton, teacherButton, otherButton);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(24));

        root = new BorderPane();
        root.setCenter(menu);
        root.setTop(HeaderBar.create(onLanguageChange));
        root.setStyle(UiStyles.DARK_BG);

        // Привязываем размеры кнопок к размеру окна
        bindButtonSize(studentButton);
        bindButtonSize(teacherButton);
        bindButtonSize(otherButton);

        applyLanguage(initialLanguage);
    }

    private void bindButtonSize(Button b) {
        b.prefWidthProperty().bind(root.widthProperty().multiply(BUTTON_WIDTH_RATIO));
        b.prefHeightProperty().bind(root.heightProperty().multiply(BUTTON_HEIGHT_RATIO));
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

    public void applyLanguage(Language lang) {
        studentButton.setText(I18n.mainStudent(lang));
        teacherButton.setText(I18n.mainTeacher(lang));
        otherButton.setText(I18n.mainOther(lang));
    }
}
