import java.io.IOException;

// CONTROLADOR: conecta la Vista con el Modelo
public class Controlador {
    private ModeloEmpleados modelo = new ModeloEmpleados();
    private Vista vista = new Vista();

    public void iniciar() {
        // Al iniciar, se leen y se muestran los empleados guardados
        vista.mostrarMensaje("Empleados guardados actualmente:");
        try {
            vista.mostrarLista(modelo.leerEmpleados());
        } catch (IOException e) {
            vista.mostrarMensaje("Error al leer el archivo: " + e.getMessage());
        }

        int opcion;
        do {
            opcion = vista.mostrarMenu();
            try {
                switch (opcion) {
                    case 1:
                        vista.mostrarLista(modelo.leerEmpleados());
                        break;
                    case 2:
                        int numero = vista.pedirEntero("Numero: ");
                        String nombre = vista.pedirTexto("Nombre: ");
                        double sueldo = vista.pedirDecimal("Sueldo: ");
                        if (modelo.agregarEmpleado(new Empleado(numero, nombre, sueldo)))
                            vista.mostrarMensaje("OK: empleado agregado.");
                        else
                            vista.mostrarMensaje("ERROR: ya existe un empleado con ese numero.");
                        break;
                    case 3:
                        Empleado e = modelo.buscarEmpleado(vista.pedirEntero("Numero a buscar: "));
                        if (e != null) vista.mostrarEmpleado(e);
                        else vista.mostrarMensaje("ERROR: no se encontro ese empleado.");
                        break;
                    case 4:
                        if (modelo.eliminarEmpleado(vista.pedirEntero("Numero a eliminar: ")))
                            vista.mostrarMensaje("OK: empleado eliminado.");
                        else
                            vista.mostrarMensaje("ERROR: no se encontro ese empleado.");
                        break;
                    case 5:
                        vista.mostrarMensaje("Adios.");
                        break;
                    default:
                        vista.mostrarMensaje("Opcion no valida.");
                }
            } catch (IOException ex) {
                vista.mostrarMensaje("ERROR de archivo: " + ex.getMessage());
            }
        } while (opcion != 5);
    }
}
