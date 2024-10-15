package umg.dem1.Metodos.Impropias;

import java.util.function.Function;

public class LogicaImpropias {


    public static double exp(double x) {
        return Math.exp(x);
    }

    public static double calcularIntegralImpropia(Function<Double, Double> funcion, double a, double b, int n) {
        if (Double.isInfinite(b)) {
            b = 1e6;
        }

        if (Double.isInfinite(a)) {
            a = -1e6;
        }

        double h = (b - a) / n;
        double suma = 0.5 * (funcion.apply(a) + funcion.apply(b));

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            suma += funcion.apply(x);
        }

        return suma * h;
    }

    public static Function<Double, Double> obtenerFuncion(String expresion) {
        return x -> {
            if (expresion.equals("exp(-x)")) {
                return exp(-x);
            } else if (expresion.equals("1 / Math.pow(x, 2)")) {
                return 1 / Math.pow(x, 2);
            } else {
                System.out.println("Función no reconocida.");
                return 0.0;
            }
        };
    }


}