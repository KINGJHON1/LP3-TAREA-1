import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// Actividad 5: carga agenda.txt en un arreglo de Persona y busca contactos por nombre.
// Formato de cada linea de agenda.txt:  nombre,telefono,direccion
public class Agenda {
    public static void main(String[] args) {
        Persona[] agenda = cargarAgenda("agenda.txt");
        if (agenda == null) {
            return;
        }
        System.out.println("Se cargaron " + agenda.length + " contactos.");

        Scanner teclado = new Scanner(System.in);
        while (true) {
            System.out.print("\nIngrese un nombre a buscar (o SALIR): ");
            if (!teclado.hasNextLine()) {
                break;
            }
            String buscado = teclado.nextLine().trim();
            if (buscado.equalsIgnoreCase("SALIR")) {
                break;
            }

            boolean encontrado = false;
            for (Persona p : agenda) {
                if (p.getNombre().equalsIgnoreCase(buscado)) {
                    System.out.println("\nContacto encontrado:\n" + p);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("El contacto no existe en la agenda.");
            }
        }
    }

    private static Persona[] cargarAgenda(String archivo) {
        try {
            // Primera pasada: contar lineas para saber el tamano del arreglo
            int total = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                while (br.readLine() != null) {
                    total++;
                }
            }

            // Segunda pasada: llenar el arreglo
            Persona[] personas = new Persona[total];
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                for (int i = 0; i < total; i++) {
                    String[] datos = br.readLine().split(",");
                    personas[i] = new Persona(datos[0].trim(), datos[1].trim(), datos[2].trim());
                }
            }
            return personas;

        } catch (IOException e) {
            System.out.println("No se pudo leer " + archivo + ": " + e.getMessage());
            return null;
        }
    }
}
