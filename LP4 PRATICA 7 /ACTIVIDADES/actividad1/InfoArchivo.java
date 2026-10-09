import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

// Actividad 1: Obtener informacion de archivos y directorios
public class InfoArchivo {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("Introduzca el nombre de un archivo o directorio:");
        Path ruta = Paths.get(entrada.nextLine());

        if (Files.exists(ruta)) {
            // getFileName: nombre del archivo o directorio, sin la ubicacion
            System.out.printf("%n%s existe%n", ruta.getFileName());
            // isDirectory: indica si la ruta es un directorio
            System.out.printf("%s un directorio%n", Files.isDirectory(ruta) ? "Es" : "No es");
            // isAbsolute: indica si la ruta es absoluta
            System.out.printf("%s una ruta absoluta%n", ruta.isAbsolute() ? "Es" : "No es");

            try {
                // getLastModifiedTime: ultima vez que se modifico
                System.out.printf("Ultima modificacion: %s%n", Files.getLastModifiedTime(ruta));
                // size: numero de bytes
                System.out.printf("Tamano (bytes): %s%n", Files.size(ruta));
                System.out.printf("Ruta: %s%n", ruta);
                // toAbsolutePath: convierte a ruta absoluta
                System.out.printf("Ruta absoluta: %s%n", ruta.toAbsolutePath());

                // Si es un directorio, se muestra su contenido
                if (Files.isDirectory(ruta)) {
                    System.out.printf("%nContenido del directorio:%n");
                    try (DirectoryStream<Path> flujo = Files.newDirectoryStream(ruta)) {
                        for (Path p : flujo) {
                            System.out.println(p);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error de E/S: " + e.getMessage());
            }
        } else {
            System.out.printf("%s no existe%n", ruta);
        }
    }
}
