package sistema;

import java.util.ArrayList;

public class Curso {

    public static final int MAX_ESTUDIANTES = 30;

    private String nombre;
    private String categoria;
    private Profesor profesor;
    private ArrayList<Estudiante> estudiantes;

    public Curso(String nombre, String categoria) {

        this.nombre = nombre;
        this.categoria = categoria;
        estudiantes = new ArrayList<>();

    }

    public void asignarProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void agregarEstudiante(Estudiante estudiante) {

        if (estudiantes.size() < MAX_ESTUDIANTES) {

            estudiantes.add(estudiante);

            System.out.println("Estudiante inscrito en " + nombre);

        } else {

            System.out.println("Curso lleno");

        }

    }

    public void mostrarCurso() {

        System.out.println("Curso: " + nombre);
        System.out.println("Categoría: " + categoria);
        System.out.println("Estudiantes inscritos: " + estudiantes.size());

        if (profesor != null) {
            System.out.println("Profesor: " + profesor.nombre);
        }

    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadEstudiantes() {
        return estudiantes.size();
    }

}
