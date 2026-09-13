public class Numero {

    private double valor;

    public void setValor(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException(
                "El valor no puede ser negativo."
            );
        }

        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }
}
