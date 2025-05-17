// ActionPanel.java
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
    private JPanel sporeSelectorPanel; // Panel to hold the spore ComboBox

    public ActionPanel(Game game) {
        this.game = game;
        this.game.addListener(this);

        setLayout(new BorderLayout(5, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topInfoPanel = new JPanel();
        topInfoPanel.setLayout(new BoxLayout(topInfoPanel, BoxLayout.Y_AXIS));
        turnLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        modeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        actionsRemainingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topInfoPanel.add(turnLabel);
        topInfoPanel.add(modeLabel);
        topInfoPanel.add(actionsRemainingLabel);
        add(topInfoPanel, BorderLayout.NORTH);

        // Panel for buttons and spore selector
        JPanel centerControlsPanel = new JPanel(new BorderLayout(5, 5));

        sporeSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sporeSelectorPanel.add(new JLabel("Spore Type:"));
        sporeTypeComboBox = new JComboBox<>(Spore.SporeType.values()); // Populate with enum values
        sporeTypeComboBox.setSelectedItem(Spore.SporeType.CUT_DISABLING); // Default selection
        sporeSelectorPanel.add(sporeTypeComboBox);
        sporeSelectorPanel.setVisible(false); // Initially hidden
        centerControlsPanel.add(sporeSelectorPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(0, 1, 5, 5));
        centerControlsPanel.add(new JScrollPane(buttonsPanel), BorderLayout.CENTER); // Buttons below spore selector

        add(centerControlsPanel, BorderLayout.CENTER);

        selectedInsectStatusArea.setEditable(false);
        selectedInsectStatusArea.setLineWrap(true);
        selectedInsectStatusArea.setWrapStyleWord(true);
        selectedInsectStatusArea.setRows(4);
        selectedInsectStatusArea.setFont(new Font("SansSerif", Font.PLAIN, 10));
        selectedInsectStatusArea.setBorder(BorderFactory.createTitledBorder("Insect Status"));
        add(selectedInsectStatusArea, BorderLayout.SOUTH);

        onStateChanged();
    }

    @Override
    public void onMapChanged() {
        // repaint(); // Could also repaint on map change if needed
    }

    @Override
    public void onStateChanged() {
        Player p = game.getActivePlayer();
        if (p == null) {
            turnLabel.setText("No active player.");
            modeLabel.setText("Mode: -");
            actionsRemainingLabel.setText("Actions: -");
            selectedInsectStatusArea.setText("Selected Insect: None");
            sporeSelectorPanel.setVisible(false);
            buttonsPanel.removeAll();
            revalidate(); repaint();
            return;
        }

        turnLabel.setText("Turn: " + p.getName() + " (" + p.getPlayerType() + ")");
        modeLabel.setText("Mode: " + game.getCurrentInteractionStep() + " (" + game.getCurrentActionType() + ")");
        actionsRemainingLabel.setText("Actions: " + p.getAction());

        buttonsPanel.removeAll();

        // Show spore selector for Mushroomer if they are about to select a target for spreading
        boolean showSporeSelector = p instanceof Mushroomer &&
                game.getCurrentActionType() == Game.GameActionType.MUSHROOMER_SPREAD_SPORES &&
                (game.getCurrentInteractionStep() == Game.InteractionStep.AWAITING_FINAL_TARGET ||
                        game.getCurrentInteractionStep() == Game.InteractionStep.IDLE); // Also show if IDLE, so they can pick then press spread
        sporeSelectorPanel.setVisible(showSporeSelector);


        if (game.getCurrentInteractionStep() != Game.InteractionStep.IDLE) {
            addAction("Cancel Current Action", ev -> game.cancelAction());
            buttonsPanel.add(new JSeparator());
        }

        Object selectedActor = game.getSelectedActor();
        if (selectedActor instanceof Insect) {
            Insect insect = (Insect) selectedActor;
            int[] effects = insect.getEffectsNoPrint();
            String status = "Selected: " + insect.getClass().getSimpleName() + " ID " + insect.getIDNoPrint() + "\n";
            status += "Paralyzed: " + effects[1] + " | No Cut: " + effects[0] + "\n";
            status += "Slowed: " + effects[3] + " | Sped Up: " + effects[2];
            selectedInsectStatusArea.setText(status);
            selectedInsectStatusArea.setVisible(true);
        } else {
            selectedInsectStatusArea.setText("Selected Insect: None");
            selectedInsectStatusArea.setVisible(p instanceof Insecter);
        }

        if (p instanceof Insecter) {
            addAction("Move",    ev -> game.startAction(Game.GameActionType.INSECT_MOVE));
            addAction("Eat Spore",     ev -> game.startAction(Game.GameActionType.INSECT_EAT_SPORE));
            addAction("Cut Yarn",     ev -> game.startAction(Game.GameActionType.INSECT_CUT_YARN));
        } else { // Mushroomer
            addAction("Spread Spores", ev -> {
                Spore.SporeType selectedType = (Spore.SporeType) sporeTypeComboBox.getSelectedItem();
                if (selectedType != null) {
                    game.setContextForSpreadAction(selectedType); // Game stores the type
                    game.startAction(Game.GameActionType.MUSHROOMER_SPREAD_SPORES); // Then starts the action
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a spore type first.", "Spore Type Selection", JOptionPane.WARNING_MESSAGE);
                }
            });
            addAction("Grow Yarn", ev -> game.startAction(Game.GameActionType.MUSHROOMER_GROW_YARN));
            addAction("Grow Body", ev -> game.startAction(Game.GameActionType.MUSHROOMER_GROW_BODY));
        }

        buttonsPanel.add(new JSeparator());
        addAction("Save (NI)", ev -> { /* save state */ });
        addAction("End Turn", ev -> game.nextPlayer());

        revalidate();
        repaint();
    }

    private void addAction(String name, ActionListener l) {
        JButton b = new JButton(name);
        b.addActionListener(l);
        buttonsPanel.add(b);
    }
}