package modelo;

import java.time.LocalDateTime;
import java.util.List;

public class Compra {
    public final int id;
    public final LocalDateTime fecha = LocalDateTime.now();
    public final List<ItemCarrito> items;
    public final double descuento;
    public final double envio;
    public final double total;

    public Compra(int id, List<ItemCarrito> items, double descuento, double envio, double total) {
        this.id = id;
        this.items = items;
        this.descuento = descuento;
        this.envio = envio;
        this.total = total;
    }
}
