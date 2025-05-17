import javax.swing.*;

public class GameLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameMenu menu = new GameMenu();
            menu.setVisible(true);
        });
    }
}