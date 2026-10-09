// Alumno es subclase de Persona (que ya es Serializable), asi que tambien lo es.
// Su atributo Fecha tambien debe ser Serializable, o daria NotSerializableException.
public class Alumno extends Persona {
    private String codigo;
    private Fecha fechaNacimiento;

    public Alumno(String nombre, String apellido, String codigo, Fecha fechaNacimiento) {
        super(nombre, apellido);
        this.codigo = codigo;
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return super.toString() + " | Codigo: " + codigo + " | Nacimiento: " + fechaNacimiento;
    }
}
