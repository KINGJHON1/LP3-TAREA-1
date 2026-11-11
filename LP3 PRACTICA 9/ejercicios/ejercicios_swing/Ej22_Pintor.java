// Ej22: pintor basico: colores (GridLayout), grosor (JRadioButton) y lienzo con eventos de raton. EXTRA: boton Deshacer.
import javax.swing.*; import java.awt.*; import java.awt.event.*;
public class Ej22_Pintor extends JFrame {
  private static class Trazo { java.util.ArrayList<Point> pts = new java.util.ArrayList<>(); Color c; int g; }
  private final java.util.ArrayList<Trazo> trazos = new java.util.ArrayList<>();
  private Color actual = Color.BLACK; private int grosor = 4;
  private class Lienzo extends JPanel {
    Lienzo() {
      setBackground(Color.WHITE);
      MouseAdapter ma = new MouseAdapter() {
        @Override public void mousePressed(MouseEvent e) { Trazo t = new Trazo(); t.c = actual; t.g = grosor; t.pts.add(e.getPoint()); trazos.add(t); repaint(); }
        @Override public void mouseDragged(MouseEvent e) { if (!trazos.isEmpty()) { trazos.get(trazos.size() - 1).pts.add(e.getPoint()); repaint(); } }
      };
      addMouseListener(ma); addMouseMotionListener(ma);
    }
    @Override protected void paintComponent(Graphics g) {
      super.paintComponent(g); Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      for (Trazo t : trazos) {
        g2.setColor(t.c); g2.setStroke(new BasicStroke(t.g, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        Point p0 = t.pts.get(0); if (t.pts.size() == 1) g2.drawLine(p0.x, p0.y, p0.x, p0.y);
        for (int i = 1; i < t.pts.size(); i++) g2.drawLine(t.pts.get(i - 1).x, t.pts.get(i - 1).y, t.pts.get(i).x, t.pts.get(i).y);
      }
    }
  }
  public Ej22_Pintor() {
    super("Ej22 - Pintor");
    String[] nc = {"Negro", "Rojo", "Verde", "Azul", "Naranja", "Magenta"};
    Color[] cc = {Color.BLACK, Color.RED, new Color(0, 150, 0), Color.BLUE, Color.ORANGE, Color.MAGENTA};
    JPanel colores = new JPanel(new GridLayout(1, nc.length, 3, 3));
    for (int i = 0; i < nc.length; i++) {
      final Color c = cc[i]; JButton b = new JButton(nc[i]); b.setForeground(c); b.addActionListener(e -> actual = c); colores.add(b);
    }
    JPanel pg = new JPanel(new GridLayout(3, 1)); pg.setBorder(BorderFactory.createTitledBorder("Grosor")); ButtonGroup g = new ButtonGroup();
    for (int t : new int[]{2, 6, 12}) {
      JRadioButton r = new JRadioButton(t + " px", t == 2); g.add(r); pg.add(r); r.addActionListener(e -> grosor = t);
    }
    grosor = 2;
    JButton deshacer = new JButton("Deshacer"), limpiar = new JButton("Limpiar");
    deshacer.addActionListener(e -> { if (!trazos.isEmpty()) { trazos.remove(trazos.size() - 1); repaint(); } });
    limpiar.addActionListener(e -> { trazos.clear(); repaint(); });
    JPanel sur = new JPanel(); sur.add(deshacer); sur.add(limpiar);
    add(colores, BorderLayout.NORTH); add(pg, BorderLayout.WEST); add(new Lienzo(), BorderLayout.CENTER); add(sur, BorderLayout.SOUTH);
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej22_Pintor f = new Ej22_Pintor(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(600,420); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
