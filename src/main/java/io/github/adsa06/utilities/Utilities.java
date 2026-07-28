package io.github.adsa06.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Utilities {
    public static void log(String msg) {
        try (FileWriter fw = new FileWriter("debug.log", true);
                PrintWriter pw = new PrintWriter(fw)) {
            pw.println(msg);
        } catch (IOException e) {
            // ignorar
        }
    }
}
