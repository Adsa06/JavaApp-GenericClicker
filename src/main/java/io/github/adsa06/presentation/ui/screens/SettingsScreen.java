package io.github.adsa06.presentation.ui.screens;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.SettingsViewModel;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class SettingsScreen {
    private Panel panel;
    private final TranslationManager translationManager;
    private final SettingsViewModel settingsViewModel;

    public SettingsScreen(TranslationManager translationManager, SettingsViewModel settingsViewModel) {
        this.translationManager = translationManager;
        this.settingsViewModel = settingsViewModel;

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
        
        Button save = new Button(translationManager.getString("saveButton"), settingsViewModel::save);

        translationManager.addListener(() -> save.setLabel(translationManager.getString("saveButton")));

        settingsViewModel.setListener(() -> {

            TextGUI textGUI = panel.getTextGUI(); 
        
            if (textGUI instanceof WindowBasedTextGUI windowTextGUI) {
                new MessageDialogBuilder()
                    .setTitle(translationManager.getString("saveSuccesTitle"))
                    .setText(translationManager.getString("saveSuccesText"))
                    .addButton(MessageDialogButton.OK)
                    .build()
                    .showDialog(windowTextGUI);
            }
        });

        root.addComponent(save);
        root.addComponent(toSpanish);
        root.addComponent(toEnglish);

        panel.addComponent(root);
    }


    
}
