package modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Carrito {
    private final Map<Integer, ItemCarrito> items = new LinkedHashMap<>();
    private Descuento descuento;
    private ZonaEnvio zona;

    public void agregar(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        ItemCarrito existente = items.get(producto.getId());
        int totalSolicitado = cantidad + (existente == null ? 0 : existente.getCantidad());
        if (totalSolicitado > producto.getStock()) {
            throw new IllegalStateException("Stock insuficiente: solo hay " + producto.getStock()
                    + " unidad(es) de \"" + producto.getNombre() + "\".");
        }
        if (existente == null) {
            items.put(producto.getId(), new ItemCarrito(producto, cantidad));
        } else {
            existente.sumar(cantidad);
        }
    }

    public void eliminar(int idProducto) {
        if (items.remove(idProducto) == null) {
            throw new IllegalArgumentException("Ese producto no está en el carrito.");
        }
    }

    public void vaciar() {
        items.clear();
        descuento = null;
        zona = null;
    }

    public boolean estaVacio() { return items.isEmpty(); }

    public List<ItemCarrito> getItems() { return new ArrayList<>(items.values()); }

    public Descuento getDescuento() { return descuento; }
    public void setDescuento(Descuento descuento) { this.descuento = descuento; }

    public ZonaEnvio getZona() { return zona; }
    public void setZona(ZonaEnvio zona) { this.zona = zona; }

    public BigDecimal subtotal() {
        BigDecimal suma = BigDecimal.ZERO;
        for (ItemCarrito item : items.values()) {
            suma = suma.add(item.subtotal());
        }
        return suma;
    }

    public BigDecimal montoDescuento() {
        return descuento == null ? BigDecimal.ZERO : descuento.calcular(subtotal());
    }

    public BigDecimal subtotalConDescuento() {
        return subtotal().subtract(montoDescuento());
    }

    
    public BigDecimal costoEnvio() {
        if (zona == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return zona.calcular(subtotalConDescuento());
    }

    public BigDecimal total() {
        return subtotalConDescuento().add(costoEnvio());
    }
}
