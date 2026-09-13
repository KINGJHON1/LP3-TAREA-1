public class Principal {

    public static void main(String[] args) {

        RegistroEstudiantes registro =
                new RegistroEstudiantes(5);

        try {
            registro.agregarEstudiante("Juan");
            registro.agregarEstudiante("Maria");
            registro.agregarEstudiante("Pedro");

            System.out.println(
                "Estudiantes agregados correctamente."
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                "Error: " + e.getMessage()
            );
        }

        try {
            registro.agregarEstudiante("");

        } catch (IllegalArgumentException e) {
            System.out.println(
                "Error al agregar: " + e.getMessage()
            );
        }

        try {
            String estudiante =
                    registro.buscarEstudiante("Maria");

            System.out.println(
                "Estudiante encontrado: " + estudiante
            );

        } catch (java.util.NoSuchElementException e) {
            System.out.println(
                "Error: " + e.getMessage()
            );
        }

        try {
            String estudiante =
                    registro.buscarEstudiante("Carlos");

            System.out.println(
                "Estudiante encontrado: " + estudiante
            );

        } catch (java.util.NoSuchElementException e) {
            System.out.println(
                "Error al buscar: " + e.getMessage()
            );
        }
    }
}
