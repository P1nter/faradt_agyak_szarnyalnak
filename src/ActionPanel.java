import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel implements GameListener {
    private final Game game;
    private final JLabel turnLabel = new JLabel(" ");
    private final JLabel modeLabel = new JLabel("Mode: IDLE");
    private final JLabel actionsRemainingLabel = new JLabel("Actions: N/A");
    private final JTextArea selectedInsectStatusArea = new JTextArea("Selected Insect: None");
    private final JPanel buttonsPanel = new JPanel();
    private JComboBox<Spore.SporeType> sporeTypeComboBox;
    private JPanel sporeSelectorPanel;

    public ActionPanel(Game game) {
        this.game = game;
        this.game.addListener(this);

        setLayout(new BorderLayout(5, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Setup top info panel
        JPanel topInfoPanel = new JPanel();
        topInfoPanel.setLayout(new BoxLayout(topInfoPanel, BoxLayout.Y_AXIS));
        turnLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        modeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        actionsRemainingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topInfoPanel.add(turnLabel);
        topInfoPanel.add(modeLabel);
        topInfoPanel.add(actionsRemainingLabel);
        add(topInfoPanel, BorderLayout.NORTH);

        // Setup center controls panel
        JPanel centerControlsPanel = new JPanel(new BorderLayout(5, 5));
        sporeSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sporeSelectorPanel.add(new JLabel("Spore Type:"));
        sporeTypeComboBox = new JComboBox<>(Spore.SporeType.values());
        sporeTypeComboBox.setSelectedItem(Spore.SporeType.CUT_DISABLING); // Default spore selection
        sporeSelectorPanel.add(sporeTypeComboBox);

        // Add apply button next to the spore selector
        JButton applySporeButton = new JButton("Apply");
        applySporeButton.addActionListener(e -> applySporeSelection());
        sporeSelectorPanel.add(applySporeButton);

        centerControlsPanel.add(sporeSelectorPanel, BorderLayout.NORTH);
        sporeSelectorPanel.setVisible(false); // Initially hidden

        buttonsPanel.setLayout(new GridLayout(0, 1, 5, 5));
        centerControlsPanel.add(new JScrollPane(buttonsPanel), BorderLayout.CENTER);

        add(centerControlsPanel, BorderLayout.CENTER);

        // Setup bottom insect status area
        selectedInsectStatusArea.setEditable(false);
        selectedInsectStatusArea.setLineWrap(true);
        selectedInsectStatusArea.setWrapStyleWord(true);
        selectedInsectStatusArea.setRows(4);
        selectedInsectStatusArea.setFont(new Font("SansSerif", Font.PLAIN, 10));
        selectedInsectStatusArea.setBorder(BorderFactory.createTitledBorder("Insect Status Info"));
        add(selectedInsectStatusArea, BorderLayout.SOUTH);

        onStateChanged(); // Initialize UI with the current game state
    }

    @Override
    public void onMapChanged() {
        repaint(); // Redraw if the map changes
    }

    @Override
    public void onStateChanged() {
        Player player = game.getActivePlayer();
        if (player == null) {
            resetPanel();
            return;
        }

        updatePlayerInfo(player);

        buttonsPanel.removeAll();

        if (game.getCurrentInteractionStep() != Game.InteractionStep.IDLE) {
            addAction("Cancel Current Action", e -> game.cancelAction());
            buttonsPanel.add(new JSeparator());
        }

        sporeSelectorPanel.setVisible(false); // Default: hidden unless a spore-requiring action is active

        if (player instanceof Mushroomer mushroomer) {
            handleMushroomerActions(mushroomer);
        } else if (player instanceof Insecter) {
            handleInsecterActions();
        }

        buttonsPanel.add(new JSeparator());
        addAction("Save Game", e -> saveGame());
        addAction("End Turn", e -> game.nextPlayer());

        revalidate();
        repaint();
    }

    private void updatePlayerInfo(Player player) {
        turnLabel.setText("Turn: " + player.getName() + " (" + player.getPlayerType() + ")");
        modeLabel.setText("Mode: " + game.getCurrentInteractionStep() + " (" + game.getCurrentActionType() + ")");
        actionsRemainingLabel.setText("Actions: " + player.getAction());

        // Update insect status if any
        Object selectedActor = game.getSelectedActor();
        if (selectedActor instanceof Insect insect) {
            int[] effects = insect.getEffectsNoPrint();
            String status = "Selected: " + insect.getClass().getSimpleName() + " ID " + insect.getIDNoPrint() + "\n";
            status += "Paralyzed: " + effects[1] + " | No Cut: " + effects[0] + "\n";
            status += "Slowed: " + effects[3] + " | Sped Up: " + effects[2];
            selectedInsectStatusArea.setText(status);
            selectedInsectStatusArea.setVisible(true);
        } else {
            selectedInsectStatusArea.setText("Selected Insect: None");
            selectedInsectStatusArea.setVisible(player instanceof Insecter);
        }
    }

    private void handleMushroomerActions(Mushroomer mushroomer) {
        // Add actions specific to the Mushroomer role
        addAction("Spread Spores", e -> {
            Spore.SporeType selectedType = (Spore.SporeType) sporeTypeComboBox.getSelectedItem();
            if (selectedType != null) {
                game.setContextForSpreadAction(selectedType); // Update context with selected spore type
                game.startAction(Game.GameActionType.MUSHROOMER_SPREAD_SPORES);
            }
        });

        // Show the spore selector panel only when the Mushroomer is spreading spores
        boolean showSporeSelector = game.getCurrentActionType() == Game.GameActionType.MUSHROOMER_SPREAD_SPORES &&
                (game.getCurrentInteractionStep() == Game.InteractionStep.AWAITING_FINAL_TARGET ||
                 game.getCurrentInteractionStep() == Game.InteractionStep.IDLE);

        sporeSelectorPanel.setVisible(showSporeSelector);

        addAction("Grow Yarn", e -> game.startAction(Game.GameActionType.MUSHROOMER_GROW_YARN));
        addAction("Grow Body", e -> game.startAction(Game.GameActionType.MUSHROOMER_GROW_BODY));

        addAction("Eat Insect", e -> {
            Object selected = game.getSelectedActor();
            if (selected instanceof Insect insect) {
                game.startAction(Game.GameActionType.MUSHROOMER_EAT);
                game.tryMushroomerEat(insect.getTekton()); // Delegate eating logic to the game
            }
        });
    }

    private void handleInsecterActions() {
        // Add actions specific to the Insecter role
        addAction("Move", e -> game.startAction(Game.GameActionType.INSECT_MOVE));
        addAction("Eat Spore", e -> game.startAction(Game.GameActionType.INSECT_EAT_SPORE));
        addAction("Cut Yarn", e -> game.startAction(Game.GameActionType.INSECT_CUT_YARN));
    }

    private void resetPanel() {
        turnLabel.setText("No active player.");
        modeLabel.setText("Mode: -");
        actionsRemainingLabel.setText("Actions: -");
        selectedInsectStatusArea.setText("Selected Insect: None");
        sporeSelectorPanel.setVisible(false);
        buttonsPanel.removeAll();
        revalidate();
        repaint();
    }

    private void applySporeSelection() {
        Spore.SporeType selectedType = (Spore.SporeType) sporeTypeComboBox.getSelectedItem();
        if (selectedType != null) {
            game.setContextForSpreadAction(selectedType); // Save the currently selected spore type
            System.out.println("Spore type applied: " + selectedType);
        }
    }

    private void saveGame() {
        // Placeholder for save logic
        System.out.println("Game state saved.");
    }

    private void addAction(String name, ActionListener listener) {
        JButton button = new JButton(name);
        button.addActionListener(listener);
        buttonsPanel.add(button);
    }
}