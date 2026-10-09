import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    // Pide un numero entero mayor que cero, y repite hasta que sea valido
    static int pedirEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n > 0) return n;
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
            System.out.println("5. Mostrar ordenados por atributo");
            System.out.println("6. Cargar personajes aleatorios");
            System.out.println("7. Actualizar UN atributo");
            System.out.println("8. Mostrar estadisticas");
            System.out.println("9. Importar desde archivo");
            System.out.println("10. Subir de nivel");
            System.out.println("0. Salir");
            opcion = pedirEntero0("Elige una opcion: ");

            switch (opcion) {
                case 1:
                    String nombre = pedirTexto("Nombre: ");
                    int v = pedirEntero("Vida: ");
                    int a = pedirEntero("Ataque: ");
                    int d = pedirEntero("Defensa: ");
                    int al = pedirEntero("Alcance: ");
                    if (gestor.anadir(new Personaje(nombre, v, a, d, al, 1)))
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
                        gestor.modificar(n3, pedirEntero("Nueva vida: "), pedirEntero("Nuevo ataque: "),
                                pedirEntero("Nueva defensa: "), pedirEntero("Nuevo alcance: "));
                        System.out.println("Personaje modificado.");
                    }
                    break;
                case 4:
                    System.out.println(gestor.borrar(pedirTexto("Nombre a borrar: "))
                            ? "Personaje borrado." : "No existe ese personaje.");
                    break;
                case 5:
                    gestor.mostrarOrdenadoPor(pedirTexto("Atributo (vida/ataque/defensa/alcance): "));
                    break;
                case 6:
                    gestor.cargarAleatorios(pedirEntero("Cuantos personajes (max 12): "));
                    break;
                case 7:
                    String n7 = pedirTexto("Nombre del personaje: ");
                    String atr = pedirTexto("Atributo (vida/ataque/defensa/alcance): ");
                    int valor = pedirEntero("Nuevo valor: ");
                    System.out.println(gestor.actualizarAtributo(n7, atr, valor)
                            ? "Atributo actualizado." : "Personaje o atributo no valido.");
                    break;
                case 8:
                    gestor.mostrarEstadisticas();
                    break;
                case 9:
                    int cant = gestor.importar(pedirTexto("Ruta del archivo (ej: importar.txt): "));
                    System.out.println("Personajes importados: " + cant);
                    break;
                case 10:
                    System.out.println(gestor.subirNivel(pedirTexto("Nombre: "))
                            ? "El personaje subio de nivel." : "No existe ese personaje.");
                    break;
                case 0:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    // Igual que pedirEntero pero permite el 0 (para el menu)
    static int pedirEntero0(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un numero.");
            }
        }
    }
}
