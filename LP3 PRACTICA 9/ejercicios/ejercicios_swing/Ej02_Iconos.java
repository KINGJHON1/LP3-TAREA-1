// Ej02: botones con icono y rollover; dialogo centrado con this. EXTRA: contador de clics en el titulo y en el mensaje.
import javax.swing.*; import java.awt.*; import java.awt.image.BufferedImage; import java.awt.event.*;
public class Ej02_Iconos extends JFrame {
  private int clics = 0;
  private static Icon circulo(Color c) {
    BufferedImage im = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = im.createGraphics(); g.setColor(c); g.fillOval(2, 2, 20, 20); g.dispose();
    return new ImageIcon(im);
  }
  public Ej02_Iconos() {
    super("Ej02 - Iconos"); setLayout(new FlowLayout());
    JButton simple = new JButton("Boton simple");
    JButton elegante = new JButton("Boton elegante", circulo(Color.ORANGE));
    elegante.setRolloverIcon(circulo(Color.MAGENTA));
    add(simple); add(elegante);
    ActionListener h = e -> {
      clics++; setTitle("Ej02 - Clics: " + clics);
      JOptionPane.showMessageDialog(this, "Usted oprimio: " + e.getActionCommand() + "\nClics totales: " + clics);
    };
    simple.addActionListener(h); elegante.addActionListener(h);
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej02_Iconos f = new Ej02_Iconos(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(330,110); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
