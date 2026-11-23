import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

final class MenuInicio extends JFrame {
    private Dificultad dificultad = Dificultad.NORMAL;
    private ClasePersonaje personaje = ClasePersonaje.GUERRERO;
    private final JButton botonDificultad = new JButton();
    private final JButton botonPersonaje = new JButton();

    MenuInicio() {
        super("Héroe vs Monstruos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("HÉROE VS MONSTRUOS", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        titulo.setForeground(new Color(0xF9D56E));

        JButton nueva = new JButton("Nueva partida");
        JButton cargar = new JButton("Cargar partida");
        JButton records = new JButton("Récords");
        JButton controles = new JButton("Controles");
        JButton salir = new JButton("Salir");
        estilizar(nueva, new Color(0x2E7D32));
        estilizar(cargar, new Color(0x1565C0));
        estilizar(botonDificultad, new Color(0x8D6E00));
        estilizar(botonPersonaje, new Color(0x5E548E));
        estilizar(records, new Color(0x00695C));
        estilizar(controles, new Color(0x5E548E));
        estilizar(salir, new Color(0xC62828));
        actualizarPreferencias();

        nueva.addActionListener(e -> iniciarNuevaPartida());
        cargar.addActionListener(e -> cargarPartida());
        botonDificultad.addActionListener(e -> seleccionarDificultad());
        botonPersonaje.addActionListener(e -> seleccionarPersonaje());
        records.addActionListener(e -> mostrarRecords());
        controles.addActionListener(e -> JOptionPane.showMessageDialog(
            this, JuegoSwing.textoControles(), "Controles", JOptionPane.INFORMATION_MESSAGE));
        salir.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new GridLayout(0, 1, 8, 8));
        botones.setOpaque(false);
        botones.add(nueva);
        botones.add(cargar);
        botones.add(botonDificultad);
        botones.add(botonPersonaje);
        botones.add(records);
        botones.add(controles);
        botones.add(salir);

        JPanel contenido = new JPanel(new BorderLayout(8, 18));
        contenido.setBackground(new Color(0x172033));
        contenido.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        contenido.add(titulo, BorderLayout.NORTH);
        contenido.add(botones, BorderLayout.CENTER);
        setContentPane(contenido);
        setPreferredSize(new Dimension(400, 440));
        pack();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(nueva);
    }

    private void estilizar(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(boton.getFont().deriveFont(Font.BOLD, 15f));
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
    }

    private void iniciarNuevaPartida() {
        new JuegoSwing(dificultad, personaje).mostrar();
        dispose();
    }

    private void seleccionarDificultad() {
        Object seleccion = JOptionPane.showInputDialog(this, "Elige la dificultad:",
            "Dificultad", JOptionPane.QUESTION_MESSAGE, null, Dificultad.values(), dificultad);
        if (seleccion instanceof Dificultad) {
            dificultad = (Dificultad) seleccion;
            actualizarPreferencias();
        }
    }

    private void seleccionarPersonaje() {
        Object seleccion = JOptionPane.showInputDialog(this, "Elige tu personaje:",
            "Personaje", JOptionPane.QUESTION_MESSAGE, null, ClasePersonaje.opcionesMenu(), personaje);
        if (seleccion instanceof ClasePersonaje) {
            personaje = (ClasePersonaje) seleccion;
            actualizarPreferencias();
        }
    }

    private void mostrarRecords() {
        try {
            JOptionPane.showMessageDialog(this, RecordManager.resumen(),
                "Récords", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudieron leer los récords: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarPreferencias() {
        botonDificultad.setText("Dificultad: " + dificultad);
        botonPersonaje.setText("Personaje: " + personaje);
    }

    private void cargarPartida() {
        try {
            JuegoSwing.cargarPartida().mostrar();
            dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                "No se pudo cargar la partida: " + ex.getMessage(),
                "Error al cargar", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuInicio().setVisible(true));
    }
}
