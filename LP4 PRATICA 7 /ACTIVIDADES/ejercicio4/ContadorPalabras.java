
package ejercicio4;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;

public class ContadorPalabras {

    public static void main(String[] args) {

        JFileChooser selector = new JFileChooser();

        int opcion = selector.showOpenDialog(null);

        if (opcion != JFileChooser.APPROVE_OPTION) {
            System.out.println("No seleccionaste un archivo.");
            return;
        }

        File archivo = selector.getSelectedFile();

        int lineas = 0;
        int palabras = 0;
        int caracteres = 0;

        HashMap<String, Integer> frecuencia = new HashMap<>();

        try (BufferedReader lector =
                new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                lineas++;
                caracteres += linea.length();

                linea = linea + " ";

                String palabra = "";

                for (int i = 0; i < linea.length(); i++) {

                    char c = linea.charAt(i);

                    if (Character.isLetterOrDigit(c)) {
                        palabra = palabra + c;
                    } else {

                        if (!palabra.isEmpty()) {

                            palabra = palabra.toLowerCase();
                            palabras++;

                            if (frecuencia.containsKey(palabra)) {
                                frecuencia.put(palabra,
                                    frecuencia.get(palabra) + 1);
                            } else {
                                frecuencia.put(palabra, 1);
                            }

                            palabra = "";
                        }
                    }
                }
            }

            double promedio = 0;

            if (lineas > 0) {
                promedio = (double) palabras / lineas;
            }

            System.out.println("\n--- RESULTADOS ---");
            System.out.println("Archivo: " + archivo.getName());
            System.out.println("Total de lineas: " + lineas);
            System.out.println("Total de palabras: " + palabras);
            System.out.println("Total de caracteres: " + caracteres);

            System.out.printf(
                "Promedio de palabras por linea: %.2f%n",
                promedio
            );

            System.out.println("\nFrecuencia de cada palabra:");

            for (Map.Entry<String, Integer> dato :
                    frecuencia.entrySet()) {

                System.out.println(
                    dato.getKey() + ": " + dato.getValue()
                );
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }
    }
}


