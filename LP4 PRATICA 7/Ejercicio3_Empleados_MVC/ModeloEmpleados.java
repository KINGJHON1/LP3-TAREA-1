import java.io.*;
import java.util.ArrayList;

// MODELO (parte 2): se encarga de leer y escribir el archivo
public class ModeloEmpleados {
    private static final String ARCHIVO = "empleados.txt";

    // Lee todos los empleados del archivo
    public ArrayList<Empleado> leerEmpleados() throws IOException {
        ArrayList<Empleado> lista = new ArrayList<>();
        File f = new File(ARCHIVO);
        if (!f.exists()) {
            return lista; // si no existe, la lista queda vacia
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(";");
                try {
                    lista.add(new Empleado(Integer.parseInt(partes[0]), partes[1], Double.parseDouble(partes[2])));
                } catch (Exception e) {
                    // linea danada: se ignora
                }
            }
        }
        return lista;
    }

    // Agrega un empleado al final del archivo. Devuelve false si el numero ya existe.
    public boolean agregarEmpleado(Empleado e) throws IOException {
        if (buscarEmpleado(e.getNumero()) != null) return false;
        // true = agregar al final sin borrar lo anterior (y crea el archivo si no existe)
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            pw.println(e.getNumero() + ";" + e.getNombre() + ";" + e.getSueldo());
        }
        return true;
    }

    // Busca por numero. Devuelve null si no lo encuentra.
    public Empleado buscarEmpleado(int numero) throws IOException {
        for (Empleado e : leerEmpleados()) {
            if (e.getNumero() == numero) return e;
        }
        return null;
    }

    // Elimina por numero: lee todos, quita el que sea, y reescribe el archivo
    public boolean eliminarEmpleado(int numero) throws IOException {
        ArrayList<Empleado> lista = leerEmpleados();
        Empleado encontrado = null;
        for (Empleado e : lista) {
            if (e.getNumero() == numero) encontrado = e;
        }
        if (encontrado == null) return false;
        lista.remove(encontrado);
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Empleado e : lista) {
                pw.println(e.getNumero() + ";" + e.getNombre() + ";" + e.getSueldo());
            }
        }
        return true;
    }
}
