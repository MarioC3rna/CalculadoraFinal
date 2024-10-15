package umg.dem1.Metodos.Definidas;

import java.util.function.BiFunction;

public class LogicaDefinidas {

    // Método para integrar respecto a cualquier variable (x o y)
    public double integrar(BiFunction<Double, Double, Double> funcion, double a, double b, double c, double d, String eje) {
        int n = 100000; // Número de subdivisiones
        double h = (eje.equals("x") ? (b - a) : (d - c)) / n; // Ancho de cada subdivisión
        double suma = 0.0;

        if (eje.equals("x")) {
            // Integrar con respecto a x
            for (int i = 0; i < n; i++) {
                double x1 = a + i * h;
                double x2 = a + (i + 1) * h;
                suma += (funcion.apply(x1, c) + funcion.apply(x2, c)) / 2 * h;
            }
        } else if (eje.equals("y")) {
            // Integrar con respecto a y
            for (int i = 0; i < n; i++) {
                double y1 = c + i * h;
                double y2 = c + (i + 1) * h;
                suma += (funcion.apply(a, y1) + funcion.apply(a, y2)) / 2 * h;
            }
        }

        return suma;
    }
}
