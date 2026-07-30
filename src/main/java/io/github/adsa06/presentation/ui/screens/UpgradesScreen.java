package io.github.adsa06.presentation.ui.screens;

import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

import io.github.adsa06.presentation.ui.translations.TranslationManager;

public class UpgradesScreen {
    private Panel panel;
    private TranslationManager translationManager;

    public UpgradesScreen(
            TranslationManager translationManager
    ) {
        this.translationManager = translationManager;

        initialize();
    }

    public Panel getPanel() {
        return panel;
    }

    private void initialize() {
        panel = new Panel();

        Panel root = new Panel(new LinearLayout(Direction.VERTICAL));

        Label counterLabel = new Label(translationManager.getString("upgradesTitle"));

        translationManager.addListener(() -> counterLabel.setText(translationManager.getString("upgradesTitle")));

        root.addComponent(counterLabel);
        
        panel.addComponent(root);

    }
}