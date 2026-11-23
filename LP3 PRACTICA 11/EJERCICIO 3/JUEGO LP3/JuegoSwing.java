import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz gráfica del juego. Reutiliza el núcleo de Main.java: la ventana es un Observador
 * más (se redibuja con cada evento TURNO) y los botones y el menú crean los mismos comandos
 * que la consola (a través de la fábrica Comandos).
 *
 * Cada acción es un javax.swing.Action compartido por el botón y el elemento de menú, así que
 * ambos se activan y desactivan juntos.
 *
 * Compilar:  javac -encoding UTF-8 Main.java Juego.java Comandos.java Controles.java Partida.java RecordManager.java MenuInicio.java JuegoSwing.java
 * Ejecutar:  java Main
 */
public class JuegoSwing extends JFrame implements Observador {
    private final Juego juego;
    private final Controles controles;
    private final Estadisticas estadisticas = new Estadisticas();

    private final Tablero tablero = new Tablero();
    private final JLabel etiquetaNivel = new JLabel();
    private final JLabel etiquetaHeroe = new JLabel();
    private final JLabel etiquetaEnemigo = new JLabel();
    private final JProgressBar vidaHeroe = new JProgressBar();
    private final JProgressBar energiaHeroe = new JProgressBar();
    private final JProgressBar vidaEnemigo = new JProgressBar();
    private final JTextArea log = new JTextArea(10, 50);

    /** Solo las acciones de juego: se apagan al terminar. Deshacer/Rehacer/Guardar/Cargar siempre quedan activas. */
    private final List<Action> accionesJuego = new ArrayList<>();
    private boolean finMostrado = false;
    private boolean recordGuardado = false;

    private static final int SIN_MODIFICADOR = 0;
    private static final int CTRL = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

    public JuegoSwing() {
        this(Dificultad.NORMAL, ClasePersonaje.GUERRERO);
    }

    JuegoSwing(Dificultad dificultad, ClasePersonaje clase) {
        this(new Juego(dificultad, clase));
    }

