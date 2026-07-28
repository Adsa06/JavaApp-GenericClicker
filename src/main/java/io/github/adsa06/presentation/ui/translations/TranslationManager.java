package io.github.adsa06.presentation.ui.translations;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationManager {
    private Locale locale;
    private ResourceBundle bundle;

    public TranslationManager(
        String locale
    ) {
        this.locale = Locale.of(locale);
        this.bundle = ResourceBundle.getBundle("i18n.messages", this.locale);
    }

    public String geString(
        String id,
        Object... arguments
    ) {
        String message = MessageFormat.format(
            bundle.getString(id),
            arguments
        );
        return message;
    }

}
