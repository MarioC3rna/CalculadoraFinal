package umg.dem1.Metodos.VolumenSolido;

public class UserVolumenSolido {


    public UserVolumenSolido() {

        System.out.println("Seleccione el tipo de sólido que desea calcular: ");
        System.out.println("1. Discos");
        System.out.println("2. Arandelas");
        System.out.println("3. Cascarones\n");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                Discos discos = new Discos();
                discos.funcionamiento();
                break;
            case 2:
                Arandelas arandelas = new Arandelas();
                arandelas.funcionamiento();
                break;
            case 3:
                Cascarones cascarones = new Cascarones();
                cascarones.funcionamiento();
                return;

            default:
                System.out.println("Opción no válida.");
                break;
        }


    }

}
