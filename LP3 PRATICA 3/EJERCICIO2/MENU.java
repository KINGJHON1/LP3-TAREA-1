
class ClienteOCP {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     CUMPLIMIENTO CON OCP (Open/Closed Principle)           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        GestorFormas gestor = new GestorFormas();
        
        gestor.agregarForma(new Circulo(5));
        gestor.agregarForma(new Rectangulo(4, 6));
        gestor.agregarForma(new Triangulo(3, 4, 5));
        gestor.agregarForma(new Pentagono(3));
        
        gestor.dibujarTodas();
        
        gestor.mostrarAreas();
        
     
        System.out.println("\n Área total de todas las formas: " 
            + String.format("%.2f", gestor.calcularAreaTotal()) + " unidades²");
        
        System.out.println("\n✅ VENTAJAS OCP:");
        System.out.println("  • No modificamos Forma ni las clases existentes");
        System.out.println("  • Agregamos Triangulo y Pentagono sin cambiar código existente");
        System.out.println("  • Podemos agregar más formas en el futuro fácilmente");
        System.out.println("  • Cada forma es responsable de su propia implementación");
    }
}
