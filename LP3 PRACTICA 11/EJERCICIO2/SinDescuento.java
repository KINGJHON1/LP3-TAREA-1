package descuentos;

import java.util.List;

public class SinDescuento implements EstrategiaDescuento {
    @Override
    public double calcularTotal(List<Producto> productos) {
        double total = 0;
        for (Producto p : productos) {
            total += p.getPrecio();
        }
        return total;
    }
}
