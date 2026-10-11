
package ejercicio3;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        EmpleadoModelo modelo = new EmpleadoModelo();
        EmpleadoVista vista = new EmpleadoVista();

        boolean continuar = true;

        while (continuar) {
            try {
                int opcion = vista.menu();

                switch (opcion) {
                    case 1:
                        for (Empleado e : modelo.leer()) {
                            vista.mostrar(e.toString());
                        }
                        break;

                    case 2:
                        modelo.agregar(vista.ingresarEmpleado());
                        vista.mostrar("Empleado agregado.");
                        break;

                    case 3:
                        Empleado e = modelo.buscar(
                                vista.ingresarNumero());

                        if (e == null) {
                            vista.mostrar("No encontrado.");
                        } else {
                            vista.mostrar(e.toString());
                        }
                        break;

                    case 4:
                        if (modelo.eliminar(
                                vista.ingresarNumero())) {
                            vista.mostrar("Empleado eliminado.");
                        } else {
                            vista.mostrar("No encontrado.");
                        }
                        break;

                    case 5:
                        continuar = false;
                        vista.mostrar("Fin del programa.");
                        break;

                    default:
                        vista.mostrar("Opcion incorrecta.");
                }

            } catch (IOException ex) {
                vista.mostrar("Error al usar el archivo.");
            } catch (NumberFormatException ex) {
                vista.mostrar("Debes ingresar un numero valido.");
            } catch (IllegalArgumentException ex) {
                vista.mostrar(ex.getMessage());
            }
        }
    }
}
