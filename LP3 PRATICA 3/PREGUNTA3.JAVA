public interface EstrategiaDescuento {
    double calcular(double monto);
}

public class DescuentoRegular implements EstrategiaDescuento {
    public double calcular(double monto) {
        return monto * 0.95;
    }
}

public class DescuentoVIP implements EstrategiaDescuento {
    public double calcular(double monto) {
        return monto * 0.85;
    }
}

public class DescuentoMayorista implements EstrategiaDescuento {
    public double calcular(double monto) {
        return monto * 0.80;
    }
}

public class CalculadoraDescuento {
    public double calcular(EstrategiaDescuento estrategia, double monto) {
        return estrategia.calcular(monto);
    }
}
