// MarcoFlowLayoutColor.java
// FlowLayout con botones de colores y barra de menú.
// FUNCIÓN EXTRA: se pueden agregar y quitar botones para ver cómo los
// componentes fluyen a la siguiente línea.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MarcoFlowLayoutColor extends JFrame
{
    private final FlowLayout esquema;               // administrador de esquemas
    private final JPanel panelBotones;              // panel que usa FlowLayout
    private final JLabel etiquetaEstado;            // muestra el estado actual
    private final ArrayList<JButton> botonesExtra = new ArrayList<>();
    private final Random azar = new Random();
    private String alineacionActual = "CENTRO";

    public MarcoFlowLayoutColor()
    {
        super("Demostracion de FlowLayout con color");

        esquema = new FlowLayout(FlowLayout.CENTER, 15, 15); // espacios de 15 píxeles
        panelBotones = new JPanel(esquema);
        panelBotones.setBackground(new Color(255, 243, 205)); // amarillo suave

        etiquetaEstado = new JLabel("", SwingConstants.CENTER);
        etiquetaEstado.setFont(new Font("SansSerif", Font.BOLD, 14));
        etiquetaEstado.setOpaque(true);
        etiquetaEstado.setBackground(new Color(44, 62, 80));
        etiquetaEstado.setForeground(Color.WHITE);
        actualizarEstado();

        // botones de alineación
        JButton botonIzquierda = darEstilo(new JButton("Izquierda"), new Color(231, 76, 60));
        JButton botonCentro = darEstilo(new JButton("Centro"), new Color(46, 204, 113));
        JButton botonDerecha = darEstilo(new JButton("Derecha"), new Color(52, 152, 219));

        botonIzquierda.addActionListener(evento -> cambiarAlineacion(
            FlowLayout.LEFT, "IZQUIERDA", new Color(253, 226, 224)));
        botonCentro.addActionListener(evento -> cambiarAlineacion(
            FlowLayout.CENTER, "CENTRO", new Color(255, 243, 205)));
        botonDerecha.addActionListener(evento -> cambiarAlineacion(
            FlowLayout.RIGHT, "DERECHA", new Color(214, 234, 248)));

        panelBotones.add(botonIzquierda);
        panelBotones.add(botonCentro);
        panelBotones.add(botonDerecha);

        add(panelBotones, BorderLayout.CENTER);
        add(etiquetaEstado, BorderLayout.SOUTH);

        crearMenu();
    }

    // crea la barra de menú
    private void crearMenu()
    {
        // menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(evento -> System.exit(0));
        menuArchivo.add(itemSalir);

        // menú Alineación
        JMenu menuAlineacion = new JMenu("Alineación");
        JMenuItem itemIzquierda = new JMenuItem("Izquierda");
        JMenuItem itemCentro = new JMenuItem("Centro");
        JMenuItem itemDerecha = new JMenuItem("Derecha");
        itemIzquierda.addActionListener(evento -> cambiarAlineacion(
            FlowLayout.LEFT, "IZQUIERDA", new Color(253, 226, 224)));
        itemCentro.addActionListener(evento -> cambiarAlineacion(
            FlowLayout.CENTER, "CENTRO", new Color(255, 243, 205)));
        itemDerecha.addActionListener(evento -> cambiarAlineacion(
            FlowLayout.RIGHT, "DERECHA", new Color(214, 234, 248)));
        menuAlineacion.add(itemIzquierda);
        menuAlineacion.add(itemCentro);
        menuAlineacion.add(itemDerecha);

        // menú Botones (función extra)
        JMenu menuBotones = new JMenu("Botones");
        JMenuItem itemAgregar = new JMenuItem("Agregar botón");
        JMenuItem itemQuitar = new JMenuItem("Quitar botón");
        itemAgregar.addActionListener(evento -> agregarBoton());
        itemQuitar.addActionListener(evento -> quitarBoton());
        menuBotones.add(itemAgregar);
        menuBotones.add(itemQuitar);

        // menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de");
        itemAcerca.addActionListener(evento -> JOptionPane.showMessageDialog(this,
            "FlowLayout acomoda los componentes de izquierda a derecha\n"
            + "y pasa a la siguiente línea cuando no caben.",
            "Acerca de", JOptionPane.INFORMATION_MESSAGE));
        menuAyuda.add(itemAcerca);

        JMenuBar barra = new JMenuBar();
        barra.add(menuArchivo);
        barra.add(menuAlineacion);
        barra.add(menuBotones);
        barra.add(menuAyuda);
        setJMenuBar(barra);
    }

    // cambia la alineación, el color de fondo y el mensaje de estado
    private void cambiarAlineacion(int alineacion, String nombre, Color fondo)
    {
        esquema.setAlignment(alineacion);
        panelBotones.setBackground(fondo);
        alineacionActual = nombre;
        refrescar();
    }

    // agrega un botón numerado de color aleatorio al panel
    private void agregarBoton()
    {
        Color color = new Color(azar.nextInt(200), azar.nextInt(200), azar.nextInt(200));
        JButton boton = darEstilo(new JButton("Botón " + (botonesExtra.size() + 1)), color);
        botonesExtra.add(boton);
        panelBotones.add(boton);
        refrescar();
    }

    // quita el último botón agregado (si hay alguno)
    private void quitarBoton()
    {
        if (botonesExtra.isEmpty())
            return;

        JButton boton = botonesExtra.remove(botonesExtra.size() - 1);
        panelBotones.remove(boton);
        refrescar();
    }

    // actualiza el estado y vuelve a acomodar el panel
    private void refrescar()
    {
        actualizarEstado();
        panelBotones.revalidate();
        panelBotones.repaint();
    }

    private void actualizarEstado()
    {
        etiquetaEstado.setText("Alineación: " + alineacionActual
            + "  |  Botones agregados: " + botonesExtra.size());
    }

    // aplica el estilo de color a un botón
    private JButton darEstilo(JButton boton, Color color)
    {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 13));
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        return boton;
    }
}
