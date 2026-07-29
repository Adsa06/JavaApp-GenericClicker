package io.github.adsa06.presentation.ui.screens;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

import io.github.adsa06.presentation.ui.translations.TranslationManager;

public class SettingsScreen {
    private Panel panel;
    private TranslationManager translationManager;

    public SettingsScreen(TranslationManager translationManager) {
        this.translationManager = translationManager;

        initialize();
    }

    public Panel getPanel() {
        return panel;
    }

    private void initialize() {
        panel = new Panel();

        Panel root = new Panel(new LinearLayout(Direction.HORIZONTAL));


        Button toSpanish = new Button("Español", () -> translationManager.setLocale("es"));
        Button toEnglish = new Button("English", () -> translationManager.setLocale("en"));
        
        
        root.addComponent(toSpanish);
        root.addComponent(toEnglish);

        panel.addComponent(root);
    }


    
}
