package io.github.adsa06.presentation.ui.screens;

import java.text.MessageFormat;

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

    public GameScreen(
            GameViewModel viewModel
    ) {
        this.viewModel = viewModel;

        initialize();
    }

    public AbstractWindow getWindow() {
        return window;
    }



    private void initialize() {
        window = new BasicWindow("Game Screen");

        Panel root = new Panel(new LinearLayout(Direction.VERTICAL));

        Label counerLabel = new Label(
            MessageFormat.format(
                TranslationManager.getBundle().getString("counter"),
                viewModel.getNumber()
            )
        );

        root.addComponent(counerLabel);

        root.addComponent(
            new Button(
                "Click me",
                () -> {
                    viewModel.increaseNumberBy(1);
                    counerLabel.setText(
                        MessageFormat.format(
                            TranslationManager.getBundle().getString("counter"),
                            viewModel.getNumber()
                        )
                    );
                }
            )
        );

        window.setComponent(root);
    }
}
