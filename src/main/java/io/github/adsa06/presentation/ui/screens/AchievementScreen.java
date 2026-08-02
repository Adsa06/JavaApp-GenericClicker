package io.github.adsa06.presentation.ui.screens;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

import io.github.adsa06.domain.model.Achievement;
import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.AchievementViewModel;

public class AchievementScreen {
    private Panel panel;
    private AchievementViewModel viewModel;
    private TranslationManager translationManager;
    private int index = 0; 

    public AchievementScreen(
            AchievementViewModel viewModel,
            TranslationManager translationManager
    ) {
        this.viewModel = viewModel;
        this.translationManager = translationManager;

        initialize();
    }

    public Panel getPanel() {
        return panel;
    }

    private void initialize() {
        panel = new Panel();

        Panel root = new Panel(new LinearLayout(Direction.HORIZONTAL));

        Panel achievementsPanel = new Panel(new GridLayout(2));
        List<Panel> achievementPanels = new ArrayList<>();
        List<Label> statusLabels = new ArrayList<>();
        List<Achievement> achievements = new ArrayList<>(viewModel.getAchievements());

        for (Achievement achievement : achievements) {
            Panel achievementPanel = new Panel(new LinearLayout(Direction.VERTICAL));

            Label title = new Label(translationManager.getString(achievement.getTitleId()));
            Label descripcion = new Label(translationManager.getString(achievement.getDescripcionId()));

            String isCompleteString = achievement.isFinished() ? "complete" : "incomplete";
            Label isComplete = new Label(translationManager.getString(isCompleteString));

            achievementPanel.addComponent(title);
            achievementPanel.addComponent(descripcion);
            achievementPanel.addComponent(isComplete);
            achievementPanels.add(achievementPanel);
            statusLabels.add(isComplete);

            translationManager.addListener(() -> {
                title.setText(translationManager.getString(achievement.getTitleId()));
                descripcion.setText(translationManager.getString(achievement.getDescripcionId()));

                isComplete.setText(translationManager.getString(achievement.isFinished() ? "complete" : "incomplete"));
            });
        }

        Button toLeft = new Button("<", () -> {
            achievementsPanel.removeComponent(achievementPanels.get(index));
            achievementsPanel.removeComponent(achievementPanels.get((index + 1) % achievementPanels.size()));
            index = (index - 1 + achievementPanels.size()) % achievementPanels.size();
            achievementsPanel.addComponent(achievementPanels.get(index));
            achievementsPanel.addComponent(achievementPanels.get((index + 1) % achievementPanels.size()));
        });

        Button toRight = new Button(">", () -> {
            achievementsPanel.removeComponent(achievementPanels.get(index));
            achievementsPanel.removeComponent(achievementPanels.get((index + 1) % achievementPanels.size()));
            index = (index + 1) % achievementPanels.size();
            achievementsPanel.addComponent(achievementPanels.get(index));
            achievementsPanel.addComponent(achievementPanels.get((index + 1) % achievementPanels.size()));
        });

        toLeft.setRenderer(new Button.FlatButtonRenderer());
        toRight.setRenderer(new Button.FlatButtonRenderer());

        Runnable updatePanels = new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < achievements.size(); i++) {
                    Achievement achievement = achievements.get(i);
                    Label statusLabel = statusLabels.get(i);
                    statusLabel.setText(translationManager.getString(achievement.isFinished() ? "complete" : "incomplete"));
                }
            }

        };

        viewModel.addListener(updatePanels);

        root.addComponent(toLeft);

        achievementsPanel.addComponent(achievementPanels.get(index));
        achievementsPanel.addComponent(achievementPanels.get((index + 1) % achievementPanels.size()));

        root.addComponent(achievementsPanel);
        root.addComponent(toRight);
        panel.addComponent(root);
    }
}
