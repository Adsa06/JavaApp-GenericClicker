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

        Label counerLabel = new Label(translationManager.geString("counter", viewModel.getNumber()));

        root.addComponent(counerLabel);

        root.addComponent(
            new Button(
                "Click me",
                () -> {
                    viewModel.increaseNumberBy(1);
                    counerLabel.setText(
                        translationManager.geString("counter", viewModel.getNumber())
                    );
                }
            )
        );

        window.setComponent(root);
    }
}
