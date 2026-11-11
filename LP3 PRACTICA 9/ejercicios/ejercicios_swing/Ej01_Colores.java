// Ej01: tres botones cambian el color de fondo. EXTRA: boton "Aleatorio" y titulo con el ultimo color elegido.
import javax.swing.*; import java.awt.*; import java.util.Random;
public class Ej01_Colores extends JFrame {
  public Ej01_Colores() {
    super("Ej01 - Colores"); setLayout(new FlowLayout());
    String[] nombres = {"Rojo", "Verde", "Azul", "Aleatorio"};
    Color[] colores = {Color.RED, Color.GREEN, Color.BLUE, null};
    for (int i = 0; i < nombres.length; i++) {
      final int k = i;
      JButton b = new JButton(nombres[i]); add(b);
      b.addActionListener(e -> {
        Color c = colores[k] != null ? colores[k] : new Color(new Random().nextInt(0x1000000));
        getContentPane().setBackground(c);
        setTitle("Ej01 - " + e.getActionCommand());
      });
    }
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej01_Colores f = new Ej01_Colores(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(380,120); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
