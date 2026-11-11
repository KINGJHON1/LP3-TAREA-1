// Ej16: calculadora con GridLayout(4,4). EXTRA: historial de la ultima operacion y control de division entre cero.
import javax.swing.*; import java.awt.*;
public class Ej16_Calculadora extends JFrame {
  private final JTextField campo = new JTextField("0");
  private final JLabel hist = new JLabel("Historial: -");
  private double acum = 0; private String op = null; private boolean nuevo = true;
  public Ej16_Calculadora() {
    super("Ej16 - Calculadora"); setLayout(new BorderLayout(5, 5));
    campo.setHorizontalAlignment(JTextField.RIGHT); campo.setEditable(false); campo.setFont(new Font("Monospaced", Font.BOLD, 22));
    add(campo, BorderLayout.NORTH);
    JPanel g = new JPanel(new GridLayout(4, 4, 5, 5));
    String[] t = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "C", "=", "+"};
    for (String s : t) { JButton b = new JButton(s); b.addActionListener(e -> pulsar(s)); g.add(b); }
    add(g, BorderLayout.CENTER); add(hist, BorderLayout.SOUTH);
  }
  private void pulsar(String s) {
    if (s.matches("[0-9]")) {
      if (nuevo) { campo.setText(""); nuevo = false; }
      campo.setText(campo.getText().equals("0") ? s : campo.getText() + s);
    } else if (s.equals("C")) {
      campo.setText("0"); acum = 0; op = null; nuevo = true; hist.setText("Historial: -");
    } else {
      if (op != null && !nuevo) calcular(); else acum = valor();
      op = s.equals("=") ? null : s; nuevo = true;
    }
  }
  private double valor() { try { return Double.parseDouble(campo.getText()); } catch (NumberFormatException e) { return 0; } }
  private String fmt(double d) { return d % 1 == 0 ? String.valueOf((long) d) : String.valueOf(d); }
  private void calcular() {
    double b = valor(), r = acum;
    switch (op) {
      case "+": r = acum + b; break;
      case "-": r = acum - b; break;
      case "*": r = acum * b; break;
      case "/":
        if (b == 0) { campo.setText("Error"); hist.setText("Historial: division entre cero"); acum = 0; return; }
        r = acum / b; break;
    }
    hist.setText("Historial: " + fmt(acum) + " " + op + " " + fmt(b) + " = " + fmt(r));
    campo.setText(fmt(r)); acum = r;
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej16_Calculadora f = new Ej16_Calculadora(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(280,340); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
