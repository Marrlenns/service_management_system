package kg.alatoo.service_management_system.keyboards;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

public class TextKeyboard extends GridPane {

    private final String keyStyle =
            "-fx-background-color: #1976D2;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 16px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 8px;" +
                    "-fx-padding: 8px 0;";

    private final String utilStyle =
            "-fx-background-color: #455A64;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 16px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 8px;" +
                    "-fx-padding: 8px 0;";

    private double keyWidth = 56;

    public TextKeyboard(Consumer<String> onKey, Runnable onBackspace, Runnable onClear) {
        setHgap(6);
        setVgap(6);
        setAlignment(Pos.CENTER);

        // Цифры
        String[] row0 = {"1","2","3","4","5","6","7","8","9","0"};
        // QWERTY
        String[] row1 = {"q","w","e","r","t","y","u","i","o","p"};
        String[] row2 = {"a","s","d","f","g","h","j","k","l"};
        String[] row3 = {"z","x","c","v","b","n","m"};

        addRowOfKeys(row0, 0, onKey);
        addRowOfKeys(row1, 1, onKey);
        addRowOfKeys(row2, 2, onKey);
        addRowOfKeys(row3, 3, onKey);

        // Последняя строка: C, @, ., _, ←
        Button clear      = makeUtilButton("C", onClear);
        Button at         = makeKeyButton("@", onKey);
        Button dot        = makeKeyButton(".", onKey);
        Button underscore = makeKeyButton("_", onKey);
        Button back       = makeUtilButton("←", onBackspace);

        add(clear,      0, 4);
        add(at,         1, 4);
        add(dot,        2, 4);
        add(underscore, 3, 4);
        add(back,       4, 4);
    }

    private void addRowOfKeys(String[] keys, int rowIndex, Consumer<String> onKey) {
        for (int c = 0; c < keys.length; c++) {
            String txt = keys[c];
            Button b = makeKeyButton(txt, onKey);
            add(b, c, rowIndex);
        }
    }

    public static TextKeyboard forTextField(TextField field, int maxLen) {
        Consumer<String> onKey = s -> {
            String t = field.getText();
            if (t.length() < maxLen) {
                field.appendText(s);
            }
        };
        Runnable onBackspace = () -> {
            String t = field.getText();
            if (!t.isEmpty()) {
                field.setText(t.substring(0, t.length() - 1));
            }
        };
        Runnable onClear = field::clear;

        return new TextKeyboard(onKey, onBackspace, onClear);
    }

    public void setKeyWidth(double w) {
        this.keyWidth = w;
        getChildren().stream()
                .filter(n -> n instanceof Button)
                .map(n -> (Button) n)
                .forEach(b -> {
                    b.setPrefWidth(w);
                    b.setMinWidth(w);
                    b.setMaxWidth(w);
                });
    }

    private Button makeKeyButton(String txt, Consumer<String> onKey) {
        Button b = new Button(txt);
        b.setStyle(keyStyle);
        b.setPrefWidth(keyWidth);
        b.setMinWidth(keyWidth);
        b.setMaxWidth(keyWidth);
        b.setOnAction(e -> onKey.accept(txt));
        return b;
    }

    private Button makeUtilButton(String txt, Runnable action) {
        Button b = new Button(txt);
        b.setStyle(utilStyle);
        b.setPrefWidth(keyWidth);
        b.setMinWidth(keyWidth);
        b.setMaxWidth(keyWidth);
        b.setOnAction(e -> action.run());
        return b;
    }
}
