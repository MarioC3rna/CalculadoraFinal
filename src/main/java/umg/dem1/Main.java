package umg.dem1;

import umg.dem1.Metodos.AreaBajoCurva.UserAreaBajoCurva;
import umg.dem1.Metodos.CentroidesyCentroides.LogicaCentroides;
import umg.dem1.Metodos.Definidas.UserDefinidas;
import umg.dem1.Metodos.Impropias.UserImpropias;
import umg.dem1.Metodos.IntegralDerivadasParciales.LogicaDerivadasParciales;
import umg.dem1.Metodos.IntegralValorMedio.LogicaValorMedio;
import umg.dem1.Metodos.IntegralporPartes.UserPorPartes;
import umg.dem1.Metodos.Sustitucion.UserSustitucion;
import umg.dem1.Metodos.Trigonometricas.UserTrigonometrica;
import umg.dem1.Metodos.VolumenSolido.UserVolumenSolido;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.println("Seleccione el método que desea usar para resolver la integrales :");
        System.out.println("1. Metodos de Area bajo la curva");
        System.out.println("2. Metodo de Integrales Definidas");
        System.out.println("3. Metodo de Integrales Impropias");
        System.out.println("4. Metodo de Integrales por Partes");
        System.out.println("5. Metodo de Sustitución");
        System.out.println("6. Metodo de Trigonometricas");
        System.out.println("7. Metodo de Volumen de Solidos");
        System.out.print("8. Metodo de Centroides\n");
        System.out.println("9. Metodo de Derivadas Parciales");
        System.out.print("10. Metodo de Integral de Valor Medio\n");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                UserAreaBajoCurva userAreaBajoCurva = new UserAreaBajoCurva();
                break;
            case 2:
                UserDefinidas userDefinidas = new UserDefinidas();
                break;
            case 3:
                UserImpropias userImpropias = new UserImpropias();
                break;
            case 4:
                UserPorPartes userPorPartes = new UserPorPartes();
                break;
            case 5:
                UserSustitucion userSustitucion = new UserSustitucion();
                break;
            case 6:
                UserTrigonometrica userTrigonometrica = new UserTrigonometrica();
                break;
            case 7:
                UserVolumenSolido userVolumenSolido = new UserVolumenSolido();
                break;
            case 8:
               LogicaCentroides.Centroides(scanner);
                break;
            case 9:
                LogicaDerivadasParciales.Parciales(scanner);
                break;
            case 10:
                LogicaValorMedio.ValorMedio(scanner);
                break;

            default:
                System.out.println("Opción no válida");
                break;
        }
    }

}