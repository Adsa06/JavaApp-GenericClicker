package io.github.adsa06.presentation.ui.screens;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.gui2.AbstractWindow;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

import io.github.adsa06.domain.model.Achievement;
import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.AchievementViewModel;

public class AchievementScreen {
    private AbstractWindow window;
    private AchievementViewModel viewModel;
    private TranslationManager translationManager;

    public AchievementScreen(
            AchievementViewModel viewModel,
            TranslationManager translationManager
    ) {
        this.viewModel = viewModel;
        this.translationManager = translationManager;

        initialize();
    }

    public AbstractWindow getWindow() {
        return window;
    }

    private void initialize() {
        window = new BasicWindow("Achievement Screen");

        Panel root = new Panel(new LinearLayout(Direction.HORIZONTAL));

        List<Panel> achievementPanels = new ArrayList<>();
        List<Label> statusLabels = new ArrayList<>();
        List<Achievement> achievements = new ArrayList<>(viewModel.getAchievements());

        for (Achievement achievement : achievements) {
            Panel achievementPanel = new Panel(new LinearLayout(Direction.VERTICAL));

            Label title = new Label(translationManager.geString(achievement.getTitleId()));
            Label descripcion = new Label(translationManager.geString(achievement.getDescripcionId()));

            String isCompleteString = achievement.isFinished() ? "complete" : "incomplete";
            Label isComplete = new Label(translationManager.geString(isCompleteString));

            achievementPanel.addComponent(title);
            achievementPanel.addComponent(descripcion);
            achievementPanel.addComponent(isComplete);
            achievementPanels.add(achievementPanel);
            statusLabels.add(isComplete);
        }

        Runnable updatePanels = new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < achievements.size(); i++) {
                    Achievement achievement = achievements.get(i);
                    Label statusLabel = statusLabels.get(i);
                    statusLabel.setText(translationManager.geString(achievement.isFinished() ? "complete" : "incomplete"));
                }
            }

        };

        viewModel.addListener(updatePanels);

        root.addComponent(new Button("null"));
        root.addComponent(new Button("null"));
        root.addComponent(new Button("null"));

        achievementPanels.forEach(root::addComponent);
        window.setComponent(root);
    }
}
