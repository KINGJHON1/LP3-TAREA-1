// Ej08: conversor de soles a otra moneda (tasas de EJEMPLO, no reales). EXTRA: historial de conversiones.
import javax.swing.*; import java.awt.*;
public class Ej08_Conversor extends JFrame {
  private static final String[] MONEDAS = {"USD", "EUR", "CLP"};
  private static final double[] TASAS = {0.27, 0.25, 250.0};
  public Ej08_Conversor() {
    super("Ej08 - Conversor"); setLayout(new FlowLayout());
    JTextField soles = new JTextField(8); JComboBox<String> combo = new JComboBox<>(MONEDAS);
    JButton conv = new JButton("Convertir"); JLabel res = new JLabel("Resultado: -");
    JTextArea historial = new JTextArea(5, 25); historial.setEditable(false);
    add(new JLabel("Soles:")); add(soles); add(combo); add(conv); add(res);
    add(new JScrollPane(historial));
    conv.addActionListener(e -> {
      try {
        double s = Double.parseDouble(soles.getText().trim());
        double r = s * TASAS[combo.getSelectedIndex()];
        String linea = String.format("S/ %.2f = %.2f %s", s, r, combo.getSelectedItem());
        res.setText("Resultado: " + linea); historial.append(linea + "\n");
      } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Ingrese un numero valido"); }
    });
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej08_Conversor f = new Ej08_Conversor(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(330,260); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
