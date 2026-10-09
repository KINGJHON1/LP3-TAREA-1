import java.io.*;
import java.util.ArrayList;

// Maneja la lista de personajes y el archivo de texto.
public class Gestor {
    private static final String ARCHIVO = "personajes.txt";
    private ArrayList<Personaje> personajes = new ArrayList<>();

    // Al crear el gestor, se lee el archivo (si existe)
    public Gestor() {
        File f = new File(ARCHIVO);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(";");
                try {
                    personajes.add(new Personaje(p[0], Integer.parseInt(p[1]), Integer.parseInt(p[2]),
                            Integer.parseInt(p[3]), Integer.parseInt(p[4])));
                } catch (Exception e) {
                    System.out.println("Linea ignorada: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Guarda todos los personajes en el archivo (se llama sola tras cada cambio)
    private void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Personaje p : personajes) pw.println(p.toLinea());
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    // Busca por nombre. Devuelve null si no existe.
    public Personaje buscar(String nombre) {
        for (Personaje p : personajes) {
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }

    public boolean anadir(Personaje nuevo) {
        if (buscar(nuevo.getNombre()) != null) return false; // ya existe
        personajes.add(nuevo);
        guardar();
        return true;
    }

    public void mostrar() {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes.");
            return;
        }
        for (Personaje p : personajes) System.out.println(p);
    }

    // Modificar: se reemplaza el personaje por uno nuevo con los mismos nombre
    public boolean modificar(String nombre, int vida, int ataque, int defensa, int alcance) {
        Personaje viejo = buscar(nombre);
        if (viejo == null) return false;
        int posicion = personajes.indexOf(viejo);
        personajes.set(posicion, new Personaje(viejo.getNombre(), vida, ataque, defensa, alcance));
        guardar();
        return true;
    }

    public boolean borrar(String nombre) {
        Personaje p = buscar(nombre);
        if (p == null) return false;
        personajes.remove(p);
        guardar();
        return true;
    }
}
