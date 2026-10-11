
package ejercicio3;

public class Empleado {
    int numero;
    String nombre;
    double sueldo;

    public Empleado(int numero, String nombre, double sueldo) {
        this.numero = numero;
        this.nombre = nombre;
        this.sueldo = sueldo;
    }

    public int getNumero() {
        return numero;
    }

    public String toString() {
        return numero + " - " + nombre + " - S/ " + sueldo;
    }
}
