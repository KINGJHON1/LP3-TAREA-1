import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;

public class ContadorPalabras {

    // Abre el JFileChooser hasta que el usuario elija un archivo valido
    // Devuelve null si el usuario cancela y decide salir
    static File elegirArchivo() {
        while (true) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Elige un archivo de texto");
            int resultado = chooser.showOpenDialog(null);

            if (resultado != JFileChooser.APPROVE_OPTION) {
                int r = JOptionPane.showConfirmDialog(null, "No elegiste archivo. Quieres salir?",
                        "Salir", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) return null;
                continue;
            }
            File f = chooser.getSelectedFile();
            if (f.exists() && f.isFile() && f.canRead()) {
                return f;
            }
            JOptionPane.showMessageDialog(null, "El archivo no existe o es invalido. Elige otro.");
        }
    }

    // Lee el archivo y muestra los resultados
    static void analizar(File archivo) throws IOException {
        int lineas = 0;
        int palabras = 0;
        int caracteres = 0;
        HashMap<String, Integer> frecuencias = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas++;
                caracteres += linea.length(); // readLine ya quita el salto de linea

                // Recorremos letra por letra para formar palabras
                StringBuilder palabra = new StringBuilder();
                for (int i = 0; i < linea.length(); i++) {
                    char c = linea.charAt(i);
                    if (Character.isLetterOrDigit(c)) {
                        palabra.append(c);               // sigue la palabra
                    } else if (palabra.length() > 0) {   // la palabra termino
                        palabras++;
                        String p = palabra.toString().toLowerCase();
                        frecuencias.put(p, frecuencias.getOrDefault(p, 0) + 1);
                        palabra = new StringBuilder();
                    }
                }
                if (palabra.length() > 0) {              // palabra al final de la linea
                    palabras++;
                    String p = palabra.toString().toLowerCase();
                    frecuencias.put(p, frecuencias.getOrDefault(p, 0) + 1);
                }
            }
        }

        System.out.println("Archivo: " + archivo.getName());
        System.out.println("Total de lineas: " + lineas);
        System.out.println("Total de palabras: " + palabras);
        System.out.println("Total de caracteres (sin saltos de linea): " + caracteres);
        double promedio = (lineas == 0) ? 0 : (double) palabras / lineas;
        System.out.printf("Promedio de palabras por linea: %.2f%n", promedio);

        // Ordenar las palabras de mayor a menor frecuencia
        ArrayList<Map.Entry<String, Integer>> lista = new ArrayList<>(frecuencias.entrySet());
        lista.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("Palabras mas frecuentes:");
        for (int i = 0; i < Math.min(5, lista.size()); i++) {
            System.out.println("  " + lista.get(i).getKey() + " -> " + lista.get(i).getValue());
        }
    }

    public static void main(String[] args) {
        File archivo = elegirArchivo();
        if (archivo == null) {
            System.out.println("Programa terminado.");
            return;
        }
        try {
            analizar(archivo);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
