package umg.dem1.Metodos.Definidas;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.Scanner;
import java.util.function.BiFunction;

public class UserDefinidas {



    Scanner scanner = new Scanner(System.in);
     public UserDefinidas() {
        try {
        // Solicitar la función al usuario
        System.out.println("Introduce la función a integrar:");
        String funcionStr = scanner.nextLine();

        // Solicitar el eje de integración
        System.out.println("¿En qué eje deseas integrar? (x/y):");
        String eje = scanner.nextLine().toLowerCase();  // Convertir a minúsculas para evitar errores

        // Solicitar los límites de integración
        double a, b;
        if (eje.equals("x")) {
            // Límites en el eje x
            System.out.println("Introduce el límite inferior de integración en x:");
            String aStr = scanner.nextLine();  // Ahora se captura como cadena
            a = parseInput(aStr);  // Convertir el límite inferior

            System.out.println("Introduce el límite superior de integración en x:");
            String bStr = scanner.nextLine();  // También se captura como cadena
            b = parseInput(bStr);  // Convertir el límite superior
        } else if (eje.equals("y")) {
            // Límites en el eje y
            System.out.println("Introduce el límite inferior de integración en y:");
            String aStr = scanner.nextLine();  // Captura como cadena
            a = parseInput(aStr);  // Convertir el límite inferior

            System.out.println("Introduce el límite superior de integración en y:");
            String bStr = scanner.nextLine();  // Captura como cadena
            b = parseInput(bStr);  // Convertir el límite superior
        } else {
            System.out.println("Error: Eje inválido. Debes ingresar 'x' o 'y'.");
            return;  // Salir si el eje no es válido
        }

        // Convertir la cadena de la función a una BiFunction<Double, Double, Double>
        BiFunction<Double, Double, Double> funcion = (x, y) -> {
            Expression e = new ExpressionBuilder(funcionStr)
                    .variables("x", "y", "π", "e")
                    .build()
                    .setVariable("x", x)
                    .setVariable("y", y)
                    .setVariable("π", Math.PI)
                    .setVariable("e", Math.E);
            return e.evaluate();
        };

        LogicaDefinidas calculadora = new LogicaDefinidas();
        double resultado;

        if (eje.equals("x")) {
            // Integrar respecto a x, y se mantiene constante
            resultado = calculadora.integrar((x, y) -> funcion.apply(x, 0.0), a, b, 0, 0, "x");
        } else {
            // Integrar respecto a y, x se mantiene constante
            resultado = calculadora.integrar((x, y) -> funcion.apply(0.0, y), 0, 0, a, b, "y");
        }

        // Redondear el resultado a 3 dígitos decimales
        System.out.printf("Resultado de la integral: %.3f%n", resultado);

        // Crear la serie de datos para la función
        XYSeries series = new XYSeries("Función");
        if (eje.equals("x")) {
            for (double x = a; x <= b; x += (b - a) / 100) {
                series.add(x, funcion.apply(x, 0.0));
            }
        } else {
            for (double y = a; y <= b; y += (b - a) / 100) {
                series.add(y, funcion.apply(0.0, y));
            }
        }

        // Crear el conjunto de datos y añadir la serie
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Gráfico de la Función",
                eje.toUpperCase(),  // Etiqueta del eje
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Mostrar el gráfico en una ventana
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gráfico");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ChartPanel(chart));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
}

// Método parseInput que acepta "π", "e", o valores numéricos
private static double parseInput(String input) {
    switch (input.toLowerCase()) {
        case "pi":
        case "π":
            return Math.PI;
        case "e":
            return Math.E;
        default:
            return Double.parseDouble(input);  // Convertir el valor numérico si no es una constante
    }
}
}

