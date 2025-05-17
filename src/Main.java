import javax.swing.*;

public class Main {
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
                JOptionPane.showMessageDialog(null, "Error launching MainMenu: " + t.toString(), "Launcher Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}