package umg.dem1.Metodos.Sustitucion;

import java.util.Scanner;

public class UserSustitucion {

    public UserSustitucion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido a la Calculadora de Integrales con Método de Sustitución");

        while (true) {
            System.out.print("Ingrese la función a integrar (o 'salir' para terminar): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("salir")) {
                break;
            }

            try {
                String resultadoFinal = LogicaSustitucion.calcularIntegral(input);
                System.out.println("Resultado final: " + resultadoFinal + " + C\n");
            } catch (Exception e) {
                System.out.println("Error al calcular la integral: " + e.getMessage());
            }
        }

        System.out.println("Gracias por usar la Calculadora de Integrales. ¡Hasta luego!");
        scanner.close();
    }





}
