import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionSQLite {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:datos.db";

        try (Connection conexion = DriverManager.getConnection(url)) {
            if (conexion != null) {
                System.out.println("Conexión establecida correctamente.");

                String sql = "SELECT id, nombre FROM usuarios";
                try (PreparedStatement pstmt = conexion.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " - " + rs.getString("nombre"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }
}
