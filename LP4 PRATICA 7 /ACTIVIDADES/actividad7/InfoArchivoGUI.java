import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

// Actividad 7: version grafica de la Actividad 1 usando JFileChooser
public class InfoArchivoGUI extends JFrame {
    private final JTextArea areaSalida = new JTextArea();

    public InfoArchivoGUI() {
        super("Informacion de archivos y directorios");
        areaSalida.setEditable(false);
        add(new JScrollPane(areaSalida));
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Muestra el cuadro de dialogo y devuelve la ruta elegida
    private Path obtenerRuta() {
        JFileChooser selector = new JFileChooser();
        selector.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        // showOpenDialog devuelve que boton se presiono (Abrir o Cancelar)
        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.CANCEL_OPTION) {
            System.exit(1);
        }
        return selector.getSelectedFile().toPath();
    }

    public void analizarRuta() {
        Path ruta = obtenerRuta();

        if (ruta != null && Files.exists(ruta)) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%s existe%n", ruta.getFileName()));
            sb.append(String.format("%s un directorio%n", Files.isDirectory(ruta) ? "Es" : "No es"));
            sb.append(String.format("%s una ruta absoluta%n", ruta.isAbsolute() ? "Es" : "No es"));

            try {
                sb.append(String.format("Ultima modificacion: %s%n", Files.getLastModifiedTime(ruta)));
                sb.append(String.format("Tamano (bytes): %s%n", Files.size(ruta)));
                sb.append(String.format("Ruta: %s%n", ruta));
                sb.append(String.format("Ruta absoluta: %s%n", ruta.toAbsolutePath()));

                if (Files.isDirectory(ruta)) {
                    sb.append(String.format("%nContenido del directorio:%n"));
                    try (DirectoryStream<Path> flujo = Files.newDirectoryStream(ruta)) {
                        for (Path p : flujo) {
                            sb.append(p).append(System.lineSeparator());
                        }
                    }
                }
            } catch (IOException e) {
                sb.append("Error de E/S: ").append(e.getMessage());
            }
            areaSalida.setText(sb.toString());
        } else {
            JOptionPane.showMessageDialog(this, "El archivo o directorio no existe",
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InfoArchivoGUI app = new InfoArchivoGUI();
            app.setVisible(true);
            app.analizarRuta();
        });
    }
}
