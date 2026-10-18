
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Ejercicio2 {

    static Scanner teclado = new Scanner(System.in);

    static final String URL = "jdbc:sqlite:estudiantes.db";

    public static void main(String[] args) {

        try {
            Connection con = DriverManager.getConnection(URL);

            GestorEstudiantes gestor = new GestorEstudiantes();
            gestor.cargar(con);

            int opcion;

            do {
                System.out.println("\n===== CONSULTAS EN JAVA =====");
                System.out.println("1. Realizar consulta");
                System.out.println("2. Salir");

                opcion = leerEntero("Seleccione una opcion: ");

                if (opcion == 1) {
                    realizarConsulta(gestor);
                } else if (opcion == 2) {
                    System.out.println("Programa finalizado.");
                } else {
                    System.out.println("Opcion incorrecta.");
                }

            } while (opcion != 2);

            con.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void realizarConsulta(GestorEstudiantes gestor) {

        System.out.println("\n--- CAMPOS QUE DESEA MOSTRAR ---");

        boolean verId = leerSiNo("Mostrar ID? (s/n): ");
        boolean verNombre = leerSiNo("Mostrar nombre? (s/n): ");
        boolean verEdad = leerSiNo("Mostrar edad? (s/n): ");
        boolean verCarrera = leerSiNo("Mostrar carrera? (s/n): ");

        if (!verId && !verNombre && !verEdad && !verCarrera) {
            System.out.println("Debe seleccionar al menos un campo.");
            return;
        }

        System.out.println("\n--- CONDICION ---");
        System.out.println("0. Sin condicion");
        System.out.println("1. ID");
        System.out.println("2. Nombre");
        System.out.println("3. Edad");
        System.out.println("4. Carrera");

        int opcionCampo = leerEntero("Campo para filtrar: ");

        String campo = "ninguno";
        String operador = "";
        String valor = "";

        switch (opcionCampo) {
            case 1: campo = "id"; break;
            case 2: campo = "nombre"; break;
            case 3: campo = "edad"; break;
            case 4: campo = "carrera"; break;
            case 0: break;
            default:
                System.out.println("Opcion incorrecta.");
                return;
        }

        if (!campo.equals("ninguno")) {

            System.out.println("Operadores: =, !=, >, <, >=, <=, contiene");
            System.out.print("Operador: ");
            operador = teclado.nextLine().trim();

            System.out.print("Valor de la condicion: ");
            valor = teclado.nextLine().trim();

            boolean numerico = campo.equals("id") || campo.equals("edad");

            if (numerico) {
                if (!operador.equals("=") && !operador.equals("!=")
                        && !operador.equals(">") && !operador.equals("<")
                        && !operador.equals(">=") && !operador.equals("<=")) {
                    System.out.println("Operador no valido para numeros.");
                    return;
                }

                try {
                    Integer.parseInt(valor);
                } catch (NumberFormatException e) {
                    System.out.println("Debe ingresar un numero.");
                    return;
                }

            } else {
                if (!operador.equals("=") && !operador.equals("!=")
                        && !operador.equals("contiene")) {
                    System.out.println("Operador no valido para texto.");
                    return;
                }
            }
        }

        System.out.println("\n--- ORDENAR POR ---");
        System.out.println("1. ID");
        System.out.println("2. Nombre");
        System.out.println("3. Edad");
        System.out.println("4. Carrera");

        int opcionOrden = leerEntero("Campo de ordenamiento: ");

        String orden;

        switch (opcionOrden) {
            case 1: orden = "id"; break;
            case 2: orden = "nombre"; break;
            case 3: orden = "edad"; break;
            case 4: orden = "carrera"; break;
            default:
                System.out.println("Opcion incorrecta.");
                return;
        }

        boolean descendente = leerSiNo(
            "Orden descendente? (s/n): "
        );

        int limite = leerEntero(
            "Cantidad maxima de registros (0 = todos): "
        );

        if (limite < 0) {
            System.out.println("El limite no puede ser negativo.");
            return;
        }

        gestor.consultar(
            verId,
            verNombre,
            verEdad,
            verCarrera,
            campo,
            operador,
            valor,
            orden,
            descendente,
            limite
        );
    }

    static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            try {
                return Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    static boolean leerSiNo(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            String respuesta = teclado.nextLine().trim();

            if (respuesta.equalsIgnoreCase("s")) {
                return true;
            }

            if (respuesta.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println("Responda s o n.");
        }
    }
}
