// Ej05: botones de opcion para el tamano de letra (ButtonGroup). EXTRA: etiqueta con el tamano actual.
import javax.swing.*; import java.awt.*; import java.awt.event.*;
public class Ej05_Tamanos extends JFrame {
  public Ej05_Tamanos() {
    super("Ej05 - Tamanos"); setLayout(new FlowLayout());
    JTextField campo = new JTextField("Observe el cambio de tamano", 25);
    campo.setFont(new Font("Serif", Font.PLAIN, 14)); add(campo);
    JLabel info = new JLabel("Tamano actual: 14 pt");
    ButtonGroup grupo = new ButtonGroup();
    for (int tam : new int[]{10, 14, 18, 24}) {
      JRadioButton r = new JRadioButton(tam + " pt", tam == 14);
      grupo.add(r); add(r);
      r.addItemListener(e -> {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          campo.setFont(new Font("Serif", Font.PLAIN, tam));
          info.setText("Tamano actual: " + tam + " pt");
        }
      });
    }
    add(info);
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej05_Tamanos f = new Ej05_Tamanos(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(450,150); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
