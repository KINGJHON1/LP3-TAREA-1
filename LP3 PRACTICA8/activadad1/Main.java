package principal;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:actividades.db";
        Connection con = null;

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            System.out.println("Conectado a la Base de Datos.");

            Statement stmt = con.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS producto (id INTEGER PRIMARY KEY, nombre TEXT, precio REAL);");
            System.out.println("Tabla verificada/creada.");

            con.setAutoCommit(false);

            String sqlInsert = "INSERT INTO producto VALUES (?, ?, ?);";
            PreparedStatement pstmtInsert = con.prepareStatement(sqlInsert);

            pstmtInsert.setInt(1, 1);
            pstmtInsert.setString(2, "Laptop");
            pstmtInsert.setDouble(3, 2500.0);
            pstmtInsert.executeUpdate();

            con.commit();
            System.out.println("Producto insertado y confirmado con Commit.");

            String sqlUpdate = "UPDATE producto SET precio = ? WHERE id = ?;";
            PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdate);

            pstmtUpdate.setDouble(1, 2300.0);
            pstmtUpdate.setInt(2, 1);
            pstmtUpdate.executeUpdate();

            con.commit();
            System.out.println("Producto actualizado y confirmado con Commit.");

            System.out.println("\n--- REGISTROS EN LA TABLA ---");
            ResultSet rs = stmt.executeQuery("SELECT * FROM producto;");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | Nombre: " + rs.getString("nombre") + " | Precio: " + rs.getDouble("precio"));
            }

            String sqlDelete = "DELETE FROM producto WHERE id = ?;";
            PreparedStatement pstmtDelete = con.prepareStatement(sqlDelete);

            pstmtDelete.setInt(1, 1);
            pstmtDelete.executeUpdate();
            System.out.println("\nSe ejecutó el borrado en la transacción...");

            con.rollback();
            System.out.println("Se aplicó Rollback. El borrado fue cancelado.");

            System.out.println("\n--- VERIFICACIÓN POST-ROLLBACK ---");
            ResultSet rsFinal = stmt.executeQuery("SELECT * FROM producto;");

            while (rsFinal.next()) {
                System.out.println("ID: " + rsFinal.getInt("id") +
                                   " | Nombre: " + rsFinal.getString("nombre"));
            }

            rs.close();
            rsFinal.close();
            stmt.close();
            pstmtInsert.close();
            pstmtUpdate.close();
            pstmtDelete.close();

        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                System.out.println("\nConexión cerrada de forma segura.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
