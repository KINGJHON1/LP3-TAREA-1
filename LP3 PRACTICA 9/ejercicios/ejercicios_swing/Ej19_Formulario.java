// Ej19: formulario con BorderLayout + GridLayout + FlowLayout. EXTRA: validacion de campos (nombre, correo, edad).
import javax.swing.*; import java.awt.*;
public class Ej19_Formulario extends JFrame {
  private final JTextField nombre = new JTextField(15), correo = new JTextField(15), edad = new JTextField(15), ciudad = new JTextField(15);
  private final JLabel mensaje = new JLabel(" ", SwingConstants.CENTER);
  public Ej19_Formulario() {
    super("Ej19 - Registro");
    JLabel titulo = new JLabel("Formulario de registro", SwingConstants.CENTER); titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
    JPanel campos = new JPanel(new GridLayout(4, 2, 5, 5)); campos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    campos.add(new JLabel("Nombre:")); campos.add(nombre); campos.add(new JLabel("Correo:")); campos.add(correo);
    campos.add(new JLabel("Edad:")); campos.add(edad); campos.add(new JLabel("Ciudad:")); campos.add(ciudad);
    JButton guardar = new JButton("Guardar"), cancelar = new JButton("Cancelar");
    JPanel botones = new JPanel(new FlowLayout()); botones.add(guardar); botones.add(cancelar);
    JPanel sur = new JPanel(new BorderLayout()); sur.add(mensaje, BorderLayout.NORTH); sur.add(botones, BorderLayout.CENTER);
    add(titulo, BorderLayout.NORTH); add(campos, BorderLayout.CENTER); add(sur, BorderLayout.SOUTH);
    guardar.addActionListener(e -> guardar());
    cancelar.addActionListener(e -> { nombre.setText(""); correo.setText(""); edad.setText(""); ciudad.setText(""); mensaje.setText(" "); });
  }
  private void guardar() {
    String err = null; int ed = 0;
    if (nombre.getText().trim().isEmpty()) err = "El nombre es obligatorio";
    else if (!correo.getText().trim().matches(".+@.+\\..+")) err = "Correo no valido";
    else try { ed = Integer.parseInt(edad.getText().trim()); if (ed < 1 || ed > 120) err = "Edad fuera de rango (1-120)"; }
    catch (NumberFormatException ex) { err = "La edad debe ser un numero entero"; }
    if (err != null) { mensaje.setForeground(Color.RED); mensaje.setText(err); return; }
    mensaje.setForeground(new Color(0, 130, 0)); mensaje.setText("Registro guardado");
    JOptionPane.showMessageDialog(this, "Nombre: " + nombre.getText() + "\nCorreo: " + correo.getText() + "\nEdad: " + ed + "\nCiudad: " + ciudad.getText());
  }
  public static void main(String[] a){ SwingUtilities.invokeLater(() -> { Ej19_Formulario f = new Ej19_Formulario(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(360,260); f.setLocationRelativeTo(null); f.setVisible(true); }); }
}
