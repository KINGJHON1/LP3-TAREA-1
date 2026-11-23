import java.util.*;

class Notificacion {
    private final String tipo, mensaje;
    public Notificacion(String tipo, String mensaje) { this.tipo = tipo; this.mensaje = mensaje; }
    public String getTipo() { return tipo; }
    public String getMensaje() { return mensaje; }
}

interface Observador { void actualizar(Notificacion n); }

class Usuario implements Observador {
    private final String nombre;
    public Usuario(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }
    @Override
    public void actualizar(Notificacion n) {
        System.out.println("  [" + nombre + "] recibió " + n.getTipo() + ": " + n.getMensaje());
    }
}

class CentroNotificaciones {
    private final List<Observador> suscriptores = new ArrayList<>();
    public void suscribir(Observador o)   { if (!suscriptores.contains(o)) suscriptores.add(o); }
    public void desuscribir(Observador o) { suscriptores.remove(o); }
    public void publicar(Notificacion n) {
        for (Observador o : suscriptores) o.actualizar(n);
    }
}

public class Main {
    public static void main(String[] args) {
        CentroNotificaciones centro = new CentroNotificaciones();
        Usuario ana = new Usuario("Ana"), luis = new Usuario("Luis"), rosa = new Usuario("Rosa");

        centro.suscribir(ana); centro.suscribir(luis); centro.suscribir(rosa);
        System.out.println("Promoción para todos:");
        centro.publicar(new Notificacion("PROMOCIÓN", "2x1 en polos"));

        centro.desuscribir(luis);
        System.out.println("Luis se dio de baja:");
        centro.publicar(new Notificacion("ACTUALIZACIÓN", "Nuevo modelo de zapatillas"));
    }
}