    private JuegoSwing(Juego juego) {
        super("Héroe vs Monstruos");
        this.juego = juego;
        this.controles = new Controles(juego);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        for (JProgressBar b : new JProgressBar[] { vidaHeroe, energiaHeroe, vidaEnemigo }) b.setStringPainted(true);
        vidaHeroe.setForeground(new Color(0x2E7D32));
        energiaHeroe.setForeground(new Color(0x1565C0));
        vidaEnemigo.setForeground(new Color(0xC62828));
        log.setEditable(false);
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        log.setFocusable(false);

        JPanel estado = new JPanel(new GridLayout(0, 1, 4, 4));
        estado.add(etiquetaNivel);
        estado.add(vidaHeroe);
        estado.add(energiaHeroe);
        estado.add(etiquetaHeroe);
        estado.add(vidaEnemigo);
        estado.add(etiquetaEnemigo);

        JPanel arriba = new JPanel(new BorderLayout(4, 4));
        arriba.add(tablero, BorderLayout.NORTH);
        arriba.add(estado, BorderLayout.CENTER);

        // ---- Acciones (compartidas por botones y menú) ----
        Action izquierda = accion("Izquierda", true, KeyEvent.VK_LEFT, SIN_MODIFICADOR, () -> jugar(Comandos.mover(juego, -1, 0)));
        Action derecha   = accion("Derecha",   true, KeyEvent.VK_RIGHT, SIN_MODIFICADOR, () -> jugar(Comandos.mover(juego, 1, 0)));
        Action subir     = accion("Arriba",    true, KeyEvent.VK_UP, SIN_MODIFICADOR, () -> jugar(Comandos.mover(juego, 0, -1)));
        Action bajar     = accion("Abajo",     true, KeyEvent.VK_DOWN, SIN_MODIFICADOR, () -> jugar(Comandos.mover(juego, 0, 1)));
        Action atacar    = accion("Atacar",    true, KeyEvent.VK_SPACE, SIN_MODIFICADOR, () -> jugar(Comandos.atacar(juego)));
        Action disparar  = accion("Disparar",  true, KeyEvent.VK_D, SIN_MODIFICADOR, () -> jugar(Comandos.disparar(juego)));
        Action defender  = accion("Defender",  true, KeyEvent.VK_E, SIN_MODIFICADOR, () -> jugar(Comandos.defender(juego)));
        Action furia     = accion("Furia",     true, KeyEvent.VK_F, SIN_MODIFICADOR, () -> jugar(Comandos.furia(juego)));
        Action habilidad = accion("Habilidad", true, KeyEvent.VK_X, SIN_MODIFICADOR, () -> jugar(Comandos.habilidad(juego)));
        Action curar     = accion("Curar",     true, KeyEvent.VK_C, SIN_MODIFICADOR, () -> jugar(Comandos.curar(juego)));
        Action pocion    = accion("Poción",    true, KeyEvent.VK_P, SIN_MODIFICADOR, () -> jugar(Comandos.pocion(juego)));
        Action deshacer  = accion("Deshacer",  false, KeyEvent.VK_Z, CTRL, this::deshacer);
        Action rehacer   = accion("Rehacer",   false, KeyEvent.VK_Y, CTRL, this::rehacer);
        Action guardar   = accion("Guardar",   false, KeyEvent.VK_S, CTRL, this::guardar);
        Action cargar    = accion("Cargar",    false, KeyEvent.VK_O, CTRL, this::cargar);
        Action nueva     = accion("Nueva partida", false, KeyEvent.VK_N, CTRL, this::nuevaPartida);
        Action salir     = accion("Salir",     false, KeyEvent.VK_Q, CTRL, this::salir);
        Action controlesAyuda = accion("Controles", false, KeyEvent.VK_F1, SIN_MODIFICADOR, this::mostrarControles);
        Action acercaDe  = accion("Acerca de", false, 0, SIN_MODIFICADOR, this::mostrarAcercaDe);

        // ---- Menú ----
        JMenuBar barra = new JMenuBar();
        barra.add(menu("Partida", 'P', nueva, null, guardar, cargar, null, salir));
        barra.add(menu("Editar", 'E', deshacer, rehacer));
        barra.add(menu("Acciones", 'A', izquierda, derecha, subir, bajar, null, atacar, disparar, null,
            defender, furia, habilidad, null, curar, pocion));
        barra.add(menu("Ayuda", 'Y', controlesAyuda, acercaDe));
        setJMenuBar(barra);

        // ---- Botones ----
        JPanel botones = new JPanel(new GridLayout(2, 0, 4, 4));
        for (Action a : new Action[] { izquierda, derecha, subir, bajar, atacar, disparar, defender, furia, habilidad, curar, pocion,
                                       deshacer, rehacer, guardar, cargar }) {
            JButton b = new JButton(a);
            b.setFocusable(false);
            botones.add(b);
        }

        add(arriba, BorderLayout.NORTH);
        add(new JScrollPane(log), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        juego.suscribir(this);
        juego.suscribir(estadisticas);
        juego.notificarTurno();

        pack();
        setLocationRelativeTo(null);
    }

    // ---- Construcción de acciones y menús ----

    private Action accion(String texto, boolean esJuego, int tecla, int modificadores, Runnable cuerpo) {
        AbstractAction a = new AbstractAction(texto) {
            @Override public void actionPerformed(ActionEvent e) { cuerpo.run(); }
        };
        if (tecla != 0) a.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(tecla, modificadores));
        if (esJuego) accionesJuego.add(a);
        return a;
    }

    /** Crea un menú; un elemento null añade un separador. */
    private JMenu menu(String titulo, char mnemonico, Action... elementos) {
        JMenu m = new JMenu(titulo);
        m.setMnemonic(mnemonico);
        for (Action a : elementos) {
            if (a == null) m.addSeparator();
            else m.add(new JMenuItem(a));
        }
        return m;
    }

    // ---- Jugar ----

    private void jugar(Comando comando) {
        controles.jugar(comando);
        atenderTienda();
        comprobarFin();
    }

    private void deshacer() {
        controles.deshacer();
        comprobarFin();
    }

