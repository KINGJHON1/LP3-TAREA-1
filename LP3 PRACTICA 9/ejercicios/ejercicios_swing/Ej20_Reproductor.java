// Ej20: mini reproductor (BorderLayout). EXTRA: barra de progreso simulada con javax.swing.Timer y casilla Repetir.
import javax.swing.*; import java.awt.*;
public class Ej20_Reproductor extends JFrame {
  private final String[] canciones = {"Cancion 1", "Cancion 2", "Cancion 3", "Cancion 4"};
  private final JList<String> lista = new JList<>(canciones);
  private final JLabel titulo = new JLabel("", SwingConstants.CENTER);
  private final JProgressBar barra = new JProgressBar(0, 100);
  private final JCheckBox repetir = new JCheckBox("Repetir");
  private final JButton play = new JButton("Play");
  private final Timer timer;
  public Ej20_Reproductor() {
    super("Ej20 - Reproductor");
    titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
    lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    lista.addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) { titulo.setText("Cancion: " + lista.getSelectedValue()); barra.setValue(0); } });
    timer = new Timer(100, e -> {
      barra.setValue(barra.getValue() + 2);
      if (barra.getValue() >= 100) { if (repetir.isSelected()) barra.setValue(0); else siguiente(); }
    });
    JButton ant = new JButton("Anterior"), sig = new JButton("Siguiente");
    ant.addActionListener(e -> lista.setSelectedIndex((lista.getSelectedIndex() + canciones.length - 1) % canciones.length));
    sig.addActionListener(e -> siguiente());
    play.addActionListener(e -> { if (timer.isRunning()) { timer.stop(); play.setText("Play"); } else { timer.start(); play.setText("Pausa"); } });
    JPanel sur = new JPanel(new FlowLayout()); sur.add(ant); sur.add(play); sur.add(sig); sur.add(repetir);
    JPanel centro = new JPanel(new BorderLayout(5, 5)); centro.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    centro.add(titulo, BorderLayout.CENTER); centro.add(barra, BorderLayout.SOUTH);
    add(new JLabel("Mi reproductor", SwingConstants.CENTER), BorderLayout.NORTH);
    add(new JScrollPane(lista), BorderLayout.WEST); add(centro, BorderLayout.CENTER); add(sur, BorderLayout.SOUTH);
    lista.setSelectedIndex(0);
  }
  private void siguiente() { lista.setSelectedIndex((lista.getSelectedIndex() + 1) % canciones.length); }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej20_Reproductor f = new Ej20_Reproductor(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(480,220); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
