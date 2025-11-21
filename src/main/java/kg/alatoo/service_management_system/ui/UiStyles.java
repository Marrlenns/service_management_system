package kg.alatoo.service_management_system.ui;

public final class UiStyles {

    private UiStyles() {}

    /** Общий фон приложения: тёмный градиент */
    public static final String DARK_BG =
            "-fx-background-color: linear-gradient(to bottom, #050816 0%, #0B1E39 45%, #02030A 100%);";

    /** Фон верхней панели (хедера) с логотипом и флагами */
    public static final String HEADER_BG =
            "-fx-background-color: rgba(6, 12, 24, 0.96);" +
                    "-fx-border-color: rgba(255,255,255,0.06);" +
                    "-fx-border-width: 0 0 1 0;";

    /** Основная голубая кнопка (главное меню, Enter и т.п.) */
    public static final String PRIMARY_BUTTON =
            "-fx-background-color: linear-gradient(to right, #1976D2, #42A5F5);" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 18px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 999;" +
                    "-fx-padding: 12 28 12 28;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.55), 16, 0.3, 0, 6);";

    /** Серая кнопка (Back, Done) */
    public static final String SECONDARY_BUTTON =
            "-fx-background-color: rgba(69, 90, 100, 0.95);" +
                    "-fx-text-fill: #ECEFF1;" +
                    "-fx-font-size: 16px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 999;" +
                    "-fx-padding: 10 24 10 24;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.45), 12, 0.25, 0, 4);";

    /** Кнопки категорий */
    public static final String CATEGORY_BUTTON =
            "-fx-background-color: linear-gradient(to right, #1E88E5, #26C6DA);" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 18px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 18;" +
                    "-fx-padding: 12 24 12 24;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.45), 16, 0.35, 0, 6);";

    /** Карточка по центру (логин, категории, талончик и т.п.) */
    public static final String CARD =
            "-fx-background-color: rgba(10, 25, 47, 0.94);" +
                    "-fx-background-radius: 24;" +
                    "-fx-border-color: rgba(255,255,255,0.08);" +
                    "-fx-border-radius: 24;" +
                    "-fx-border-width: 1;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 32, 0.45, 0, 18);";
}
