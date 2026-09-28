public class Main {

    public static <F, S> void imprimirPar(Par<F, S> par) {
        System.out.println(par);
    }

    public static void main(String[] args) {

        Par<String, Integer> par1 = new Par<>("Juan", 20);

        Par<Double, Boolean> par2 = new Par<>(15.5, true);

        Persona persona = new Persona("Carlos");
        Par<Persona, Integer> par3 = new Par<>(persona, 25);

        imprimirPar(par1);
        imprimirPar(par2);
        imprimirPar(par3);
    }
}
