package io.github.adsa06.presentation.ui.translations;

import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationManager {
    private static Locale locale = Locale.of("es");
    private static ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", locale);

    public static ResourceBundle getBundle() {
        return bundle;
    }
}
