import java.util.ArrayList;
import java.util.List;

interface Observador {
    void actualizar(Juego juego, Evento tipo, String mensaje, int valor);
}

enum Evento {
    MENSAJE, TURNO
}

enum Dificultad {
    FACIL("Fácil", 0.8, 0.8),
    NORMAL("Normal", 1.0, 1.0),
    DIFICIL("Difícil", 1.3, 1.25);

    final String etiqueta;
    final double multiplicadorVidaEnemigo;
    final double multiplicadorDanioEnemigo;

    Dificultad(String etiqueta, double multiplicadorVidaEnemigo,
               double multiplicadorDanioEnemigo) {
        this.etiqueta = etiqueta;
        this.multiplicadorVidaEnemigo = multiplicadorVidaEnemigo;
        this.multiplicadorDanioEnemigo = multiplicadorDanioEnemigo;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}

enum ClasePersonaje {
    HEROE("Héroe", 100, 100, 20, 25, 35),
    GUERRERO("Guerrero", 130, 85, 30, 20, 45),
    ARQUERA("Arquera", 100, 110, 20, 35, 30),
    MAGA("Maga", 90, 130, 18, 30, 40);

    final String nombre;
    final int vida, energia, danioAtaque, danioDisparo, danioFuria;

    ClasePersonaje(String nombre, int vida, int energia, int danioAtaque,
                   int danioDisparo, int danioFuria) {
        this.nombre = nombre;
        this.vida = vida;
        this.energia = energia;
        this.danioAtaque = danioAtaque;
        this.danioDisparo = danioDisparo;
        this.danioFuria = danioFuria;
    }

    @Override
    public String toString() {
        return nombre;
    }

    static ClasePersonaje[] opcionesMenu() {
        return new ClasePersonaje[] { GUERRERO, ARQUERA, MAGA };
    }
}

final class Juego {
    static final int ANCHO = 10;
    static final int ALTO = 6;
    static final int NIVELES = 5;

    Personaje jugador;
    Enemigo enemigo;
    int nivel = 1;
    int puntos;
    Dificultad dificultad;
    boolean tiendaDisponible;

    private final List<Observador> observadores = new ArrayList<>();

    Juego() {
        this(Dificultad.NORMAL, ClasePersonaje.GUERRERO);
    }

    Juego(Dificultad dificultad, ClasePersonaje clase) {
        this.dificultad = dificultad;
        jugador = new Personaje(clase, 0, ALTO / 2, 3);
        enemigo = crearEnemigo(1);
    }

    void suscribir(Observador observador) {
        if (!observadores.contains(observador)) observadores.add(observador);
    }

    void notificarTurno() {
        for (Observador observador : new ArrayList<>(observadores)) {
            observador.actualizar(this, Evento.TURNO, "", 0);
        }
    }

    void notificarMensaje(String mensaje) {
        for (Observador observador : new ArrayList<>(observadores)) {
            observador.actualizar(this, Evento.MENSAJE, mensaje, 0);
        }
    }

    boolean terminado() {
        return !jugador.vivo() || (nivel == NIVELES && !enemigo.vivo());
    }

    int distancia() {
        return Math.abs(jugador.pos - enemigo.pos) + Math.abs(jugador.fila - enemigo.fila);
    }

    boolean mover(int desplazamiento) {
        return mover(desplazamiento, 0);
    }

    boolean mover(int dx, int dy) {
        int nuevaX = jugador.pos + dx;
        int nuevaY = jugador.fila + dy;
        if (nuevaX < 0 || nuevaX >= ANCHO || nuevaY < 0 || nuevaY >= ALTO) {
            notificarMensaje("No puedes avanzar más allá del borde del tablero.");
            return false;
        }
        if (nuevaX == enemigo.pos && nuevaY == enemigo.fila) {
            notificarMensaje("La casilla está ocupada por el enemigo.");
            return false;
        }
        jugador.pos = nuevaX;
        jugador.fila = nuevaY;
        notificarMensaje("Te mueves a la casilla (" + nuevaX + ", " + nuevaY + ").");
        return true;
    }

