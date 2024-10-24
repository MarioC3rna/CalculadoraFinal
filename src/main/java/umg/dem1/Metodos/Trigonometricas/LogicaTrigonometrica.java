package umg.dem1.Metodos.Trigonometricas;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;
import umg.dem1.Metodos.utilidades.GraficadorFunciones;

import java.util.Scanner;
public class LogicaTrigonometrica {

    public String ResolverIntegral(String termino, char diferencial) {
        ExprEvaluator resolver = new ExprEvaluator();
        String resultado = "";
        try {
            // Calcular la integral
            String integral1 = "Integrate[" + termino + "," + diferencial + "]";
            IExpr result = resolver.eval(integral1);
            resultado = result.toString();

            // Preparar la función para graficar
            final String terminoFinal = termino.replace(String.valueOf(diferencial), "x");
            final String terminoSimplificado = resolver.eval(terminoFinal).toString();

            UnivariateFunction f = new UnivariateFunction() {
                @Override
                public double value(double x) {
                    try {
                        // Convertir el valor a radianes si es necesario
                        String expr = terminoSimplificado.replace("x", String.valueOf(x));
                        // Asegurarse de que las funciones trigonométricas usen radianes
                        expr = ajustarExpresionTrigonometrica(expr);
                        return resolver.eval(expr).evalDouble();
                    } catch (Exception e) {
                        return Double.NaN;
                    }
                }
            };

            // Calcular límites apropiados para funciones trigonométricas
            double[] limites = calcularLimitesGrafica(terminoSimplificado, f);
            double limiteInferior = limites[0];
            double limiteSuperior = limites[1];

            // Graficar la función
            GraficadorFunciones.mostrarGrafica(terminoSimplificado, f, limiteInferior, limiteSuperior);

        } catch (SyntaxError e) {
            System.err.println("Error de sintaxis: " + e.getMessage());
        } catch (MathException e) {
            System.err.println("Error matemático: " + e.getMessage());
        }

        return resultado;
    }

    private String ajustarExpresionTrigonometrica(String expr) {
        // Asegurarse de que las funciones trigonométricas usen radianes
        expr = expr.replaceAll("(?i)sin\\(", "Sin[");
        expr = expr.replaceAll("(?i)cos\\(", "Cos[");
        expr = expr.replaceAll("(?i)tan\\(", "Tan[");
        expr = expr.replaceAll("\\)", "]");
        return expr;
    }

    private double[] calcularLimitesGrafica(String expresion, UnivariateFunction f) {
        // Por defecto, usar un período completo para funciones trigonométricas
        double limiteInferior = -2 * Math.PI;
        double limiteSuperior = 2 * Math.PI;

        // Si la expresión contiene funciones trigonométricas, ajustar los límites
        boolean esTrigonometrica = expresion.toLowerCase().contains("sin") ||
                expresion.toLowerCase().contains("cos") ||
                expresion.toLowerCase().contains("tan");

        if (esTrigonometrica) {
            // Para funciones trigonométricas, mostrar al menos dos períodos completos
            limiteInferior = -2 * Math.PI;
            limiteSuperior = 2 * Math.PI;
        } else {
            // Para otras funciones, buscar un rango apropiado
            double minY = Double.POSITIVE_INFINITY;
            double maxY = Double.NEGATIVE_INFINITY;

            // Muestrear puntos para encontrar un rango apropiado
            for (double x = -10; x <= 10; x += 0.1) {
                double y = f.value(x);
                if (!Double.isNaN(y) && !Double.isInfinite(y)) {
                    minY = Math.min(minY, y);
                    maxY = Math.max(maxY, y);
                }
            }

            // Ajustar límites basados en los valores encontrados
            if (minY != Double.POSITIVE_INFINITY && maxY != Double.NEGATIVE_INFINITY) {
                double rango = maxY - minY;
                if (rango < 1) {
                    limiteInferior = -5;
                    limiteSuperior = 5;
                } else if (rango > 100) {
                    limiteInferior = -2;
                    limiteSuperior = 2;
                } else {
                    limiteInferior = -10;
                    limiteSuperior = 10;
                }
            }
        }

        return new double[]{limiteInferior, limiteSuperior};
    }
}