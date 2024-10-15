package umg.dem1.Metodos.IntegralporPartes;

import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

public class UserPorPartes {


    public void ejecutarIntegracion() {
    LogicaPorPartes porPartes = new LogicaPorPartes();
    java.util.Scanner scanner = new java.util.Scanner(System.in);

        try{
        System.out.println("Ingrese la funcion a integrar (por ejemplo, x * Sin[x]):");
        String funcion = scanner.nextLine();

        System.out.print("Ingrese la variable de integracion: ");
        String variable = scanner.nextLine();

        String resultadoIntegracion = porPartes.integrar(funcion, variable);
        System.out.println("La integral de " + funcion + " con respecto a " + variable + "es:");

        String resultadoDerivacion = porPartes.integrar(resultadoIntegracion, variable);
        System.out.println("\nLa derivada del resultado es: ");
        System.out.println(resultadoDerivacion);

    } catch(
    SyntaxError e)

    {
        System.err.println("Error de sintaxis: " + e.getMessage());
    } catch(
    MathException e)

    {
        System.err.println("Error matematico: " + e.getMessage());
    }finally

    {
        scanner.close();
    }
}
}