    boolean atacar() {
        if (distancia() > 1) {
            notificarMensaje("El enemigo está fuera del alcance del ataque.");
            return false;
        }
        int danio = jugador.furia ? jugador.clase.danioFuria : jugador.clase.danioAtaque;
        enemigo.recibirDanio(danio);
        jugador.furia = false;
        puntos += danio;
        notificarMensaje("Atacas al " + enemigo.nombre + " e infliges " + danio + " de daño.");
        return true;
    }

    boolean disparar() {
        if (distancia() > 3) {
            notificarMensaje("El enemigo está fuera del alcance del disparo.");
            return false;
        }
        if (jugador.energia < 20) {
            notificarMensaje("No tienes energía suficiente para disparar.");
            return false;
        }
        jugador.energia -= 20;
        int danio = jugador.clase.danioDisparo;
        enemigo.recibirDanio(danio);
        puntos += danio;
        notificarMensaje("Disparas al " + enemigo.nombre + " e infliges " + danio + " de daño.");
        return true;
    }

    boolean defender() {
        jugador.defendiendo = true;
        notificarMensaje("Te preparas para reducir el próximo golpe.");
        return true;
    }

    boolean usarFuria() {
        if (jugador.energia < 30) {
            notificarMensaje("No tienes energía suficiente para usar Furia.");
            return false;
        }
        jugador.energia -= 30;
        jugador.furia = true;
        notificarMensaje("Furia activada: tu próximo ataque será más fuerte.");
        return true;
    }

    boolean usarHabilidad() {
        if (jugador.habilidadUsada) {
            notificarMensaje("La habilidad especial ya se usó en este nivel.");
            return false;
        }
        if (jugador.energia < 30) {
            notificarMensaje("Necesitas 30 puntos de energía para la habilidad especial.");
            return false;
        }
        if (jugador.clase == ClasePersonaje.GUERRERO && distancia() > 1) {
            notificarMensaje("Golpe sísmico requiere estar junto al enemigo.");
            return false;
        }
        if (jugador.clase != ClasePersonaje.GUERRERO && distancia() > 4) {
            notificarMensaje("La habilidad no alcanza al enemigo desde aquí.");
            return false;
        }

        jugador.energia -= 30;
        jugador.habilidadUsada = true;
        switch (jugador.clase) {
            case GUERRERO:
                enemigo.recibirDanio(45);
                enemigo.aturdido = true;
                puntos += 45;
                notificarMensaje("¡Golpe sísmico! Infliges 45 de daño y aturdes al enemigo.");
                break;
            case ARQUERA:
                enemigo.recibirDanio(40);
                puntos += 40;
                notificarMensaje("¡Lluvia de flechas! Infliges 40 de daño.");
                break;
            case MAGA:
            case HEROE:
                enemigo.recibirDanio(35);
                jugador.energia = Math.min(jugador.energiaMax, jugador.energia + 20);
                puntos += 35;
                notificarMensaje("¡Rayo arcano! Infliges 35 de daño y recuperas 20 de energía.");
                break;
            default:
                throw new IllegalStateException("Clase de personaje no compatible.");
        }
        return true;
    }

    boolean curar() {
        if (jugador.vida >= jugador.vidaMax) {
            notificarMensaje("Ya tienes toda la vida.");
            return false;
        }
        if (jugador.energia < 15) {
            notificarMensaje("No tienes energía suficiente para curarte.");
            return false;
        }
        jugador.energia -= 15;
        int recuperada = jugador.curar(25);
        notificarMensaje("Recuperas " + recuperada + " puntos de vida.");
        return true;
    }

    boolean usarPocion() {
        if (jugador.pociones <= 0) {
            notificarMensaje("No te quedan pociones.");
            return false;
        }
        if (jugador.vida >= jugador.vidaMax) {
            notificarMensaje("Ya tienes toda la vida.");
            return false;
        }
        jugador.pociones--;
        int recuperada = jugador.curar(40);
        notificarMensaje("Usas una poción y recuperas " + recuperada + " puntos de vida.");
        return true;
    }

