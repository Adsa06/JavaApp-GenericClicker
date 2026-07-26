package io.github.adsa06;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import io.github.adsa06.presentation.ui.screens.GameScreen;
import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.GameViewModel;

import java.io.IOException;
import java.util.ResourceBundle;

public class Main {

    public static void main(String[] args) {
        ResourceBundle bundle = TranslationManager.getBundle();

        // 1. Inicializar la fábrica de terminales por defecto
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();

        try (Terminal terminal = terminalFactory.createTerminal()) {
            // 2. Configurar la pantalla (Screen) para manejar el búfer de texto
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

            // Crear un tema minimalista y limpio
            // Definimos colores sobrios: Texto blanco, fondo negro, y selección sutil
            Theme minimalistTheme = SimpleTheme.makeTheme(
                    false,
                    TextColor.ANSI.WHITE, // Color de texto normal
                    TextColor.ANSI.BLACK, // Color de fondo normal
                    TextColor.ANSI.WHITE, // Texto cuando está activo/enfocado
                    TextColor.ANSI.BLACK, // Fondo cuando está activo (invertido o sutil)
                    TextColor.ANSI.BLACK, // PREGUNTAR/OTRO
                    TextColor.ANSI.WHITE, // Borde o detalles
                    TextColor.ANSI.BLACK // Escritorio de fondo
            );

            // 3. Crear el sistema de gestión de ventanas (MultiWindowTextGUI)
            WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);
            gui.setTheme(minimalistTheme);
            // 4. Crear una ventana básica con un panel y contenido

            GameViewModel gameViewModel = new GameViewModel();
            GameScreen gameScreen = new GameScreen(gameViewModel);
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