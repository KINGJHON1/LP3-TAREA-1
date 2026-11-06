import javax.swing.*;
import java.awt.*;

public class CompraPasaje extends JFrame {

    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtDocumento = new JTextField(20);
    private final JTextField txtFecha = new JTextField("dd/mm/aaaa", 20);

    private final JCheckBox chkAudifonos = new JCheckBox("Headphones");
    private final JCheckBox chkManta = new JCheckBox("Manta");
    private final JCheckBox chkRevistas = new JCheckBox("Revistas");

    private final JRadioButton rbPiso1 = new JRadioButton("1er piso", true);
    private final JRadioButton rbPiso2 = new JRadioButton("2do piso");

    private static final String[] CIUDADES = {
        "Arequipa", "Lima", "Cusco", "Tacna", "Puno", "Ica", "Trujillo"
    };
    // Tarifas de ejemplo en soles para Economy; cada ruta tiene el mismo precio en ambos sentidos.
    private static final int[][] TARIFAS_ECONOMICAS = {
        {  0, 100,  70,  45,  40,  75, 150},
        {100,   0, 100, 130, 110,  45,  65},
        { 70, 100,   0, 100,  45, 130, 140},
        { 45, 130, 100,   0,  55, 140, 170},
        { 40, 110,  45,  55,   0, 120, 150},
        { 75,  45, 130, 140, 120,   0, 100},
        {150,  65, 140, 170, 150, 100,   0}
    };
    private final JComboBox<String> cboOrigen = new JComboBox<>(CIUDADES);
    private final JComboBox<String> cboDestino = new JComboBox<>(CIUDADES);

    private final JList<String> lstServicio =
        new JList<>(new String[]{"Economy", "Standard", "VIP"});
    private final JLabel lblPrecio = new JLabel();

