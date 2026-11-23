package descuentos;

import java.util.List;

public class DescuentoPorcentualAcumulado implements EstrategiaDescuento {
    @Override
    public double calcularTotal(List<Producto> productos) {
        double total = 0;
        double precioMasBajo = Double.MAX_VALUE;

        for (Producto p : productos) {
            total += p.getPrecio();
            if (p.getPrecio() < precioMasBajo) {
                precioMasBajo = p.getPrecio();
            }
        }

        // Si hay 3 o más productos, resta la mitad del producto más barato
        if (productos.size() >= 3) {
            double descuento = precioMasBajo * 0.50;
            return total - descuento;
        }

        System.out.println("Aviso: Este descuento requiere al menos 3 productos.");
        return total;
    }
}
