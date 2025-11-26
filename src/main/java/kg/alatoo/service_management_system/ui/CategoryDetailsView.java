package kg.alatoo.service_management_system.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import kg.alatoo.service_management_system.i18n.I18n;
import kg.alatoo.service_management_system.i18n.Language;

import java.util.function.Consumer;

public class CategoryDetailsView {

    private final BorderPane root;
    private final Label titleLabel;
    private final Label descriptionLabel;
    private final Button continueButton;
    private final Button cancelButton;

    private int currentCategoryIndex = 1;

    private static final double BTN_WIDTH_RATIO  = 0.18;
    private static final double BTN_HEIGHT_RATIO = 0.06;

    public CategoryDetailsView(Language initialLanguage,
                               Consumer<Language> onLanguageChange) {

        titleLabel = new Label();
        titleLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;"
        );

        descriptionLabel = new Label();
        descriptionLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 26px;"
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextAlignment(TextAlignment.LEFT);
        descriptionLabel.setMaxWidth(800);

        VBox content = new VBox(20, titleLabel, descriptionLabel);
        content.setAlignment(Pos.TOP_LEFT);

        StackPane card = new StackPane(content);
        card.setStyle(UiStyles.CARD);
        card.setPadding(new Insets(32));
        card.setMaxWidth(900);

        StackPane center = new StackPane(card);
        center.setPadding(new Insets(32));
        center.setAlignment(Pos.CENTER);

        continueButton = new Button();
        continueButton.setStyle(UiStyles.PRIMARY_BUTTON);

        cancelButton = new Button();
        cancelButton.setStyle(UiStyles.SECONDARY_BUTTON);

        HBox buttonsRow = new HBox(20, cancelButton, continueButton);
        buttonsRow.setAlignment(Pos.CENTER);

        StackPane bottom = new StackPane(buttonsRow);
        bottom.setPadding(new Insets(20));

        root = new BorderPane();
        root.setTop(HeaderBar.create(onLanguageChange));
        root.setCenter(center);
        root.setBottom(bottom);
        root.setStyle(UiStyles.DARK_BG);

        // размеры кнопок завязываем на размер окна
        continueButton.prefWidthProperty()
                .bind(root.widthProperty().multiply(BTN_WIDTH_RATIO));
        continueButton.prefHeightProperty()
                .bind(root.heightProperty().multiply(BTN_HEIGHT_RATIO));

        cancelButton.prefWidthProperty()
                .bind(root.widthProperty().multiply(BTN_WIDTH_RATIO));
        cancelButton.prefHeightProperty()
                .bind(root.heightProperty().multiply(BTN_HEIGHT_RATIO));

        applyLanguage(initialLanguage);
    }

    public Parent getRoot() {
        return root;
    }

    public Button getContinueButton() {
        return continueButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    /**
     * Устанавливаем номер категории и сразу обновляем текст под текущий язык.
     */
    public void setCategoryIndex(int categoryIndex, Language lang) {
        this.currentCategoryIndex = categoryIndex;
        applyLanguage(lang);
    }

    public void applyLanguage(Language lang) {
        titleLabel.setText(I18n.categoryTitle(lang, currentCategoryIndex));
        descriptionLabel.setText(I18n.categoryDescription(lang, currentCategoryIndex));
        continueButton.setText(I18n.buttonContinue(lang));
        cancelButton.setText(I18n.buttonCancel(lang));
    }
}
