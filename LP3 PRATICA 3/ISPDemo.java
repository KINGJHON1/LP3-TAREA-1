interface Imprimible {
    void imprimir(String documento);
}

interface Escaneable {
    String escanear();
}

interface Copiable {
    void copiar(String documento);
}

interface Faxeable {
    void enviarFax(String documento, String destino);
}

class Impresora implements Imprimible {

    private String modelo;

    public Impresora(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public void imprimir(String documento) {
        System.out.println("[Impresora " + modelo + "] Imprimiendo: \"" + documento + "\"");
    }
}

class ImpresoraMultifuncional implements Imprimible, Escaneable, Copiable, Faxeable {

    private String modelo;

    public ImpresoraMultifuncional(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public void imprimir(String documento) {
        System.out.println("[Multifuncional " + modelo + "] Imprimiendo: \"" + documento + "\"");
    }

    @Override
    public String escanear() {
        String resultado = "Documento_escaneado_" + System.currentTimeMillis() + ".pdf";
        System.out.println("[Multifuncional " + modelo + "] Escaneando... -> " + resultado);
        return resultado;
    }

    @Override
    public void copiar(String documento) {
        System.out.println("[Multifuncional " + modelo + "] Copiando: \"" + documento + "\"");
    }

    @Override
    public void enviarFax(String documento, String destino) {
        System.out.println("[Multifuncional " + modelo + "] Enviando fax a " + destino + ": \"" + documento + "\"");
    }
}

class Escaner implements Escaneable {

    private String modelo;

    public Escaner(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String escanear() {
        String resultado = "Scan_" + modelo + "_" + System.currentTimeMillis() + ".jpg";
        System.out.println("[Escáner " + modelo + "] Escaneando... -> " + resultado);
        return resultado;
    }
}

public class ISPDemo {

    public static void main(String[] args) {

        System.out.println("=== DEMOSTRACIÓN ISP: Dispositivos de Oficina ===\n");

        Impresora impresoraSimple = new Impresora("HP LaserJet 100");
        impresoraSimple.imprimir("Reporte mensual");
        System.out.println();

        Escaner escanerSimple = new Escaner("Canon Lide 300");
        escanerSimple.escanear();
        System.out.println();

        ImpresoraMultifuncional multifuncional = new ImpresoraMultifuncional("Epson EcoTank L5590");
        multifuncional.imprimir("Contrato de servicio");
        multifuncional.escanear();
        multifuncional.copiar("Cédula de identidad");
