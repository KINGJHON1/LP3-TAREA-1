
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class GestorEstudiantes {

    private Estudiante[] estudiantes;
    private int cantidad;

    public void cargar(Connection con) throws Exception {

        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery(
            "SELECT COUNT(*) FROM estudiante"
        );

        rs.next();
        cantidad = rs.getInt(1);

        rs.close();

        estudiantes = new Estudiante[cantidad];

        rs = st.executeQuery(
            "SELECT id, nombre, edad, carrera FROM estudiante"
        );

        int i = 0;

        while (rs.next()) {
            estudiantes[i] = new Estudiante(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getInt("edad"),
                rs.getString("carrera")
            );
            i++;
        }

        rs.close();
        st.close();
    }

    public void consultar(
        boolean verId,
        boolean verNombre,
        boolean verEdad,
        boolean verCarrera,
        String campoCondicion,
        String operador,
        String valor,
        String campoOrden,
        boolean descendente,
        int limite
    ) {

        Estudiante[] resultado = new Estudiante[cantidad];
        int total = 0;

        for (int i = 0; i < cantidad; i++) {

            Estudiante e = estudiantes[i];

            if (cumpleCondicion(
                    e, campoCondicion, operador, valor)) {

                resultado[total] = e;
                total++;
            }
        }

        for (int i = 0; i < total - 1; i++) {
            for (int j = 0; j < total - 1 - i; j++) {

                int comparacion = comparar(
                    resultado[j],
                    resultado[j + 1],
                    campoOrden
                );

                if ((!descendente && comparacion > 0)
                        || (descendente && comparacion < 0)) {

                    Estudiante aux = resultado[j];
                    resultado[j] = resultado[j + 1];
                    resultado[j + 1] = aux;
                }
            }
        }

        System.out.println("\n===== RESULTADOS =====");

        int mostrados = 0;

        for (int i = 0; i < total; i++) {

            if (limite > 0 && mostrados >= limite) {
                break;
            }

            Estudiante e = resultado[i];

            if (verId) {
                System.out.print("ID: " + e.id + " | ");
            }

            if (verNombre) {
                System.out.print("Nombre: " + e.nombre + " | ");
            }

            if (verEdad) {
                System.out.print("Edad: " + e.edad + " | ");
            }

            if (verCarrera) {
                System.out.print("Carrera: " + e.carrera + " | ");
            }

            System.out.println();
            mostrados++;
        }

        System.out.println("Total de registros mostrados: " + mostrados);
    }

    private boolean cumpleCondicion(
        Estudiante e,
        String campo,
        String operador,
        String valor
    ) {

        if (campo.equals("ninguno")) {
            return true;
        }

        if (campo.equals("id") || campo.equals("edad")) {

            int dato;

            if (campo.equals("id")) {
                dato = e.id;
            } else {
                dato = e.edad;
            }

            int numero;

            try {
                numero = Integer.parseInt(valor);
            } catch (NumberFormatException ex) {
                System.out.println("La condición necesita un número.");
                return false;
            }

            switch (operador) {
                case "=":  return dato == numero;
                case "!=": return dato != numero;
                case ">":  return dato > numero;
                case "<":  return dato < numero;
                case ">=": return dato >= numero;
                case "<=": return dato <= numero;
            }
        }

        String dato;

        if (campo.equals("nombre")) {
            dato = e.nombre;
        } else if (campo.equals("carrera")) {
            dato = e.carrera;
        } else {
            return false;
        }

        if (dato == null) {
            dato = "";
        }

        if (operador.equals("=")) {
            return dato.equalsIgnoreCase(valor);
        }

        if (operador.equals("!=")) {
            return !dato.equalsIgnoreCase(valor);
        }

        if (operador.equals("contiene")) {
            return dato.toLowerCase().contains(valor.toLowerCase());
        }

        return false;
    }

    private int comparar(
        Estudiante a,
        Estudiante b,
        String campo
    ) {

        switch (campo) {

            case "id":
                return Integer.compare(a.id, b.id);

            case "nombre":
                return compararTexto(a.nombre, b.nombre);

            case "edad":
                return Integer.compare(a.edad, b.edad);

            case "carrera":
                return compararTexto(a.carrera, b.carrera);

            default:
                return Integer.compare(a.id, b.id);
        }
    }

    private int compararTexto(String a, String b) {
        if (a == null) a = "";
        if (b == null) b = "";

        return a.compareToIgnoreCase(b);
    }
}
