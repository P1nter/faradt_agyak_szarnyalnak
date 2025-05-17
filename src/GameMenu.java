import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMenu extends JDialog {
    private final JComboBox<Integer> comboPlayers;
    private final JComboBox<Integer> comboTektons;
    private final JPanel nameFieldsPanel;
    private final List<JTextField> playerNameFields = new ArrayList<>();

    public GameMenu(Frame owner) {
        super(owner, "New Game Setup", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(new JLabel("Number of players (1-10):"));
        comboPlayers = new JComboBox<>();
        for (int i = 1; i <= 10; i++) comboPlayers.addItem(i);
        comboPlayers.setMaximumSize(new Dimension(100, 30));
        comboPlayers.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(comboPlayers);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        nameFieldsPanel = new JPanel();
        nameFieldsPanel.setLayout(new BoxLayout(nameFieldsPanel, BoxLayout.Y_AXIS));
        nameFieldsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JScrollPane nameFieldsScrollPane = new JScrollPane(nameFieldsPanel);
        nameFieldsScrollPane.setPreferredSize(new Dimension(300, 150));
        nameFieldsScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(nameFieldsScrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(new JLabel("Number of tektons (1-20):"));
        comboTektons = new JComboBox<>();
        for (int i = 1; i <= 20; i++) comboTektons.addItem(i);
        comboTektons.setMaximumSize(new Dimension(100, 30));
        comboTektons.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(comboTektons);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnStart = new JButton("Start Game");
        JButton btnCancel = new JButton("Cancel");
        buttonPanel.add(btnStart);
        buttonPanel.add(btnCancel);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(buttonPanel);

        comboPlayers.addActionListener(e -> updatePlayerNameFields());
        btnStart.addActionListener(this::startNewGame);
        btnCancel.addActionListener(e -> dispose());

        setContentPane(mainPanel);
        updatePlayerNameFields();
        pack();
        setLocationRelativeTo(owner);
    }

    private void updatePlayerNameFields() {
        nameFieldsPanel.removeAll();
        playerNameFields.clear();
        Object selectedItem = comboPlayers.getSelectedItem();
        if (selectedItem == null) {
            if (comboPlayers.getItemCount() > 0) {
                comboPlayers.setSelectedIndex(0);
                selectedItem = comboPlayers.getSelectedItem();
            }
            if (selectedItem == null) return;
        }
        int numPlayers = (Integer) selectedItem;
        for (int i = 1; i <= numPlayers; i++) {
            JPanel playerRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            playerRowPanel.add(new JLabel("Player " + i + " Name:"));
            JTextField nameField = new JTextField(15);
            playerRowPanel.add(nameField);
            playerNameFields.add(nameField);
            nameFieldsPanel.add(playerRowPanel);
        }
        nameFieldsPanel.revalidate();
        nameFieldsPanel.repaint();
    }

    private void startNewGame(ActionEvent e) {
        System.out.println("GameMenu: startNewGame called.");
        Integer numPlayersSelected = (Integer) comboPlayers.getSelectedItem();
        Integer numTektonsSelected = (Integer) comboTektons.getSelectedItem();

        if (numPlayersSelected == null || numTektonsSelected == null) { /* ... error handling ... */ return; }
        int numPlayers = numPlayersSelected;
        int numTektons = numTektonsSelected;
        if (numTektons <= 0 || numPlayers <= 0) { /* ... error handling ... */ return; }

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) { /* ... player creation ... */
            String name = playerNameFields.get(i).getText().trim();
            if (name.isEmpty()) name = "Player " + (i + 1);
            Player p = ((i + 1) % 2 != 0) ? new Mushroomer(name) : new Insecter(name);
            players.add(p);
        }

        int maxNeighbors = (numTektons > 1) ? Math.min(3, numTektons - 1) : 0;
        List<Tekton> tektons = MapBuilder.build(numTektons, maxNeighbors);
        // Ensure Tektons have their Mushroom manager and IDs (if not set by MapBuilder/constructor)
        for(int i=0; i < tektons.size(); i++){
            Tekton t = tektons.get(i);
            if(t.getIDNoPrint() == 0 && t instanceof DefaultTekton){ // Assuming DefaultTekton might need ID set
                // ((DefaultTekton)t).setID(i+1); // Requires setID in DefaultTekton
            }
            if(t.getMushroomNoPrint() == null){
            //    t.setMushroom(new Mushroom(t)); // Tekton creates its Mushroom manager, passing itself
            } else if (t.getMushroomNoPrint().getTektonNoPrint() == null) {
            //    t.getMushroomNoPrint().setTekton(t); // Ensure existing mushroom manager knows its Tekton
            }
        }


        Game game = new Game(tektons, players);

        System.out.println("GameMenu: Performing initial player setup on map...");
        Random random = new Random();
        if (!tektons.isEmpty()) {
            for (Player player : players) {
                Tekton randomTekton = tektons.get(random.nextInt(tektons.size()));
                System.out.println("  Setting up " + player.getName() + " on Tekton " + randomTekton.getIDNoPrint());
                if (player instanceof Insecter) {
                    // Assumes Insect constructor: Insect(Tekton location, Insecter owner)
                    // and that it calls owner.addInsect(this) and location.addNewInsect(this)
                    new Insect(randomTekton, (Insecter) player);
                    System.out.println("    Added Insect for " + player.getName());
                } else if (player instanceof Mushroomer) {
                    // The Tekton MUST have its 'Mushroom' (manager) instance.
                    // Tekton's constructor should ideally do: this.mushroomManager = new Mushroom(this);
                    Mushroom mushroomManagerOnTekton = randomTekton.getMushroomNoPrint();
                    if (mushroomManagerOnTekton == null) { // Should have been created above or in Tekton constructor
                        System.err.println("    CRITICAL: Tekton " + randomTekton.getIDNoPrint() + " has no mushroom manager for " + player.getName());
                        continue; // Skip if no manager
                    }

                    MushroomBody body = mushroomManagerOnTekton.growBody(randomTekton, (Mushroomer) player);
                    if (body != null) {
                        // growBody should have called player.addMushroomBody(body)
                        System.out.println("    SUCCESS: Added MushroomBody for " + player.getName() + " on Tekton " + randomTekton.getIDNoPrint());
                    } else {
                        System.err.println("    FAILURE: Failed to add MushroomBody for " + player.getName() + " on Tekton " + randomTekton.getIDNoPrint() +
                                ". Tekton canGrow(): " + randomTekton.canGrow() +
                                ", MushroomManager already has body: " + mushroomManagerOnTekton.hasMushroomBody());
                    }
                }
            }
        } else { System.err.println("GameMenu: No tektons available for initial player setup!"); }
        System.out.println("GameMenu: Initial player setup complete.");

        JFrame gameFrame = new JFrame("Game View");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout(5, 5));
        try {
            MapPanel mapPanel = new MapPanel(game);
            gameFrame.add(new JScrollPane(mapPanel), BorderLayout.CENTER);
            ActionPanel actionPanel = new ActionPanel(game);
            gameFrame.add(actionPanel, BorderLayout.EAST);
            gameFrame.setMinimumSize(new Dimension(800, 600));
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
            dispose();
        } catch (Throwable ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "CRITICAL Error creating game UI: \n" + ex.toString(), "UI Creation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}