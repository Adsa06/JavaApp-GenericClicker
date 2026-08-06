package io.github.adsa06;

import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import io.github.adsa06.data.repository.GameRepository;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.domain.service.GameEngine;
import io.github.adsa06.presentation.ui.screens.AchievementScreen;
import io.github.adsa06.presentation.ui.screens.BuildingScreen;
import io.github.adsa06.presentation.ui.screens.GameScreen;
import io.github.adsa06.presentation.ui.screens.SettingsScreen;
import io.github.adsa06.presentation.ui.screens.StatsScreen;
import io.github.adsa06.presentation.ui.screens.UpgradesScreen;
import io.github.adsa06.presentation.ui.theme.ThemeManager;
import io.github.adsa06.presentation.ui.theme.ThemeManager.ThemeType;
import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.utilities.Utilities;
import io.github.adsa06.utilities.di.Injector;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Injector injector = new Injector();

        GameRepository gameRepository = injector.getInstance(GameRepository.class);
        GameState gameState = gameRepository.findStats();
        injector.registerInstance(GameState.class, gameState);

        GameEngine gameEngine = injector.getInstance(GameEngine.class);

        ThemeManager themeManager = injector.getInstance(ThemeManager.class);

        TranslationManager translationManager = injector.getInstance(TranslationManager.class);

        AchievementScreen achievementScreen = injector.getInstance(AchievementScreen.class);
        GameScreen gameScreen = injector.getInstance(GameScreen.class);
        StatsScreen statsScreen = injector.getInstance(StatsScreen.class);
        BuildingScreen buildingScreen = injector.getInstance(BuildingScreen.class);
        UpgradesScreen upgradesScreen = injector.getInstance(UpgradesScreen.class);
        SettingsScreen settingsScreen = injector.getInstance(SettingsScreen.class);

        boolean[] isInBuildings = { true };
        // 1. Inicializar la fábrica de terminales por defecto
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();

        try (Terminal terminal = terminalFactory.createTerminal()) {
            // 2. Configurar la pantalla (Screen) para manejar el búfer de texto
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

            // Crear un tema minimalista y limpio
            // Definimos colores sobrios: Texto blanco, fondo negro, y selección sutil
            Theme theme = themeManager.getTheme(ThemeType.MINIMALIST);

            // 3. Crear el sistema de gestión de ventanas (MultiWindowTextGUI)
            WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);
            Window window = new BasicWindow("The Clicker Game");
            Panel rootPanel = new Panel(new BorderLayout());
            gui.setTheme(theme);

            // 4. Crear una ventana básica con un panel y contenido
            Panel settingsPanel = settingsScreen.getPanel();
            Panel gamePanel = gameScreen.getPanel();
            Panel statsPanel = statsScreen.getPanel();
            Panel upgradesPanel = upgradesScreen.getPanel();
            Panel buildingPanel = buildingScreen.getPanel();
            Panel achievementPanel = achievementScreen.getPanel();

            Panel middleRightPanel = new Panel(new LinearLayout(Direction.VERTICAL));
            Button switchButton = new Button(translationManager.getString("toUpgrades"));

            Runnable rebuildRightPanel = () -> {
                boolean hadFocus = switchButton.isFocused();
                middleRightPanel.removeAllComponents();
                middleRightPanel.addComponent(switchButton);

                Component rightContent = isInBuildings[0]
                        ? buildingPanel.withBorder(Borders.singleLine(translationManager.getString("buildingsTitle")))
                        : upgradesPanel.withBorder(Borders.singleLine(translationManager.getString("upgradesTitle")));

                middleRightPanel.addComponent(rightContent);
                switchButton.setLabel(translationManager.getString(isInBuildings[0] ? "toUpgrades" : "toBuildings"));
                if (hadFocus)
                    switchButton.takeFocus();
            };

            switchButton.addListener((button) -> {
                isInBuildings[0] = !isInBuildings[0];
                rebuildRightPanel.run();
            });

            Runnable refreshUi = () -> {
                rebuildRightPanel.run();

                rootPanel.removeAllComponents();

                // --- Sección superior (siempre visible) ---
                Border topWithBorder = settingsPanel
                        .withBorder(Borders.singleLine(translationManager.getString("settingsTitle")));
                rootPanel.addComponent(topWithBorder, BorderLayout.Location.TOP);

                // --- Sección media: 3 paneles en horizontal ---

                Panel middlePanel = new Panel(new LinearLayout(Direction.HORIZONTAL));

                middlePanel.addComponent(
                        statsPanel.withBorder(Borders.singleLine(translationManager.getString("statsTitle"))));
                middlePanel.addComponent(
                        gamePanel.withBorder(Borders.singleLine(translationManager.getString("gameTitle"))));

                middlePanel.addComponent(middleRightPanel);
                rootPanel.addComponent(middlePanel, BorderLayout.Location.CENTER);

                // --- Sección inferior ---
                Border bottomWithBorder = achievementPanel
                        .withBorder(Borders.singleLine(translationManager.getString("achievementsTitle")));
                rootPanel.addComponent(bottomWithBorder, BorderLayout.Location.BOTTOM);

                window.setComponent(rootPanel);
            };

            refreshUi.run();
            translationManager.addListener(refreshUi);

            gameEngine.start();
            gui.addWindowAndWait(window);

            // 6. Detener la pantalla al terminar
            screen.stopScreen();

        } catch (IOException e) {
            Utilities.log("Main", e.getMessage());
        }
    }
}