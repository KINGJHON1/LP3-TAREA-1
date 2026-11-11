// Ej10: copiar entre listas (>>>, <<<, Limpiar). EXTRA: contador de elementos copiados.
import javax.swing.*; import java.awt.*;
public class Ej10_CopiaListas extends JFrame {
  private final DefaultListModel<String> mA = new DefaultListModel<>(), mB = new DefaultListModel<>();
  private final JList<String> listaA = new JList<>(mA), listaB = new JList<>(mB);
  private final JLabel cont = new JLabel("Elementos copiados: 0");
  public Ej10_CopiaListas() {
    super("Ej10 - Copia de listas"); setLayout(new FlowLayout());
    for (String s : new String[]{"Negro", "Azul", "Cyan", "Gris", "Verde", "Magenta", "Naranja", "Rosa", "Rojo", "Amarillo"}) mA.addElement(s);
    listaA.setVisibleRowCount(5); listaA.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    listaB.setVisibleRowCount(5); listaB.setFixedCellWidth(100);
    JButton cop = new JButton("Copiar >>>"), dev = new JButton("<<< Quitar"), lim = new JButton("Limpiar");
    cop.addActionListener(e -> { for (String s : listaA.getSelectedValuesList()) if (!mB.contains(s)) mB.addElement(s); actualizar(); });
    dev.addActionListener(e -> { for (String s : listaB.getSelectedValuesList()) mB.removeElement(s); actualizar(); });
    lim.addActionListener(e -> { mB.clear(); actualizar(); });
    JPanel bot = new JPanel(new GridLayout(3, 1, 0, 5)); bot.add(cop); bot.add(dev); bot.add(lim);
    add(new JScrollPane(listaA)); add(bot); add(new JScrollPane(listaB)); add(cont);
  }
  private void actualizar() { cont.setText("Elementos copiados: " + mB.size()); }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej10_CopiaListas f = new Ej10_CopiaListas(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(420,200); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
