import javax.swing.*;

public class GameView extends JFrame {
    public GameView() {
        setTitle("Játék");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ide jön a játék tényleges tartalma
        JLabel content = new JLabel("Játék nézet", SwingConstants.CENTER);
        add(content);
    }
}