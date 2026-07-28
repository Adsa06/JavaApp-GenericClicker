package io.github.adsa06.presentation.ui.screens;

import com.googlecode.lanterna.gui2.AbstractWindow;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.GameViewModel;

public class GameScreen {
    private AbstractWindow window;
    private GameViewModel viewModel;
    private TranslationManager translationManager;

    public GameScreen(
            GameViewModel viewModel,
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
        window = new BasicWindow("Game Screen");

        Panel root = new Panel(new LinearLayout(Direction.VERTICAL));

        Label counterLabel = new Label(translationManager.geString("counter", viewModel.getCounter()));

        Runnable updateLabel = new Runnable() {

            @Override
            public void run() {
                counterLabel.setText(translationManager.geString("counter", viewModel.getCounter()));
            }

        };

        viewModel.addListener(updateLabel);


        root.addComponent(counterLabel);

        root.addComponent(
            new Button(
                translationManager.geString("clickMe"),
                () -> viewModel.onClickButtonPressed()
            )
        );

        root.addComponent(
            new Button(
                translationManager.geString("clickMe"),
                () -> viewModel.onClickButtonPressed()
            )
        );

        window.setComponent(root);
    }
}
