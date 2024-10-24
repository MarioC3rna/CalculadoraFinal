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
            String integral1 = "Integrate[" + termino + "," + diferencial + "]";
            IExpr result = resolver.eval(integral1);
            resultado = result.toString();

            final String terminoFinal = termino.replace(String.valueOf(diferencial), "x");
            final String terminoSimplificado = resolver.eval(terminoFinal).toString();

            UnivariateFunction f = new UnivariateFunction() {
                @Override
                public double value(double x) {
                    try {
                        return resolver.eval(terminoSimplificado.replace("x", String.valueOf(x))).evalDouble();
                    } catch (Exception e) {
                        return Double.NaN;
                    }
                }
            };

            // Calcular límites apropiados
            double limiteInferior = -5; // Empezamos con un rango más pequeño
            double limiteSuperior = 5;

            // Encontrar valores máximos y mínimos para ajustar el rango
            double minY = Double.POSITIVE_INFINITY;
            double maxY = Double.NEGATIVE_INFINITY;

            // Muestrear puntos para encontrar un rango apropiado
            for (double x = limiteInferior; x <= limiteSuperior; x += 0.5) {
                double y = f.value(x);
                if (!Double.isNaN(y) && !Double.isInfinite(y)) {
                    minY = Math.min(minY, y);
                    maxY = Math.max(maxY, y);
                }
            }

            // Ajustar límites basados en los valores encontrados
            if (minY != Double.POSITIVE_INFINITY && maxY != Double.NEGATIVE_INFINITY) {
                double rango = maxY - minY;
                // Si la función es muy plana o muy empinada, ajustar el rango X
                if (rango < 1) {
                    limiteInferior = -2;
                    limiteSuperior = 2;
                } else if (rango > 100) {
                    limiteInferior = -1;
                    limiteSuperior = 1;
                }
            }

            // Llamar al método estático con los límites calculados
            GraficadorFunciones.mostrarGrafica(terminoSimplificado, f, limiteInferior, limiteSuperior);

        } catch (SyntaxError e) {
            System.err.println("Error de sintaxis: " + e.getMessage());
        } catch (MathException e) {
            System.err.println("Error matemático: " + e.getMessage());
        }

        return resultado;
    }
}