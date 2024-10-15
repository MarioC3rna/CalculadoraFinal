package umg.dem1.Metodos.AreaBajoCurva;

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
                    System.out.print("Ingresa la función a integrar (en términos de x, ej. x^2): ");
                    scanner.nextLine();
                    String funcionNumericaX = scanner.nextLine();

                    System.out.print("Ingresa el límite inferior de integración: ");
                    double limiteInferiorX = scanner.nextDouble();

                    System.out.print("Ingresa el límite superior de integración: ");
                    double limiteSuperiorX = scanner.nextDouble();

                    logica.calcularAreaBajoLaCurva(funcionNumericaX, limiteInferiorX, limiteSuperiorX, "x");

                    System.out.println("¿Deseas ver la gráfica de la función? (1. Sí, 2. No)");
                    int opcionGraficaX = scanner.nextInt();
                    if (opcionGraficaX == 1) {
                        logica.graficarFuncion(funcionNumericaX, limiteInferiorX, limiteSuperiorX, "x");
                    }
                    break;

                case 2:
                    System.out.print("Ingresa la función a integrar (en términos de y, ej. y^2): ");
                    scanner.nextLine();
                    String funcionNumericaY = scanner.nextLine();

                    System.out.print("Ingresa el límite inferior de integración: ");
                    double limiteInferiorY = scanner.nextDouble();

                    System.out.print("Ingresa el límite superior de integración: ");
                    double limiteSuperiorY = scanner.nextDouble();

                    logica.calcularAreaBajoLaCurva(funcionNumericaY, limiteInferiorY, limiteSuperiorY, "y");

                    System.out.println("¿Deseas ver la gráfica de la función? (1. Sí, 2. No)");
                    int opcionGraficaY = scanner.nextInt();
                    if (opcionGraficaY == 1) {
                        logica.graficarFuncion(funcionNumericaY, limiteInferiorY, limiteSuperiorY, "y");
                    }
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


}
