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

public class LogicaAreaBajoCurva {

    public void calcularAreaBajoLaCurva(String funcion, double limiteInferior, double limiteSuperior, String variable) {
        UnivariateFunction function = v -> {
            ExprEvaluator util = new ExprEvaluator();
            IExpr expr = util.evaluate(funcion.replace(variable, "(" + v + ")"));
            return expr.evalDouble();
        };

        SimpsonIntegrator integrator = new SimpsonIntegrator();

        try {
            double resultado = integrator.integrate(1000, function, limiteInferior, limiteSuperior);
            System.out.println("El área bajo la curva desde " + limiteInferior + " hasta " + limiteSuperior + " es: " + resultado);
        } catch (Exception e) {
            System.out.println("Error al calcular el área bajo la curva.");
        }
    }

    public void graficarFuncion(String funcion, double limiteInferior, double limiteSuperior, String variable) {
        SwingUtilities.invokeLater(() -> {
            XYSeries series = new XYSeries("f(" + variable + ")");

            ExprEvaluator util = new ExprEvaluator();

            for (double v = limiteInferior; v <= limiteSuperior; v += 0.1) {
                try {
                    IExpr expr = util.evaluate(funcion.replace(variable, "(" + v + ")"));
                    series.add(v, expr.evalDouble());
                } catch (Exception e) {
                    System.out.println("Error al evaluar la función.");
                    return;
                }
            }

            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(series);

            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Gráfica de la función",
                    variable.toUpperCase(),
                    "f(" + variable + ")",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            JFrame frame = new JFrame("Gráfica");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            ChartPanel chartPanel = new ChartPanel(chart);
            frame.add(chartPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
