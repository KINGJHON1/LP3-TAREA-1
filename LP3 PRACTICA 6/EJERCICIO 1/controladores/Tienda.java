package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Todo el modelo: productos, carrito, descuento, envío e historial. */
public class Tienda {
    private static final double ENVIO = 10.0;
    private static final double ENVIO_GRATIS_DESDE = 200.0;
    private static final Map<String, Integer> CUPONES = Map.of("DESC10", 10, "DESC20", 20);

    public final List<Producto> productos = new ArrayList<>();
    public final List<ItemCarrito> carrito = new ArrayList<>();
    public final List<Compra> historial = new ArrayList<>();
    private int porcentajeDescuento = 0;

    public void agregarProducto(String nombre, double precio, int stock) {
        if (nombre.isBlank() || precio <= 0 || stock < 0) {
            throw new IllegalArgumentException("Datos de producto no válidos.");
        }
        productos.add(new Producto(productos.size() + 1, nombre, precio, stock));
    }

    public void agregarAlCarrito(int idProducto, int cantidad) {
        Producto p = buscarProducto(idProducto);
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que 0.");
        ItemCarrito item = buscarItem(idProducto);
        int total = cantidad + (item == null ? 0 : item.cantidad);
        if (total > p.stock) throw new IllegalArgumentException("Stock insuficiente (hay " + p.stock + ").");
        if (item == null) carrito.add(new ItemCarrito(p, cantidad));
        else item.cantidad = total;
    }

    public void eliminarDelCarrito(int idProducto) {
        ItemCarrito item = buscarItem(idProducto);
        if (item == null) throw new IllegalArgumentException("Ese producto no está en el carrito.");
        carrito.remove(item);
    }

    public void aplicarDescuento(String codigo) {
        Integer pct = CUPONES.get(codigo.trim().toUpperCase());
        if (pct == null) throw new IllegalArgumentException("Cupón no válido.");
        porcentajeDescuento = pct;
    }

    public double subtotal() {
        return carrito.stream().mapToDouble(ItemCarrito::subtotal).sum();
    }

    public double descuento() {
        return subtotal() * porcentajeDescuento / 100;
    }

    public double envio() {
        if (carrito.isEmpty()) return 0;
        return subtotal() - descuento() >= ENVIO_GRATIS_DESDE ? 0 : ENVIO;
    }

    public double total() {
        return subtotal() - descuento() + envio();
    }

    public Compra comprar() {
        if (carrito.isEmpty()) throw new IllegalArgumentException("El carrito está vacío.");
        for (ItemCarrito i : carrito) i.producto.stock -= i.cantidad;
        Compra c = new Compra(historial.size() + 1, new ArrayList<>(carrito), descuento(), envio(), total());
        historial.add(c);
        carrito.clear();
        porcentajeDescuento = 0;
        return c;
    }

    private Producto buscarProducto(int id) {
        return productos.stream().filter(p -> p.id == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No existe el producto " + id + "."));
    }

    private ItemCarrito buscarItem(int idProducto) {
        return carrito.stream().filter(i -> i.producto.id == idProducto).findFirst().orElse(null);
    }
}
