
package ejercicio3;

import java.util.*;

public class EmpleadoVista {
    Scanner teclado = new Scanner(System.in);

    public int menu() {
        System.out.println("\n--- EMPLEADOS ---");
        System.out.println("1. Listar");
        System.out.println("2. Agregar");
        System.out.println("3. Buscar");
        System.out.println("4. Eliminar");
        System.out.println("5. Salir");
        System.out.print("Opcion: ");

        return Integer.parseInt(teclado.nextLine());
    }

    public Empleado ingresarEmpleado() {
        System.out.print("Numero: ");
        int numero = Integer.parseInt(teclado.nextLine());

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Sueldo: ");
        double sueldo = Double.parseDouble(teclado.nextLine());

        return new Empleado(numero, nombre, sueldo);
    }

    public int ingresarNumero() {
        System.out.print("Numero del empleado: ");
        return Integer.parseInt(teclado.nextLine());
    }

    public void mostrar(String mensaje) {
        System.out.println(mensaje);
    }
}
