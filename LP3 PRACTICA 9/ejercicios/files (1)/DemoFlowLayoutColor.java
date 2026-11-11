// DemoFlowLayoutColor.java
// Prueba de MarcoFlowLayoutColor.
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DemoFlowLayoutColor
{
    public static void main(String[] args)
    {
        // las ventanas se crean en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() ->
        {
            MarcoFlowLayoutColor marco = new MarcoFlowLayoutColor();
            marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            marco.setSize(450, 230);
            marco.setLocationRelativeTo(null); // centra la ventana en la pantalla
            marco.setVisible(true);
        });
    }
}
