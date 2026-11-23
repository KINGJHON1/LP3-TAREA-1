import java.util.ArrayDeque;
import java.util.Deque;

final class Controles {
    private static final int MAX_HISTORIAL = 50;

    private final Juego juego;
    private final Deque<Juego.Estado> deshacer = new ArrayDeque<>();
    private final Deque<Juego.Estado> rehacer = new ArrayDeque<>();

    Controles(Juego juego) {
        this.juego = juego;
    }

    void jugar(Comando comando) {
        if (juego.terminado() || juego.tiendaDisponible) return;
        Juego.Estado anterior = juego.capturarEstado();
        if (!comando.ejecutar()) return;
        juego.resolverRespuestaEnemiga();
        deshacer.push(anterior);
        if (deshacer.size() > MAX_HISTORIAL) deshacer.removeLast();
        rehacer.clear();
        juego.notificarTurno();
    }

    void deshacer() {
        if (deshacer.isEmpty()) return;
        rehacer.push(juego.capturarEstado());
        juego.restaurarEstado(deshacer.pop());
    }

    void rehacer() {
        if (rehacer.isEmpty()) return;
        deshacer.push(juego.capturarEstado());
        juego.restaurarEstado(rehacer.pop());
    }

    void limpiarHistorial() {
        deshacer.clear();
        rehacer.clear();
    }
}
