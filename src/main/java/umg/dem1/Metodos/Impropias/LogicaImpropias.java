package umg.dem1.Metodos.Impropias;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.RombergIntegrator;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import umg.dem1.Metodos.utilidades.GraficadorFunciones;

import java.util.function.BiFunction;
public class LogicaImpropias {
    // Constantes para representar infinito
    public static final double INFINITO_POSITIVO = Double.POSITIVE_INFINITY;
    public static final double INFINITO_NEGATIVO = Double.NEGATIVE_INFINITY;

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

    // Método principal de integración que maneja límites infinitos
    public double integrar(BiFunction<Double, Double, Double> funcion, double a, double b, double c, double d, String eje) {
        if (eje.equals("x")) {
            // Si los límites en x son infinitos
            if (Double.isInfinite(a) || Double.isInfinite(b)) {
                return integrarInfinito(funcion, a, b, c, eje);
            }
            // Si los límites son finitos, usar el método original
            return integrarFinito(funcion, a, b, c, d, eje);
        } else if (eje.equals("y")) {
            // Si los límites en y son infinitos
            if (Double.isInfinite(c) || Double.isInfinite(d)) {
                return integrarInfinito(funcion, c, d, a, eje);
            }
            // Si los límites son finitos, usar el método original
            return integrarFinito(funcion, a, b, c, d, eje);
        }
        throw new IllegalArgumentException("Eje debe ser 'x' o 'y'");
    }

    // Método para integrar con límites finitos
    private double integrarFinito(BiFunction<Double, Double, Double> funcion, double a, double b, double c, double d, String eje) {
        int n = 10000000;
        double h = (eje.equals("x") ? (b - a) : (d - c)) / n;
        double suma = 0.0;

        if (eje.equals("x")) {
            for (int i = 0; i < n; i++) {
                double x1 = a + i * h;
                double x2 = a + (i + 1) * h;
                suma += (funcion.apply(x1, c) + funcion.apply(x2, c)) / 2 * h;
            }
            graficarFuncion(funcion, a, b, c, "x");
        } else {
            for (int i = 0; i < n; i++) {
                double y1 = c + i * h;
                double y2 = c + (i + 1) * h;
                suma += (funcion.apply(a, y1) + funcion.apply(a, y2)) / 2 * h;
            }
            graficarFuncion(funcion, c, d, a, "y");
        }

        return suma;
    }

    // Método para integrar con límites infinitos
    private double integrarInfinito(BiFunction<Double, Double, Double> funcion, double lim1, double lim2, double constante, String eje) {
        double limiteInferior, limiteSuperior;

        if (lim1 == INFINITO_NEGATIVO && lim2 == INFINITO_POSITIVO) {
            limiteInferior = -10000;
            limiteSuperior = 10000;
        } else if (lim1 == INFINITO_NEGATIVO) {
            limiteInferior = -10000;
            limiteSuperior = lim2;
        } else if (lim2 == INFINITO_POSITIVO) {
            limiteInferior = lim1;
            limiteSuperior = 10000;
        } else {
            limiteInferior = lim1;
            limiteSuperior = lim2;
        }

        return integrarFinito(funcion,
                eje.equals("x") ? limiteInferior : constante,
                eje.equals("x") ? limiteSuperior : constante,
                eje.equals("y") ? limiteInferior : constante,
                eje.equals("y") ? limiteSuperior : constante,
                eje);
    }

    // Método modificado para graficar la función
    private void graficarFuncion(BiFunction<Double, Double, Double> funcion, double min, double max, double constante, String eje) {
        UnivariateFunction f;
        if (eje.equals("x")) {
            f = (x) -> funcion.apply(x, constante);
        } else {
            f = (y) -> funcion.apply(constante, y);
        }
        GraficadorFunciones.mostrarGrafica("f(" + eje + ")", f, min, max);
    }

    // Método parseInput para manejar infinito
    public static double parseInput(String input) {
        switch (input.toLowerCase()) {
            case "pi":
            case "π":
                return Math.PI;
            case "e":
                return Math.E;
            case "inf":
            case "∞":
                return INFINITO_POSITIVO;
            case "-inf":
            case "-∞":
                return INFINITO_NEGATIVO;
            default:
                try {
                    return Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Entrada no válida: " + input);
                }
        }
    }
}