package ejercicio;

import javax.swing.*;
import java.awt.*;

public class GestionProducto extends JFrame {

    private Producto producto;
    private JTextField txtNombre, txtPrecio, txtStock, txtCategoria;
    private JLabel lblResultado;

    public GestionProducto() {
        producto = new Producto();

        setTitle("Gestión de Producto");
        setSize(420, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        panelFormulario.add(new JLabel("Nombre del producto:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Cantidad en stock:"));
        txtStock = new JTextField();
        panelFormulario.add(txtStock);

        panelFormulario.add(new JLabel("Categoría:"));
        txtCategoria = new JTextField();
        panelFormulario.add(txtCategoria);

        JPanel panelBoton = new JPanel();
        JButton btnActualizar = new JButton("Actualizar Producto");
        panelBoton.add(btnActualizar);

        lblResultado = new JLabel("<html><i>Ingrese los datos y presione Actualizar Producto.</i></html>");
        lblResultado.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        lblResultado.setVerticalAlignment(SwingConstants.TOP);

        btnActualizar.addActionListener(e -> actualizarDatos());

        add(panelFormulario, BorderLayout.NORTH);
        add(panelBoton, BorderLayout.CENTER);
        add(lblResultado, BorderLayout.SOUTH);
    }

    private void actualizarDatos() {
        try {
            producto.setNombre(txtNombre.getText());
            producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            producto.setCantidadStock(Integer.parseInt(txtStock.getText()));
            producto.setCategoria(txtCategoria.getText());

            String infoEstructurada = "<html><b>Información del Producto Actualizada:</b><br>"
                    + "• <b>Nombre:</b> " + producto.getNombre() + "<br>"
                    + "• <b>Precio:</b> S/ " + String.format("%.2f", producto.getPrecio()) + "<br>"
                    + "• <b>Stock:</b> " + producto.getCantidadStock() + " unidades<br>"
                    + "• <b>Categoría:</b> " + producto.getCategoria() + "</html>";

            lblResultado.setText(infoEstructurada);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese números válidos en Precio y Stock.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestionProducto().setVisible(true));
    }
}
