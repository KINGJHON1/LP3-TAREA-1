import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

// Actividad 2: try-with-resources, lee lineas por teclado y las agrega a datos.txt
public class AgregarDatos {
    public static void main(String[] args) {
        System.out.println("Escriba lineas de texto. Escriba FIN para terminar.");

        // Los recursos declarados en el try se cierran solos al terminar
        try (Scanner teclado = new Scanner(System.in);
             PrintWriter salida = new PrintWriter(new FileWriter("datos.txt", true))) {

            while (teclado.hasNextLine()) {
                String linea = teclado.nextLine();
                if (linea.equalsIgnoreCase("FIN")) {
                    break;
                }
                salida.println(linea);
            }
            System.out.println("Datos agregados al final de datos.txt");

        } catch (IOException e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
}
