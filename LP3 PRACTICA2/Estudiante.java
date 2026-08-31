package sistema;

public class Estudiante extends Persona implements Gestionable {

    private String codigo;

    public Estudiante(String nombre, String codigo) {
        super(nombre);
        this.codigo = codigo;
    }

    public void inscribirse(Curso curso) {
        curso.agregarEstudiante(this);
    }

    @Override
    public void mostrarRol() {
        System.out.println("Rol: Estudiante");
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Estudiante: " + nombre);
        System.out.println("Código: " + codigo);
    }
}
