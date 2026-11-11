// DemoBorderLayoutColor.java
// Prueba de MarcoBorderLayoutColor.
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DemoBorderLayoutColor
{
    public static void main(String[] args)
    {
        // las ventanas se crean en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() ->
        {
            MarcoBorderLayoutColor marco = new MarcoBorderLayoutColor();
            marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            marco.setSize(450, 300);
            marco.setLocationRelativeTo(null); // centra la ventana en la pantalla
            marco.setVisible(true);
        });
    }
}
