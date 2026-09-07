interface Forma {
    void dibujar();
    double calcularArea();
    String obtenerNombre();
}

class Circulo implements Forma {
    private double radio;
    
    public Circulo(double radio) {
        this.radio = radio;
    }
    
    @Override
    public void dibujar() {
        System.out.println("🔴 Dibujando círculo con radio: " + radio);
    }
    
    @Override
    public double calcularArea() {
        return Math.PI * radio * radio;
    }
    
    @Override
    public String obtenerNombre() {
        return "Círculo";
    }
}

class Rectangulo implements Forma {
    private double ancho;
    private double alto;
    
    public Rectangulo(double ancho, double alto) {
        this.ancho = ancho;
        this.alto = alto;
    }
    
    @Override
    public void dibujar() {
        System.out.println("▭ Dibujando rectángulo " + ancho + " x " + alto);
    }
    
    @Override
    public double calcularArea() {
        return ancho * alto;
    }
    
    @Override
    public String obtenerNombre() {
        return "Rectángulo";
    }
}

class Triangulo implements Forma {
    private double lado1;
    private double lado2;
    private double lado3;
    
    public Triangulo(double lado1, double lado2, double lado3) {
        this.lado1 = lado1;
        this.lado2 = lado2;
        this.lado3 = lado3;
    }
    
    @Override
    public void dibujar() {
        System.out.println("△ Dibujando triángulo con lados: " + lado1 + ", " + lado2 + ", " + lado3);
    }
    
    @Override
    public double calcularArea() {
        // Fórmula de Herón
        double s = (lado1 + lado2 + lado3) / 2;
        return Math.sqrt(s * (s - lado1) * (s - lado2) * (s - lado3));
    }
    
    @Override
    public String obtenerNombre() {
        return "Triángulo";
    }
}


class Pentagono implements Forma {
    private double lado;
    
    public Pentagono(double lado) {
        this.lado = lado;
    }
    
    @Override
    public void dibujar() {
        System.out.println("⬠ Dibujando pentágono regular con lado: " + lado);
    }
    
    @Override
    public double calcularArea() {
        return (lado * lado * Math.sqrt(25 + 10 * Math.sqrt(5))) / 4;
    }
    
    @Override
    public String obtenerNombre() {
        return "Pentágono";
    }
}


class GestorFormas {
    private java.util.List<Forma> formas = new java.util.ArrayList<>();
    
    public void agregarForma(Forma forma) {
        formas.add(forma);
    }
    
    public void dibujarTodas() {
        System.out.println("\n📐 Dibujando todas las formas:");
        System.out.println("-".repeat(50));
        for (Forma forma : formas) {
            forma.dibujar();
        }
    }
    
    public void mostrarAreas() {
        System.out.println("\n📏 Áreas de las formas:");
        System.out.println("-".repeat(50));
        for (Forma forma : formas) {
            System.out.printf("%s: %.2f unidades²\n", 
                forma.obtenerNombre(), forma.calcularArea());
        }
    }
    
    public double calcularAreaTotal() {
        return formas.stream()
            .mapToDouble(Forma::calcularArea)
            .sum();
    }
}
