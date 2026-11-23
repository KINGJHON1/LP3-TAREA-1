package descuentos;

import java.util.List;

public class DescuentoPorcentual implements EstrategiaDescuento {
    @Override
    public double calcularTotal(List<Producto> productos) {
        double total = 0;
        for (Producto p : productos) {
            total += p.getPrecio();
        }

        // Verifica si hay exactamente 2 productos y tienen el mismo nombre
        if (productos.size() == 2 && 
            productos.get(0).getNombre().equalsIgnoreCase(productos.get(1).getNombre())) {
            return total * 0.70; // Descuenta el 30%
        }

        System.out.println("Aviso: Este descuento requiere exactamente 2 productos iguales.");
        return total;
    }
}
