
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {

    static class Jugador {
        String nombre;
        int salud;
        int nivel;
        ArrayList<String> inventario;
        String equipado;

        Jugador(String nombre) {
            this.nombre = nombre;
            this.salud = 100;
            this.nivel = 1;
            this.inventario = new ArrayList<>();

            inventario.add("Espada");
            inventario.add("Pocion");
            equipado = "Espada";
        }

        int atacar() {
            if (equipado.equals("Espada")) {
                return 20;
            } else if (equipado.equals("Arco")) {
                return 15;
            }
            return 5;
        }

        void recibirDaño(int daño) {
            salud -= daño;

            if (salud < 0) {
                salud = 0;
            }
        }

        void usarPocion() {
            if (inventario.contains("Pocion")) {
                salud += 20;

                if (salud > 100) {
                    salud = 100;
                }

                inventario.remove("Pocion");
                System.out.println("Usaste una pocion. Salud: " + salud);
            } else {
                System.out.println("Ya no tienes pociones.");
            }
        }
    }

    static class Enemigo {
        String nombre;
        int salud;
        int nivel;
        String tipo;

        Enemigo(String nombre) {
            this.nombre = nombre;
            this.salud = 60;
            this.nivel = 1;
            this.tipo = "Normal";
        }

        int atacar() {
            return 10;
        }

        void recibirDaño(int daño) {
            salud -= daño;

            if (salud < 0) {
                salud = 0;
            }
        }
    }

    static class Vista {

        void mostrarEstado(Jugador jugador, Enemigo enemigo) {
            System.out.println("\nJugador: " + jugador.nombre
                    + " | Salud: " + jugador.salud
                    + " | Nivel: " + jugador.nivel);

            System.out.println("Enemigo: " + enemigo.nombre
                    + " | Salud: " + enemigo.salud
                    + " | Nivel: " + enemigo.nivel
                    + " | Tipo: " + enemigo.tipo);
        }

        void mostrarMenu() {
            System.out.println("\n1. Atacar");
            System.out.println("2. Usar pocion");
            System.out.println("3. Equipar arco");
            System.out.print("Opcion: ");
        }
    }

    static class Combate {
        Jugador jugador;
        Enemigo enemigo;
        Vista vista;
        Scanner sc;
        Random random;

        Combate(String nombre, Scanner sc) {
            jugador = new Jugador(nombre);
            enemigo = new Enemigo("Goblin");
            vista = new Vista();
            this.sc = sc;
            random = new Random();
        }

        void iniciar() {

            while (jugador.salud > 0 && enemigo.salud > 0) {

                vista.mostrarEstado(jugador, enemigo);
                vista.mostrarMenu();

                if (!sc.hasNextInt()) {
                    System.out.println("Debes ingresar un numero.");
                    break;
                }

                int opcion = sc.nextInt();

                if (opcion == 1) {
                    int daño = jugador.atacar();
                    enemigo.recibirDaño(daño);

                    System.out.println("Atacaste al enemigo e hiciste "
                            + daño + " de daño.");

                } else if (opcion == 2) {
                    jugador.usarPocion();

                } else if (opcion == 3) {
                    jugador.equipado = "Arco";
                    System.out.println("Ahora tienes equipado el arco.");

                } else {
                    System.out.println("Opcion no valida.");
                    continue;
                }

                if (enemigo.salud > 0) {

                    if (random.nextBoolean()) {
                        int daño = enemigo.atacar();
                        jugador.recibirDaño(daño);

                        System.out.println("El Goblin te ataco e hizo "
                                + daño + " de daño.");
                    } else {
                        System.out.println("El Goblin no te ataco.");
                    }
                }
            }

            vista.mostrarEstado(jugador, enemigo);

            if (jugador.salud > 0 && enemigo.salud == 0) {
                jugador.nivel++;
                System.out.println("Ganaste el combate. Subiste de nivel.");
                System.out.println("Nivel actual: " + jugador.nivel);
            } else if (jugador.salud == 0) {
                System.out.println("Perdiste el combate.");
            } else {
                System.out.println("El combate termino.");
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Nombre del jugador: ");
        String nombre = sc.nextLine();

        Combate combate = new Combate(nombre, sc);
        combate.iniciar();
    }
}
