import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


interface Imprimible {
    void imprimir();
}


interface Escaneable {
    void escanear();
}

interface Copiable {
    void copiar();
}

interface Enviable {
    void enviarFax();
}


class Impresora implements Imprimible {
    private String modelo;
    private int pagasImpresaas = 0;
    
    public Impresora(String modelo) {
        this.modelo = modelo;
    }
    
    @Override
    public void imprimir() {
        pagasImpresaas++;
        System.out.println("  [" + modelo + "] Imprimiendo documento... (Página #" + pagasImpresaas + ")");
    }
    
    public int getPagenasImpresas() {
        return pagasImpresaas;
    }
}

class ImpresoraMultifuncional implements Imprimible, Escaneable, Copiable, Enviable {
    private String modelo;
    private int paginasImpresas = 0;
    private int documentosEscaneados = 0;
    private int copiaRealizadas = 0;
    private List<String> faxesEnviados = new ArrayList<>();
    
    public ImpresoraMultifuncional(String modelo) {
        this.modelo = modelo;
    }
    
    @Override
    public void imprimir() {
        paginasImpresas++;
        System.out.println(" [" + modelo + "] Imprimiendo documento... (Página #" + paginasImpresas + ")");
    }
    
    @Override
    public void escanear() {
        documentosEscaneados++;
        System.out.println("[" + modelo + "] Escaneando documento... (Documento #" + documentosEscaneados + ")");
    }
    
    @Override
    public void copiar() {
        copiaRealizadas++;
        System.out.println(" [" + modelo + "] Realizando copia... (Copia #" + copiaRealizadas + ")");
    }
    
    @Override
    public void enviarFax() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        faxesEnviados.add(timestamp);
        System.out.println(" [" + modelo + "] Enviando fax... (" + timestamp + ")");
    }
    
    public void mostrarEstadisticas() {
        System.out.println("\n Estadísticas del dispositivo " + modelo + ":");
        System.out.println("  • Páginas impresas: " + paginasImpresas);
        System.out.println("  • Documentos escaneados: " + documentosEscaneados);
        System.out.println("  • Copias realizadas: " + copiaRealizadas);
        System.out.println("  • Faxes enviados: " + faxesEnviados.size());
    }
}

class Escaner implements Escaneable {
    private String modelo;
    private int documentosEscaneados = 0;
    
    public Escaner(String modelo) {
        this.modelo = modelo;
    }
    
    @Override
    public void escanear() {
        documentosEscaneados++;
        System.out.println(" [" + modelo + "] Escaneando documento... (Documento #" + documentosEscaneados + ")");
    }
}
class MaquinaCopias implements Copiable {
    private String modelo;
    private int copiasRealizadas = 0;
    
    public MaquinaCopias(String modelo) {
        this.modelo = modelo;
    }
    
    @Override
    public void copiar() {
        copiasRealizadas++;
        System.out.println(" [" + modelo + "] Realizando copia... (Copia #" + copiasRealizadas + ")");
    }
}

class MaquinaFax implements Enviable {
    private String modelo;
    private List<String> faxesEnviados = new ArrayList<>();
    
    public MaquinaFax(String modelo) {
        this.modelo = modelo;
    }
    
    @Override
    public void enviarFax() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        faxesEnviados.add(timestamp);
        System.out.println("📠 [" + modelo + "] Enviando fax... (" + timestamp + ")");
    }
}

class CentroOficina {
    private List<Imprimible> imprimibles = new ArrayList<>();
    private List<Escaneable> escaneables = new ArrayList<>();
    private List<Copiable> copiables = new ArrayList<>();
    private List<Enviable> enviables = new ArrayList<>();
    
    public void registrarImprimible(Imprimible imprimible) {
        imprimibles.add(imprimible);
    }
    
    public void registrarEscaneable(Escaneable escaneable) {
        escaneables.add(escaneable);
    }
    
    public void registrarCopiable(Copiable copiable) {
        copiables.add(copiable);
    }
    
    public void registrarEnviable(Enviable enviable) {
        enviables.add(enviable);
    }
    
    public void usarImpresoras() {
        System.out.println("\n━━━ USANDO IMPRESORAS ━━━");
        for (Imprimible imprimible : imprimibles) {
            imprimible.imprimir();
        }
    }
    
    public void usarEscaneables() {
        System.out.println("\n━━━ USANDO ESCANEABLES ━━━");
        for (Escaneable escaneable : escaneables) {
            escaneable.escanear();
        }
    }
    
    public void usarCopiables() {
        System.out.println("\n━━━ USANDO COPIABLES ━━━");
        for (Copiable copiable : copiables) {
            copiable.copiar();
        }
    }
    
    public void usarEnviables() {
        System.out.println("\n━━━ USANDO ENVIABLES ━━━");
        for (Enviable enviable : enviables) {
            enviable.enviarFax();
        }
    }
}
