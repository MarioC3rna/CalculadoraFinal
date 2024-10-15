package umg.dem1.Metodos.IntegralporPartes;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogicaPorPartes {

    private final ExprEvaluator evaluador;

    public LogicaPorPartes(){
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "OFF");
        Logger.getLogger("org.matheclipse").setLevel(Level.OFF);
        this.evaluador = new ExprEvaluator();
    }

    public String integrar(String funcion, String variable) throws SyntaxError, MathException {
        IExpr derivada = evaluador.eval("Integrate[" + funcion + "," + variable + "]");
        return derivada.toString();
    }



















}
