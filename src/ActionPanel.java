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

    public ActionPanel(Game game) {
        this.game = game;
        this.game.addListener(this);

        setLayout(new BorderLayout(5, 10)); // Increased vgap
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

        buttonsPanel.setLayout(new GridLayout(0, 1, 5, 5));
        add(new JScrollPane(buttonsPanel), BorderLayout.CENTER);

        selectedInsectStatusArea.setEditable(false);
        selectedInsectStatusArea.setLineWrap(true);
        selectedInsectStatusArea.setWrapStyleWord(true);
        selectedInsectStatusArea.setRows(4); // Adjust as needed
        selectedInsectStatusArea.setFont(new Font("SansSerif", Font.PLAIN, 10));
        selectedInsectStatusArea.setBorder(BorderFactory.createTitledBorder("Insect Status"));
        add(selectedInsectStatusArea, BorderLayout.SOUTH);


        onStateChanged();  // initial build
    }

    @Override
    public void onMapChanged() {
        // Usually, state changes cover UI updates.
    }

    @Override
    public void onStateChanged() {
        Player p = game.getActivePlayer();
        if (p == null) {
            turnLabel.setText("No active player.");
            modeLabel.setText("Mode: -");
            actionsRemainingLabel.setText("Actions: -");
            selectedInsectStatusArea.setText("Selected Insect: None");
            buttonsPanel.removeAll();
            revalidate();
            repaint();
            return;
        }

        turnLabel.setText("Turn: " + p.getName() + " (" + p.getPlayerType() + ")");
        modeLabel.setText("Mode: " + game.getCurrentInteractionStep() + " (" + game.getCurrentActionType() + ")");
        actionsRemainingLabel.setText("Actions: " + p.getAction() ); // Assumes Player has getAction() for actions remaining

        buttonsPanel.removeAll();

        if (game.getCurrentInteractionStep() != Game.InteractionStep.IDLE) {
            addAction("Cancel Current Action", ev -> game.cancelAction());
            buttonsPanel.add(new JSeparator());
        }

        Object selectedActor = game.getSelectedActor();
        if (selectedActor instanceof Insect) {
            Insect insect = (Insect) selectedActor;
            int[] effects = insect.getEffectsNoPrint(); // {cutDisabled, paralyzed, spedUp, slowedDown}
            String status = "Selected Insect: ID " + insect.getIDNoPrint() + "\n";
            status += "Paralyzed for: " + effects[1] + " turns\n";
            status += "Can't cut for: " + effects[0] + " turns\n";
            status += "Slowed for: " + effects[3] + " turns\n"; // Swapped indices based on common game logic
            status += "Speeded for: " + effects[2] + " turns";
            selectedInsectStatusArea.setText(status);
            selectedInsectStatusArea.setVisible(true);
        } else {
            selectedInsectStatusArea.setText("Selected Insect: None");
            if (p instanceof Insecter) { // Only show area if it's an insecter's turn but no insect selected
                selectedInsectStatusArea.setVisible(true);
            } else {
                selectedInsectStatusArea.setVisible(false); // Hide for mushroomer if no insect context
            }
        }


        if (p instanceof Insecter) {
            addAction("Move",    ev -> game.startAction(Game.GameActionType.INSECT_MOVE));
            addAction("Eat Spore",     ev -> game.startAction(Game.GameActionType.INSECT_EAT_SPORE));
            addAction("Cut Yarn",     ev -> game.startAction(Game.GameActionType.INSECT_CUT_YARN));
        } else { // Mushroomer
            addAction("Spread Spores", ev -> game.startAction(Game.GameActionType.MUSHROOMER_SPREAD_SPORES));
            addAction("Grow Yarn", ev -> game.startAction(Game.GameActionType.MUSHROOMER_GROW_YARN));
            addAction("Grow Body", ev -> game.startAction(Game.GameActionType.MUSHROOMER_GROW_BODY));
            // addAction("Eat",       ev -> game.startAction(Game.GameActionType.MUSHROOMER_EAT));
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