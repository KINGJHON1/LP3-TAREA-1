// MarcoBorderLayoutColor.java
// BorderLayout con cinco regiones de colores y barra de menú.
// FUNCIÓN EXTRA: menú "Regiones" para mostrar todo y "Opciones" para
// cambiar el espaciado entre regiones.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MarcoBorderLayoutColor extends JFrame implements ActionListener
{
    private static final String TITULO = "Demostracion de BorderLayout con color";
    private final JButton[] botones;
    private static final String[] nombres =
        {"Ocultar Norte", "Ocultar Sur", "Ocultar Este", "Ocultar Oeste", "Ocultar Centro"};
    private static final String[] regiones =
        {"Norte", "Sur", "Este", "Oeste", "Centro"};
    private static final Color[] colores = {
        new Color(52, 152, 219),   // norte: azul
        new Color(46, 204, 113),   // sur: verde
        new Color(230, 126, 34),   // este: naranja
        new Color(155, 89, 182),   // oeste: morado
        new Color(231, 76, 60)     // centro: rojo
    };
    private final BorderLayout esquema;
    private boolean espaciadoAmplio = false;

    public MarcoBorderLayoutColor()
    {
        super(TITULO);

        esquema = new BorderLayout(8, 8); // espacios de 8 píxeles
        setLayout(esquema);
        getContentPane().setBackground(new Color(44, 62, 80)); // fondo oscuro

        botones = new JButton[nombres.length];
        for (int cuenta = 0; cuenta < nombres.length; cuenta++)
        {
            botones[cuenta] = new JButton(nombres[cuenta]);
            botones[cuenta].setBackground(colores[cuenta]);
            botones[cuenta].setForeground(Color.WHITE);
            botones[cuenta].setFont(new Font("SansSerif", Font.BOLD, 13));
            botones[cuenta].setOpaque(true);
            botones[cuenta].setBorderPainted(false);
            botones[cuenta].setFocusPainted(false);
            botones[cuenta].addActionListener(this);
        }

        add(botones[0], BorderLayout.NORTH);
        add(botones[1], BorderLayout.SOUTH);
        add(botones[2], BorderLayout.EAST);
        add(botones[3], BorderLayout.WEST);
        add(botones[4], BorderLayout.CENTER);

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

        // menú Regiones (función extra)
        JMenu menuRegiones = new JMenu("Regiones");
        JMenuItem itemMostrar = new JMenuItem("Mostrar todo");
        itemMostrar.addActionListener(evento ->
        {
            for (JButton boton : botones)
                boton.setVisible(true);
            setTitle(TITULO);
            redistribuir();
        });
        menuRegiones.add(itemMostrar);

        // menú Opciones (función extra)
        JMenu menuOpciones = new JMenu("Opciones");
        JMenuItem itemEspacio = new JMenuItem("Cambiar espaciado");
        itemEspacio.addActionListener(evento ->
        {
            espaciadoAmplio = !espaciadoAmplio;
            int espacio = espaciadoAmplio ? 20 : 8;
            esquema.setHgap(espacio);
            esquema.setVgap(espacio);
            redistribuir();
        });
        menuOpciones.add(itemEspacio);

        // menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de");
        itemAcerca.addActionListener(evento -> JOptionPane.showMessageDialog(this,
            "BorderLayout divide el contenedor en cinco regiones:\n"
            + "Norte, Sur, Este, Oeste y Centro.",
            "Acerca de", JOptionPane.INFORMATION_MESSAGE));
        menuAyuda.add(itemAcerca);

        JMenuBar barra = new JMenuBar();
        barra.add(menuArchivo);
        barra.add(menuRegiones);
        barra.add(menuOpciones);
        barra.add(menuAyuda);
        setJMenuBar(barra);
    }

    // vuelve a distribuir el panel de contenido
    private void redistribuir()
    {
        esquema.layoutContainer(getContentPane());
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    @Override
    public void actionPerformed(ActionEvent evento)
    {
        for (int cuenta = 0; cuenta < botones.length; cuenta++)
        {
            if (evento.getSource() == botones[cuenta])
            {
                botones[cuenta].setVisible(false); // oculta el botón oprimido
                setTitle("BorderLayout con color - Oculto: " + regiones[cuenta]);
            }
            else
                botones[cuenta].setVisible(true);  // muestra los demás
        }

        redistribuir();
    }
}
