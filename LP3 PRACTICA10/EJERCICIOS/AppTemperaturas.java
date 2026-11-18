package principal;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Locale;

public class AppTemperaturas extends JFrame {
    private static final String[] DIAS = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
    private final JTextField[] campos = new JTextField[7];
    private final PanelLineas panel = new PanelLineas();

    public AppTemperaturas() {
        super("Temperatura semanal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 520);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel entrada = new JPanel(new BorderLayout(5, 5));
        JPanel camposPanel = new JPanel(new GridLayout(1, 14, 4, 4));
        for (int i = 0; i < 7; i++) {
            campos[i] = new JTextField(4);
            camposPanel.add(new JLabel(DIAS[i] + ":"));
            camposPanel.add(campos[i]);
        }
        JButton boton = new JButton("Mostrar gráfico");
        boton.addActionListener(e -> mostrarGrafico());
        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonPanel.add(boton);
        entrada.add(camposPanel, BorderLayout.CENTER);
        entrada.add(botonPanel, BorderLayout.SOUTH);

        add(entrada, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void mostrarGrafico() {
        double[] datos = new double[7];
        try {
            for (int i = 0; i < 7; i++) {
                String texto = campos[i].getText().trim();
                texto = texto.replace(',', '.');
                datos[i] = Double.parseDouble(texto);
                if (!Double.isFinite(datos[i])) {
                    throw new NumberFormatException("Temperatura no finita");
                }
            }
            panel.setTemperaturas(datos);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Ingrese una temperatura numérica para cada día.",
                "Datos inválidos", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppTemperaturas::new);
    }

    static class PanelLineas extends JPanel {
        private double[] temperaturas;

        public void setTemperaturas(double[] t) {
            this.temperaturas = t;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            int mIzq = 60, mDer = 30, mSup = 30, mInf = 50;
            int ancho = getWidth() - mIzq - mDer;
            int alto = getHeight() - mSup - mInf;

            if (temperaturas == null) {
                g2.drawString("Ingrese las temperaturas y presione \"Mostrar Gráfico\"", 20, 30);
                return;
            }

            double min = Arrays.stream(temperaturas).min().getAsDouble() - 2;
            double max = Arrays.stream(temperaturas).max().getAsDouble() + 2;
            if (min == max) {
                min -= 1;
                max += 1;
            }

            g2.setColor(Color.LIGHT_GRAY);
            for (int k = 0; k <= 5; k++) {
                int y = mSup + alto - k * alto / 5;
                g2.drawLine(mIzq, y, mIzq + ancho, y);
                g2.setColor(Color.BLACK);
                g2.drawString(String.format(Locale.ROOT, "%.1f°",
                        min + k * (max - min) / 5), 10, y + 4);
                g2.setColor(Color.LIGHT_GRAY);
            }

            g2.setColor(Color.BLACK);
            g2.drawLine(mIzq, mSup, mIzq, mSup + alto);
            g2.drawLine(mIzq, mSup + alto, mIzq + ancho, mSup + alto);

            int[] xs = new int[7];
            int[] ys = new int[7];
            for (int i = 0; i < 7; i++) {
                xs[i] = mIzq + i * ancho / 6;
                ys[i] = (int) (mSup + alto - (temperaturas[i] - min) / (max - min) * alto);
            }

            g2.setStroke(new BasicStroke(2f));
            g2.setColor(new Color(30, 110, 200));
            for (int i = 0; i < 6; i++) {
                g2.drawLine(xs[i], ys[i], xs[i + 1], ys[i + 1]);
            }

            for (int i = 0; i < 7; i++) {
                g2.setColor(Color.RED);
                g2.fillOval(xs[i] - 4, ys[i] - 4, 8, 8);
                g2.setColor(Color.BLACK);
                g2.drawString(String.valueOf(temperaturas[i]), xs[i] - 10, ys[i] - 10);
                g2.drawString(DIAS[i], xs[i] - 10, mSup + alto + 20);
            }
        }
    }
}
