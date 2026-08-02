package io.github.adsa06.presentation.ui.translations;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationManager {
    private Locale locale;
    private ResourceBundle bundle;
    private List<Runnable> listeners = new ArrayList<>();

    public TranslationManager(Locale locale) {
        this.locale = locale;
        this.bundle = ResourceBundle.getBundle("i18n.messages", this.locale);
    }

    public String getString(String id, Object... arguments) {
        String message = MessageFormat.format(
                bundle.getString(id),
                arguments);
        return message;
    }

    public void setLocale(String locale) {
        if(!this.locale.equals(Locale.of(locale))) {
            this.locale = Locale.of(locale);
            this.bundle = ResourceBundle.getBundle("i18n.messages", this.locale);
            updateTexts();
        }
    }

    public void updateTexts() {
        listeners.forEach(Runnable::run);
    }

    public void addListener(Runnable callback) {
        listeners.add(callback);
    }

    public void removeListener(Runnable callback) {
        listeners.remove(callback);
    }

    public Locale getLocale() {
        return locale;
    }
}
