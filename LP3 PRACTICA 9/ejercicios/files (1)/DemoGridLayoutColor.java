// DemoGridLayoutColor.java
// Prueba de MarcoGridLayoutColor.
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DemoGridLayoutColor
{
    public static void main(String[] args)
    {
        // las ventanas se crean en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() ->
        {
            MarcoGridLayoutColor marco = new MarcoGridLayoutColor();
            marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            marco.setSize(450, 300);
            marco.setLocationRelativeTo(null); // centra la ventana en la pantalla
            marco.setVisible(true);
        });
    }
}
