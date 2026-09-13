import java.io.IOException;

public class Main {

    private LeerEntrada entrada;

    public Main() {
        entrada = new LeerEntrada(System.in);
    }

    public void procesar() throws ExcepcionVocal, ExcepcionNumero,
            ExcepcionBlanco, ExcepcionSalida, IOException {

        char caracter = entrada.getChar();

        if (caracter == 'x') {
            throw new ExcepcionSalida();
        }

        if (caracter == 'a' || caracter == 'e' || caracter == 'i' ||
            caracter == 'o' || caracter == 'u') {
            throw new ExcepcionVocal();
        }

        if (caracter >= '0' && caracter <= '9') {
            throw new ExcepcionNumero();
        }

        if (caracter == ' ') {
            throw new ExcepcionBlanco();
        }
    }

    public static void main(String[] args) {

        Main programa = new Main();

        while (true) {

            try {
                programa.procesar();

            } catch (ExcepcionVocal e) {
                System.out.println("Excepcion: vocal");

            } catch (ExcepcionNumero e) {
                System.out.println("Excepcion: numero");

            } catch (ExcepcionBlanco e) {
                System.out.println("Excepcion: blanco");

            } catch (ExcepcionSalida e) {
                System.out.println("Excepcion: salida");
                break;

            } catch (IOException e) {
                System.out.println("Error de lectura");
                break;
            }
        }
    }
}