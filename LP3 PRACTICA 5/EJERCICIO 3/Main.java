public class Main {

    public static <F, S> void imprimirPar(Par<F, S> par) {
        System.out.println(par);
    }

    public static void main(String[] args) {

        // 1
        System.out.println("EJERCICIO 1");

        Par<String, Integer> par = new Par<>("Juan", 20);

        System.out.println(par);
        System.out.println("Primero: " + par.getPrimero());
        System.out.println("Segundo: " + par.getSegundo());


        // 2
        System.out.println();
        PruebaPar.probar();


        // 3
        System.out.println();
        System.out.println("EJERCICIO 3");

        Par<String, Integer> par1 = new Par<>("Juan", 20);
        Par<Double, Boolean> par2 = new Par<>(15.5, true);

        Persona persona = new Persona("Carlos");
        Par<Persona, Integer> par3 = new Par<>(persona, 25);

        imprimirPar(par1);
        imprimirPar(par2);
        imprimirPar(par3);


        // 4
        System.out.println();
        System.out.println("EJERCICIO 4");

        Contenedor<String, Integer> contenedor = new Contenedor<>();

        contenedor.agregarPar("Juan", 20);
        contenedor.agregarPar("Pedro", 25);
        contenedor.agregarPar("Maria", 30);

        contenedor.mostrarPares();

        System.out.println();
        System.out.println("Par en la posicion 1:");
        System.out.println(contenedor.obtenerPar(1));
    }
}
