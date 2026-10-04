import java.util.ArrayList;
import java.util.Scanner;

class Item {
    String nombre;
    int cantidad;
    String tipo;
    String descripcion;

    Item(String nombre, int cantidad, String tipo, String descripcion) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    void usarItem() {
        if (cantidad > 0) {
            cantidad--;
            System.out.println("Usaste: " + nombre);
        } else {
            System.out.println("No hay cantidad disponible.");
        }
    }
}

class InventarioModel {
    ArrayList<Item> items = new ArrayList<>();

    void agregarItem(Item item) {
        items.add(item);
    }

    void eliminarItem(Item item) {
        items.remove(item);
    }

    ArrayList<Item> obtenerItems() {
        return items;
    }

    Item buscarItem(String nombre) {
        for (Item item : items) {
            if (item.nombre.equalsIgnoreCase(nombre)) {
                return item;
            }
        }
        return null;
    }
}

class InventarioView {

    void mostrarInventario(ArrayList<Item> items) {
        System.out.println("\n--- INVENTARIO ---");

        for (Item item : items) {
            System.out.println(
                item.nombre + " - " + item.cantidad
            );
        }
    }

    void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    void mostrarDetalles(Item item) {
        if (item != null) {
            System.out.println("\n--- DETALLES ---");
            System.out.println("Nombre: " + item.nombre);
            System.out.println("Cantidad: " + item.cantidad);
            System.out.println("Tipo: " + item.tipo);
            System.out.println("Descripcion: " + item.descripcion);
        } else {
            System.out.println("Item no encontrado.");
        }
    }
}

class InventarioController {

    InventarioModel modelo;
    InventarioView vista;

    InventarioController(InventarioModel modelo, InventarioView vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    void agregarItem(Item item) {
        modelo.agregarItem(item);
        vista.mostrarMensaje("Item agregado.");
    }

    void eliminarItem(String nombre) {
        Item item = modelo.buscarItem(nombre);

        if (item != null) {
            modelo.eliminarItem(item);
            vista.mostrarMensaje("Item eliminado.");
        } else {
            vista.mostrarMensaje("Item no encontrado.");
        }
    }

    void verInventario() {
        vista.mostrarInventario(modelo.obtenerItems());
    }

    void mostrarDetalles(String nombre) {
        Item item = modelo.buscarItem(nombre);
        vista.mostrarDetalles(item);
    }

    void buscarItem(String nombre) {
        Item item = modelo.buscarItem(nombre);

        if (item != null) {
            vista.mostrarMensaje("Item encontrado.");
        } else {
            vista.mostrarMensaje("Item no encontrado.");
        }
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        InventarioModel modelo = new InventarioModel();
        InventarioView vista = new InventarioView();

        InventarioController controlador =
            new InventarioController(modelo, vista);

        // Items iniciales
        controlador.agregarItem(
            new Item("Espada", 1, "Arma", "Espada para atacar")
        );

        controlador.agregarItem(
            new Item("Pocion", 3, "Pocion", "Recupera salud")
        );

        int opcion;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Ver inventario");
            System.out.println("2. Buscar item");
            System.out.println("3. Ver detalles");
            System.out.println("4. Usar item");
            System.out.println("5. Eliminar item");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {

                controlador.verInventario();

            } else if (opcion == 2) {

                System.out.print("Nombre del item: ");
                String nombre = sc.nextLine();

                controlador.buscarItem(nombre);

            } else if (opcion == 3) {

                System.out.print("Nombre del item: ");
                String nombre = sc.nextLine();

                controlador.mostrarDetalles(nombre);

            } else if (opcion == 4) {

                System.out.print("Nombre del item: ");
                String nombre = sc.nextLine();

                Item item = modelo.buscarItem(nombre);

                if (item != null) {
                    item.usarItem();
                } else {
                    System.out.println("Item no encontrado.");
                }

            } else if (opcion == 5) {

                System.out.print("Nombre del item: ");
                String nombre = sc.nextLine();

                controlador.eliminarItem(nombre);

            } else if (opcion == 0) {

                System.out.println("Programa terminado.");

            } else {

                System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}
