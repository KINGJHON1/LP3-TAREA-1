public List<Usuario> buscarPorNombre(Connection conexion, String nombre) throws SQLException {
    List<Usuario> resultado = new ArrayList<>();
    String sql = "SELECT id, nombre FROM usuarios WHERE nombre = ?";

    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setString(1, nombre);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                resultado.add(new Usuario(rs.getInt("id"), rs.getString("nombre")));
            }
        }
    }
    return resultado;
}
