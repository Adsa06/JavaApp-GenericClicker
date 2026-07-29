package io.github.adsa06;

import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import io.github.adsa06.data.local.dao.AchievementsDao;
import io.github.adsa06.data.local.dao.StatsDao;
import io.github.adsa06.data.local.dao.UpgradesDao;
import io.github.adsa06.data.local.database.DatabaseConnection;
import io.github.adsa06.domain.model.AchievementManager;
import io.github.adsa06.domain.model.GameAchievements;
import io.github.adsa06.domain.model.GameEngine;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.presentation.ui.screens.AchievementScreen;
import io.github.adsa06.presentation.ui.screens.GameScreen;
import io.github.adsa06.presentation.ui.screens.SettingsScreen;
import io.github.adsa06.presentation.ui.theme.ThemeManager;
import io.github.adsa06.presentation.ui.theme.ThemeManager.ThemeType;
import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.AchievementViewModel;
import io.github.adsa06.presentation.viewmodel.GameViewModel;
import io.github.adsa06.utilities.Utilities;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // 1. Instanciamos la configuración indicando la ruta del archivo SQLite
        DatabaseConnection dbConfig = new DatabaseConnection("app.db");
        
        // 2. Creación de tablas
        dbConfig.initDatabase();

        // 3. Inyectamos la configuración al DAO
        AchievementsDao achievementsDao = new AchievementsDao(dbConfig);
        StatsDao statsDao = new StatsDao(dbConfig);
        UpgradesDao upgradesDao = new UpgradesDao(dbConfig);


        TranslationManager translationManager = new TranslationManager("es");
        ThemeManager themeManager = new ThemeManager();

        SettingsScreen settingsScreen = new SettingsScreen(translationManager);

        GameState gameState = new GameState();
        GameEngine gameEngine = new GameEngine(gameState);
        gameEngine.start();
        GameViewModel gameViewModel = new GameViewModel(gameState);
        GameScreen gameScreen = new GameScreen(gameViewModel, translationManager);

        GameAchievements gameAchievements = new GameAchievements();
        AchievementManager achievementManager = new AchievementManager(gameState, gameAchievements);
        AchievementViewModel achievementViewModel = new AchievementViewModel(achievementManager);
        AchievementScreen achievementScreen = new AchievementScreen(achievementViewModel, translationManager);

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
            Panel gamePanel = gameScreen.getPanel();
            Panel achievementPanel = achievementScreen.getPanel();
            Panel settingsPanel = settingsScreen.getPanel();

            Runnable refreshUi = () -> {
                rootPanel.removeAllComponents();

                // --- Sección superior (siempre visible) ---
                Border topWithBorder = settingsPanel
                        .withBorder(Borders.singleLine(translationManager.getString("settingsTitle")));
                rootPanel.addComponent(topWithBorder, BorderLayout.Location.TOP);

                // --- Sección media: 3 paneles en horizontal ---

                Panel middlePanel = new Panel(new LinearLayout(Direction.HORIZONTAL));

                Panel middleLeft = new Panel(new LinearLayout(Direction.VERTICAL));
                middleLeft.addComponent(new Label(translationManager.getString("leftPanel")));

                Panel middleRight = new Panel(new LinearLayout(Direction.VERTICAL));
                middleRight.addComponent(new Label(translationManager.getString("rightPanel")));

                middlePanel.addComponent(
                        middleLeft.withBorder(Borders.singleLine(translationManager.getString("leftPanel"))));
                middlePanel.addComponent(
                        gamePanel.withBorder(Borders.singleLine(translationManager.getString("gameTitle"))));
                middlePanel.addComponent(
                        middleRight.withBorder(Borders.singleLine(translationManager.getString("rightPanel"))));

                rootPanel.addComponent(middlePanel, BorderLayout.Location.CENTER);

                // --- Sección inferior ---
                Border bottomWithBorder = achievementPanel
                        .withBorder(Borders.singleLine(translationManager.getString("achievementsTitle")));
                rootPanel.addComponent(bottomWithBorder, BorderLayout.Location.BOTTOM);

                window.setComponent(rootPanel);
            };

            refreshUi.run();
            translationManager.addListener(refreshUi);
            gui.addWindowAndWait(window);

            // 6. Detener la pantalla al terminar
            screen.stopScreen();

        } catch (IOException e) {
            Utilities.log("Main", e.getMessage());
        }
    }
}