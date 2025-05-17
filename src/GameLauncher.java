import javax.swing.*;

public class GameLauncher { // Or your Main class that contains main()
    public static void main(String[] args) {
        System.out.println("LAUNCHER: main - START");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("LAUNCHER: main - Look and Feel set. About to invokeLater.");
        SwingUtilities.invokeLater(() -> {
            System.out.println("LAUNCHER: invokeLater - START of task on EDT.");
            try {
                GameMenu menu = new GameMenu(null); // Ensure this is your GameMenu class
                System.out.println("LAUNCHER: invokeLater - GameMenu object created. Hash: " + menu.hashCode());
                System.out.println("LAUNCHER: invokeLater - About to menu.setVisible(true).");
                menu.setVisible(true); // This should make the GameMenu JDialog appear
                System.out.println("LAUNCHER: invokeLater - menu.setVisible(true) CALLED AND RETURNED.");
            } catch (Throwable t) {
                System.err.println("LAUNCHER: invokeLater - CRITICAL ERROR IN EDT TASK:");
                t.printStackTrace(); // Print any error from GameMenu constructor or setVisible
                JOptionPane.showMessageDialog(null, "Error in Launcher EDT task: " + t.toString(), "Launcher Error", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("LAUNCHER: invokeLater - END of task on EDT.");
        });
        System.out.println("LAUNCHER: main - invokeLater posted. Main thread finishing.");
    }
}