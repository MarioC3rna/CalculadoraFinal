package umg.dem1.Metodos.utilidades;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import umg.dem1.Metodos.VolumenSolido.Arandelas;

import javax.swing.*;
import java.awt.*;


public class GraficadorArandelas {

    public static void graficarFunciones(String funcionF, String funcionG, double limInferior, double limSuperior, char eje, Arandelas arandelas) {
        // Crear series para ambas funciones
        XYSeries seriesF = new XYSeries("f(" + eje + ") = " + funcionF);
        XYSeries seriesG = new XYSeries("g(" + eje + ") = " + funcionG);

        // Calcular puntos para la gráfica
        double paso = (limSuperior - limInferior) / 200;
        for (double x = limInferior; x <= limSuperior; x += paso) {
            try {
                // Evaluar F(x)
                double yF = arandelas.evalSimpleExpression(
                        funcionF.replace(String.valueOf(eje), String.valueOf(x)),
                        eje
                );

                // Evaluar G(x)
                double yG = arandelas.evalSimpleExpression(
                        funcionG.replace(String.valueOf(eje), String.valueOf(x)),
                        eje
                );

                if (!Double.isNaN(yF) && !Double.isInfinite(yF)) {
                    seriesF.add(x, yF);
                }
                if (!Double.isNaN(yG) && !Double.isInfinite(yG)) {
                    seriesG.add(x, yG);
                }
            } catch (Exception e) {
                continue;
            }
        }

        // Crear el dataset
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesF);
        dataset.addSeries(seriesG);

        // Crear la gráfica
        String titulo = "Gráfica de funciones para Arandela (Eje " + eje + ")";
        JFreeChart chart = ChartFactory.createXYLineChart(
                titulo,                   // Título
                String.valueOf(eje),      // Etiqueta eje X
                "f(" + eje + "), g(" + eje + ")", // Etiqueta eje Y
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
        renderer.setSeriesPaint(0, Color.BLUE);    // Color para F
        renderer.setSeriesPaint(1, Color.RED);     // Color para G
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // Crear y mostrar la ventana
        JFrame frame = new JFrame("Gráfica de Arandela");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void Cascarones(){

    }
}