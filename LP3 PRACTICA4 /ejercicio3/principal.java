public class Principal {

    public static void main(String[] args) {

        Numero numero = new Numero();

        try {
            numero.setValor(25.5);
            System.out.println("Valor ingresado: " + numero.getValor());

            numero.setValor(-10);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("El programa continúa.");
    }
}
