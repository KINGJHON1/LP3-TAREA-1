// Ej17: tres en raya 3x3 con GridLayout y turnos con boolean. EXTRA: deteccion de ganador/empate y boton Reiniciar.
import javax.swing.*; import java.awt.*;
public class Ej17_TresEnRaya extends JFrame {
  private final JButton[][] c = new JButton[3][3];
  private final JLabel estado = new JLabel("Turno de X", SwingConstants.CENTER);
  private boolean turnoX = true, terminado = false; private int jugadas = 0;
  public Ej17_TresEnRaya() {
    super("Ej17 - Tres en raya");
    JPanel tablero = new JPanel(new GridLayout(3, 3, 5, 5));
    for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) {
      c[i][j] = new JButton(""); c[i][j].setFont(new Font("SansSerif", Font.BOLD, 36));
      c[i][j].addActionListener(e -> jugar((JButton) e.getSource())); tablero.add(c[i][j]);
    }
    JButton reiniciar = new JButton("Reiniciar");
    reiniciar.addActionListener(e -> {
      for (JButton[] fila : c) for (JButton b : fila) b.setText("");
      turnoX = true; terminado = false; jugadas = 0; estado.setText("Turno de X");
    });
    add(estado, BorderLayout.NORTH); add(tablero, BorderLayout.CENTER); add(reiniciar, BorderLayout.SOUTH);
  }
  private void jugar(JButton b) {
    if (terminado || !b.getText().isEmpty()) return;
    b.setText(turnoX ? "X" : "O"); jugadas++;
    if (gana()) { estado.setText("Gana " + (turnoX ? "X" : "O") + "!"); terminado = true; }
    else if (jugadas == 9) { estado.setText("Empate"); terminado = true; }
    else { turnoX = !turnoX; estado.setText("Turno de " + (turnoX ? "X" : "O")); }
  }
  private boolean iguales(JButton a, JButton b, JButton d) {
    return !a.getText().isEmpty() && a.getText().equals(b.getText()) && a.getText().equals(d.getText());
  }
  private boolean gana() {
    for (int i = 0; i < 3; i++) if (iguales(c[i][0], c[i][1], c[i][2]) || iguales(c[0][i], c[1][i], c[2][i])) return true;
    return iguales(c[0][0], c[1][1], c[2][2]) || iguales(c[0][2], c[1][1], c[2][0]);
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej17_TresEnRaya f = new Ej17_TresEnRaya(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(300,360); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
