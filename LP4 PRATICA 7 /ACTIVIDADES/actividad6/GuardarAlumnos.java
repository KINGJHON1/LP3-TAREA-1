import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// Actividad 6: escribe tres objetos Alumno en un archivo (serializacion) y luego los lee
public class GuardarAlumnos {
    public static void main(String[] args) {
        Alumno[] alumnos = {
            new Alumno("Luis", "Garcia", "A001", new Fecha(15, 3, 2004)),
            new Alumno("Rosa", "Mendoza", "A002", new Fecha(2, 11, 2003)),
            new Alumno("Diego", "Quispe", "A003", new Fecha(27, 7, 2005))
        };

        // Escritura de objetos
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("alumnos.dat"))) {
            for (Alumno a : alumnos) {
                salida.writeObject(a);
            }
            System.out.println("Se guardaron " + alumnos.length + " alumnos en alumnos.dat\n");
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
            return;
        }

        // Lectura de objetos
        System.out.println("Alumnos leidos del archivo:");
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("alumnos.dat"))) {
            while (true) {
                Alumno a = (Alumno) entrada.readObject();
                System.out.println(a);
            }
        } catch (EOFException e) {
            // Se llego al final del archivo: es normal
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }
}
