interface Forma {
    void dibujar();
}

class Circulo implements Forma {

    @Override
    public void dibujar() {
        System.out.println("Dibujando un Círculo");
    }
}

class Rectangulo implements Forma {

    @Override
    public void dibujar() {
        System.out.println("Dibujando un Rectángulo");
    }
}

class Triangulo implements Forma {

    @Override
    public void dibujar() {
        System.out.println("Dibujando un Triángulo");
    }
}

public class OCPDemo {

    public static void main(String[] args) {

        System.out.println("=== DEMOSTRACIÓN OCP: Formas Geométricas ===\n");

        Forma[] formas = { new Circulo(), new Rectangulo(), new Triangulo() };

        for (Forma f : formas) {
            f.dibujar();
        }

        System.out.println("\n=== Fin de la demostración ===");
    }
}
