package descuentos;

import java.util.List;

public class CalculadoraDePrecios {
    private EstrategiaDescuento estrategia;

    public void setEstrategia(EstrategiaDescuento estrategia) {
        this.estrategia = estrategia;
    }

    public double calcular(List<Producto> productos) {
        if (estrategia == null) {
            throw new IllegalStateException("No se ha seleccionado una estrategia de descuento.");
        }
        return estrategia.calcularTotal(productos);
    }
}
