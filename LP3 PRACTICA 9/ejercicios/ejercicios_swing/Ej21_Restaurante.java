// Ej21: pedido de restaurante con JComboBox, JRadioButton, JCheckBox y JButton. EXTRA: propina opcional del 10%.
import javax.swing.*; import java.awt.*;
public class Ej21_Restaurante extends JFrame {
  private static final String[] PLATOS = {"Lomo saltado", "Aji de gallina", "Ceviche", "Arroz con pollo"};
  private static final double[] P_PLATOS = {28, 22, 30, 20};
  private static final String[] BEBIDAS = {"Ninguna", "Gaseosa", "Jugo"};
  private static final double[] P_BEBIDAS = {0, 5, 7};
  private static final String[] EXTRAS = {"Postre", "Ensalada"};
  private static final double[] P_EXTRAS = {8, 6};
  public Ej21_Restaurante() {
    super("Ej21 - Restaurante");
    JComboBox<String> plato = new JComboBox<>(PLATOS);
    JPanel pPlato = new JPanel(); pPlato.setBorder(BorderFactory.createTitledBorder("Plato")); pPlato.add(plato);
    JPanel pBeb = new JPanel(); pBeb.setBorder(BorderFactory.createTitledBorder("Bebida"));
    JRadioButton[] rb = new JRadioButton[BEBIDAS.length]; ButtonGroup g = new ButtonGroup();
    for (int i = 0; i < rb.length; i++) { rb[i] = new JRadioButton(BEBIDAS[i], i == 0); g.add(rb[i]); pBeb.add(rb[i]); }
    JPanel pExt = new JPanel(); pExt.setBorder(BorderFactory.createTitledBorder("Extras"));
    JCheckBox[] cb = new JCheckBox[EXTRAS.length];
    for (int i = 0; i < cb.length; i++) { cb[i] = new JCheckBox(EXTRAS[i]); pExt.add(cb[i]); }
    JCheckBox propina = new JCheckBox("Agregar propina (10%)"); pExt.add(propina);
    JPanel izq = new JPanel(new GridLayout(0, 1)); izq.add(pPlato); izq.add(pBeb); izq.add(pExt);
    JTextArea boleta = new JTextArea(10, 22); boleta.setEditable(false); boleta.setFont(new Font("Monospaced", Font.PLAIN, 12));
    JButton calcular = new JButton("Calcular");
    calcular.addActionListener(e -> {
      double total = P_PLATOS[plato.getSelectedIndex()];
      StringBuilder sb = new StringBuilder("--- BOLETA ---\n");
      sb.append(String.format("%-14s S/ %.2f%n", PLATOS[plato.getSelectedIndex()], total));
      for (int i = 0; i < rb.length; i++) if (rb[i].isSelected() && P_BEBIDAS[i] > 0) { sb.append(String.format("%-14s S/ %.2f%n", BEBIDAS[i], P_BEBIDAS[i])); total += P_BEBIDAS[i]; }
      for (int i = 0; i < cb.length; i++) if (cb[i].isSelected()) { sb.append(String.format("%-14s S/ %.2f%n", EXTRAS[i], P_EXTRAS[i])); total += P_EXTRAS[i]; }
      if (propina.isSelected()) { double p = total * 0.10; sb.append(String.format("%-14s S/ %.2f%n", "Propina", p)); total += p; }
      sb.append(String.format("%nTOTAL: S/ %.2f", total));
      boleta.setText(sb.toString());
    });
    add(izq, BorderLayout.WEST); add(new JScrollPane(boleta), BorderLayout.CENTER); add(calcular, BorderLayout.SOUTH);
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej21_Restaurante f = new Ej21_Restaurante(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(560,400); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
