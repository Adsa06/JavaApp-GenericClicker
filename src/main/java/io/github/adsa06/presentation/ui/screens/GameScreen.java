package io.github.adsa06.presentation.ui.screens;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.GameViewModel;
import io.github.adsa06.utilities.Utilities;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class GameScreen {
    private Panel panel;
    private final GameViewModel viewModel;
    private final TranslationManager translationManager;

    public GameScreen(
            GameViewModel viewModel,
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

        Panel root = new Panel(new LinearLayout(Direction.VERTICAL));

        Label counterLabel = new Label(translationManager.getString("counter", Utilities.formatNum(viewModel.getCounter())));

        Button counterButton = new Button(translationManager.getString("clickMe"), viewModel::onClickButtonPressed);

        viewModel.addListener(() -> {
            counterLabel.setText(translationManager.getString("counter", Utilities.formatNum(viewModel.getCounter())));
        });

        translationManager.addListener(() -> {
            counterLabel.setText(translationManager.getString("counter", Utilities.formatNum(viewModel.getCounter())));
            counterButton.setLabel(translationManager.getString("clickMe"));
        });
        
        
        root.addComponent(counterLabel);
        
        root.addComponent(counterButton);

        panel.addComponent(root);

    }
}
