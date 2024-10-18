package umg.dem1.Metodos.CentroidesyCentroides;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;

import java.util.Scanner;

public class LogicaCentroides {

    public static double evaluarFuncion(String funcion, double x) {
        try {
            Expression expression = new ExpressionBuilder(funcion)
                    .variable("x")
                    .build()
                    .setVariable("x", x);
            return expression.evaluate();
        } catch (Exception e) {
            System.err.println("Error al evaluar la funcion: " + e.getMessage());
            return Double.NaN;
        }
    }

    public static double evaluarIntegral(UnivariateFunction funcion, double a, double b) {
        try {
            SimpsonIntegrator integrador = new SimpsonIntegrator();
            return integrador.integrate(10000, funcion, a, b);
        } catch (Exception e) {
            System.err.println("Error al calcular la integral: " + e.getMessage());
            return Double.NaN;
        }
    }

    public static void Centroides() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Ingresa la función en términos de x: ");
            String funcionInput = scanner.nextLine();

            System.out.print("Ingresa el límite inferior (a): ");
            double a = scanner.nextDouble();
            System.out.print("Ingresa el límite superior (b): ");
            double b = scanner.nextDouble();

            UnivariateFunction funcion = x -> {
                try {
                    return evaluarFuncion(funcionInput, x);
                } catch (Exception e) {
                    System.out.println("Error al evaluar la función: " + e.getMessage());
                    return 0;
                }
            };

            double area = evaluarIntegral(funcion, a, b);

            if (!Double.isNaN(area) && area != 0) {
                double xBar = evaluarIntegral(x -> x * funcion.value(x), a, b) / area;
                double yBar = evaluarIntegral(funcion, a, b) / area;

                System.out.println("Área (A): " + area);
                System.out.println("Centro de masa (x̄, ȳ): (" + xBar + ", " + yBar + ")");
            } else {
                System.out.println("El área es 0 o no se pudo calcular correctamente.");
            }
        } catch (Exception e) {
            System.err.println("Error en la entrada de datos o ejecución: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
