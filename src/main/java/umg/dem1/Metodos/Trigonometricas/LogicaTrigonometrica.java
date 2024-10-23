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


    public  String ResolverIntegral(String termino,char diferencial){
        ExprEvaluator resolver = new ExprEvaluator();
        String resultado = "";
        try {
            String integral1 = "Integrate["+termino+","+diferencial+"]";
            IExpr result = resolver.eval(integral1);
            resultado= result.toString();

        } catch (SyntaxError e){
            System.err.println("Error de sintaxis: " + e.getMessage());
        } catch (MathException e) {
            System.err.println("Error matemático: " + e.getMessage());
        }
        return resultado;
    }



}
