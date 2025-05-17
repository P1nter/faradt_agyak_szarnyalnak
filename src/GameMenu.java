// GameMenu.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameMenu extends JDialog {
    private final JComboBox<Integer> comboPlayers;
    private final JComboBox<Integer> comboTektons;
    private final JPanel nameFieldsPanel; // Panel to hold player name JTextFields
    private final List<JTextField> playerNameFields = new ArrayList<>();

    public GameMenu(Frame owner) {
        super(owner, "New Game Setup", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // This is the mainPanel for the GameMenu JDialog itself
        JPanel dialogMainPanel = new JPanel();
        dialogMainPanel.setLayout(new BoxLayout(dialogMainPanel, BoxLayout.Y_AXIS));
        dialogMainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        dialogMainPanel.add(new JLabel("Number of players (1-10):"));
        comboPlayers = new JComboBox<>();
        for (int i = 1; i <= 10; i++) comboPlayers.addItem(i);
        comboPlayers.setSelectedItem(2);
        comboPlayers.setMaximumSize(new Dimension(120, 30));
        comboPlayers.setAlignmentX(Component.LEFT_ALIGNMENT);
        dialogMainPanel.add(comboPlayers);
        dialogMainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        nameFieldsPanel = new JPanel();
        nameFieldsPanel.setLayout(new BoxLayout(nameFieldsPanel, BoxLayout.Y_AXIS));
        nameFieldsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JScrollPane nameFieldsScrollPane = new JScrollPane(nameFieldsPanel);
        nameFieldsScrollPane.setPreferredSize(new Dimension(330, 150));
        nameFieldsScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        dialogMainPanel.add(nameFieldsScrollPane);
        dialogMainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        dialogMainPanel.add(new JLabel("Number of tektons (3-20):"));
        comboTektons = new JComboBox<>();
        for (int i = 3; i <= 20; i++) comboTektons.addItem(i);
        comboTektons.setSelectedItem(5);
        comboTektons.setMaximumSize(new Dimension(120, 30));
        comboTektons.setAlignmentX(Component.LEFT_ALIGNMENT);
        dialogMainPanel.add(comboTektons);
        dialogMainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnStart = new JButton("Start Game");
        JButton btnCancel = new JButton("Cancel");
        buttonPanel.add(btnStart);
        buttonPanel.add(btnCancel);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dialogMainPanel.add(buttonPanel);

        comboPlayers.addActionListener(e -> updatePlayerNameFields());
        btnStart.addActionListener(this::startNewGame);
        btnCancel.addActionListener(e -> dispose());

        setContentPane(dialogMainPanel); // Set the dialog's content pane
        updatePlayerNameFields();
        pack();
        setLocationRelativeTo(owner);
    }

    private void updatePlayerNameFields() {
        nameFieldsPanel.removeAll();
        playerNameFields.clear();
        Object selectedItem = comboPlayers.getSelectedItem();
        if (selectedItem == null) {
            if (comboPlayers.getItemCount() > 0) comboPlayers.setSelectedIndex(0);
            selectedItem = comboPlayers.getSelectedItem();
            if (selectedItem == null) return;
        }
        int numPlayers = (Integer) selectedItem;
        for (int i = 1; i <= numPlayers; i++) {
            JPanel playerRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            playerRowPanel.add(new JLabel("Player " + i + " Name:"));
            JTextField nameField = new JTextField(15); nameField.setText("Player" + i);
            playerRowPanel.add(nameField); playerNameFields.add(nameField);
            nameFieldsPanel.add(playerRowPanel);
        }
        nameFieldsPanel.revalidate(); nameFieldsPanel.repaint();
        pack();
    }

    private void startNewGame(ActionEvent e) {
        System.out.println("GameMenu: startNewGame called.");
        Integer numPlayersSelected = (Integer) comboPlayers.getSelectedItem();
        Integer numTektonsSelected = (Integer) comboTektons.getSelectedItem();

        if (numPlayersSelected == null || numTektonsSelected == null || numTektonsSelected <= 0 || numPlayersSelected <= 0) {
            JOptionPane.showMessageDialog(this, "Please select a valid number of players and tektons (at least 1).", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int numPlayers = numPlayersSelected;
        int numTektons = numTektonsSelected;

        List<Player> players = new ArrayList<>();
        int mushroomerCount = 0;
        for (int i = 0; i < numPlayers; i++) {
            String name = playerNameFields.get(i).getText().trim();
            if (name.isEmpty()) name = "Player " + (i + 1);
            Player p;
            if (((i + 1) % 2 != 0)) { p = new Mushroomer(name); mushroomerCount++; }
            else { p = new Insecter(name); }
            players.add(p);
        }

        int maxNeighbors = (numTektons > 1) ? Math.min(Math.max(2, numTektons / 2), numTektons - 1) : 0;
        List<Tekton> tektons = MapBuilder.build(numTektons, maxNeighbors);

        long growableTektonsCount = tektons.stream().filter(Tekton::canGrow).count();
        if (growableTektonsCount < mushroomerCount && numTektons > 0) {
            JOptionPane.showMessageDialog(this, "Warning: Only " + growableTektonsCount + " Tektons can grow bodies, but there are " + mushroomerCount + " Mushroomers.", "Setup Warning", JOptionPane.WARNING_MESSAGE);
        }

        Game game = new Game(tektons, players);

        System.out.println("GameMenu: Performing initial player setup on map...");
        Random random = new Random();
        List<Tekton> availableTektonsForInitialBody = tektons.stream()
                .filter(t -> t.canGrow() && (t.getMushroomNoPrint() == null || !t.getMushroomNoPrint().hasMushroomBody()))
                .collect(Collectors.toList());
        Collections.shuffle(availableTektonsForInitialBody, random);
        int suitableTektonIdx = 0;

        if (!tektons.isEmpty()) {
            for (Player player : players) {
                Tekton chosenTekton = null;
                if (player instanceof Mushroomer) {
                    if (suitableTektonIdx < availableTektonsForInitialBody.size()) {
                        chosenTekton = availableTektonsForInitialBody.get(suitableTektonIdx++);
                    } else {
                        System.err.println("    WARNING: No unique suitable Tekton for Mushroomer " + player.getName() + ". Trying any random (may fail or overwrite).");
                        if (!tektons.isEmpty()) chosenTekton = tektons.get(random.nextInt(tektons.size()));
                    }
                } else { chosenTekton = tektons.get(random.nextInt(tektons.size())); }

                if (chosenTekton == null) { System.err.println("    CRITICAL: No Tekton available for " + player.getName()); continue; }
                System.out.println("  Setting up " + player.getName() + " on T" + chosenTekton.getIDNoPrint() + " (" + chosenTekton.getClass().getSimpleName() + ")");
                if (player instanceof Insecter) { new Insect(chosenTekton, (Insecter) player); }
                else if (player instanceof Mushroomer) {
                    MushroomBody body = chosenTekton.getMushroomNoPrint().growBody(chosenTekton, (Mushroomer) player);
                    if (body != null) System.out.println("    SUCCESS: Added MushroomBody for " + player.getName());
                    else System.err.println("    FAILURE (Initial Setup): Failed for " + player.getName() + " on T" + chosenTekton.getIDNoPrint() + ". T.canGrow(): " + chosenTekton.canGrow() + ", M.hasBody(): " + chosenTekton.getMushroomNoPrint().hasMushroomBody());
                }
            }
        } else { System.err.println("GameMenu: No tektons for initial setup!"); }
        System.out.println("GameMenu: Initial player setup complete.");

        JFrame gameFrame = new JFrame("Fungus vs Insects");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout(5, 5));
        try {
            MapPanel mapPanel = new MapPanel(game);

            // Get a valid background color for the legend.
            // Using UIManager default is safer than relying on 'this' JDialog's panel
            // which might not be fully realized when this method is called.
            Color legendBg = UIManager.getColor("Panel.background");
            if (legendBg == null) legendBg = new Color(238,238,238); // A common default
            JScrollPane legendPanel = createLegendPanel(legendBg);

            JPanel eastPanel = new JPanel(new BorderLayout(0, 10));
            ActionPanel actionPanel = new ActionPanel(game);
            eastPanel.add(actionPanel, BorderLayout.NORTH);
            eastPanel.add(legendPanel, BorderLayout.CENTER);

            gameFrame.add(new JScrollPane(mapPanel), BorderLayout.CENTER);
            gameFrame.add(eastPanel, BorderLayout.EAST);

            gameFrame.setMinimumSize(new Dimension(1000, 750));
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
            dispose();
        } catch (Throwable ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "CRITICAL Error creating game UI: \n" + ex.toString(), "UI Creation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to create the legend panel
    private JScrollPane createLegendPanel(Color bgColor) { // bgColor is passed from the caller
        JPanel legendContentPanel = new JPanel();
        legendContentPanel.setLayout(new BoxLayout(legendContentPanel, BoxLayout.Y_AXIS));
        legendContentPanel.setBackground(bgColor); // Use the passed background color
        legendContentPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        legendContentPanel.add(new JLabel("<html><b>--- Tekton Types ---</b></html>"));
        legendContentPanel.add(createLegendEntry(MapPanel.getTektonTypeColor("DefaultTekton"), "Default", bgColor));
        legendContentPanel.add(createLegendEntry(MapPanel.getTektonTypeColor("DisabledBodyGrowthTekton"), "No M.Body Growth", bgColor));
        // ... (add other Tekton types from MapPanel.getTektonTypeColor mapping)
        legendContentPanel.add(createLegendEntry(MapPanel.getTektonTypeColor("DisappearingYarnTekton"), "Disappearing Yarn", bgColor));
        legendContentPanel.add(createLegendEntry(MapPanel.getTektonTypeColor("FastGrowthTekton"), "Fast Growth", bgColor));
        legendContentPanel.add(createLegendEntry(MapPanel.getTektonTypeColor("LifeTekton"), "Life (No Cut)", bgColor));
        legendContentPanel.add(createLegendEntry(MapPanel.getTektonTypeColor("SingleMushroomOnlyTekton"), "Single Yarn Only", bgColor));


        legendContentPanel.add(Box.createRigidArea(new Dimension(0,15)));
        legendContentPanel.add(new JLabel("<html><b>--- Spore Effects (Type Char) ---</b></html>"));
        for (Spore.SporeType type : Spore.SporeType.values()) {
            legendContentPanel.add(createLegendEntry(MapPanel.getSporeEffectColor(type), type.toString() + " (" + MapPanel.getSporeTypeChar(type) + ")", bgColor));
        }
        legendContentPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(legendContentPanel);
        scrollPane.setPreferredSize(new Dimension(220, 280)); // Adjusted preferred height
        scrollPane.setBorder(BorderFactory.createTitledBorder("Legend"));
        // For BorderLayout.CENTER in eastPanel, the scrollpane itself is what we add.
        return scrollPane;
    }

    // Helper for legend entries
    private JPanel createLegendEntry(Color color, String text, Color bgColor) {
        JPanel entryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        entryPanel.setBackground(bgColor); // Use the passed background color for each entry
        JLabel colorSwatch = new JLabel("■");
        colorSwatch.setForeground(color);
        colorSwatch.setFont(new Font("SansSerif", Font.BOLD, 16));
        entryPanel.add(colorSwatch);
        entryPanel.add(new JLabel(text));
        return entryPanel;
    }
}