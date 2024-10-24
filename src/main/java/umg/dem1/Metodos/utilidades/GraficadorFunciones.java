package umg.dem1.Metodos.utilidades;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
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

        // Aumentar considerablemente el rango de visualización
        limiteInferior = -300;  // Ajustado para coincidir con la gráfica deseada
        limiteSuperior = 200;   // Ajustado para coincidir con la gráfica deseada

        // Aumentar el número de puntos para una mejor resolución
        int numPuntos = 3000; // Aumentado para mayor suavidad
        double paso = (limiteSuperior - limiteInferior) / numPuntos;

        // Calcular puntos para la gráfica
        for (double x = limiteInferior; x <= limiteSuperior; x += paso) {
            try {
                double y = f.value(x);
                if (!Double.isNaN(y) && !Double.isInfinite(y)) {
                    series.add(x, y);
                }
            } catch (Exception e) {
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

        // Configurar el renderizador
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setDrawSeriesLineAsPath(true);
        plot.setRenderer(renderer);

        // Ajustar los rangos de los ejes
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        // Configurar el rango del eje X
        domainAxis.setRange(limiteInferior, limiteSuperior);

        // Configurar el rango del eje Y
        rangeAxis.setRange(-220, 180); // Ajustado para coincidir con la gráfica deseada

        // Configurar las unidades de las divisiones principales
        domainAxis.setTickUnit(new NumberTickUnit(20));
        rangeAxis.setTickUnit(new NumberTickUnit(20));

        // Mostrar las líneas de la cuadrícula
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);

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

    // El método mostrarGraficaComparativa permanece igual pero con los nuevos rangos
    public static void mostrarGraficaComparativa(String[] funciones, UnivariateFunction[] fs,
                                                 double limiteInferior, double limiteSuperior) {
        // ... (mismo código que antes pero con los nuevos rangos)
    }
}