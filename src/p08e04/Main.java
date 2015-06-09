
package p08e04;
import java.util.Scanner;

/**
 *
 * @author Astorga Dario - Coria Gaston - Tissera Adrian
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opc;
        do {
            System.out.println("\nSimulación del modelo de Lotka-Volterra\n");
            System.out.println("\nElija una opción\n");
            System.out.println("0) Salir");
            System.out.println("1) Simulación personalizada");
            System.out.println("2) Simulación por defecto (r = 3, a = 2, b = 1, m = 2. Condiciones iniciales: p(0) = d(0) = 2 )");
            opc = sc.nextInt();
            switch (opc) {
                case 1:
                    System.out.println("Eligió 1");
                    Util.setInitialState();
                    Util.lotkaVolterraSimulation();
                    break;
                case 2:
                    System.out.println("Eligió 2");
                    Util.setDefaults();
                    Util.lotkaVolterraSimulation();
                    break;
            }
        } while (opc != 0);
    }
}
