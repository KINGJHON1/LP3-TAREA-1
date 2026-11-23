package descuentos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Producto> carrito = new ArrayList<>();
        CalculadoraDePrecios calculadora = new CalculadoraDePrecios();

        System.out.print("¿Cuántos productos deseas ingresar al carrito?: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        for (int i = 1; i <= cantidad; i++) {
            System.out.print("Nombre del producto " + i + ": ");
            String nombre = scanner.nextLine();
            System.out.print("Precio del producto " + i + ": ");
            double precio = scanner.nextDouble();
            scanner.nextLine();

            carrito.add(new Producto(nombre, precio));
        }

        int opcion;
        do {
            System.out.println("\n--- MENÚ DE ESTRATEGIAS DE DESCUENTO ---");
            System.out.println("1. Sin Descuento");
            System.out.println("2. Descuento Fijo (10%)");
            System.out.println("3. Descuento Porcentual (30% por 2 productos iguales)");
            System.out.println("4. Descuento Porcentual Acumulado (50% en el más barato si llevas 3 o más)");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    calculadora.setEstrategia(new SinDescuento());
                    System.out.println("Total a pagar: S/ " + calculadora.calcular(carrito));
                    break;
                case 2:
                    calculadora.setEstrategia(new DescuentoFijo());
                    System.out.println("Total a pagar: S/ " + calculadora.calcular(carrito));
                    break;
                case 3:
                    calculadora.setEstrategia(new DescuentoPorcentual());
                    System.out.println("Total a pagar: S/ " + calculadora.calcular(carrito));
                    break;
                case 4:
                    calculadora.setEstrategia(new DescuentoPorcentualAcumulado());
                    System.out.println("Total a pagar: S/ " + calculadora.calcular(carrito));
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);

        scanner.close();
    }
}
