public class Main {

    public static void main(String[] args) {

        Empleado empleado = new Empleado("Juan", 1500,"Sistemas");

        CalculadoraPago calculadora = new CalculadoraPago();

        System.out.println("Nombre: " + empleado.getNombre());
        System.out.println("Salario: " + empleado.getSalario());
        System.out.println("Departamento: " + empleado.getDepartamento());

        System.out.println("Pago mensual: " +
            calculadora.calcularPagoMensual(empleado));
    }
}