    private void rehacer() {
        controles.rehacer();
        atenderTienda();
        comprobarFin();
    }

    /** Muestra el resultado una sola vez por estado terminal (también al llegar con Rehacer o Cargar). */
    private void comprobarFin() {
        if (!juego.terminado()) {
            finMostrado = false;
            return;
        }
        if (finMostrado) return;
        finMostrado = true;
        if (!recordGuardado) {
            try {
                RecordManager.registrar(juego.puntos, juego.jugador.clase, juego.dificultad);
                recordGuardado = true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el récord: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        String titulo = juego.jugador.vivo() ? "¡VICTORIA!" : "DERROTA";
        JOptionPane.showMessageDialog(this,
            titulo + "  Puntos: " + juego.puntos + "\n\n" + estadisticas.resumen(juego), titulo,
            JOptionPane.INFORMATION_MESSAGE);
    }

    // ---- Partida ----

    private void nuevaPartida() {
        int r = JOptionPane.showConfirmDialog(this,
            "Se perderá el progreso no guardado. ¿Empezar una partida nueva?",
            "Nueva partida", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) return;
        // Una ventana nueva trae Juego, historial y estadísticas limpios.
        new JuegoSwing(juego.dificultad, juego.jugador.clase).mostrar();
        dispose();
    }

    private void salir() {
        int r = JOptionPane.showConfirmDialog(this,
            "¿Salir del juego? Se perderá el progreso no guardado.", "Salir", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) System.exit(0);
    }

    private void guardar() {
        try {
            Partida.guardar(juego, Main.RUTA_PARTIDA);
            log.append("-- Partida guardada --\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar: " + ex.getMessage());
        }
    }

    private void cargar() {
        try {
            Partida.cargar(juego, Main.RUTA_PARTIDA);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                "No se pudo cargar: " + ex.getMessage()
                + "\nComprueba que el archivo de partida sea válido. Si el estado del juego quedó inconsistente, inicia una partida nueva.");
            return;
        }
        controles.limpiarHistorial();
        log.setText("");
        log.append("-- Partida cargada --\n");
        finMostrado = false;
        recordGuardado = false;
        juego.notificarTurno();
        atenderTienda();
        comprobarFin();
    }

    private void atenderTienda() {
        while (juego.tiendaDisponible) {
            Object[] opciones = {
                "Poción (+1) — 60 puntos",
                "Curación (+50 vida) — 45 puntos",
                "Energía (+30) — 35 puntos",
                "Continuar al siguiente nivel"
            };
            int seleccion = JOptionPane.showOptionDialog(this,
                "Tienda entre niveles\nPuntos disponibles: " + juego.puntos,
                "Tienda", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[3]);
            if (seleccion == 0) juego.comprarPocion();
            else if (seleccion == 1) juego.comprarCuracion();
            else if (seleccion == 2) juego.comprarEnergia();
            else {
                juego.continuarDesdeTienda();
                return;
            }
        }
    }

    // ---- Ayuda ----

    private void mostrarControles() {
        JOptionPane.showMessageDialog(this, textoControles(),
            "Controles", JOptionPane.INFORMATION_MESSAGE);
    }

    static String textoControles() {
        return "← → ↑ ↓   Mover en el tablero\n"
            + "Espacio    Atacar\n"
            + "D          Disparar\n"
            + "E          Defender\n"
            + "F          Furia\n"
            + "X          Habilidad especial (una vez por nivel)\n"
            + "C          Curar\n"
            + "P          Poción\n"
            + "Ctrl+Z / Ctrl+Y   Deshacer / Rehacer\n"
            + "Ctrl+S / Ctrl+O   Guardar / Cargar\n"
            + "Ctrl+N     Nueva partida";
    }

    static JuegoSwing cargarPartida() throws IOException {
        JuegoSwing ventana = new JuegoSwing();
        try {
            Partida.cargar(ventana.juego, Main.RUTA_PARTIDA);
        } catch (IOException ex) {
            ventana.dispose();
            throw ex;
        }
        ventana.controles.limpiarHistorial();
        ventana.log.setText("-- Partida cargada --\n");
        ventana.juego.notificarTurno();
        return ventana;
    }

    void mostrar() {
        setVisible(true);
        atenderTienda();
        comprobarFin();
    }

    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(this,
            "Héroe vs Monstruos\nPatrones: Command, Observer, Decorator, Strategy, Factory.",
            "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---- Observador: la ventana reacciona a los mismos eventos que la consola ----

    @Override
    public void actualizar(Juego j, Evento tipo, String mensaje, int valor) {
        if (tipo == Evento.MENSAJE) {
            log.append(mensaje + "\n");
            log.setCaretPosition(log.getDocument().getLength());
        } else if (tipo == Evento.TURNO) {
            refrescar();
        }
    }

    private void refrescar() {
        Personaje h = juego.jugador;
        Enemigo e = juego.enemigo;
        etiquetaNivel.setText("Nivel " + juego.nivel + "/" + Juego.NIVELES
            + "   |   " + juego.dificultad + "   |   Puntos: " + juego.puntos);
        ajustar(vidaHeroe, h.vida, h.vidaMax, "Vida ");
        ajustar(energiaHeroe, h.energia, h.energiaMax, "Energía ");
        etiquetaHeroe.setText(h.nombre + "  |  Pociones: " + h.pociones + h.estado());
        ajustar(vidaEnemigo, e.vida, e.vidaMax, e.nombre + " ");
        etiquetaEnemigo.setText(e.nombre + " (" + e.control.actual().nombre() + ")" + e.estado());

        boolean activas = !juego.terminado() && !juego.tiendaDisponible;
        for (Action a : accionesJuego) a.setEnabled(activas); // botón y menú a la vez
        tablero.repaint();
    }

    private void ajustar(JProgressBar barra, int valor, int maximo, String prefijo) {
        int v = Math.max(0, valor); // la vida puede quedar negativa tras un golpe
        barra.setMaximum(maximo);
        barra.setValue(v);
        barra.setString(prefijo + v + "/" + maximo);
    }

    // ---- Tablero ----

    private class Tablero extends JPanel {
        Tablero() { setPreferredSize(new Dimension(Juego.ANCHO * 50, Juego.ALTO * 50)); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int ancho = Math.max(1, getWidth() / Juego.ANCHO);
            int alto = Math.max(1, getHeight() / Juego.ALTO);
            g.setFont(g.getFont().deriveFont(Font.BOLD, Math.min(22f, alto * 0.45f)));
            g.setColor(Color.GRAY);
            for (int fila = 0; fila < Juego.ALTO; fila++) {
                for (int columna = 0; columna < Juego.ANCHO; columna++) {
                    g.drawRect(columna * ancho + 2, fila * alto + 2, ancho - 4, alto - 4);
                }
            }
            Personaje h = juego.jugador;
            ficha(g, h.pos, h.fila, h.nombre.substring(0, 1), new Color(0x2E7D32), ancho, alto);
            Enemigo e = juego.enemigo;
            ficha(g, e.pos, e.fila, e.nombre.substring(0, 1),
                e.vivo() ? new Color(0xC62828) : Color.GRAY, ancho, alto);
        }

        private void ficha(Graphics g, int columna, int fila, String letra, Color color,
                           int ancho, int alto) {
            int margenX = Math.max(3, ancho / 8);
            int margenY = Math.max(3, alto / 8);
            int xCasilla = columna * ancho;
            int yCasilla = fila * alto;
            g.setColor(color);
            g.fillRoundRect(xCasilla + margenX, yCasilla + margenY,
                ancho - 2 * margenX, alto - 2 * margenY, 12, 12);
            g.setColor(Color.WHITE);
            FontMetrics fm = g.getFontMetrics();
            int x = xCasilla + (ancho - fm.stringWidth(letra)) / 2;
            int y = yCasilla + (alto + fm.getAscent() - fm.getDescent()) / 2;
            g.drawString(letra, x, y);
        }
    }

    public static void main(String[] args) {
        MenuInicio.main(args);
    }
}
