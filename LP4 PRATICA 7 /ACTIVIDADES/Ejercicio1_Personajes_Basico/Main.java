import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    // Pide un entero; si permiteCero es false, exige que sea mayor que cero
    static int pedirEntero(String mensaje, boolean permiteCero) {
        while (true) {
            System.out.print(mensaje);
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n > 0 || (permiteCero && n == 0)) return n;
                System.out.println("Debe ser mayor que cero.");
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un numero entero.");
            }
        }
    }

    static String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    public static void main(String[] args) {
        Gestor gestor = new Gestor();
        int opcion;
        do {
            System.out.println("\n===== GESTOR DE PERSONAJES =====");
            System.out.println("1. Anadir personaje");
            System.out.println("2. Mostrar personajes");
            System.out.println("3. Modificar personaje");
            System.out.println("4. Borrar personaje");
            System.out.println("0. Salir");
            opcion = pedirEntero("Elige una opcion: ", true);

            switch (opcion) {
                case 1:
                    String nombre = pedirTexto("Nombre: ");
                    int v = pedirEntero("Vida: ", false);
                    int a = pedirEntero("Ataque: ", false);
                    int d = pedirEntero("Defensa: ", false);
                    int al = pedirEntero("Alcance: ", false);
                    if (gestor.anadir(new Personaje(nombre, v, a, d, al)))
                        System.out.println("Personaje anadido.");
                    else
                        System.out.println("Ese personaje ya existe, no se creo.");
                    break;
                case 2:
                    gestor.mostrar();
                    break;
                case 3:
                    String n3 = pedirTexto("Nombre del personaje a modificar: ");
                    if (gestor.buscar(n3) == null) {
                        System.out.println("No existe ese personaje.");
                    } else {
                        gestor.modificar(n3, pedirEntero("Nueva vida: ", false), pedirEntero("Nuevo ataque: ", false),
                                pedirEntero("Nueva defensa: ", false), pedirEntero("Nuevo alcance: ", false));
                        System.out.println("Personaje modificado.");
                    }
                    break;
                case 4:
                    System.out.println(gestor.borrar(pedirTexto("Nombre a borrar: "))
                            ? "Personaje borrado." : "No existe ese personaje.");
                    break;
                case 0:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }
}
