
package ejercicio4;

import java.io.*;
import java.util.*;

public class ContadorPalabras {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        System.out.print("Escribe la ruta del archivo: ");
        String ruta = teclado.nextLine();

        File archivo = new File(ruta);

        int lineas = 0;
        int caracteres = 0;
        int palabras = 0;

        HashMap<String, Integer> frecuencia = new HashMap<>();

        try {
            BufferedReader lector = new BufferedReader(
                    new FileReader(archivo));

            String linea;

            while ((linea = lector.readLine()) != null) {

                lineas++;
                caracteres += linea.length();

                String[] lista = linea.split("\\s+");

                for (String palabra : lista) {

                    if (!palabra.isEmpty()) {

                        palabra = palabra.replaceAll(
                                "[^a-zA-ZáéíóúÁÉÍÓÚñÑüÜ0-9]", "");

                        if (!palabra.isEmpty()) {
                            palabras++;
                            palabra = palabra.toLowerCase();

                            if (frecuencia.containsKey(palabra)) {
                                frecuencia.put(palabra,
                                    frecuencia.get(palabra) + 1);
                            } else {
                                frecuencia.put(palabra, 1);
                            }
                        }
                    }
                }
            }

            lector.close();

            double promedio = 0;

            if (lineas > 0) {
                promedio = (double) palabras / lineas;
            }

            System.out.println("\n--- RESULTADOS ---");
            System.out.println("Lineas: " + lineas);
            System.out.println("Palabras: " + palabras);
            System.out.println("Caracteres: " + caracteres);
            System.out.printf("Promedio por linea: %.2f%n", promedio);

            System.out.println("\nFrecuencia de palabras:");

            for (String palabra : frecuencia.keySet()) {
                System.out.println(
                    palabra + ": " + frecuencia.get(palabra));
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }

        teclado.close();
    }
}

