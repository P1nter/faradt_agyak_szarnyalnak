import javax.swing.*;
import java.util.Observer;
/*
public class SporeView extends JLabel implements Observer {
    private final Spore spore;
    private final Color[] TYPE_COLORS = {Color.RED, Color.BLUE, Color.YELLOW};

    public SporeView(Spore spore) {
        this.spore = spore;
        spore.addObserver(this);
        setOpaque(true);
        setSize(10, 10);
        update();
    }

    @Override
    public void update() {
        setBackground(TYPE_COLORS[spore.getType().ordinal()]);
        setToolTipText(spore.getType().toString());
    }
}*/