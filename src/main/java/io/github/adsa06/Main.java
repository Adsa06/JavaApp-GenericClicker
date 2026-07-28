package io.github.adsa06;

import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import io.github.adsa06.domain.model.GameEngine;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.presentation.ui.screens.GameScreen;
import io.github.adsa06.presentation.ui.theme.ThemeManager;
import io.github.adsa06.presentation.ui.theme.ThemeManager.ThemeType;
import io.github.adsa06.presentation.ui.translations.TranslationManager;
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

            gui.addWindow(gameWindow);
            gui.waitForWindowToClose(gameWindow);

            // 6. Detener la pantalla al terminar
            screen.stopScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}