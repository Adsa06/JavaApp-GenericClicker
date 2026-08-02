package io.github.adsa06.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Utilities {
    public static void log(String tag, String msg) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateFormated = now.format(format);

        try (FileWriter fw = new FileWriter("debug.log", true);
                PrintWriter pw = new PrintWriter(fw)) {
            pw.println("[" + dateFormated + "] In: " + tag + " | Log: " + msg);
        } catch (IOException e) {
            // ignorar
        }
    }

    private static final DecimalFormatSymbols SYMBOLS = new DecimalFormatSymbols(Locale.US);

    public static String formatNum(long rawValue) {
        String formatedValue = new String();
        double value = rawValue / 10.0;
        double absValue = Math.abs(value);

        if (absValue < 1000) {
            DecimalFormat dfSimple = new DecimalFormat("0.#", SYMBOLS);
            formatedValue = dfSimple.format(value);
        } else {

            int exponent = (int) Math.floor(Math.log10(absValue));
            double mantissa = value / Math.pow(10, exponent);

            double roundedMantissa = Math.round(mantissa * 100.0) / 100.0;
            if (Math.abs(roundedMantissa) >= 10.0) {
                mantissa /= 10.0;
                exponent += 1;
            }

            DecimalFormat df = new DecimalFormat("0.##", SYMBOLS);
            formatedValue = df.format(mantissa) + " \u00D7 10" + toSuperscript(exponent);
        }
        return formatedValue;
    }

    private static String toSuperscript(int n) {
        final char[] SUPERSCRIPT = { '⁰', '¹', '²', '³', '⁴', '⁵', '⁶', '⁷', '⁸', '⁹' };
        StringBuilder sb = new StringBuilder();

        if (n < 0) {
            sb.append('⁻');
            n = -n;
        }

        for (char c : Integer.toString(n).toCharArray()) {
            sb.append(SUPERSCRIPT[c - '0']);
        }

        return sb.toString();
    }
}
