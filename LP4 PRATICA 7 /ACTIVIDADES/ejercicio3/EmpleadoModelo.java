
package ejercicio3;

import java.io.*;
import java.util.*;

public class EmpleadoModelo {
    String archivo = "empleados.txt";

    public ArrayList<Empleado> leer() throws IOException {
        ArrayList<Empleado> lista = new ArrayList<>();
        File f = new File(archivo);

        if (!f.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(
                new FileReader(f))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                lista.add(new Empleado(
                    Integer.parseInt(datos[0]),
                    datos[1],
                    Double.parseDouble(datos[2])
                ));
            }
        }

        return lista;
    }

    public void guardar(ArrayList<Empleado> lista)
            throws IOException {
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(archivo))) {

            for (Empleado e : lista) {
                pw.println(e.numero + ";" + e.nombre
                        + ";" + e.sueldo);
            }
        }
    }

    public void agregar(Empleado nuevo) throws IOException {
        ArrayList<Empleado> lista = leer();

        for (Empleado e : lista) {
            if (e.numero == nuevo.numero) {
                throw new IllegalArgumentException(
                        "Ese número ya existe.");
            }
        }

        lista.add(nuevo);
        guardar(lista);
    }

    public Empleado buscar(int numero) throws IOException {
        for (Empleado e : leer()) {
            if (e.numero == numero) {
                return e;
            }
        }

        return null;
    }

    public boolean eliminar(int numero) throws IOException {
        ArrayList<Empleado> lista = leer();

        boolean eliminado = lista.removeIf(
                e -> e.numero == numero);

        if (eliminado) {
            guardar(lista);
        }

        return eliminado;
    }
}
