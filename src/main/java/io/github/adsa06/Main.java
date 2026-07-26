package io.github.adsa06;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Main {
    static int counter = 0;
    public static void main(String[] args) {
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
            TextColor.ANSI.WHITE,      // Color de texto normal
            TextColor.ANSI.BLACK,      // Color de fondo normal
            TextColor.ANSI.WHITE,      // Texto cuando está activo/enfocado
            TextColor.ANSI.BLACK,      // Fondo cuando está activo (invertido o sutil)
            TextColor.ANSI.BLACK,      // PREGUNTAR/OTRO
            TextColor.ANSI.WHITE,      // Borde o detalles
            TextColor.ANSI.BLACK       // Escritorio de fondo
        );

            // 3. Crear el sistema de gestión de ventanas (MultiWindowTextGUI)
            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            textGUI.setTheme(minimalistTheme);
            // 4. Crear una ventana básica con un panel y contenido
            BasicWindow window = new BasicWindow("Mi Primera Ventana Lanterna");
            
            Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
            panel.addComponent(new Label("¡Hola, mundo desde Lanterna 3.2.0-alpha1!"));
            
            // Creamos la etiqueta del contador y la guardamos en una variable
            Label counterLabel = new Label("Contador: " + counter);

            // Botón para incrementar el contador y actualizar la etiqueta
            Button.Listener click = new Button.Listener() {

                @Override
                public void onTriggered(Button button) {
                    counter++;
                    counterLabel.setText("Contador: " + counter); // <-- Actualiza el texto visualmente
                }
                
            };
            Button boton = new Button("A");
            boton.addListener(click);
            panel.addComponent(new Button("Click", () -> {
                counter++;
                counterLabel.setText("Contador: " + counter); // <-- Actualiza el texto visualmente
            }));
            panel.addComponent(counterLabel);
            panel.addComponent(new Button("Salir", window::close));

            window.setComponent(panel);

            // 5. Mostrar la ventana y bloquear hasta que se cierre
            textGUI.addWindowAndWait(window);

            // 6. Detener la pantalla al terminar
            screen.stopScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}