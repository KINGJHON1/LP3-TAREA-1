// MarcoGridLayoutColor.java
// GridLayout con botones de colores que rotan en cada clic y barra de menú.
// FUNCIÓN EXTRA: cuatro cuadrículas (2x3, 3x2, 1x6 y 6x1) que se pueden elegir
// desde el menú, y cada botón cuenta cuántas veces se oprimió.
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MarcoGridLayoutColor extends JFrame implements ActionListener
{
    private final JButton[] botones;
    private final int[] clics;          // clics por botón
    private static final String[] nombres =
        {"uno", "dos", "tres", "cuatro", "cinco", "seis"};
    private static final Color[] paleta = {
        new Color(231, 76, 60),    // rojo
        new Color(230, 126, 34),   // naranja
        new Color(241, 196, 15),   // amarillo
        new Color(46, 204, 113),   // verde
        new Color(52, 152, 219),   // azul
        new Color(155, 89, 182)    // morado
    };
    private int desplazamiento = 0;     // cuánto rota la paleta
    private int modoActual = 0;         // índice de la cuadrícula actual
    private final Container contenedor;
    private final GridLayout[] cuadriculas = {
        new GridLayout(2, 3, 6, 6),     // 2 por 3
        new GridLayout(3, 2, 6, 6),     // 3 por 2
        new GridLayout(1, 6, 6, 6),     // 1 por 6
        new GridLayout(6, 1, 6, 6)      // 6 por 1
    };
    private static final String[] titulos = {"2 x 3", "3 x 2", "1 x 6", "6 x 1"};

    public MarcoGridLayoutColor()
    {
        super("GridLayout con color - " + titulos[0]);
        contenedor = getContentPane();
        contenedor.setBackground(new Color(44, 62, 80));
        setLayout(cuadriculas[0]);

        botones = new JButton[nombres.length];
        clics = new int[nombres.length];
        for (int cuenta = 0; cuenta < nombres.length; cuenta++)
        {
            botones[cuenta] = new JButton(nombres[cuenta]);
            botones[cuenta].setForeground(Color.WHITE);
            botones[cuenta].setFont(new Font("SansSerif", Font.BOLD, 16));
            botones[cuenta].setOpaque(true);
            botones[cuenta].setBorderPainted(false);
            botones[cuenta].setFocusPainted(false);
            botones[cuenta].addActionListener(this);
            add(botones[cuenta]);
        }
        pintarBotones();
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

        // menú Cuadrícula (función extra): una opción por cada cuadrícula
        JMenu menuCuadricula = new JMenu("Cuadrícula");
        for (int cuenta = 0; cuenta < titulos.length; cuenta++)
        {
            final int modo = cuenta;
            JMenuItem item = new JMenuItem(titulos[cuenta]);
            item.addActionListener(evento -> cambiarModo(modo));
            menuCuadricula.add(item);
        }

        // menú Opciones (función extra)
        JMenu menuOpciones = new JMenu("Opciones");
        JMenuItem itemRotar = new JMenuItem("Rotar colores");
        JMenuItem itemReiniciar = new JMenuItem("Reiniciar contadores");
        itemRotar.addActionListener(evento ->
        {
            desplazamiento++;
            pintarBotones();
        });
        itemReiniciar.addActionListener(evento ->
        {
            for (int cuenta = 0; cuenta < botones.length; cuenta++)
            {
                clics[cuenta] = 0;
                botones[cuenta].setText(nombres[cuenta]);
            }
        });
        menuOpciones.add(itemRotar);
        menuOpciones.add(itemReiniciar);

        // menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de");
        itemAcerca.addActionListener(evento -> JOptionPane.showMessageDialog(this,
            "GridLayout divide el contenedor en filas y columnas.\n"
            + "Todos los componentes tienen el mismo tamaño.",
            "Acerca de", JOptionPane.INFORMATION_MESSAGE));
        menuAyuda.add(itemAcerca);

        JMenuBar barra = new JMenuBar();
        barra.add(menuArchivo);
        barra.add(menuCuadricula);
        barra.add(menuOpciones);
        barra.add(menuAyuda);
        setJMenuBar(barra);
    }

    // cambia a la cuadrícula indicada y redistribuye el contenedor
    private void cambiarModo(int modo)
    {
        modoActual = modo;
        contenedor.setLayout(cuadriculas[modoActual]);
        setTitle("GridLayout con color - " + titulos[modoActual]);
        contenedor.validate();
        contenedor.repaint();
    }

    // asigna a cada botón un color de la paleta, rotándola
    private void pintarBotones()
    {
        for (int cuenta = 0; cuenta < botones.length; cuenta++)
            botones[cuenta].setBackground(
                paleta[(cuenta + desplazamiento) % paleta.length]);
    }

    @Override
    public void actionPerformed(ActionEvent evento)
    {
        // contador de clics en el botón oprimido
        for (int cuenta = 0; cuenta < botones.length; cuenta++)
            if (evento.getSource() == botones[cuenta])
            {
                clics[cuenta]++;
                botones[cuenta].setText(nombres[cuenta] + " (" + clics[cuenta] + ")");
            }

        // pasa a la siguiente cuadrícula y rota los colores
        desplazamiento++;
        pintarBotones();
        cambiarModo((modoActual + 1) % cuadriculas.length);
    }
}
