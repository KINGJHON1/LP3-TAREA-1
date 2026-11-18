package ejercicid;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ReproductorEfectos extends JFrame {

    private File[] archivosWav;

    public ReproductorEfectos() {
        setTitle("Efectos de Sonido");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));

        File carpetaProyecto = new File(".");
        archivosWav = carpetaProyecto.listFiles((dir, nombre) -> nombre.toLowerCase().endsWith(".wav"));

        JButton btnAplausos = new JButton("Aplausos");
        JButton btnCampana = new JButton("Campana");
        JButton btnExplosion = new JButton("Explosión");

        btnAplausos.addActionListener(e -> reproducirPorIndice(0));
        btnCampana.addActionListener(e -> reproducirPorIndice(1));
        btnExplosion.addActionListener(e -> reproducirPorIndice(2));

        add(btnAplausos);
        add(btnCampana);
        add(btnExplosion);
    }

    private void reproducirPorIndice(int indice) {
        if (archivosWav != null && indice < archivosWav.length) {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivosWav[indice]);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al reproducir el audio.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Falta agregar más archivos .wav en la carpeta del proyecto.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReproductorEfectos().setVisible(true));
    }
}
