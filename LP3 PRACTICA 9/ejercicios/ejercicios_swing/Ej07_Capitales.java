// Ej07: JComboBox de paises que muestra la capital. EXTRA: boton "Pais aleatorio".
import javax.swing.*; import java.awt.*; import java.awt.event.*; import java.util.Random;
public class Ej07_Capitales extends JFrame {
  private static final String[] PAISES = {"Peru", "Chile", "Argentina", "Brasil", "Colombia"};
  private static final String[] CAPITALES = {"Lima", "Santiago", "Buenos Aires", "Brasilia", "Bogota"};
  public Ej07_Capitales() {
    super("Ej07 - Capitales"); setLayout(new FlowLayout());
    JComboBox<String> combo = new JComboBox<>(PAISES); combo.setMaximumRowCount(3);
    JLabel capital = new JLabel("Capital: " + CAPITALES[0]);
    combo.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) capital.setText("Capital: " + CAPITALES[combo.getSelectedIndex()]);
    });
    JButton azar = new JButton("Pais aleatorio");
    azar.addActionListener(e -> combo.setSelectedIndex(new Random().nextInt(PAISES.length)));
    add(combo); add(capital); add(azar);
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej07_Capitales f = new Ej07_Capitales(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(400,100); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
