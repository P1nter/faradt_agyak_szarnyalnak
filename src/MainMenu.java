import javax.swing.*;
import java.awt.*;
// ActionEvent import might be needed if you use anonymous listeners for buttons
// import java.awt.event.ActionEvent;

public class MainMenu {

    private JFrame frame; // Keep a reference to the frame if needed

    public void createAndShowGUI() {
        System.out.println("MAIN_MENU: createAndShowGUI - START");
        frame = new JFrame("Main Game Menu"); // Your main menu title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400); // Or your preferred size
        frame.setLocationRelativeTo(null); // Center on screen

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        // ... (your existing MainMenu layout: title, buttons panel, etc.)

        JLabel title = new JLabel("Menu", SwingConstants.CENTER); // Example title
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        container.add(title);

        JPanel centerButtons = new JPanel(new GridLayout(0, 1, 0, 10)); // 0 rows, 1 col, 10 vgap
        centerButtons.setMaximumSize(new Dimension(300, 150)); // Adjust size
        centerButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnStartNewGame = new JButton("Start New Game");
        JButton btnLoadGame = new JButton("Load Game (NI)");
        JButton btnExitGame = new JButton("Exit Game");

        // Set font or other properties for buttons if desired
        Font buttonFont = new Font("SansSerif", Font.PLAIN, 18);
        btnStartNewGame.setFont(buttonFont);
        btnLoadGame.setFont(buttonFont);
        btnExitGame.setFont(buttonFont);

        centerButtons.add(btnStartNewGame);
        centerButtons.add(btnLoadGame);
        container.add(centerButtons); // Add button panel to main container

        container.add(Box.createVerticalGlue()); // Pushes exit button to bottom

        btnExitGame.setAlignmentX(Component.CENTER_ALIGNMENT); // Center exit button
        btnExitGame.setMaximumSize(new Dimension(150,40));
        container.add(btnExitGame);
        container.add(Box.createRigidArea(new Dimension(0,20)));


        frame.setContentPane(container);

        // --- Action Listeners for MainMenu buttons ---
        btnStartNewGame.addActionListener(e -> {
            System.out.println("MAIN_MENU: 'Start New Game' button clicked.");
            // Create and show the GameMenu dialog
            // Pass 'frame' (the MainMenu's JFrame) as the owner to make GameMenu modal to it
            GameMenu gameMenuDialog = new GameMenu(frame); // Use the GameMenu class from my previous response
            System.out.println("MAIN_MENU: GameMenu dialog created. About to setVisible.");
            gameMenuDialog.setVisible(true);
            System.out.println("MAIN_MENU: GameMenu dialog setVisible(true) called and returned (dialog closed).");
            // After GameMenu dialog is closed (either by starting game or cancelling),
            // you might want to hide or dispose the MainMenu frame, or keep it.
            // For now, let's assume it stays or is handled by GameMenu's success.
        });

        btnLoadGame.addActionListener(e -> {
            System.out.println("MAIN_MENU: 'Load Game' button clicked.");
            JOptionPane.showMessageDialog(frame, "Load game functionality is not implemented yet.", "Load Game", JOptionPane.INFORMATION_MESSAGE);
        });

        btnExitGame.addActionListener(e -> {
            System.out.println("MAIN_MENU: 'Exit Game' button clicked.");
            System.exit(0); // Exits the application
        });

        System.out.println("MAIN_MENU: createAndShowGUI - About to setVisible frame.");
        frame.setVisible(true);
        System.out.println("MAIN_MENU: createAndShowGUI - Frame setVisible(true) CALLED.");
        System.out.println("MAIN_MENU: createAndShowGUI - END");
    }
}