package io.github.adsa06.presentation.ui.screens;

import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.StatsViewModel;
import io.github.adsa06.utilities.Utilities;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class StatsScreen {
    private Panel panel;
    private final StatsViewModel statsViewModel;
    private final TranslationManager translationManager;

    public StatsScreen(
            StatsViewModel statsViewModel,
            TranslationManager translationManager) {
        this.statsViewModel = statsViewModel;
        this.translationManager = translationManager;

        initialize();
    }

    public Panel getPanel() {
        return panel;
    }

    private void initialize() {
        panel = new Panel();

        Panel root = new Panel(new LinearLayout(Direction.VERTICAL));

        Label counterLabel = new Label(translationManager.getString("statsTitle"));
        Label totalCounter = new Label(
                translationManager.getString("totalCounter", Utilities.formatNum(statsViewModel.getTotalCounter())));
        Label clicksPerSecond = new Label(translationManager.getString("clicksPerSecond",
                Utilities.formatNum(statsViewModel.getClicksPerSecond())));
        Label counterPerClick = new Label(translationManager.getString("counterPerClick",
                Utilities.formatNum(statsViewModel.getCounterPerClick())));
        Label purchasedBuildings = new Label(translationManager.getString("purchasedBuildings",
                Utilities.formatNum(statsViewModel.getPurchasedBuildings())));

        translationManager.addListener(() -> {
            counterLabel.setText(translationManager.getString("statsTitle"));
            totalCounter.setText(translationManager.getString("totalCounter",
                    Utilities.formatNum(statsViewModel.getTotalCounter())));
            clicksPerSecond.setText(translationManager.getString("clicksPerSecond",
                    Utilities.formatNum(statsViewModel.getClicksPerSecond())));
            counterPerClick.setText(translationManager.getString("counterPerClick",
                    Utilities.formatNum(statsViewModel.getCounterPerClick())));
            purchasedBuildings.setText(translationManager.getString("purchasedBuildings",
                    statsViewModel.getPurchasedBuildings()));
        });

        statsViewModel.addListener(() -> {
            totalCounter.setText(translationManager.getString("totalCounter",
                    Utilities.formatNum(statsViewModel.getTotalCounter())));
            clicksPerSecond.setText(translationManager.getString("clicksPerSecond",
                    Utilities.formatNum(statsViewModel.getClicksPerSecond())));
            counterPerClick.setText(translationManager.getString("counterPerClick",
                    Utilities.formatNum(statsViewModel.getCounterPerClick())));
            purchasedBuildings.setText(translationManager.getString("purchasedBuildings",
                    statsViewModel.getPurchasedBuildings()));
        });

        root.addComponent(counterLabel);
        root.addComponent(totalCounter);
        root.addComponent(clicksPerSecond);
        root.addComponent(counterPerClick);
        root.addComponent(purchasedBuildings);

        panel.addComponent(root);

    }
}