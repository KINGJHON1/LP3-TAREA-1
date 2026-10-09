import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

// Actividad 3: guarda una matriz de double en un archivo.
// Primero se escriben dos enteros (filas y columnas) y luego los elementos.
public class MatrizArchivo {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Numero de filas: ");
        int filas = teclado.nextInt();
        System.out.print("Numero de columnas: ");
        int columnas = teclado.nextInt();

        double[][] matriz = new double[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.printf("Elemento [%d][%d]: ", i, j);
                matriz[i][j] = teclado.nextDouble();
            }
        }

        // Escritura
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream("matriz.dat"))) {
            salida.writeInt(filas);
            salida.writeInt(columnas);
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    salida.writeDouble(matriz[i][j]);
                }
            }
            System.out.println("\nMatriz guardada en matriz.dat");
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
            return;
        }

        // Lectura para comprobar que se guardo bien
        try (DataInputStream entrada = new DataInputStream(new FileInputStream("matriz.dat"))) {
            int f = entrada.readInt();
            int c = entrada.readInt();
            System.out.println("\nContenido leido del archivo (" + f + " x " + c + "):");
            for (int i = 0; i < f; i++) {
                for (int j = 0; j < c; j++) {
                    System.out.print(entrada.readDouble() + "\t");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }
}
