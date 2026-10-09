import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

// Actividad 4: muestra el contenido de un archivo de texto en un JTextArea
// usando un FileInputStream. Debe existir TestFile.java en la misma carpeta.
public class VisualizarArchivo extends JFrame {

    public VisualizarArchivo(String ruta) {
        super("Contenido de " + ruta);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        try (FileInputStream entrada = new FileInputStream(ruta)) {
            byte[] datos = entrada.readAllBytes();
            area.setText(new String(datos, StandardCharsets.UTF_8));
        } catch (IOException e) {
            area.setText("No se pudo leer el archivo: " + e.getMessage());
        }

        add(new JScrollPane(area));
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisualizarArchivo("TestFile.java").setVisible(true));
    }
}
