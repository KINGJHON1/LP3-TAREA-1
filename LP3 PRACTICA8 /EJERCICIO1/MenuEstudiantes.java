import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 * EJERCICIO 1: Menu por consola con confirmacion por clave
 *
 * Que hace este programa:
 *  - Crea (si no existe) una base de datos SQLite llamada estudiantes.db
 *  - Muestra un menu: Ingresar, Borrar, Actualizar, Mostrar y Salir
 *  - Cuando se modifica un dato (ingresar, borrar o actualizar) pide una clave:
 *       clave correcta   -> COMMIT   (el cambio se guarda)
 *       clave incorrecta -> ROLLBACK (el cambio se cancela)
 *
 * Clave de ejemplo: 1234
 */
public class MenuEstudiantes {

    // Direccion de la base de datos (si el archivo no existe, SQLite lo crea)
    static final String URL = "jdbc:sqlite:estudiantes.db";

    // Clave para confirmar los cambios
    static final String CLAVE = "1234";

    // Objeto para leer lo que escribe el usuario en la consola
    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        Connection con = null;

        try {
            // 1. Abrir la conexion con la base de datos
            con = DriverManager.getConnection(URL);

            // 2. Crear la tabla estudiante si todavia no existe
            Statement st = con.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS estudiante ("
                    + "id INTEGER PRIMARY KEY, "
                    + "nombre TEXT NOT NULL, "
                    + "edad INTEGER, "
                    + "carrera TEXT)");
            st.close();

            // 3. Desactivar el guardado automatico.
            //    Asi los cambios quedan "pendientes" hasta hacer commit.
            con.setAutoCommit(false);

            // 4. Repetir el menu hasta que el usuario elija 5 (Salir)
            int opcion = 0;
            while (opcion != 5) {
                System.out.println();
                System.out.println("===== MENU ESTUDIANTES =====");
                System.out.println("1. Ingresar");
                System.out.println("2. Borrar");
                System.out.println("3. Actualizar");
                System.out.println("4. Mostrar");
                System.out.println("5. Salir");
                opcion = leerEntero("Opcion: ");

                try {
                    if (opcion == 1) {
                        ingresar(con);
                    } else if (opcion == 2) {
                        borrar(con);
                    } else if (opcion == 3) {
                        actualizar(con);
                    } else if (opcion == 4) {
                        mostrar(con);
                    } else if (opcion == 5) {
                        System.out.println("Hasta luego.");
                    } else {
                        System.out.println("Opcion no valida.");
                    }
                } catch (SQLException e) {
                    // Si hubo un error en la base de datos (por ejemplo, un ID repetido),
                    // se cancelan los cambios pendientes.
                    con.rollback();
                    System.out.println("Error: " + e.getMessage() + " (cambios cancelados)");
                }
            }

        } catch (SQLException e) {
            System.out.println("No se pudo conectar: " + e.getMessage());
        } finally {
            // 5. Cerrar la conexion al terminar (haya o no errores)
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar: " + e.getMessage());
            }
        }
    }

    // ---------------------------------------------------------------
    // INGRESAR: pide los datos y agrega un estudiante nuevo
    // ---------------------------------------------------------------
    static void ingresar(Connection con) throws SQLException {
        int id = leerEntero("ID: ");
        String nombre = leerTexto("Nombre: ");
        int edad = leerEntero("Edad: ");
        String carrera = leerTexto("Carrera: ");

        // Los signos ? se reemplazan despues por los valores reales
        String sql = "INSERT INTO estudiante (id, nombre, edad, carrera) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);          // primer ?  -> id
        ps.setString(2, nombre);   // segundo ? -> nombre
        ps.setInt(3, edad);        // tercer ?  -> edad
        ps.setString(4, carrera);  // cuarto ?  -> carrera

        int filas = ps.executeUpdate();   // devuelve cuantas filas se afectaron
        ps.close();
        System.out.println(filas + " registro(s) ingresado(s) (pendiente de confirmar)");

        confirmar(con);
    }

    // ---------------------------------------------------------------
    // BORRAR: elimina el estudiante con el ID indicado
    // ---------------------------------------------------------------
    static void borrar(Connection con) throws SQLException {
        int id = leerEntero("ID a borrar: ");

        PreparedStatement ps = con.prepareStatement("DELETE FROM estudiante WHERE id = ?");
        ps.setInt(1, id);

        int filas = ps.executeUpdate();
        ps.close();
        System.out.println(filas + " registro(s) borrado(s) (pendiente de confirmar)");

        confirmar(con);
    }

    // ---------------------------------------------------------------
    // ACTUALIZAR: cambia los datos del estudiante con el ID indicado
    // ---------------------------------------------------------------
    static void actualizar(Connection con) throws SQLException {
        int id = leerEntero("ID a actualizar: ");
        String nombre = leerTexto("Nuevo nombre: ");
        int edad = leerEntero("Nueva edad: ");
        String carrera = leerTexto("Nueva carrera: ");

        String sql = "UPDATE estudiante SET nombre = ?, edad = ?, carrera = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setInt(2, edad);
        ps.setString(3, carrera);
        ps.setInt(4, id);

        int filas = ps.executeUpdate();
        ps.close();
        System.out.println(filas + " registro(s) actualizado(s) (pendiente de confirmar)");

        confirmar(con);
    }

    // ---------------------------------------------------------------
    // MOSTRAR: lista todos los estudiantes
    // ---------------------------------------------------------------
    static void mostrar(Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, nombre, edad, carrera FROM estudiante ORDER BY id");

        System.out.println("ID | NOMBRE | EDAD | CARRERA");
        while (rs.next()) {   // next() pasa a la siguiente fila; es false cuando ya no hay mas
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            int edad = rs.getInt("edad");
            String carrera = rs.getString("carrera");
            System.out.println(id + " | " + nombre + " | " + edad + " | " + carrera);
        }

        rs.close();
        st.close();
    }

    // ---------------------------------------------------------------
    // CONFIRMAR: pide la clave y decide entre commit y rollback
    // ---------------------------------------------------------------
    static void confirmar(Connection con) throws SQLException {
        String clave = leerTexto("Ingrese la clave para confirmar los cambios: ");

        if (clave.equals(CLAVE)) {
            con.commit();     // guarda los cambios
            System.out.println("Clave correcta. Cambios guardados (COMMIT).");
        } else {
            con.rollback();   // cancela los cambios
            System.out.println("Clave incorrecta. Cambios cancelados (ROLLBACK).");
        }
    }

    // ---------------------------------------------------------------
    // Metodos auxiliares para leer datos del teclado
    // ---------------------------------------------------------------

    // Lee un numero entero. Si el usuario escribe otra cosa, vuelve a preguntar.
    static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = teclado.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    // Lee una linea de texto
    static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }
}
