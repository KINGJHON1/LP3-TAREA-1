package controlador;

import modelo.Carrito;
import modelo.Compra;
import modelo.Descuento;
import modelo.Producto;
import modelo.Tienda;
import modelo.ZonaEnvio;
import vista.VistaConsola;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

/** Controlador: recibe las acciones del usuario, usa el modelo y le pide a la vista que muestre el resultado. */
public class CarritoController {
    private final Tienda tienda;
    private final VistaConsola vista;

    public CarritoController(Tienda tienda, VistaConsola vista) {
        this.tienda = tienda;
        this.vista = vista;
    }

    public void iniciar() {
        vista.mostrarBienvenida();
        boolean activo = true;
        while (activo) {
            try {
                vista.mostrarMenu();
                int opcion = vista.leerEntero("Elige una opción");
                switch (opcion) {
                    case 1 -> agregarProductoAlCatalogo();
                    case 2 -> listarProductos();
                    case 3 -> agregarAlCarrito();
                    case 4 -> verCarrito();
                    case 5 -> eliminarDelCarrito();
                    case 6 -> aplicarDescuento();
                    case 7 -> calcularEnvio();
                    case 8 -> verHistorial();
                    case 9 -> realizarCompra();
                    case 0 -> activo = false;
                    default -> vista.mostrarError("Opción no válida.");
                }
            } catch (NoSuchElementException e) {
                activo = false; // se cerró la entrada estándar
            } catch (IllegalArgumentException | IllegalStateException e) {
                vista.mostrarError(e.getMessage());
            }
        }
        vista.mostrarMensaje("\n¡Gracias por visitarnos!");
    }

    private void agregarProductoAlCatalogo() {
        String nombre = vista.leerTexto("Nombre del producto");
        BigDecimal precio = vista.leerDecimal("Precio (S/)");
        int stock = vista.leerEntero("Stock inicial");
        Producto p = tienda.getInventario().agregar(nombre, precio, stock);
        vista.mostrarMensaje("Producto agregado con ID " + p.getId() + ".");
    }

    private void listarProductos() {
        vista.mostrarProductos(tienda.getInventario().listar());
    }

    private void agregarAlCarrito() {
        vista.mostrarProductos(tienda.getInventario().listar());
        if (tienda.getInventario().listar().isEmpty()) {
            return;
        }
        int id = vista.leerEntero("ID del producto");
        Producto producto = tienda.getInventario().buscar(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un producto con ID " + id + "."));
        int cantidad = vista.leerEntero("Cantidad");
        tienda.getCarrito().agregar(producto, cantidad);
        vista.mostrarMensaje(cantidad + " x " + producto.getNombre() + " agregado(s) al carrito.");
    }

    private void verCarrito() {
        vista.mostrarCarrito(tienda.getCarrito());
    }

    private void eliminarDelCarrito() {
        Carrito carrito = tienda.getCarrito();
        if (carrito.estaVacio()) {
            vista.mostrarMensaje("El carrito está vacío.");
            return;
        }
        vista.mostrarCarrito(carrito);
        int id = vista.leerEntero("ID del producto a eliminar");
        carrito.eliminar(id);
        vista.mostrarMensaje("Producto eliminado del carrito.");
    }

    private void aplicarDescuento() {
        Carrito carrito = tienda.getCarrito();
        if (carrito.estaVacio()) {
            throw new IllegalStateException("Agrega productos al carrito antes de aplicar un descuento.");
        }
        vista.mostrarCupones(tienda.getDescuentos().listar());
        String codigo = vista.leerTexto("Código de cupón");
        Descuento d = tienda.getDescuentos().buscar(codigo)
                .orElseThrow(() -> new IllegalArgumentException("El cupón \"" + codigo + "\" no existe."));
        if (!d.aplicaA(carrito.subtotal())) {
            throw new IllegalStateException("El cupón " + d.getCodigo() + " requiere una compra mínima de "
                    + VistaConsola.dinero(d.getMontoMinimo()) + ".");
        }
        carrito.setDescuento(d);
        vista.mostrarMensaje("Cupón " + d.getCodigo() + " aplicado: ahorras "
                + VistaConsola.dinero(carrito.montoDescuento()) + ".");
    }

    private void calcularEnvio() {
        Carrito carrito = tienda.getCarrito();
        if (carrito.estaVacio()) {
            throw new IllegalStateException("Agrega productos al carrito para calcular el envío.");
        }
        ZonaEnvio zona = vista.pedirZona();
        carrito.setZona(zona);
        BigDecimal envio = carrito.costoEnvio();
        vista.mostrarMensaje(envio.signum() == 0
                ? "¡Envío GRATIS a " + zona.getNombre() + "!"
                : "Envío a " + zona.getNombre() + ": " + VistaConsola.dinero(envio));
    }

    private void verHistorial() {
        vista.mostrarHistorial(tienda.getHistorial().listar());
    }

    private void realizarCompra() {
        Carrito carrito = tienda.getCarrito();
        if (carrito.estaVacio()) {
            throw new IllegalStateException("El carrito está vacío.");
        }
        if (carrito.getZona() == null) {
            carrito.setZona(vista.pedirZona());
        }
        vista.mostrarCarrito(carrito);
        if (!vista.confirmar("¿Confirmas la compra?")) {
            vista.mostrarMensaje("Compra cancelada.");
            return;
        }
        Compra compra = tienda.finalizarCompra();
        vista.mostrarComprobante(compra);
    }
}
