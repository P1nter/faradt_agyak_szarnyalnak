import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class TektonPanelView extends JPanel implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Tekton) {
            SwingUtilities.invokeLater(() -> {
                removeAll();
                // ... (frissítési logika)
                revalidate();
                repaint();
            });
        }
    }
}