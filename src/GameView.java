import javax.swing.*;

/**
 * Main application window for the game.
 * Extends JFrame to configure and display the game view.
 */
public class GameView extends JFrame {

    /**
     * Constructs the game view frame with title, size, close operation,
     * centers it on screen, and adds initial placeholder content.
     */
    public GameView() {
        setTitle("Játék");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel content = new JLabel("Játék nézet", SwingConstants.CENTER);
        add(content);
    }
}