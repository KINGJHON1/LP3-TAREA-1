// Ej04: pedido de pizza (base S/20 + S/3 por ingrediente). EXTRA: casilla Delivery (+S/5).
import javax.swing.*; import java.awt.*;
public class Ej04_Pizza extends JFrame {
  public Ej04_Pizza() {
    super("Ej04 - Pizza"); setLayout(new GridLayout(0, 1));
    String[] ing = {"Queso extra", "Jamon", "Champinones", "Aceitunas"};
    JCheckBox[] cb = new JCheckBox[ing.length];
    for (int i = 0; i < ing.length; i++) { cb[i] = new JCheckBox(ing[i] + " (+S/ 3)"); add(cb[i]); }
    JCheckBox delivery = new JCheckBox("Delivery (+S/ 5)"); add(delivery);
    JButton calcular = new JButton("Calcular"); add(calcular);
    JLabel total = new JLabel("Total: S/ 20.00", SwingConstants.CENTER); add(total);
    calcular.addActionListener(e -> {
      double t = 20;
      for (JCheckBox c : cb) if (c.isSelected()) t += 3;
      if (delivery.isSelected()) t += 5;
      total.setText(String.format("Total: S/ %.2f", t));
    });
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej04_Pizza f = new Ej04_Pizza(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(280,280); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
