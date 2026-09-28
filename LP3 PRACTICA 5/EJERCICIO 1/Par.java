public class Par<F, S> {

    private F primero;
    private S segundo;

    public Par(F primero, S segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public F getPrimero() {
        return primero;
    }

    public S getSegundo() {
        return segundo;
    }

    public void setPrimero(F primero) {
        this.primero = primero;
    }

    public void setSegundo(S segundo) {
        this.segundo = segundo;
    }

    public String toString() {
        return "(Primero: " + primero + ", Segundo: " + segundo + ")";
    }

    public boolean esIgual(Par<F, S> otro) {
        return primero.equals(otro.primero)
                && segundo.equals(otro.segundo);
    }
}
