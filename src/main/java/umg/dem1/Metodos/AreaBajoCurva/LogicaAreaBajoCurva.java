package umg.dem1.Metodos.AreaBajoCurva;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

import javax.swing.*;
import java.awt.*;

import static umg.dem1.Metodos.utilidades.GraficadorFunciones.mostrarGrafica;
public class LogicaAreaBajoCurva {

    public String calcularAreaBajoLaCurva(String funcion, double limiteInferior, double limiteSuperior, String variable) {
        UnivariateFunction function = v -> {
            ExprEvaluator util = new ExprEvaluator();
            IExpr expr = util.evaluate(funcion.replace(variable, "(" + v + ")"));
            return expr.evalDouble();
        };

        SimpsonIntegrator integrator = new SimpsonIntegrator();
        String resultado;

        try {
            double area = integrator.integrate(1000, function, limiteInferior, limiteSuperior);
            resultado = "El área bajo la curva desde " + limiteInferior + " hasta " + limiteSuperior + " es: " + area;
        } catch (Exception e) {
            resultado = "Error al calcular el área bajo la curva.";
        }
        mostrarGrafica(funcion, function, limiteInferior, limiteSuperior);
        return resultado;


    }

}
