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

        // Top info
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        turnLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        modeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        actionsRemainingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(turnLabel);
        top.add(modeLabel);
        top.add(actionsRemainingLabel);
        add(top, BorderLayout.NORTH);

        // Center: spore selector + buttons
        JPanel center = new JPanel(new BorderLayout(5,5));
        sporeSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sporeSelectorPanel.add(new JLabel("Spore Type:"));
        sporeTypeComboBox = new JComboBox<>(Spore.SporeType.values());
        sporeTypeComboBox.setSelectedItem(Spore.SporeType.CUT_DISABLING);
        sporeSelectorPanel.add(sporeTypeComboBox);
        JButton applySpore = new JButton("Apply");
        applySpore.addActionListener(e -> applySporeSelection());
        sporeSelectorPanel.add(applySpore);
        sporeSelectorPanel.setVisible(false);
        center.add(sporeSelectorPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(0,1,5,5));
        center.add(new JScrollPane(buttonsPanel), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // Bottom insect info
        selectedInsectStatusArea.setEditable(false);
        selectedInsectStatusArea.setLineWrap(true);
        selectedInsectStatusArea.setWrapStyleWord(true);
        selectedInsectStatusArea.setRows(4);
        selectedInsectStatusArea.setFont(new Font("SansSerif", Font.PLAIN, 10));
        selectedInsectStatusArea.setBorder(BorderFactory.createTitledBorder("Insect Status Info"));
        add(selectedInsectStatusArea, BorderLayout.SOUTH);

        onStateChanged();  // init UI
    }

    @Override
    public void onMapChanged() {
        repaint();
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

        sporeSelectorPanel.setVisible(false);

        if (player instanceof Mushroomer m) {
            handleMushroomerActions(m);
        } else if (player instanceof Insecter) {
            handleInsecterActions();
        }

        buttonsPanel.add(new JSeparator());
        addAction("Save Game",   e -> saveGame());
        addAction("End Turn",    e -> game.nextPlayer());
        addAction("End Game",    e -> {
            // manual end-game
            String summary = game.getWinnersSummary();
            JOptionPane.showMessageDialog(
                    this, summary, "Game Over – Winners", JOptionPane.INFORMATION_MESSAGE
            );
            // close window
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w != null) w.dispose();
        });

        revalidate();
        repaint();
    }

    @Override
    public void onGameEnd(Game.Winners winners) {
        // automatic end-game after rounds limit
        String summary = game.getWinnersSummary();
        JOptionPane.showMessageDialog(
                this, summary, "Game Over – Winners", JOptionPane.INFORMATION_MESSAGE
        );
        Window w = SwingUtilities.getWindowAncestor(this);
        if (w != null) w.dispose();
    }

    private void updatePlayerInfo(Player player) {
        turnLabel.setText("Turn: " + player.getName() + " (" + player.getPlayerType() + ")");
        modeLabel.setText("Mode: " + game.getCurrentInteractionStep() +
                " (" + game.getCurrentActionType() + ")");
        actionsRemainingLabel.setText("Actions: " + player.getAction());

        Object sel = game.getSelectedActor();
        if (sel instanceof Insect insect) {
            int[] fx = insect.getEffectsNoPrint();
            String status =
                    "Selected: " + insect.getClass().getSimpleName() +
                            " ID " + insect.getIDNoPrint() + "\n" +
                            "Paralyzed: " + fx[1] + " | No Cut: " + fx[0] + "\n" +
                            "Slowed: "     + fx[3] + " | Sped Up: " + fx[2];
            selectedInsectStatusArea.setText(status);
            selectedInsectStatusArea.setVisible(true);
        } else {
            selectedInsectStatusArea.setText("Selected Insect: None");
            selectedInsectStatusArea.setVisible(player instanceof Insecter);
        }
    }

    private void handleMushroomerActions(Mushroomer m) {
        addAction("Spread Spores", e -> {
            Spore.SporeType t = (Spore.SporeType)sporeTypeComboBox.getSelectedItem();
            if (t != null) {
                game.setContextForSpreadAction(t);
                game.startAction(Game.GameActionType.MUSHROOMER_SPREAD_SPORES);
            }
        });
        boolean show = game.getCurrentActionType() == Game.GameActionType.MUSHROOMER_SPREAD_SPORES
                && (game.getCurrentInteractionStep() == Game.InteractionStep.AWAITING_FINAL_TARGET
                || game.getCurrentInteractionStep() == Game.InteractionStep.IDLE);
        sporeSelectorPanel.setVisible(show);

        addAction("Grow Yarn", e -> game.startAction(Game.GameActionType.MUSHROOMER_GROW_YARN));
        addAction("Grow Body", e -> game.startAction(Game.GameActionType.MUSHROOMER_GROW_BODY));
        addAction("Eat Insect", e -> {
            Object s = game.getSelectedActor();
            if (s instanceof Insect ins) {
                game.startAction(Game.GameActionType.MUSHROOMER_EAT);
                game.tryMushroomerEat(ins.getTektonNoPrint());
            }
        });
    }

    private void handleInsecterActions() {
        addAction("Move",      e -> game.startAction(Game.GameActionType.INSECT_MOVE));
        addAction("Eat Spore", e -> game.startAction(Game.GameActionType.INSECT_EAT_SPORE));
        addAction("Cut Yarn",  e -> game.startAction(Game.GameActionType.INSECT_CUT_YARN));
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
        Spore.SporeType t = (Spore.SporeType)sporeTypeComboBox.getSelectedItem();
        if (t != null) {
            game.setContextForSpreadAction(t);
            System.out.println("Spore type applied: " + t);
        }
    }

    private void saveGame() {
        System.out.println("Game state saved.");
    }

    private void addAction(String name, ActionListener listener) {
        JButton btn = new JButton(name);
        btn.addActionListener(listener);
        buttonsPanel.add(btn);
    }
}