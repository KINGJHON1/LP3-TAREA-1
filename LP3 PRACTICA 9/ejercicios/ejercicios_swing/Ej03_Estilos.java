// Ej03: Negrita, Cursiva y Subrayado (con HTML en un JLabel). EXTRA: casilla "Mayusculas".
import javax.swing.*; import java.awt.*;
public class Ej03_Estilos extends JFrame {
  private static final String BASE = "Observe como cambia el estilo";
  private final JLabel texto = new JLabel();
  private final JCheckBox neg = new JCheckBox("Negrita"), cur = new JCheckBox("Cursiva"),
      sub = new JCheckBox("Subrayado"), may = new JCheckBox("Mayusculas");
  public Ej03_Estilos() {
    super("Ej03 - Estilos"); setLayout(new FlowLayout());
    texto.setFont(new Font("Serif", Font.PLAIN, 18)); add(texto);
    for (JCheckBox c : new JCheckBox[]{neg, cur, sub, may}) { add(c); c.addItemListener(e -> actualizar()); }
    actualizar();
  }
  private void actualizar() {
    String t = may.isSelected() ? BASE.toUpperCase() : BASE;
    if (neg.isSelected()) t = "<b>" + t + "</b>";
    if (cur.isSelected()) t = "<i>" + t + "</i>";
    if (sub.isSelected()) t = "<u>" + t + "</u>";
    texto.setText("<html>" + t + "</html>");
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej03_Estilos f = new Ej03_Estilos(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(420,130); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
