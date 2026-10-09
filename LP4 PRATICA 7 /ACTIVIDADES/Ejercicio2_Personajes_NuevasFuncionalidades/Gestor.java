import java.io.*;
import java.util.*;

// El Gestor maneja la lista de personajes y el archivo de texto.
public class Gestor {
    private static final String ARCHIVO = "personajes.txt";
    private ArrayList<Personaje> personajes = new ArrayList<>();

    // Al crear el gestor, lee el archivo automaticamente
    public Gestor() {
        cargarDesdeArchivo(ARCHIVO, false);
    }

    // ---------- ARCHIVO ----------

    // Lee un archivo y agrega los personajes. Devuelve cuantos agrego.
    private int cargarDesdeArchivo(String ruta, boolean guardarDespues) {
        int agregados = 0;
        File f = new File(ruta);
        if (!f.exists()) {
            return 0; // si no existe, no hay nada que leer (se creara al guardar)
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(";");
                try {
                    Personaje nuevo = new Personaje(p[0], Integer.parseInt(p[1]), Integer.parseInt(p[2]),
                            Integer.parseInt(p[3]), Integer.parseInt(p[4]), Integer.parseInt(p[5]));
                    if (buscar(nuevo.getNombre()) == null) { // no repetir
                        personajes.add(nuevo);
                        agregados++;
                    }
                } catch (Exception e) {
                    System.out.println("Linea ignorada (formato incorrecto): " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        if (guardarDespues) guardar();
        return agregados;
    }

    // Escribe TODOS los personajes en el archivo (se llama sola tras cada cambio)
    private void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Personaje p : personajes) {
                pw.println(p.toLinea());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // ---------- OPERACIONES BASICAS ----------

    // Busca por nombre (sin importar mayusculas). Devuelve null si no existe.
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

    public boolean modificar(String nombre, int vida, int ataque, int defensa, int alcance) {
        Personaje p = buscar(nombre);
        if (p == null) return false;
        p.setVida(vida);
        p.setAtaque(ataque);
        p.setDefensa(defensa);
        p.setAlcance(alcance);
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

    // ---------- NUEVAS FUNCIONALIDADES ----------

    // Ordenar y mostrar segun un atributo (de mayor a menor)
    public void mostrarOrdenadoPor(String atributo) {
        ArrayList<Personaje> copia = new ArrayList<>(personajes);
        switch (atributo.toLowerCase()) {
            case "vida":    copia.sort((a, b) -> b.getVida() - a.getVida()); break;
            case "ataque":  copia.sort((a, b) -> b.getAtaque() - a.getAtaque()); break;
            case "defensa": copia.sort((a, b) -> b.getDefensa() - a.getDefensa()); break;
            case "alcance": copia.sort((a, b) -> b.getAlcance() - a.getAlcance()); break;
            default:
                System.out.println("Atributo no valido.");
                return;
        }
        for (Personaje p : copia) System.out.println(p);
    }

    // Crea personajes inventados con numeros al azar
    public void cargarAleatorios(int cantidad) {
        String[] nombres = {"Arthur", "Merlin", "Lancelot", "Morgana", "Gandalf", "Aragorn",
                            "Legolas", "Gimli", "Zelda", "Link", "Ganon", "Kratos"};
        Random r = new Random();
        int creados = 0;
        for (String n : nombres) {
            if (creados >= cantidad) break;
            Personaje p = new Personaje(n, r.nextInt(100) + 50, r.nextInt(20) + 5,
                    r.nextInt(20) + 5, r.nextInt(10) + 1, 1);
            if (anadir(p)) creados++;
        }
        System.out.println("Se crearon " + creados + " personajes aleatorios.");
    }

    // Cambiar SOLO un atributo
    public boolean actualizarAtributo(String nombre, String atributo, int valor) {
        Personaje p = buscar(nombre);
        if (p == null) return false;
        switch (atributo.toLowerCase()) {
            case "vida":    p.setVida(valor); break;
            case "ataque":  p.setAtaque(valor); break;
            case "defensa": p.setDefensa(valor); break;
            case "alcance": p.setAlcance(valor); break;
            default: return false;
        }
        guardar();
        return true;
    }

    public void mostrarEstadisticas() {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes para calcular estadisticas.");
            return;
        }
        double vida = 0, ataque = 0, defensa = 0, alcance = 0;
        for (Personaje p : personajes) {
            vida += p.getVida();
            ataque += p.getAtaque();
            defensa += p.getDefensa();
            alcance += p.getAlcance();
        }
        int n = personajes.size();
        System.out.println("Total de personajes: " + n);
        System.out.printf("Vida promedio: %.2f%n", vida / n);
        System.out.printf("Ataque promedio: %.2f%n", ataque / n);
        System.out.printf("Defensa promedio: %.2f%n", defensa / n);
        System.out.printf("Alcance promedio: %.2f%n", alcance / n);
    }

    // Importa desde otro archivo (mismo formato: nombre;vida;ataque;defensa;alcance;nivel)
    public int importar(String ruta) {
        return cargarDesdeArchivo(ruta, true);
    }

    public boolean subirNivel(String nombre) {
        Personaje p = buscar(nombre);
        if (p == null) return false;
        p.subirNivel();
        guardar();
        return true;
    }
}
