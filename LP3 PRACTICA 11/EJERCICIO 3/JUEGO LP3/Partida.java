import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

final class Partida {
    private Partida() {}

    static void guardar(Juego juego, String archivo) throws IOException {
        Path destino = Paths.get(archivo).toAbsolutePath();
        Path carpeta = destino.getParent();
        if (carpeta != null) Files.createDirectories(carpeta);
        Path temporal = Files.createTempFile(carpeta, "partida-", ".tmp");
        boolean movido = false;
        try {
            Properties datos = propiedades(juego.capturarEstado());
            try (OutputStream salida = Files.newOutputStream(temporal)) {
                datos.store(salida, "Partida de Heroe vs Monstruos");
            }
            try {
                Files.move(temporal, destino, StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(temporal, destino, StandardCopyOption.REPLACE_EXISTING);
            }
            movido = true;
        } finally {
            if (!movido) Files.deleteIfExists(temporal);
        }
    }

    static void cargar(Juego juego, String archivo) throws IOException {
        Properties datos = new Properties();
        try (InputStream entrada = Files.newInputStream(Paths.get(archivo))) {
            datos.load(entrada);
        } catch (IllegalArgumentException ex) {
            throw new IOException("El archivo de partida contiene un formato inválido.", ex);
        }

        Juego.Estado estado;
        try {
            estado = new Juego.Estado(
                ClasePersonaje.valueOf(datos.getProperty("jugador.clase", "HEROE")),
                Dificultad.valueOf(datos.getProperty("dificultad", "NORMAL")),
                obligatorio(datos, "jugador.nombre"),
                entero(datos, "jugador.vida"), entero(datos, "jugador.vidaMax"),
                entero(datos, "jugador.energia"), entero(datos, "jugador.energiaMax"),
                entero(datos, "jugador.pos"), entero(datos, "jugador.fila", Juego.ALTO / 2),
                entero(datos, "jugador.pociones"),
                booleano(datos, "jugador.defendiendo"), booleano(datos, "jugador.furia"),
                booleano(datos, "jugador.habilidadUsada", false),
                obligatorio(datos, "enemigo.nombre"),
                entero(datos, "enemigo.vida"), entero(datos, "enemigo.vidaMax"),
                entero(datos, "enemigo.pos"), entero(datos, "enemigo.fila", Juego.ALTO / 2),
                booleano(datos, "enemigo.jefe", "Rey monstruo".equals(datos.getProperty("enemigo.nombre"))),
                booleano(datos, "enemigo.aturdido", false),
                entero(datos, "enemigo.turnos", 0),
                entero(datos, "nivel"), entero(datos, "puntos"),
                booleano(datos, "tienda.disponible", false));
            validar(estado);
        } catch (IllegalArgumentException ex) {
            throw new IOException("El archivo de partida está incompleto o contiene datos inválidos.", ex);
        }
        juego.restaurarEstado(estado);
    }

    private static Properties propiedades(Juego.Estado estado) {
        Properties datos = new Properties();
        poner(datos, "jugador.clase", estado.claseJugador.name());
        poner(datos, "dificultad", estado.dificultad.name());
        poner(datos, "jugador.nombre", estado.nombreJugador);
        poner(datos, "jugador.vida", estado.vidaJugador);
        poner(datos, "jugador.vidaMax", estado.vidaMaxJugador);
        poner(datos, "jugador.energia", estado.energiaJugador);
        poner(datos, "jugador.energiaMax", estado.energiaMaxJugador);
        poner(datos, "jugador.pos", estado.posJugador);
        poner(datos, "jugador.fila", estado.filaJugador);
        poner(datos, "jugador.pociones", estado.pociones);
        poner(datos, "jugador.defendiendo", estado.defendiendo);
        poner(datos, "jugador.furia", estado.furia);
        poner(datos, "jugador.habilidadUsada", estado.habilidadUsada);
        poner(datos, "enemigo.nombre", estado.nombreEnemigo);
        poner(datos, "enemigo.vida", estado.vidaEnemigo);
        poner(datos, "enemigo.vidaMax", estado.vidaMaxEnemigo);
        poner(datos, "enemigo.pos", estado.posEnemigo);
        poner(datos, "enemigo.fila", estado.filaEnemigo);
        poner(datos, "enemigo.jefe", estado.jefe);
        poner(datos, "enemigo.aturdido", estado.aturdido);
        poner(datos, "enemigo.turnos", estado.turnosEnemigo);
        poner(datos, "nivel", estado.nivel);
        poner(datos, "puntos", estado.puntos);
        poner(datos, "tienda.disponible", estado.tiendaDisponible);
        return datos;
    }

    private static void validar(Juego.Estado estado) throws IOException {
        if (estado.nombreJugador.isEmpty() || estado.nombreEnemigo.isEmpty()
            || estado.vidaMaxJugador != estado.claseJugador.vida
            || estado.vidaMaxJugador <= 0 || estado.vidaJugador < 0
            || estado.vidaJugador > estado.vidaMaxJugador
            || estado.energiaMaxJugador != estado.claseJugador.energia
            || estado.energiaMaxJugador <= 0 || estado.energiaJugador < 0
            || estado.energiaJugador > estado.energiaMaxJugador
            || estado.pociones < 0 || estado.vidaMaxEnemigo <= 0
            || estado.vidaEnemigo < 0 || estado.vidaEnemigo > estado.vidaMaxEnemigo
            || estado.nivel < 1 || estado.nivel > Juego.NIVELES || estado.puntos < 0
            || estado.turnosEnemigo < 0 || estado.jefe != (estado.nivel == Juego.NIVELES)
            || (estado.tiendaDisponible && (estado.nivel >= Juego.NIVELES || estado.vidaEnemigo != 0))
            || estado.posJugador < 0 || estado.posJugador >= Juego.ANCHO
            || estado.posEnemigo < 0 || estado.posEnemigo >= Juego.ANCHO
            || estado.filaJugador < 0 || estado.filaJugador >= Juego.ALTO
            || estado.filaEnemigo < 0 || estado.filaEnemigo >= Juego.ALTO
            || (estado.posJugador == estado.posEnemigo && estado.filaJugador == estado.filaEnemigo)) {
            throw new IOException("El archivo de partida contiene valores fuera de rango.");
        }
    }

    private static String obligatorio(Properties datos, String clave) {
        String valor = datos.getProperty(clave);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta " + clave);
        }
        return valor;
    }

    private static int entero(Properties datos, String clave) {
        return Integer.parseInt(obligatorio(datos, clave));
    }

    private static int entero(Properties datos, String clave, int valorPredeterminado) {
        String valor = datos.getProperty(clave);
        return valor == null ? valorPredeterminado : Integer.parseInt(valor);
    }

    private static boolean booleano(Properties datos, String clave) {
        String valor = obligatorio(datos, clave);
        if (!"true".equalsIgnoreCase(valor) && !"false".equalsIgnoreCase(valor)) {
            throw new IllegalArgumentException("Valor booleano inválido: " + clave);
        }
        return Boolean.parseBoolean(valor);
    }

    private static boolean booleano(Properties datos, String clave, boolean valorPredeterminado) {
        String valor = datos.getProperty(clave);
        if (valor == null) return valorPredeterminado;
        if (!"true".equalsIgnoreCase(valor) && !"false".equalsIgnoreCase(valor)) {
            throw new IllegalArgumentException("Valor booleano inválido: " + clave);
        }
        return Boolean.parseBoolean(valor);
    }

    private static void poner(Properties datos, String clave, Object valor) {
        datos.setProperty(clave, String.valueOf(valor));
    }
}
