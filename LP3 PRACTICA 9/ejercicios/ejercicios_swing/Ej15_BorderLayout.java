// Ej15: BorderLayout que oculta la region oprimida (Fig. 12.41). EXTRA: el titulo indica que region esta oculta.
// Prueba: si agrega dos componentes a la misma region, solo se ve el ultimo (Error comun 12.5).
import javax.swing.*; import java.awt.*; import java.awt.event.*;
public class Ej15_BorderLayout extends JFrame implements ActionListener {
  private final JButton[] botones;
  private static final String[] NOMBRES = {"Ocultar Norte", "Ocultar Sur", "Ocultar Este", "Ocultar Oeste", "Ocultar Centro"};
  private final BorderLayout esquema = new BorderLayout(5, 5);
  public Ej15_BorderLayout() {
    super("Ej15 - BorderLayout"); setLayout(esquema);
    botones = new JButton[NOMBRES.length];
    for (int i = 0; i < NOMBRES.length; i++) { botones[i] = new JButton(NOMBRES[i]); botones[i].addActionListener(this); }
    add(botones[0], BorderLayout.NORTH); add(botones[1], BorderLayout.SOUTH);
    add(botones[2], BorderLayout.EAST); add(botones[3], BorderLayout.WEST); add(botones[4], BorderLayout.CENTER);
  }
  @Override public void actionPerformed(ActionEvent evento) {
    for (JButton b : botones) {
      if (evento.getSource() == b) { b.setVisible(false); setTitle("Ej15 - Oculto: " + b.getText().replace("Ocultar ", "")); }
      else b.setVisible(true);
    }
    esquema.layoutContainer(getContentPane());
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej15_BorderLayout f = new Ej15_BorderLayout(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(400,250); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
