package umg.dem1.Metodos.Impropias;

import java.util.Scanner;
import java.util.function.Function;

public class UserImpropias {


    public UserImpropias() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa la función para integrar (usa 'x' como variable, por ejemplo: exp(-x) para la función exponencial): ");
        String expresion = scanner.nextLine();

        Function<Double, Double> funcion = LogicaImpropias.obtenerFuncion(expresion);

        try {
            System.out.print("Ingresa el valor de a (límite inferior): ");
            double a = scanner.nextDouble();

            System.out.print("Ingresa el valor de b (límite superior, usa 'infinito' para infinito positivo): ");
            String bInput = scanner.next();
            double b;
            if (bInput.equalsIgnoreCase("infinito")) {
                b = Double.POSITIVE_INFINITY;
            } else {
                b = Double.parseDouble(bInput);
            }

            System.out.println("\nLas divisiones determinan cuántas partes se divide el intervalo de integración.");
            System.out.println("Un número mayor de divisiones significa mayor precisión, pero más tiempo de cálculo.");
            System.out.println("Por ejemplo, con 10 divisiones se obtendrá una aproximación rápida, pero con 1000 divisiones será más precisa.");
            System.out.println("Ingresa el número de divisiones que deseas (n):");

            int n = scanner.nextInt();

            if (n <= 0) {
                System.out.println("El número de divisiones debe ser mayor a 0.");
                return;
            }

            double resultado = LogicaImpropias.calcularIntegralImpropia(funcion, a, b, n);

            System.out.println("El valor de la integral es: " + resultado);

        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de los números: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