    public CompraPasaje() {
        // TODO: agregue aquí los integrantes del grupo
        super("Compra de pasajes - Integrantes: Alumno 1, Alumno 2, Alumno 3");
        setLayout(new BorderLayout(10, 10));

        // --- Datos personales (GridLayout) ---
        JPanel pDatos = new JPanel(new GridLayout(3, 2, 5, 5));
        pDatos.setBorder(BorderFactory.createTitledBorder("Datos del pasajero"));
        pDatos.add(new JLabel("Nombres y apellidos:"));
        pDatos.add(txtNombre);
        pDatos.add(new JLabel("Documento de identidad:"));
        pDatos.add(txtDocumento);
        pDatos.add(new JLabel("Fecha de viaje:"));
        pDatos.add(txtFecha);

        // --- Ruta ---
        cboDestino.setSelectedIndex(1);
        JPanel pRuta = new JPanel(new GridLayout(2, 2, 5, 5));
        pRuta.setBorder(BorderFactory.createTitledBorder("Ruta"));
        pRuta.add(new JLabel("Origen:"));
        pRuta.add(cboOrigen);
        pRuta.add(new JLabel("Destino:"));
        pRuta.add(cboDestino);

        // --- Piso ---
        ButtonGroup grupoPiso = new ButtonGroup();
        grupoPiso.add(rbPiso1);
        grupoPiso.add(rbPiso2);
        JPanel pPiso = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pPiso.setBorder(BorderFactory.createTitledBorder("Piso del bus"));
        pPiso.add(rbPiso1);
        pPiso.add(rbPiso2);

        // --- Servicios opcionales ---
        JPanel pOpc = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pOpc.setBorder(BorderFactory.createTitledBorder("Servicios opcionales"));
        pOpc.add(chkAudifonos);
        pOpc.add(chkManta);
        pOpc.add(chkRevistas);

        // --- Calidad de servicio ---
        lstServicio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstServicio.setSelectedIndex(1);
        JPanel pCalidad = new JPanel(new BorderLayout());
        pCalidad.setBorder(BorderFactory.createTitledBorder("Calidad de servicio"));
        pCalidad.add(new JScrollPane(lstServicio), BorderLayout.CENTER);
        pCalidad.add(lblPrecio, BorderLayout.SOUTH);

        // Columna izquierda y derecha
        JPanel izquierda = new JPanel(new GridLayout(4, 1, 5, 5));
        izquierda.add(pDatos);
        izquierda.add(pRuta);
        izquierda.add(pPiso);
        izquierda.add(pOpc);

        JPanel centro = new JPanel(new GridLayout(1, 2, 10, 10));
        centro.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        centro.add(izquierda);
        centro.add(pCalidad);

        // --- Botones ---
        JButton btnResumen = new JButton("Mostrar resumen");
        JButton btnReiniciar = new JButton("Reiniciar");
        btnResumen.addActionListener(e -> mostrarResumen());
        btnReiniciar.addActionListener(e -> reiniciar());

        JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pBotones.add(btnResumen);
        pBotones.add(btnReiniciar);

        add(new JLabel("Compra de pasajes - Transporte terrestre", SwingConstants.CENTER),
            BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(pBotones, BorderLayout.SOUTH);

        cboOrigen.addActionListener(e -> actualizarPrecio());
        cboDestino.addActionListener(e -> actualizarPrecio());
        lstServicio.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) actualizarPrecio();
        });
        actualizarPrecio();

        aplicarFondoNegro(getContentPane());

        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void aplicarFondoNegro(Component componente) {
        componente.setBackground(new Color(45, 45, 45));
        componente.setForeground(Color.WHITE);

        if (componente instanceof JComponent) {
            JComponent componenteSwing = (JComponent) componente;
            componenteSwing.setOpaque(true);
            if (componenteSwing.getBorder() instanceof javax.swing.border.TitledBorder) {
                ((javax.swing.border.TitledBorder) componenteSwing.getBorder())
                    .setTitleColor(Color.WHITE);
            }
        }

        if (componente instanceof JTextField) {
            componente.setBackground(new Color(255, 245, 170));
            componente.setForeground(Color.BLACK);
            componente.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            ((JTextField) componente).setCaretColor(new Color(190, 130, 0));
        } else if (componente instanceof JComboBox || componente instanceof JList
                || componente instanceof JButton) {
            componente.setBackground(new Color(60, 60, 60));
        }

        if (componente instanceof Container) {
            for (Component hijo : ((Container) componente).getComponents()) {
                aplicarFondoNegro(hijo);
            }
        }
    }

    private int calcularPrecio() {
        int origen = cboOrigen.getSelectedIndex();
        int destino = cboDestino.getSelectedIndex();
        int servicio = lstServicio.getSelectedIndex();
        if (origen < 0 || destino < 0 || servicio < 0 || origen == destino) return 0;

        int tarifaEconomica = TARIFAS_ECONOMICAS[origen][destino];
        int[] porcentajes = {100, 150, 200};
        return tarifaEconomica * porcentajes[servicio] / 100;
    }

    private void actualizarPrecio() {
        if (cboOrigen.getSelectedIndex() == cboDestino.getSelectedIndex()) {
            lblPrecio.setText("Elija ciudades distintas para ver el precio.");
            return;
        }
        lblPrecio.setText("Precio por pasaje: " + formatoPrecio(calcularPrecio()));
    }

    private String formatoPrecio(int precio) {
        return String.format(java.util.Locale.US, "S/ %.2f", (double) precio);
    }

    private void mostrarResumen() {
        String nombre = txtNombre.getText().trim();
        String doc = txtDocumento.getText().trim();
        String fecha = txtFecha.getText().trim();

        if (nombre.isEmpty() || doc.isEmpty() || fecha.isEmpty()
                || fecha.equals("dd/mm/aaaa")) {
            JOptionPane.showMessageDialog(this,
                "Complete nombre, documento y fecha de viaje.",
                "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cboOrigen.getSelectedItem().equals(cboDestino.getSelectedItem())) {
            JOptionPane.showMessageDialog(this,
                "El origen y el destino deben ser distintos.",
                "Ruta inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder extras = new StringBuilder();
        if (chkAudifonos.isSelected()) extras.append("Headphones ");
        if (chkManta.isSelected()) extras.append("Manta ");
        if (chkRevistas.isSelected()) extras.append("Revistas ");
        if (extras.length() == 0) extras.append("Ninguno");

        String servicio = lstServicio.getSelectedValue();
        String piso = rbPiso1.isSelected() ? "1er piso" : "2do piso";

        String resumen =
            "Pasajero: " + nombre + "\n" +
            "Documento: " + doc + "\n" +
            "Fecha de viaje: " + fecha + "\n" +
            "Origen: " + cboOrigen.getSelectedItem() + "\n" +
            "Destino: " + cboDestino.getSelectedItem() + "\n" +
            "Piso: " + piso + "\n" +
            "Servicio: " + (servicio == null ? "No seleccionado" : servicio) + "\n" +
            "Precio del pasaje: " + formatoPrecio(calcularPrecio()) + "\n" +
            "Opcionales: " + extras;

        JOptionPane.showMessageDialog(this, resumen,
            "Resumen de la compra", JOptionPane.INFORMATION_MESSAGE);
    }

    private void reiniciar() {
        txtNombre.setText("");
        txtDocumento.setText("");
        txtFecha.setText("dd/mm/aaaa");
        chkAudifonos.setSelected(false);
        chkManta.setSelected(false);
        chkRevistas.setSelected(false);
        rbPiso1.setSelected(true);
        cboOrigen.setSelectedIndex(0);
        cboDestino.setSelectedIndex(1);
        lstServicio.setSelectedIndex(1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CompraPasaje::new);
    }
}