    boolean comprarPocion() {
        if (puntos < 60) return compraFallida("Necesitas 60 puntos para comprar una poción.");
        puntos -= 60;
        jugador.pociones++;
        notificarMensaje("Compras una poción.");
        notificarTurno();
        return true;
    }

    boolean comprarCuracion() {
        if (jugador.vida >= jugador.vidaMax) return compraFallida("Ya tienes toda la vida.");
        if (puntos < 45) return compraFallida("Necesitas 45 puntos para comprar curación.");
        puntos -= 45;
        jugador.curar(50);
        notificarMensaje("Compras curación y recuperas vida.");
        notificarTurno();
        return true;
    }

    boolean comprarEnergia() {
        if (jugador.energia >= jugador.energiaMax) return compraFallida("Ya tienes toda la energía.");
        if (puntos < 35) return compraFallida("Necesitas 35 puntos para comprar energía.");
        puntos -= 35;
        jugador.energia = Math.min(jugador.energiaMax, jugador.energia + 30);
        notificarMensaje("Compras una recarga de energía.");
        notificarTurno();
        return true;
    }

    private boolean compraFallida(String mensaje) {
        notificarMensaje(mensaje);
        return false;
    }

    void continuarDesdeTienda() {
        if (!tiendaDisponible) return;
        tiendaDisponible = false;
        nivel++;
        enemigo = crearEnemigo(nivel);
        jugador.pos = 0;
        jugador.fila = ALTO / 2;
        jugador.habilidadUsada = false;
        notificarMensaje("¡Nivel " + nivel + "! Aparece " + enemigo.nombre + ".");
        notificarTurno();
    }

    void resolverRespuestaEnemiga() {
        if (!enemigo.vivo()) {
            puntos += 50 * nivel;
            if (nivel < NIVELES) {
                tiendaDisponible = true;
                notificarMensaje("Nivel superado. Visita la tienda antes de continuar.");
            }
            return;
        }

        if (enemigo.aturdido) {
            enemigo.aturdido = false;
            notificarMensaje(enemigo.nombre + " pierde su turno por el aturdimiento.");
            return;
        }

        boolean ataqueADistancia = enemigo.jefe || "Dragón".equals(enemigo.nombre);
        if (distancia() > 1 && !(ataqueADistancia && distancia() <= 3)) {
            int dx = jugador.pos - enemigo.pos;
            int dy = jugador.fila - enemigo.fila;
            if (Math.abs(dx) >= Math.abs(dy) && dx != 0) {
                enemigo.pos += Integer.signum(dx);
            } else if (dy != 0) {
                enemigo.fila += Integer.signum(dy);
            }
            notificarMensaje(enemigo.nombre + " avanza hacia ti.");
            return;
        }

        enemigo.turnos++;
        int danio = (int) Math.round((10 + nivel * 3) * dificultad.multiplicadorDanioEnemigo);
        String ataque;
        if (enemigo.jefe && (distancia() > 1 || enemigo.turnos % 2 == 0)) {
            danio += 12;
            ataque = "lanza una explosión oscura";
        } else if ("Dragón".equals(enemigo.nombre)
                   && (distancia() > 1 || enemigo.turnos % 2 == 0)) {
            danio += 8;
            ataque = "lanza un aliento de fuego";
        } else if ("Trol".equals(enemigo.nombre) && enemigo.turnos % 2 == 0) {
            danio += 10;
            ataque = "descarga un martillazo pesado";
        } else if ("Gólem".equals(enemigo.nombre) && enemigo.turnos % 2 == 0) {
            danio += 6;
            ataque = "golpea con su puño de piedra";
        } else {
            ataque = "te golpea";
        }
        if (jugador.defendiendo) {
            danio = Math.max(1, danio / 2);
            jugador.defendiendo = false;
        }
        jugador.recibirDanio(danio);
        notificarMensaje(enemigo.nombre + " " + ataque + " y causa " + danio + " de daño.");
    }

