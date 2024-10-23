package umg.dem1.Metodos.utilidades;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;
import java.awt.Color;


public class GraficadorFunciones {

    public static void mostrarGrafica(String funcion, UnivariateFunction f, double limiteInferior, double limiteSuperior) {
        // Crear serie de datos
        XYSeries series = new XYSeries("f(x) = " + funcion);

        // Calcular puntos para la gráfica
        double paso = (limiteSuperior - limiteInferior) / 1000;
        for (double x = limiteInferior; x <= limiteSuperior; x += paso) {
            try {
                double y = f.value(x);
                if (!Double.isNaN(y) && !Double.isInfinite(y)) {
                    series.add(x, y);
                }
            } catch (Exception e) {
                // Ignorar puntos que causen errores
                continue;
            }
        }

        // Crear el dataset
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Crear la gráfica
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Gráfica de la función",  // Título
                "x",                      // Etiqueta eje X
                "f(x)",                   // Etiqueta eje Y
                dataset,                  // Datos
                PlotOrientation.VERTICAL,
                true,                     // Incluir leyenda
                true,                     // Tooltips
                false                     // URLs
        );

        // Personalizar la apariencia
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesShapesVisible(0, false);
        plot.setRenderer(renderer);

        // Crear y mostrar la ventana
        JFrame frame = new JFrame("Gráfica");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Sobrecarga para cuando necesites mostrar múltiples funciones
    public static void mostrarGraficaComparativa(String[] funciones, UnivariateFunction[] fs,
                                                 double limiteInferior, double limiteSuperior) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int i = 0; i < funciones.length; i++) {
            XYSeries series = new XYSeries("f" + (i+1) + "(x) = " + funciones[i]);
            double paso = (limiteSuperior - limiteInferior) / 1000;

            for (double x = limiteInferior; x <= limiteSuperior; x += paso) {
                try {
                    double y = fs[i].value(x);
                    if (!Double.isNaN(y) && !Double.isInfinite(y)) {
                        series.add(x, y);
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            dataset.addSeries(series);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Comparación de funciones",
                "x",
                "f(x)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Personalizar la apariencia
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // Crear y mostrar la ventana
        JFrame frame = new JFrame("Gráfica Comparativa");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}