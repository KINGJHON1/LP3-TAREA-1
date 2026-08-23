package sistema;

public class Main {

    public static void main(String[] args) {

        Estudiante estudiante1 = new Estudiante("Carlos", "E001");
        Estudiante estudiante2 = new Estudiante("Ana", "E002");

        Profesor profesor = new Profesor("Juan", "Programacion");

        Curso curso = new Curso("Programacion", "Programación");

        SistemaGestion sistema = new SistemaGestion();

        curso.asignarProfesor(profesor);

        sistema.agregarCurso(curso);

        estudiante1.inscribirse(curso);
        estudiante2.inscribirse(curso);

        System.out.println("\n--- CURSO ---");

        curso.mostrarCurso();

        System.out.println("\n--- ESTUDIANTE ---");

        estudiante1.mostrarInformacion();
        estudiante1.mostrarRol();

        System.out.println("\n--- PROFESOR ---");

        profesor.mostrarInformacion();
        profesor.mostrarRol();

        System.out.println("\n--- SISTEMA ---");

        SistemaGestion.mostrarTotalCursos();

        sistema.mostrarCursosDisponibles();

    }

}
