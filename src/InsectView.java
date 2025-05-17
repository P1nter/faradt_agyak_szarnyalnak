import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JLabel;
/*
public class InsectView extends JLabel implements PropertyChangeListener {
    public InsectView(Insect insect) {
        insect.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("tekton".equals(evt.getPropertyName())) {
            Tekton newTekton = (Tekton) evt.getNewValue();
            SwingUtilities.invokeLater(() -> setText("Tekton: " + newTekton.getIDNoPrint()));
        }
    }
}*/