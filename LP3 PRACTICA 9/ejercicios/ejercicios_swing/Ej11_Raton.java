// Ej11: coordenadas del raton y cambio de color al entrar/salir. EXTRA: contador de clics.
import javax.swing.*; import java.awt.*; import java.awt.event.*;
public class Ej11_Raton extends JFrame {
  private int clics = 0;
  private final JLabel info = new JLabel("Mueva el raton sobre el panel");
  public Ej11_Raton() {
    super("Ej11 - Raton");
    final JPanel p = new JPanel(); p.setBackground(Color.LIGHT_GRAY);
    p.addMouseListener(new MouseAdapter() {
      @Override public void mouseEntered(MouseEvent e) { p.setBackground(Color.CYAN); }
      @Override public void mouseExited(MouseEvent e) { p.setBackground(Color.LIGHT_GRAY); }
      @Override public void mouseClicked(MouseEvent e) { clics++; info.setText("Clic en (" + e.getX() + ", " + e.getY() + ") | Clics: " + clics); }
    });
    p.addMouseMotionListener(new MouseMotionAdapter() {
      @Override public void mouseMoved(MouseEvent e) { info.setText("Posicion: (" + e.getX() + ", " + e.getY() + ") | Clics: " + clics); }
    });
    add(p, BorderLayout.CENTER); add(info, BorderLayout.SOUTH);
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej11_Raton f = new Ej11_Raton(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(400,250); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
