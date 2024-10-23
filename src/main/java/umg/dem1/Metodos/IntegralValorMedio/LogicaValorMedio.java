package umg.dem1.Metodos.IntegralValorMedio;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import umg.dem1.Metodos.utilidades.GraficadorFunciones;

import java.util.Scanner;

public class LogicaValorMedio {


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

    public static class FuncionUsuario implements UnivariateFunction {
        private final String funcion;

        public FuncionUsuario(String funcion) {
            this.funcion = funcion;
        }

        @Override
        public double value(double x) {
            try {
                return evaluarFuncion(funcion, x);
            } catch (Exception e) {
                System.err.println("Error en la evaluación del valor de la función: " + e.getMessage());
                return Double.NaN;
            }
        }
    }

    public static double calcularIntegralPromedio(UnivariateFunction funcion, double a, double b) {
        try {
            SimpsonIntegrator integrador = new SimpsonIntegrator();
            double integral = integrador.integrate(10000, funcion, a, b);
            return integral / (b - a);
        } catch (Exception e) {
            System.err.println("Error al calcular la integral: " + e.getMessage());
            return Double.NaN;
        }
    }

    public static void ValorMedio(Scanner scanner) {

        try {
            scanner.nextLine();
            System.out.print("Ingresa la función en términos de x: ");
            String funcionInput = scanner.nextLine();

            System.out.print("Ingresa el límite inferior (a): ");
            double a = scanner.nextDouble();
            System.out.print("Ingresa el límite superior (b): ");
            double b = scanner.nextDouble();

            UnivariateFunction funcion = new FuncionUsuario(funcionInput);

            double resultado = calcularIntegralPromedio(funcion, a, b);

            System.out.println("El valor promedio de la integral de la función '" + funcionInput + "' en el intervalo [" + a + ", " + b + "] es: " + resultado);

            GraficadorFunciones.mostrarGrafica(funcionInput, funcion, a, b);

        } catch (Exception e) {
            System.err.println("Error en la entrada de datos o ejecución: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}





























