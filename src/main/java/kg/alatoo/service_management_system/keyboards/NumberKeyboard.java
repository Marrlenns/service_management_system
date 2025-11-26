package kg.alatoo.service_management_system.keyboards;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

public class NumberKeyboard extends GridPane {

    private final String digitStyle =
            "-fx-background-color: #1976D2;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 18px;" +          // было 16px → стало чуть больше
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 8px;" +
                    "-fx-padding: 12px 0;";           // было 8px → стало 12px

    private final String utilStyle =
            "-fx-background-color: #455A64;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 18px;" +          // тоже увеличили
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 8px;" +
                    "-fx-padding: 12px 0;";

    private double keyWidth  = 80;            // было 64 → сделали шире
    private double keyHeight = 56;            // добавили высоту

    public NumberKeyboard(Consumer<String> onDigit, Runnable onBackspace, Runnable onClear) {
        setHgap(10);
        setVgap(10);
        setAlignment(Pos.CENTER);

        int n = 1;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                String txt = String.valueOf(n++);
                Button b = makeDigitButton(txt, onDigit);
                add(b, c, r);
            }
        }

        Button clear = makeUtilButton("C", onClear);
        Button zero  = makeDigitButton("0", onDigit);
        Button back  = makeUtilButton("←", onBackspace);

        add(clear, 0, 3);
        add(zero,  1, 3);
        add(back,  2, 3);
    }

    public static NumberKeyboard forTextField(javafx.scene.control.TextField field, int maxLen) {
        Consumer<String> onDigit = d -> {
            String t = field.getText();
            if (t.length() < maxLen) {
                field.appendText(d);
            }
        };
        Runnable onBackspace = () -> {
            String t = field.getText();
            if (!t.isEmpty()) field.setText(t.substring(0, t.length() - 1));
        };
        Runnable onClear = field::clear;

        return new NumberKeyboard(onDigit, onBackspace, onClear);
    }

    /**
     * Оставляем твой старый метод, но теперь он ещё и высоту обновляет.
     */
    public void setKeyWidth(double w) {
        this.keyWidth = w;
        getChildren().stream()
                .filter(n -> n instanceof Button)
                .map(n -> (Button) n)
                .forEach(b -> {
                    b.setPrefWidth(w);
                    b.setMinWidth(w);
                    b.setMaxWidth(w);
                    b.setPrefHeight(keyHeight);
                    b.setMinHeight(keyHeight);
                    b.setMaxHeight(keyHeight);
                });
    }

    /**
     * Если захочешь ещё поиграться с размерами — можно вызывать setKeySize(w, h).
     */
    public void setKeySize(double w, double h) {
        this.keyWidth = w;
        this.keyHeight = h;
        getChildren().stream()
                .filter(n -> n instanceof Button)
                .map(n -> (Button) n)
                .forEach(b -> {
                    b.setPrefWidth(w);
                    b.setMinWidth(w);
                    b.setMaxWidth(w);
                    b.setPrefHeight(h);
                    b.setMinHeight(h);
                    b.setMaxHeight(h);
                });
    }

    private Button makeDigitButton(String txt, Consumer<String> onDigit) {
        Button b = new Button(txt);
        b.setStyle(digitStyle);
        b.setPrefWidth(keyWidth);
        b.setMinWidth(keyWidth);
        b.setMaxWidth(keyWidth);
        b.setPrefHeight(keyHeight);
        b.setMinHeight(keyHeight);
        b.setMaxHeight(keyHeight);
        b.setOnAction(e -> onDigit.accept(txt));
        return b;
    }

    private Button makeUtilButton(String txt, Runnable action) {
        Button b = new Button(txt);
        b.setStyle(utilStyle);
        b.setPrefWidth(keyWidth);
        b.setMinWidth(keyWidth);
        b.setMaxWidth(keyWidth);
        b.setPrefHeight(keyHeight);
        b.setMinHeight(keyHeight);
        b.setMaxHeight(keyHeight);
        b.setOnAction(e -> action.run());
        return b;
    }
}
