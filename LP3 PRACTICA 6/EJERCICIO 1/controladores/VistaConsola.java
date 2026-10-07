package vista;

import modelo.Compra;
import modelo.ItemCarrito;
import modelo.Producto;
import modelo.Tienda;

import java.util.List;
import java.util.Scanner;

public class VistaConsola {
    private final Scanner in = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("\n1. Agregar producto     2. Listar productos");
        System.out.println("3. Agregar al carrito   4. Ver carrito");
        System.out.println("5. Quitar del carrito   6. Aplicar descuento");
        System.out.println("7. Calcular envío       8. Ver historial");
        System.out.println("9. Comprar              0. Salir");
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje + ": ");
        return in.nextLine().trim();
    }

    public int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                mostrar("Escribe un número entero.");
            }
        }
    }

    public double leerDecimal(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(mensaje).replace(',', '.'));
            } catch (NumberFormatException e) {
                mostrar("Escribe un número (ejemplo: 25.90).");
            }
        }
    }

    public void mostrar(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarProductos(List<Producto> productos) {
        if (productos.isEmpty()) {
            mostrar("No hay productos.");
            return;
        }
        System.out.printf("%n%-4s %-25s %10s %6s%n", "ID", "Producto", "Precio", "Stock");
        for (Producto p : productos) {
            System.out.printf("%-4d %-25s %10.2f %6d%n", p.id, p.nombre, p.precio, p.stock);
        }
    }

    public void mostrarCarrito(Tienda t) {
        if (t.carrito.isEmpty()) {
            mostrar("El carrito está vacío.");
            return;
        }
        System.out.printf("%n%-4s %-25s %10s %5s %10s%n", "ID", "Producto", "Precio", "Cant", "Subtotal");
        for (ItemCarrito i : t.carrito) {
            System.out.printf("%-4d %-25s %10.2f %5d %10.2f%n",
                    i.producto.id, i.producto.nombre, i.producto.precio, i.cantidad, i.subtotal());
        }
        System.out.printf("Subtotal: %.2f | Descuento: -%.2f | Envío: %.2f | TOTAL: %.2f%n",
                t.subtotal(), t.descuento(), t.envio(), t.total());
    }

    public void mostrarHistorial(List<Compra> compras) {
        if (compras.isEmpty()) {
            mostrar("Aún no hay compras.");
            return;
        }
        for (Compra c : compras) {
            System.out.printf("%nCompra #%d (%s)%n", c.id, c.fecha.toLocalDate());
            for (ItemCarrito i : c.items) {
                System.out.printf("  %d x %s = %.2f%n", i.cantidad, i.producto.nombre, i.subtotal());
            }
            System.out.printf("  Descuento: -%.2f | Envío: %.2f | TOTAL: %.2f%n", c.descuento, c.envio, c.total);
        }
    }
}
