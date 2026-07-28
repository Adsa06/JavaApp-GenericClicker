package io.github.adsa06;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
            gui.setTheme(theme);

            // 4. Crear una ventana básica con un panel y contenido
            Window gameWindow = gameScreen.getWindow();
            Window achievementWindow = achievementScreen.getWindow();

            // Fijamos la posición de gameWindow (por ejemplo, esquina superior izquierda)
            gameWindow.setHints(java.util.Collections.singletonList(Window.Hint.FIXED_POSITION));
            gameWindow.setPosition(new TerminalPosition(0, 0));

            achievementWindow.setHints(java.util.Collections.singletonList(Window.Hint.FIXED_POSITION));

            // Añadimos primero gameWindow y forzamos un render para que calcule su tamaño real
            gui.addWindow(gameWindow);
            gui.updateScreen(); // <-- clave: sin esto, getDecoratedSize() puede devolver (0,0)

            // Ahora sí conocemos su tamaño y posición reales
            TerminalPosition gamePos = gameWindow.getPosition();
            TerminalSize gameSize = gameWindow.getDecoratedSize();

            // Colocamos achievementWindow justo debajo
            achievementWindow.setPosition(gamePos.withRelativeRow(gameSize.getRows()));

            gui.addWindow(achievementWindow);
            gui.setActiveWindow(gameWindow);
            gui.waitForWindowToClose(gameWindow);

            // 6. Detener la pantalla al terminar
            screen.stopScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}