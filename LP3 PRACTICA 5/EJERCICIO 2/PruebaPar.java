package sistema;

public class PruebaPar {

    public static void main(String[] args) {

        Par<String, Integer> p1 = new Par<>("Juan", 20);
        Par<String, Integer> p2 = new Par<>("Juan", 20);
        Par<String, Integer> p3 = new Par<>("Pedro", 30);

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);

        System.out.println("p1 es igual a p2: " + p1.esIgual(p2));
        System.out.println("p1 es igual a p3: " + p1.esIgual(p3));
    }
}
