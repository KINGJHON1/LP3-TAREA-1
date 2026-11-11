// Ej12: dibujo libre con mouseDragged. EXTRA: botones de color y Limpiar.
import javax.swing.*; import java.awt.*; import java.awt.event.*;
public class Ej12_Dibujo extends JFrame {
  private static class Trazo { java.util.ArrayList<Point> pts = new java.util.ArrayList<>(); Color c; }
  private final java.util.ArrayList<Trazo> trazos = new java.util.ArrayList<>();
  private Color actual = Color.BLACK;
  private class Lienzo extends JPanel {
    Lienzo() {
      setBackground(Color.WHITE);
      MouseAdapter ma = new MouseAdapter() {
        @Override public void mousePressed(MouseEvent e) { Trazo t = new Trazo(); t.c = actual; t.pts.add(e.getPoint()); trazos.add(t); repaint(); }
        @Override public void mouseDragged(MouseEvent e) { if (!trazos.isEmpty()) { trazos.get(trazos.size() - 1).pts.add(e.getPoint()); repaint(); } }
      };
      addMouseListener(ma); addMouseMotionListener(ma);
    }
    @Override protected void paintComponent(Graphics g) {
      super.paintComponent(g); Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
      for (Trazo t : trazos) {
        g2.setColor(t.c);
        if (t.pts.size() == 1) g2.drawLine(t.pts.get(0).x, t.pts.get(0).y, t.pts.get(0).x, t.pts.get(0).y);
        for (int i = 1; i < t.pts.size(); i++) g2.drawLine(t.pts.get(i - 1).x, t.pts.get(i - 1).y, t.pts.get(i).x, t.pts.get(i).y);
      }
    }
  }
  public Ej12_Dibujo() {
    super("Ej12 - Dibujo libre");
    add(new Lienzo(), BorderLayout.CENTER);
    JPanel barra = new JPanel();
    String[] nombres = {"Negro", "Rojo", "Verde", "Azul"};
    Color[] cols = {Color.BLACK, Color.RED, new Color(0, 150, 0), Color.BLUE};
    for (int i = 0; i < nombres.length; i++) { final Color c = cols[i]; JButton b = new JButton(nombres[i]); b.addActionListener(e -> actual = c); barra.add(b); }
    JButton limpiar = new JButton("Limpiar"); limpiar.addActionListener(e -> { trazos.clear(); repaint(); });
    barra.add(limpiar); add(barra, BorderLayout.SOUTH);
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej12_Dibujo f = new Ej12_Dibujo(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(450,350); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