    Estado capturarEstado() {
        return new Estado(jugador.clase, dificultad, jugador.nombre, jugador.vida, jugador.vidaMax, jugador.energia,
            jugador.energiaMax, jugador.pos, jugador.fila, jugador.pociones, jugador.defendiendo,
            jugador.furia, jugador.habilidadUsada, enemigo.nombre, enemigo.vida,
            enemigo.vidaMax, enemigo.pos, enemigo.fila, enemigo.jefe, enemigo.aturdido, enemigo.turnos,
            nivel, puntos, tiendaDisponible);
    }

    void restaurarEstado(Estado estado) {
        jugador = new Personaje(estado.claseJugador, estado.vidaJugador,
            estado.energiaJugador, estado.posJugador, estado.filaJugador, estado.pociones,
            estado.defendiendo, estado.furia, estado.habilidadUsada);
        enemigo = new Enemigo(estado.nombreEnemigo, estado.vidaEnemigo,
            estado.vidaMaxEnemigo, estado.posEnemigo, estado.filaEnemigo, estado.jefe,
            estado.aturdido, estado.turnosEnemigo);
        nivel = estado.nivel;
        puntos = estado.puntos;
        dificultad = estado.dificultad;
        tiendaDisponible = estado.tiendaDisponible;
        notificarTurno();
    }

    private Enemigo crearEnemigo(int nivelActual) {
        String[] nombres = { "Orco", "Trol", "Gólem", "Dragón", "Rey monstruo" };
        int vida = (int) Math.round((70 + nivelActual * 15)
            * dificultad.multiplicadorVidaEnemigo);
        boolean jefe = nivelActual == NIVELES;
        if (jefe) vida = (int) Math.round(vida * 1.5);
        return new Enemigo(nombres[nivelActual - 1], vida, vida, ANCHO - 3,
            ALTO / 2, jefe, false, 0);
    }

    static final class Estado {
        final ClasePersonaje claseJugador;
        final Dificultad dificultad;
        final String nombreJugador;
        final int vidaJugador, vidaMaxJugador, energiaJugador, energiaMaxJugador;
        final int posJugador, filaJugador, pociones;
        final boolean defendiendo, furia, habilidadUsada, jefe, aturdido, tiendaDisponible;
        final String nombreEnemigo;
        final int vidaEnemigo, vidaMaxEnemigo, posEnemigo, filaEnemigo;
        final int turnosEnemigo, nivel, puntos;

        Estado(ClasePersonaje claseJugador, Dificultad dificultad, String nombreJugador,
               int vidaJugador, int vidaMaxJugador,
               int energiaJugador, int energiaMaxJugador, int posJugador, int filaJugador, int pociones,
               boolean defendiendo, boolean furia, boolean habilidadUsada,
               String nombreEnemigo, int vidaEnemigo, int vidaMaxEnemigo, int posEnemigo, int filaEnemigo,
               boolean jefe, boolean aturdido, int turnosEnemigo,
               int nivel, int puntos, boolean tiendaDisponible) {
            this.claseJugador = claseJugador;
            this.dificultad = dificultad;
            this.nombreJugador = nombreJugador;
            this.vidaJugador = vidaJugador;
            this.vidaMaxJugador = vidaMaxJugador;
            this.energiaJugador = energiaJugador;
            this.energiaMaxJugador = energiaMaxJugador;
            this.posJugador = posJugador;
            this.filaJugador = filaJugador;
            this.pociones = pociones;
            this.defendiendo = defendiendo;
            this.furia = furia;
            this.habilidadUsada = habilidadUsada;
            this.nombreEnemigo = nombreEnemigo;
            this.vidaEnemigo = vidaEnemigo;
            this.vidaMaxEnemigo = vidaMaxEnemigo;
            this.posEnemigo = posEnemigo;
            this.filaEnemigo = filaEnemigo;
            this.jefe = jefe;
            this.aturdido = aturdido;
            this.turnosEnemigo = turnosEnemigo;
            this.nivel = nivel;
            this.puntos = puntos;
            this.tiendaDisponible = tiendaDisponible;
        }
    }
}

class Personaje {
    final String nombre;
    final ClasePersonaje clase;
    final int vidaMax;
    final int energiaMax;
    int vida, energia, pos, fila, pociones;
    boolean defendiendo, furia, habilidadUsada;

