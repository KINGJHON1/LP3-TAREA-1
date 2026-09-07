lass ClienteISP {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  CUMPLIMIENTO CON ISP (Interface Segregation Principle)    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        Impresora impresora = new Impresora("HP LaserJet Pro");
        ImpresoraMultifuncional multifuncional = new ImpresoraMultifuncional("Canon imageRUNNER");
        Escaner escaner = new Escaner("Fujitsu ScanSnap");
        MaquinaCopias copiadora = new MaquinaCopias("Xerox VersaLink");
        MaquinaFax fax = new MaquinaFax("Brother MFC-L8690CDW");

        CentroOficina centro = new CentroOficina();
     
        centro.registrarImprimible(impresora);
        centro.registrarImprimible(multifuncional);

        centro.registrarEscaneable(escaner);
        centro.registrarEscaneable(multifuncional);

        centro.registrarCopiable(copiadora);
        centro.registrarCopiable(multifuncional);

        centro.registrarEnviable(fax);
        centro.registrarEnviable(multifuncional);
        

        centro.usarImpresoras();
        centro.usarEscaneables();
        centro.usarCopiables();
        centro.usarEnviables();
        
 
        System.out.println("\n━━━ ESTADÍSTICAS ━━━");
        if (multifuncional instanceof ImpresoraMultifuncional) {
            ((ImpresoraMultifuncional) multifuncional).mostrarEstadisticas();
        }
      
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    VENTAJAS DE ISP                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println(" Impresora solo implementa Imprimible");
        System.out.println(" Escáner solo implementa Escaneable");
        System.out.println(" Multifuncional implementa solo las interfaces que necesita");
        System.out.println(" No hay métodos innecesarios ni excepciones");
        System.out.println(" Cada clase tiene responsabilidades claras");
        System.out.println(" Código más flexible y mantenible");
    }
}
