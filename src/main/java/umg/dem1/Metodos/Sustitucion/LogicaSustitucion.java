package umg.dem1.Metodos.Sustitucion;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogicaSustitucion {


    private static final ExprEvaluator evaluator = new ExprEvaluator();

    public static String calcularIntegral(String funcion) throws SyntaxError, MathException {
        String u = identificarSustitucion(funcion);
        String du = calcularDerivada(u);
        String integralEnU = reescribirIntegral(funcion, u, du);
        String resultadoEnU = resolverIntegral(integralEnU);
        return sustituirDeVuelta(resultadoEnU, u);
    }

    private static String identificarSustitucion(String funcion) {
        Pattern pattern = Pattern.compile("\\(([^()]+)\\)|(?:sin|cos|tan|exp)\\([^()]+\\)");
        Matcher matcher = pattern.matcher(funcion);
        if (matcher.find()) {
            return matcher.group();
        }
        return "x";
    }

    private static String calcularDerivada(String expresion) throws SyntaxError, MathException {
        String comando = "D[" + expresion + ", x]";
        IExpr resultado = evaluator.evaluate(comando);
        return resultado.toString();
    }

    private static String reescribirIntegral(String funcion, String u, String du) throws SyntaxError, MathException {
        String nuevaFuncion = funcion.replace(u, "u");
        String comandoSimplificar = "Simplify[(" + nuevaFuncion + ")/(" + du + ")]";
        IExpr resultadoSimplificado = evaluator.evaluate(comandoSimplificar);
        return resultadoSimplificado.toString() + "*du";
    }

    private static String resolverIntegral(String integralEnU) throws SyntaxError, MathException {
        String comando = "Integrate[" + integralEnU + ", u]";
        IExpr resultado = evaluator.evaluate(comando);
        return resultado.toString();
    }

    private static String sustituirDeVuelta(String resultadoEnU, String u) {
        return resultadoEnU.replace("u", "(" + u + ")");
    }


}
