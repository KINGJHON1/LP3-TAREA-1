package modelo;

public class Producto {
    public final int id;
    public final String nombre;
    public final double precio;
    public int stock;

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
}
