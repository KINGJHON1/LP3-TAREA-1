// Ej18: alterna entre tres GridLayout (2x3, 3x2, 1x6) con validate(). EXTRA: el titulo muestra la cuadricula actual.
import javax.swing.*; import java.awt.*; import java.awt.event.*;
public class Ej18_Cuadriculas extends JFrame implements ActionListener {
  private final GridLayout[] grids = {new GridLayout(2, 3, 5, 5), new GridLayout(3, 2), new GridLayout(1, 6, 3, 3)};
  private final String[] nombres = {"2x3 con espacios", "3x2 sin espacios", "1x6 con espacios"};
  private int actual = 0; private final Container contenedor;
  public Ej18_Cuadriculas() {
    super("Ej18 - " + "2x3 con espacios");
    contenedor = getContentPane(); setLayout(grids[0]);
    for (String s : new String[]{"uno", "dos", "tres", "cuatro", "cinco", "seis"}) { JButton b = new JButton(s); b.addActionListener(this); add(b); }
  }
  @Override public void actionPerformed(ActionEvent e) {
    actual = (actual + 1) % grids.length;
    contenedor.setLayout(grids[actual]); setTitle("Ej18 - " + nombres[actual]);
    contenedor.validate();
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej18_Cuadriculas f = new Ej18_Cuadriculas(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(400,200); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
