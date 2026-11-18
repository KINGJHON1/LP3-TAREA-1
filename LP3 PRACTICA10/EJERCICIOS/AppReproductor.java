package principal;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.stream.Stream;

public class AppReproductor extends JFrame {
    private Clip clip;
    private long posicion = 0;
    private File archivoTemporal;

    public AppReproductor() {
        super("Reproductor de Música");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 110);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        JButton btnReproducir = new JButton("Reproducir");
        JButton btnPausar = new JButton("Pausar");
        JButton btnReanudar = new JButton("Reanudar");

        btnReproducir.addActionListener(e -> reproducir());
        btnPausar.addActionListener(e -> pausar());
        btnReanudar.addActionListener(e -> reanudar());

        add(btnReproducir);
        add(btnPausar);
        add(btnReanudar);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarClip();
                eliminarArchivoTemporal();
            }
        });

        setVisible(true);
    }

    private void reproducir() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar archivo de audio");
        selector.setFileFilter(new FileNameExtensionFilter(
                "Archivos de audio (*.wav, *.flac)", "wav", "flac"));

        File predeterminado = new File("musica.wav");
        if (predeterminado.isFile()) {
            selector.setSelectedFile(predeterminado);
        }

        if (selector.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();
        if (!archivo.isFile()) {
            mostrarError("El archivo seleccionado no existe.");
            return;
        }

        if (archivo.getName().toLowerCase(Locale.ROOT).endsWith(".flac")) {
            archivo = convertirFlacAWav(archivo);
            if (archivo == null) {
                return;
            }
        } else {
            eliminarArchivoTemporal();
        }

        try (AudioInputStream audio = AudioSystem.getAudioInputStream(archivo)) {
            Clip nuevoClip = AudioSystem.getClip();
            nuevoClip.open(audio);
            cerrarClip();
            clip = nuevoClip;
            posicion = 0;
            clip.setMicrosecondPosition(0);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            mostrarError("No se pudo reproducir: " + obtenerDetalle(ex));
        }
    }

    private File convertirFlacAWav(File archivoFlac) {
        try {
            eliminarArchivoTemporal();
            archivoTemporal = Files.createTempFile("reproductor-", ".wav").toFile();

            String ejecutableFfmpeg = obtenerEjecutableFfmpeg();
            if (ejecutableFfmpeg == null) {
                eliminarArchivoTemporal();
                mostrarError("No se encontró FFmpeg. "
                        + "Instálalo y reinicia VS Code o vuelve a iniciar el programa.");
                return null;
            }

            Process proceso = new ProcessBuilder(
                    ejecutableFfmpeg,
                    "-y",
                    "-v", "error",
                    "-i", archivoFlac.getAbsolutePath(),
                    "-vn",
                    "-acodec", "pcm_s16le",
                    archivoTemporal.getAbsolutePath())
                    .redirectErrorStream(true)
                    .start();

            int codigoSalida = proceso.waitFor();
            if (codigoSalida != 0 || archivoTemporal.length() == 0) {
                eliminarArchivoTemporal();
                mostrarError("No se pudo convertir el archivo FLAC. "
                        + "Verifica que FFmpeg esté instalado y disponible en el PATH.");
                return null;
            }

            return archivoTemporal;
        } catch (IOException ex) {
            eliminarArchivoTemporal();
            mostrarError("No se pudo iniciar FFmpeg. "
                    + "Instálalo y agrega ffmpeg al PATH del sistema.");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            eliminarArchivoTemporal();
            mostrarError("Se interrumpió la conversión del archivo FLAC.");
        }
        return null;
    }

    private String obtenerEjecutableFfmpeg() {
        try {
            new ProcessBuilder("ffmpeg", "-version").start();
            return "ffmpeg";
        } catch (IOException ignored) {
        }

        String localAppData = System.getenv("LOCALAPPDATA");
        if (localAppData != null) {
            Path winGetPackages = new File(localAppData,
                    "Microsoft\\WinGet\\Packages").toPath();
            if (Files.isDirectory(winGetPackages)) {
                try (Stream<Path> rutas = Files.walk(winGetPackages)) {
                    return rutas
                            .filter(ruta -> ruta.getFileName().toString()
                                    .equalsIgnoreCase("ffmpeg.exe"))
                            .map(Path::toString)
                            .findFirst()
                            .orElse(null);
                } catch (IOException ignored) {
                }
            }
        }

        return null;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this,
                mensaje,
                "Error de reproducción",
                JOptionPane.ERROR_MESSAGE);
    }

    private String obtenerDetalle(Exception ex) {
        String detalle = ex.getMessage();
        if (detalle == null || detalle.isBlank()) {
            detalle = ex.getClass().getSimpleName();
        }
        return detalle;
    }

    private void eliminarArchivoTemporal() {
        if (archivoTemporal != null) {
            try {
                Files.deleteIfExists(archivoTemporal.toPath());
            } catch (IOException ignored) {
            }
            archivoTemporal = null;
        }
    }

    private void pausar() {
        if (clip != null && clip.isRunning()) {
            posicion = clip.getMicrosecondPosition();
            clip.stop();
        }
    }

    private void reanudar() {
        if (clip != null && !clip.isRunning()) {
            if (posicion >= clip.getMicrosecondLength()) {
                posicion = 0;
            }
            clip.setMicrosecondPosition(posicion);
            clip.start();
        }
    }

    private void cerrarClip() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
            posicion = 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppReproductor::new);
    }
}
