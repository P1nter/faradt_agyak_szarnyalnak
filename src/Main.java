import javax.swing.*;
/**
 * Entry point to launch the primary application menu.
 * Sets the native look-and-feel and initializes the MainMenu GUI on the Swing event thread.
 */
public class Main {

    /**
     * Application main method. Configures look-and-feel and displays the main menu.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            try {
                MainMenu mainMenu = new MainMenu();
                mainMenu.createAndShowGUI();
            } catch (Throwable t) {
                System.err.println("LAUNCHER: invokeLater - CRITICAL ERROR IN EDT TASK (launching MainMenu):");
                t.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Error launching MainMenu: " + t,
                        "Launcher Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
