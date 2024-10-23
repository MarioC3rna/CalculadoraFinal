package umg.dem1.Metodos.utilidades;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import umg.dem1.Metodos.VolumenSolido.Cascarones;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class GraficadorCascarones extends JFrame {
    private double limInferior;
    private double limSuperior;
    private String funcion;
    private char eje;
    private char variable;

    public GraficadorCascarones(String funcion, double limInferior, double limSuperior, char eje, char variable) {
        this.funcion = funcion;
        this.limInferior = limInferior;
        this.limSuperior = limSuperior;
        this.eje = eje;
        this.variable = variable;

        setTitle("Gráfica del Método de Cascarones Cilíndricos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JPanel panelGrafica = crearPanelGrafica();

        // Agregar leyenda explicativa
        JPanel panelLeyenda = new JPanel();
        panelLeyenda.add(new JLabel("<html><body>" +
                "<p><font color='blue'>— Función</font></p>" +
                "<p><font color='red'>--- Ejemplo de cascaron</font></p>" +
                "</body></html>"));

        panelPrincipal.add(panelGrafica, BorderLayout.CENTER);
        panelPrincipal.add(panelLeyenda, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearPanelGrafica() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Crear series para la función y un ejemplo de cascaron
        XYSeries seriesFuncion = new XYSeries("Función");
        XYSeries seriesCascaron = new XYSeries("Cascaron");

        // Generar puntos para la función
        double paso = (limSuperior - limInferior) / 200;
        for (double v = limInferior; v <= limSuperior; v += paso) {
            if (variable == 'x') {
                seriesFuncion.add(v, evaluarFuncion(funcion, v));
            } else {
                seriesFuncion.add(evaluarFuncion(funcion, v), v);
            }
        }

        // Generar puntos para un ejemplo de cascaron
        double posicionCascaron = (limSuperior + limInferior) / 2;
        if (variable == 'x') {
            // Crear un círculo vertical centrado en la función
            double radio = evaluarFuncion(funcion, posicionCascaron);
            for (double theta = 0; theta <= 2 * Math.PI; theta += 0.1) {
                seriesCascaron.add(
                        posicionCascaron,
                        radio * Math.cos(theta)
                );
            }
        } else {
            // Crear un círculo horizontal centrado en la función
            double radio = evaluarFuncion(funcion, posicionCascaron);
            for (double theta = 0; theta <= 2 * Math.PI; theta += 0.1) {
                seriesCascaron.add(
                        radio * Math.cos(theta),
                        posicionCascaron
                );
            }
        }

        dataset.addSeries(seriesFuncion);
        dataset.addSeries(seriesCascaron);

        // Crear la gráfica
        String ejeX = (variable == 'x') ? "X" : "R";
        String ejeY = (variable == 'x') ? "R" : "Y";
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Método de Cascarones Cilíndricos",
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
        renderer.setSeriesPaint(0, Color.BLUE);  // Función en azul
        renderer.setSeriesPaint(1, Color.RED);   // Cascaron en rojo
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(1, new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        plot.setRenderer(renderer);

        // Ajustar los rangos para mejor visualización
        double maxY = dataset.getDomainUpperBound(true);
        double minY = dataset.getDomainLowerBound(true);
        double range = maxY - minY;
        plot.getRangeAxis().setRange(minY - range * 0.2, maxY + range * 0.2);

        // Crear el panel de la gráfica
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(750, 500));

        return chartPanel;
    }

    private double evaluarFuncion(String funcion, double valor) {
        // Utilizamos el método evalSimpleExpression de la clase Cascarones
        Cascarones cascarones = new Cascarones();
        try {
            return cascarones.evalSimpleExpression(
                    funcion.replace(variable == 'x' ? "x" : "y", String.valueOf(valor)),
                    variable
            );
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