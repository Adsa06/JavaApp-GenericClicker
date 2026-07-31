package io.github.adsa06.presentation.ui.screens;

import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.BuildingViewModel;

public class BuildingScreen {
    private Panel panel;
    private BuildingViewModel buildingViewModel;
    private TranslationManager translationManager;

    public BuildingScreen(
        BuildingViewModel buildingViewModel,
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

        Label counterLabel = new Label(translationManager.getString("buildingsTitle"));

        translationManager.addListener(() -> counterLabel.setText(translationManager.getString("buildingsTitle")));

        root.addComponent(counterLabel);
        
        panel.addComponent(root);

    }
}