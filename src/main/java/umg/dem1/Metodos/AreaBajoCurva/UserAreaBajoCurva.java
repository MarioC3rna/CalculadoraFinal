package umg.dem1.Metodos.AreaBajoCurva;
import java.util.Scanner;
import java.util.Scanner;

public class UserAreaBajoCurva {

    public UserAreaBajoCurva() {
        Scanner scanner = new Scanner(System.in);
        LogicaAreaBajoCurva logica = new LogicaAreaBajoCurva();
        boolean continuar = true;

        while (continuar) {
            System.out.println("Selecciona una opción:");
            System.out.println("1. Calcular área bajo la curva en el eje X (integral numérica)");
            System.out.println("2. Calcular área bajo la curva en el eje Y (integral numérica)");
            System.out.println("3. Salir");

            int metodo = scanner.nextInt();

            switch (metodo) {
                case 1:
                    procesarAreaBajoCurva(scanner, logica, "x");
                    break;

                case 2:
                    procesarAreaBajoCurva(scanner, logica, "y");
                    break;

                case 3:
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }

        System.out.println("Programa finalizado.");
    }

    private void procesarAreaBajoCurva(Scanner scanner, LogicaAreaBajoCurva logica, String variable) {
        System.out.print("Ingresa la función a integrar (en términos de " + variable + ", ej. " + variable + "^2): ");
        scanner.nextLine(); // Consumir el salto de línea
        String funcionNumerica = scanner.nextLine();

        System.out.print("Ingresa el límite inferior de integración: ");
        double limiteInferior = scanner.nextDouble();

        System.out.print("Ingresa el límite superior de integración: ");
        double limiteSuperior = scanner.nextDouble();

        // Obtener el resultado como String
        String resultado = logica.calcularAreaBajoLaCurva(funcionNumerica, limiteInferior, limiteSuperior, variable);
        System.out.println(resultado);

        // Preguntar al usuario si desea continuar
        System.out.print("¿Deseas realizar otro cálculo? (1. Sí, 2. No): ");
        int continuarRespuesta = scanner.nextInt();
        if (continuarRespuesta != 1) {
            // Si el usuario no quiere continuar, se sale del ciclo
            System.out.println("Programa finalizado.");
            System.exit(0);
        }
    }
}
