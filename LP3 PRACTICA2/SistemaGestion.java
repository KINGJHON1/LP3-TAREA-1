package sistema;

import java.util.ArrayList;

public class SistemaGestion {

    private static int totalCursos = 0;

    private ArrayList<Curso> cursos;

    public SistemaGestion() {
        cursos = new ArrayList<>();
    }

    public void agregarCurso(Curso curso) {

        cursos.add(curso);
        totalCursos++;

    }

    public static void mostrarTotalCursos() {

        System.out.println("Total de cursos: " + totalCursos);

    }

    public void mostrarCursosDisponibles() {

        System.out.println("\n--- CURSOS DISPONIBLES ---");

        for (Curso curso : cursos) {

            if (curso.getCantidadEstudiantes() < Curso.MAX_ESTUDIANTES) {

                System.out.println("- " + curso.getNombre());

            }

        }

    }

}
