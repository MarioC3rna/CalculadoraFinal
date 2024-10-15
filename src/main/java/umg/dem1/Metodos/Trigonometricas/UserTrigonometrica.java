package umg.dem1.Metodos.Trigonometricas;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

import java.util.Scanner;

public class UserTrigonometrica  {

    static LogicaTrigonometrica integrales = new LogicaTrigonometrica();

    public UserTrigonometrica() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingresa la integral que deseas resolver");
        String expression = sc.nextLine();
        String[] partes = expression.split(" ");
        char diferencial = partes[1].charAt(1);
        String termino = partes[0];
        System.out.println("El resultado es: "+integrales.ResolverIntegral(termino,diferencial));
    }



}
