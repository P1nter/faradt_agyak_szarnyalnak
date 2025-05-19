import javax.swing.*;
import java.awt.*;
/**
 * Main menu window for the application.
 * Presents options to start a new game, load an existing game, or exit.
 */
public class MainMenu {

    private JFrame frame;

    /**
     * Constructs and displays the main menu GUI on the event dispatch thread.
     */
    public void createAndShowGUI() {
        frame = new JFrame("Main Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Menu", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        container.add(title);

        JPanel centerButtons = new JPanel(new GridLayout(0, 1, 0, 10));
        centerButtons.setMaximumSize(new Dimension(300, 150));
        centerButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnStartNewGame = new JButton("Start New Game");
        JButton btnLoadGame = new JButton("Load Game (NI)");
        JButton btnExitGame = new JButton("Exit Game");

        Font buttonFont = new Font("SansSerif", Font.PLAIN, 18);
        btnStartNewGame.setFont(buttonFont);
        btnLoadGame.setFont(buttonFont);
        btnExitGame.setFont(buttonFont);

        centerButtons.add(btnStartNewGame);
        centerButtons.add(btnLoadGame);
        container.add(centerButtons);

        container.add(Box.createVerticalGlue());

        btnExitGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExitGame.setMaximumSize(new Dimension(150, 40));
        container.add(btnExitGame);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        frame.setContentPane(container);

        // Listener to start a new game via GameMenu dialog
        btnStartNewGame.addActionListener(e -> {
            GameMenu gameMenuDialog = new GameMenu(frame);
            gameMenuDialog.setVisible(true);
        });

        // Listener for load game (not implemented)
        btnLoadGame.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "Load game functionality is not implemented yet.",
                    "Load Game",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Listener to exit the application
        btnExitGame.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }
}