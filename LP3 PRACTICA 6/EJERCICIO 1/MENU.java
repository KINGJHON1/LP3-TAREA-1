import controlador.CarritoController;
import modelo.Tienda;
import vista.VistaConsola;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Tienda tienda = new Tienda();
        cargarProductosDeEjemplo(tienda);

        VistaConsola vista = new VistaConsola();
        CarritoController controlador = new CarritoController(tienda, vista);
        controlador.iniciar();
    }

    private static void cargarProductosDeEjemplo(Tienda tienda) {
        tienda.getInventario().agregar("Laptop 14\"", new BigDecimal("2499.00"), 5);
        tienda.getInventario().agregar("Mouse inalámbrico", new BigDecimal("45.90"), 30);
        tienda.getInventario().agregar("Teclado mecánico", new BigDecimal("189.50"), 12);
        tienda.getInventario().agregar("Audífonos Bluetooth", new BigDecimal("129.00"), 20);
        tienda.getInventario().agregar("Cable USB-C", new BigDecimal("19.90"), 50);
    }
}
