package kg.alatoo.service_management_system.utils;

import java.time.LocalDate;
import java.util.prefs.Preferences;

public class CategoryDailyCounter {

    private final Preferences prefs = Preferences.userRoot().node("kg.alatoo.sms.categories");
    private final String[] letters;

    public CategoryDailyCounter(String[] letters) {
        if (letters == null || letters.length == 0) throw new IllegalArgumentException("letters required");
        this.letters = letters.clone();
    }

    public String nextCode(int categoryIndex) {
        if (categoryIndex < 1 || categoryIndex > letters.length) throw new IllegalArgumentException("bad category index");
        String today = LocalDate.now().toString();
        String key = today + ".cat" + categoryIndex;

        int count = prefs.getInt(key, 0);
        int turn = (count % 2) + 1;
        prefs.putInt(key, count + 1);

        String letter = letters[categoryIndex - 1];
        return letter + turn;
    }
}
