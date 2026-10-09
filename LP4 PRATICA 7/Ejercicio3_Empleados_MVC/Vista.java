import java.util.ArrayList;
import java.util.Scanner;

// VISTA: solo muestra cosas y pide datos al usuario. No toca archivos.
public class Vista {
    private Scanner sc = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n===== EMPLEADOS =====");
        System.out.println("1. Listar todos los empleados");
        System.out.println("2. Agregar un nuevo empleado");
        System.out.println("3. Buscar un empleado por numero");
        System.out.println("4. Eliminar un empleado por numero");
        System.out.println("5. Salir");
        return pedirEntero("Elige una opcion: ");
    }

    public int pedirEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debes escribir un numero entero.");
            }
        }
    }

    public double pedirDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debes escribir un numero (ej: 1500.50).");
            }
        }
    }

    public String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    public void mostrarLista(ArrayList<Empleado> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay empleados registrados.");
        } else {
            for (Empleado e : lista) System.out.println(e);
        }
    }

    public void mostrarEmpleado(Empleado e) {
        System.out.println(e);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
