package io.github.adsa06;

import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button.Listener;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import io.github.adsa06.data.local.dao.AchievementsDao;
import io.github.adsa06.data.local.dao.BuildingsDao;
import io.github.adsa06.data.local.dao.SettingsDao;
import io.github.adsa06.data.local.dao.StatsDao;
import io.github.adsa06.data.local.dao.UpgradesDao;
import io.github.adsa06.data.local.database.DatabaseConnection;
import io.github.adsa06.data.repository.GameRepository;
import io.github.adsa06.data.repository.SettingsRepository;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.domain.service.AchievementManager;
import io.github.adsa06.domain.service.BuildingsManager;
import io.github.adsa06.domain.service.GameEngine;
import io.github.adsa06.domain.service.JsonService;
import io.github.adsa06.presentation.ui.screens.AchievementScreen;
import io.github.adsa06.presentation.ui.screens.BuildingScreen;
import io.github.adsa06.presentation.ui.screens.GameScreen;
import io.github.adsa06.presentation.ui.screens.SettingsScreen;
import io.github.adsa06.presentation.ui.screens.StatsScreen;
import io.github.adsa06.presentation.ui.screens.UpgradesScreen;
import io.github.adsa06.presentation.ui.theme.ThemeManager;
import io.github.adsa06.presentation.ui.theme.ThemeManager.ThemeType;
import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.AchievementViewModel;
import io.github.adsa06.presentation.viewmodel.BuildingViewModel;
import io.github.adsa06.presentation.viewmodel.GameViewModel;
import io.github.adsa06.presentation.viewmodel.SettingsViewModel;
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
        BuildingsDao buildingsDao = new BuildingsDao(dbConfig);
        UpgradesDao upgradesDao = new UpgradesDao(dbConfig);
        SettingsDao settingsDao = new SettingsDao(dbConfig);

        GameRepository gameRepository = new GameRepository(achievementsDao, statsDao, buildingsDao, upgradesDao);
        SettingsRepository settingsRepository = new SettingsRepository(settingsDao);

        JsonService jsonService = new JsonService();

        TranslationManager translationManager = new TranslationManager(settingsRepository.findSettings());
        ThemeManager themeManager = new ThemeManager();

        GameState gameState = gameRepository.findStats();
        GameEngine gameEngine = new GameEngine(gameState);
        gameEngine.start();
        GameViewModel gameViewModel = new GameViewModel(gameState);
        GameScreen gameScreen = new GameScreen(gameViewModel, translationManager);

        AchievementManager achievementManager = new AchievementManager(jsonService.readAchievements(), gameState,
                gameRepository.findAllAchievements());
        AchievementViewModel achievementViewModel = new AchievementViewModel(achievementManager);
        AchievementScreen achievementScreen = new AchievementScreen(achievementViewModel, translationManager);

        StatsScreen statsScreen = new StatsScreen(translationManager);

        BuildingsManager buildingsManager = new BuildingsManager(jsonService.readBuildings(), gameState,
                gameRepository.findAllBuildings());
        BuildingViewModel buildingViewModel = new BuildingViewModel(buildingsManager);
        BuildingScreen buildingScreen = new BuildingScreen(buildingViewModel, translationManager);

        UpgradesScreen upgradesScreen = new UpgradesScreen(translationManager);

        //AtomicBoolean isInBuildings = new AtomicBoolean(true);
        boolean[] isInBuildings = {false};

        SettingsViewModel settingsViewModel = new SettingsViewModel(translationManager, settingsRepository,
                gameRepository, gameState, achievementManager, buildingsManager);
        SettingsScreen settingsScreen = new SettingsScreen(translationManager, settingsViewModel);

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
                if(hadFocus) switchButton.takeFocus();
            };

            switchButton.addListener(new Listener() {

                @Override
                public void onTriggered(Button button) {
                    isInBuildings[0] = !isInBuildings[0];
                    rebuildRightPanel.run();
                }

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
            gui.addWindowAndWait(window);

            // 6. Detener la pantalla al terminar
            screen.stopScreen();

        } catch (IOException e) {
            Utilities.log("Main", e.getMessage());
        }
    }
}