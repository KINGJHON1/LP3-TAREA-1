package descuentos;

import java.util.List;

public interface EstrategiaDescuento {
    double calcularTotal(List<Producto> productos);
}
