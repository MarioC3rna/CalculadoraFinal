package umg.dem1.Metodos.utilidades;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import umg.dem1.Metodos.VolumenSolido.Discos;

import javax.swing.*;
import java.awt.*;

public class GraficadorDiscos extends JFrame {
    private double limInferior;
    private double limSuperior;
    private String funcionF;
    private String funcionG;
    private char eje;

    public GraficadorDiscos(String funcionF, String funcionG, double limInferior, double limSuperior, char eje) {
        this.funcionF = funcionF;
        this.funcionG = funcionG;
        this.limInferior = limInferior;
        this.limSuperior = limSuperior;
        this.eje = eje;

        setTitle("Gráfica del Método de Discos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = crearPanelGrafica();
        add(panel);
    }

    private JPanel crearPanelGrafica() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Crear series para las funciones f(x) y g(x)
        XYSeries seriesF = new XYSeries("f(x)");
        XYSeries seriesG = new XYSeries("g(x)");

        // Generar puntos para las funciones
        double paso = (limSuperior - limInferior) / 200;
        for (double x = limInferior; x <= limSuperior; x += paso) {
            if (eje == 'x') {
                seriesF.add(x, evaluarFuncion(funcionF, x));
                seriesG.add(x, evaluarFuncion(funcionG, x));
            } else {
                seriesF.add(evaluarFuncion(funcionF, x), x);
                seriesG.add(evaluarFuncion(funcionG, x), x);
            }
        }

        dataset.addSeries(seriesF);
        dataset.addSeries(seriesG);

        // Crear la gráfica
        String ejeX = (eje == 'x') ? "X" : "R";
        String ejeY = (eje == 'x') ? "R" : "Y";
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Método de Discos - Sólido de Revolución",
                ejeX,
                ejeY,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Personalizar la apariencia
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        plot.setRenderer(renderer);

        // Crear el panel de la gráfica
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(750, 500));

        return chartPanel;
    }

    private double evaluarFuncion(String funcion, double valor) {
        // Utilizamos el método evalSimpleExpression de la clase Discos
        Discos discos = new Discos();
        try {
            // Necesitamos acceder al método evalSimpleExpression que es privado en Discos
            // Por lo que deberás hacer público este método en la clase Discos
            return discos.evalSimpleExpression(funcion.replace(eje == 'x' ? "x" : "y",
                    String.valueOf(valor)), eje);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void mostrarGrafica() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
        });
    }
}