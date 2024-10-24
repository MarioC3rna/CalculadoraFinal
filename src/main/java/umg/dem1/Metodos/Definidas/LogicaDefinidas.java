package umg.dem1.Metodos.Definidas;

import java.util.function.BiFunction;
import java.util.function.BiFunction;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.math3.analysis.UnivariateFunction;
import umg.dem1.Metodos.utilidades.GraficadorFunciones;

import static umg.dem1.Metodos.utilidades.GraficadorFunciones.mostrarGrafica;
public class LogicaDefinidas {
    // Método para convertir la cadena de la función a una BiFunction
    public BiFunction<Double, Double, Double> convertirFuncion(String funcionStr) {
        return (x, y) -> {
            Expression e = new ExpressionBuilder(funcionStr)
                    .variables("x", "y", "π", "e")
                    .build()
                    .setVariable("x", x)
                    .setVariable("y", y)
                    .setVariable("π", Math.PI)
                    .setVariable("e", Math.E);
            return e.evaluate();
        };
    }

    // Método para integrar respecto a cualquier variable (x o y)
    public double integrar(BiFunction<Double, Double, Double> funcion, double a, double b, double c, double d, String eje) {
        int n = 100000; // Número de subdivisiones
        double h = (eje.equals("x") ? (b - a) : (d - c)) / n; // Ancho de cada subdivisión
        double suma = 0.0;

        // Graficar según el eje de integración
        if (eje.equals("x")) {
            graficarFuncion(funcion, a, b, c, "x");
        } else if (eje.equals("y")) {
            graficarFuncion(funcion, c, d, a, "y");
        }

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

    // Método modificado para graficar la función
    public void graficarFuncion(BiFunction<Double, Double, Double> funcion, double min, double max, double constante, String eje) {
        UnivariateFunction f;
        if (eje.equals("x")) {
            // Para integración respecto a x, y es constante
            f = (x) -> funcion.apply(x, constante);
        } else {
            // Para integración respecto a y, x es constante
            f = (y) -> funcion.apply(constante, y);
        }
        // Llamar al método mostrarGrafica de GraficadorFunciones
        GraficadorFunciones.mostrarGrafica("f(" + eje + ")", f, min, max);
    }

    // Método parseInput para aceptar "π", "e", o valores numéricos
    public static double parseInput(String input) {
        switch (input.toLowerCase()) {
            case "pi":
            case "π":
                return Math.PI;
            case "e":
                return Math.E;
            default:
                return Double.parseDouble(input);
        }
    }
}