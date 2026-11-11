// Ej06: dos ButtonGroup independientes (talla y color). EXTRA: boton Confirmar con resumen del pedido.
import javax.swing.*; import java.awt.*;
public class Ej06_TallaColor extends JFrame {
  private final ButtonGroup gTalla = new ButtonGroup(), gColor = new ButtonGroup();
  private JPanel panel(String titulo, ButtonGroup g, String[] opciones) {
    JPanel p = new JPanel(new GridLayout(0, 1)); p.setBorder(BorderFactory.createTitledBorder(titulo));
    for (int i = 0; i < opciones.length; i++) {
      JRadioButton r = new JRadioButton(opciones[i], i == 0);
      r.setActionCommand(opciones[i]); g.add(r); p.add(r);
    }
    return p;
  }
  public Ej06_TallaColor() {
    super("Ej06 - Talla y color"); setLayout(new FlowLayout());
    add(panel("Talla", gTalla, new String[]{"S", "M", "L"}));
    add(panel("Color", gColor, new String[]{"Rojo", "Azul", "Negro"}));
    JButton ok = new JButton("Confirmar"); add(ok);
    ok.addActionListener(e -> JOptionPane.showMessageDialog(this,
        "Talla: " + gTalla.getSelection().getActionCommand() + "\nColor: " + gColor.getSelection().getActionCommand()));
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej06_TallaColor f = new Ej06_TallaColor(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(340,170); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
