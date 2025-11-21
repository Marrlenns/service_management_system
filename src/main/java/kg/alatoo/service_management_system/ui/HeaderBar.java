package kg.alatoo.service_management_system.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import kg.alatoo.service_management_system.i18n.Language;

import java.io.InputStream;
import java.util.function.Consumer;

public final class HeaderBar {

    private HeaderBar() {}

    /**
     * Верхняя панель:
     * [ ЛОГОТИП ] .............................. [ флаги языков ]
     */
    public static Node create(Consumer<Language> onLanguageChange) {
        // ----- ЛОГОТИП (слева) -----
        HBox logoBox = new HBox();
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setPadding(new Insets(10, 16, 0, 16)); // top, right, bottom, left

        try {
            // Путь к логотипу в resources: /logo.png
            InputStream is = HeaderBar.class.getResourceAsStream("/logo.png");
            if (is != null) {
                Image img = new Image(is);
                ImageView logoView = new ImageView(img);
                logoView.setFitHeight(80);       // высота логотипа
                logoView.setPreserveRatio(true); // пропорции
                logoBox.getChildren().add(logoView);
            } else {
                // если файл не найден — просто логируем в консоль, но не падаем
                System.err.println("[HeaderBar] logo.png not found on classpath (/logo.png)");
            }
        } catch (Exception ex) {
            // тоже не даём приложению упасть
            System.err.println("[HeaderBar] Failed to load logo: " + ex.getMessage());
        }

        // ----- Панель языков (справа) -----
        Node langBar = LanguageBar.create(onLanguageChange);

        // ----- Общий header -----
        BorderPane header = new BorderPane();
        header.setLeft(logoBox);
        header.setRight(langBar);
        header.setStyle(UiStyles.DARK_BG);

        return header;
    }
}
