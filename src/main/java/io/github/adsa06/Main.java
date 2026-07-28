package io.github.adsa06;

import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import io.github.adsa06.domain.model.AchievementManager;
import io.github.adsa06.domain.model.GameAchievements;
import io.github.adsa06.domain.model.GameEngine;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.presentation.ui.screens.AchievementScreen;
import io.github.adsa06.presentation.ui.screens.GameScreen;
import io.github.adsa06.presentation.ui.theme.ThemeManager;
import io.github.adsa06.presentation.ui.theme.ThemeManager.ThemeType;
import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.AchievementViewModel;
import io.github.adsa06.presentation.viewmodel.GameViewModel;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        TranslationManager translationManager = new TranslationManager("es");
        ThemeManager themeManager = new ThemeManager();

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
            Window window = new BasicWindow("Clicker Game");
            Panel rootPanel = new Panel(new BorderLayout());
            gui.setTheme(theme);

            // 4. Crear una ventana básica con un panel y contenido
            Panel gameWindow = gameScreen.getPanel();
            Panel achievementWindow = achievementScreen.getPanel();

            // --- Sección superior (siempre visible) ---
            Panel topPanel = new Panel(new LinearLayout(Direction.VERTICAL));
            topPanel.addComponent(new Label("Sección superior"));
            Border topWithBorder = topPanel.withBorder(Borders.singleLine("Top"));
            rootPanel.addComponent(topWithBorder, BorderLayout.Location.TOP);

            // --- Sección media: 3 paneles en horizontal ---
            Panel middlePanel = new Panel(new LinearLayout(Direction.HORIZONTAL));

            Panel middleLeft = new Panel(new LinearLayout(Direction.VERTICAL));
            middleLeft.addComponent(new Label("Panel izquierdo"));
            Panel middleRight = new Panel(new LinearLayout(Direction.VERTICAL));
            middleRight.addComponent(new Label("Panel derecho"));

            middlePanel.addComponent(middleLeft.withBorder(Borders.singleLine("Izq")));
            middlePanel.addComponent(gameWindow.withBorder(Borders.singleLine("Centro")));
            middlePanel.addComponent(middleRight.withBorder(Borders.singleLine("Der")));

            rootPanel.addComponent(middlePanel, BorderLayout.Location.CENTER);

            // --- Sección inferior ---
            Border bottomWithBorder = achievementWindow.withBorder(Borders.singleLine("Bottom"));
            rootPanel.addComponent(bottomWithBorder, BorderLayout.Location.BOTTOM);

            window.setComponent(rootPanel);
            gui.addWindowAndWait(window);

            // 6. Detener la pantalla al terminar
            screen.stopScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}