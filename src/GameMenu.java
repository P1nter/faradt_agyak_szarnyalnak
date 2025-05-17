import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameMenu extends JFrame {
    public GameMenu() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Game Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton newGameButton = createMenuButton("Start new game");
        JButton loadGameButton = createMenuButton("Load game");
        JButton exitButton = createMenuButton("Exit");

        // Gombkezelők
        newGameButton.addActionListener(this::startNewGame);
        loadGameButton.addActionListener(this::loadGame);
        exitButton.addActionListener(e -> System.exit(0));

        menuPanel.add(newGameButton);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(loadGameButton);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(exitButton);

        add(menuPanel, BorderLayout.CENTER);
        pack();
        setSize(300, 200);
        setLocationRelativeTo(null);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        return button;
    }

    private void startNewGame(ActionEvent e) {
        this.dispose(); // Menü bezárása
        GameView gameView = new GameView();
        gameView.setVisible(true); // Játék nézet megnyitása
    }

    private void loadGame(ActionEvent e) {
        // Példa betöltési logikára
        JOptionPane.showMessageDialog(this, "Betöltés funkció még nincs implementálva");
    }
}