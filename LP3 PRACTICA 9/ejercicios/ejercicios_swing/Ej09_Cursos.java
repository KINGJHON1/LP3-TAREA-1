// Ej09: JList de cursos con JScrollPane y ListSelectionListener. EXTRA: campo + boton para agregar cursos.
import javax.swing.*; import java.awt.*;
public class Ej09_Cursos extends JFrame {
  public Ej09_Cursos() {
    super("Ej09 - Cursos"); setLayout(new FlowLayout());
    DefaultListModel<String> modelo = new DefaultListModel<>();
    for (String c : new String[]{"Programacion I", "Matematica", "Fisica", "Algebra", "Calculo", "Base de datos",
        "Redes", "Estadistica", "Ingles", "Peruanidad"}) modelo.addElement(c);
    JList<String> lista = new JList<>(modelo);
    lista.setVisibleRowCount(4); lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JLabel sel = new JLabel("Curso: (ninguno)");
    lista.addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting() && lista.getSelectedValue() != null) sel.setText("Curso: " + lista.getSelectedValue());
    });
    JTextField nuevo = new JTextField(10); JButton agregar = new JButton("Agregar");
    agregar.addActionListener(e -> {
      String t = nuevo.getText().trim();
      if (!t.isEmpty()) { modelo.addElement(t); nuevo.setText(""); lista.ensureIndexIsVisible(modelo.size() - 1); }
    });
    add(new JScrollPane(lista)); add(sel); add(nuevo); add(agregar);
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej09_Cursos f = new Ej09_Cursos(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(330,220); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
