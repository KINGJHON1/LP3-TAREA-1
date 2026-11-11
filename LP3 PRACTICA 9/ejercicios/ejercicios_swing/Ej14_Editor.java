// Ej14: editor simple con BorderLayout. EXTRA: contador de caracteres y palabras en la barra de estado.
import javax.swing.*; import javax.swing.event.DocumentEvent; import javax.swing.event.DocumentListener;
import java.awt.*; import java.io.File; import java.io.IOException; import java.nio.file.Files;
public class Ej14_Editor extends JFrame {
  private final JTextArea area = new JTextArea();
  private final JLabel estado = new JLabel("Caracteres: 0 | Palabras: 0");
  private final DefaultListModel<String> archivos = new DefaultListModel<>();
  public Ej14_Editor() {
    super("Ej14 - Editor");
    JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton nuevo = new JButton("Nuevo"), abrir = new JButton("Abrir"), guardar = new JButton("Guardar");
    barra.add(nuevo); barra.add(abrir); barra.add(guardar);
    nuevo.addActionListener(e -> area.setText(""));
    abrir.addActionListener(e -> {
      JFileChooser fc = new JFileChooser();
      if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        File f = fc.getSelectedFile();
        try { area.setText(new String(Files.readAllBytes(f.toPath()))); registrar(f.getName()); }
        catch (IOException ex) { JOptionPane.showMessageDialog(this, "No se pudo abrir: " + ex.getMessage()); }
      }
    });
    guardar.addActionListener(e -> {
      JFileChooser fc = new JFileChooser();
      if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        File f = fc.getSelectedFile();
        try { Files.write(f.toPath(), area.getText().getBytes()); registrar(f.getName()); }
        catch (IOException ex) { JOptionPane.showMessageDialog(this, "No se pudo guardar: " + ex.getMessage()); }
      }
    });
    area.getDocument().addDocumentListener(new DocumentListener() {
      public void insertUpdate(DocumentEvent e) { contar(); }
      public void removeUpdate(DocumentEvent e) { contar(); }
      public void changedUpdate(DocumentEvent e) { contar(); }
    });
    JList<String> lista = new JList<>(archivos);
    JScrollPane izq = new JScrollPane(lista); izq.setPreferredSize(new Dimension(120, 0));
    add(barra, BorderLayout.NORTH); add(izq, BorderLayout.WEST);
    add(new JScrollPane(area), BorderLayout.CENTER); add(estado, BorderLayout.SOUTH);
  }
  private void registrar(String nombre) { if (!archivos.contains(nombre)) archivos.addElement(nombre); }
  private void contar() {
    String t = area.getText().trim();
    estado.setText("Caracteres: " + area.getText().length() + " | Palabras: " + (t.isEmpty() ? 0 : t.split("\\s+").length));
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej14_Editor f = new Ej14_Editor(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(600,400); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
