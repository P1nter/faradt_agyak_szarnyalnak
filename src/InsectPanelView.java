import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
/*
public class InsectPanelView extends JPanel implements Observer {
    private final JLabel scoreLabel = new JLabel();
    private final JLabel speedLabel = new JLabel();
    private final JPanel effectsPanel = new JPanel();
    private final Insect insect;

    public InsectPanelView(Insect insect) {
        this.insect = insect;
        insect.addObserver(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Játékos állapota:"));
        add(scoreLabel);
        add(speedLabel);
        add(new JLabel("Aktív hatások:"));
        add(effectsPanel);

        update();
    }

    @Override
    public void update() {
        scoreLabel.setText("Pontszám: " + insect.getScore());
        speedLabel.setText("Sebesség: " + insect.getSpeed());

        effectsPanel.removeAll();
        for (Spore effect : insect.getActiveEffects()) {
            effectsPanel.add(new SporeView(effect));
        }
        effectsPanel.revalidate();
    }

    @Override
    public void update(Observable o, Object arg) {


    }
}
*/