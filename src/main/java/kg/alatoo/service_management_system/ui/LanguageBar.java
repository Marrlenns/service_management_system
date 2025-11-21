package kg.alatoo.service_management_system.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import kg.alatoo.service_management_system.i18n.Language;

import java.io.InputStream;
import java.util.function.Consumer;

public final class LanguageBar {

    private LanguageBar() {}

    public static HBox create(Consumer<Language> onLanguageChange) {
        Button ru = createFlagButton("/flags/ru.jpg");
        Button ky = createFlagButton("/flags/ky.jpeg");
        Button en = createFlagButton("/flags/en.png");

        ru.setOnAction(e -> onLanguageChange.accept(Language.RU));
        ky.setOnAction(e -> onLanguageChange.accept(Language.KY));
        en.setOnAction(e -> onLanguageChange.accept(Language.EN));

        HBox box = new HBox(8, ru, ky, en);
        box.setAlignment(Pos.CENTER_RIGHT);
        box.setPadding(new Insets(10, 16, 0, 0)); // top-right
        return box;
    }

    private static Button createFlagButton(String resourcePath) {
        Button b = new Button();
        b.setText(null); // без текста

        InputStream is = LanguageBar.class.getResourceAsStream(resourcePath);
        if (is != null) {
            Image img = new Image(is);
            ImageView iv = new ImageView(img);
            iv.setFitHeight(24);        // высота флага
            iv.setPreserveRatio(true);  // сохраняем пропорции
            b.setGraphic(iv);
        }

        // стили: прозрачный фон, без лишних отступов
        b.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-padding: 2 4 2 4;"
        );

        return b;
    }
}
