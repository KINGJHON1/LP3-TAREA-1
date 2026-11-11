// Ej13: 8 botones en FlowLayout con alineacion variable. EXTRA: JSlider para el espacio entre componentes.
import javax.swing.*; import java.awt.*;
public class Ej13_FlowLayout extends JFrame {
  public Ej13_FlowLayout() {
    super("Ej13 - FlowLayout");
    FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 5, 5);
    JPanel centro = new JPanel(fl);
    for (int i = 1; i <= 8; i++) centro.add(new JButton("Btn " + i));
    JPanel ctrl = new JPanel();
    JButton izq = new JButton("Izquierda"), cen = new JButton("Centro"), der = new JButton("Derecha");
    izq.addActionListener(e -> { fl.setAlignment(FlowLayout.LEFT); centro.revalidate(); });
    cen.addActionListener(e -> { fl.setAlignment(FlowLayout.CENTER); centro.revalidate(); });
    der.addActionListener(e -> { fl.setAlignment(FlowLayout.RIGHT); centro.revalidate(); });
    JSlider sl = new JSlider(0, 40, 5);
    sl.addChangeListener(e -> { fl.setHgap(sl.getValue()); fl.setVgap(sl.getValue()); centro.revalidate(); });
    ctrl.add(izq); ctrl.add(cen); ctrl.add(der); ctrl.add(new JLabel("Espacio:")); ctrl.add(sl);
    add(centro, BorderLayout.CENTER); add(ctrl, BorderLayout.SOUTH);
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej13_FlowLayout f = new Ej13_FlowLayout(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(480,200); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