    Personaje(ClasePersonaje clase, int pos, int fila, int pociones) {
        this(clase, clase.vida, clase.energia, pos, fila, pociones, false, false, false);
    }

    Personaje(ClasePersonaje clase, int vida, int energia, int pos, int fila, int pociones,
              boolean defendiendo, boolean furia, boolean habilidadUsada) {
        this.nombre = clase.nombre;
        this.clase = clase;
        this.vida = vida;
        this.vidaMax = clase.vida;
        this.energia = energia;
        this.energiaMax = clase.energia;
        this.pos = pos;
        this.fila = fila;
        this.pociones = pociones;
        this.defendiendo = defendiendo;
        this.furia = furia;
        this.habilidadUsada = habilidadUsada;
    }

    Personaje(String nombre, int vida, int vidaMax, int energia, int energiaMax,
              int pos, int fila, int pociones, boolean defendiendo, boolean furia) {
        this.nombre = nombre;
        this.clase = null;
        this.vida = vida;
        this.vidaMax = vidaMax;
        this.energia = energia;
        this.energiaMax = energiaMax;
        this.pos = pos;
        this.fila = fila;
        this.pociones = pociones;
        this.defendiendo = defendiendo;
        this.furia = furia;
    }

    boolean vivo() {
        return vida > 0;
    }

    int curar(int cantidad) {
        int recuperada = Math.min(cantidad, vidaMax - vida);
        vida += recuperada;
        return recuperada;
    }

    void recibirDanio(int cantidad) {
        vida = Math.max(0, vida - cantidad);
    }

    String estado() {
        StringBuilder estado = new StringBuilder();
        if (defendiendo) estado.append(" | Defendiendo");
        if (furia) estado.append(" | Furia");
        if (habilidadUsada) estado.append(" | Habilidad usada");
        return estado.toString();
    }
}

final class Enemigo extends Personaje {
    final ControlEnemigo control = new ControlEnemigo(this);
    final boolean jefe;
    boolean aturdido;
    int turnos;

    Enemigo(String nombre, int vida, int vidaMax, int pos, int fila, boolean jefe,
            boolean aturdido, int turnos) {
        super(nombre, vida, vidaMax, 0, 0, pos, fila, 0, false, false);
        this.jefe = jefe;
        this.aturdido = aturdido;
        this.turnos = turnos;
    }
}

final class ControlEnemigo {
    private final Enemigo enemigo;

    ControlEnemigo(Enemigo enemigo) {
        this.enemigo = enemigo;
    }

    EstrategiaEnemigo actual() {
        if (enemigo.jefe) return new EstrategiaEnemigo("JEFE");
        if (enemigo.vida * 3 <= enemigo.vidaMax) return new EstrategiaEnemigo("Agresivo");
        return new EstrategiaEnemigo("Cazador");
    }
}

final class EstrategiaEnemigo {
    private final String nombre;

    EstrategiaEnemigo(String nombre) {
        this.nombre = nombre;
    }

    String nombre() {
        return nombre;
    }
}

final class Estadisticas implements Observador {
    private int acciones;
    private int mensajes;

    @Override
    public void actualizar(Juego juego, Evento tipo, String mensaje, int valor) {
        if (tipo == Evento.MENSAJE) mensajes++;
        if (tipo == Evento.TURNO) acciones++;
    }

    String resumen(Juego juego) {
        return "Nivel alcanzado: " + juego.nivel + "/" + Juego.NIVELES
            + "\nAcciones registradas: " + Math.max(0, acciones - 1)
            + "\nEventos registrados: " + mensajes;
    }
}
