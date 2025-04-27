import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

public class MushroomYarn {
    private Tekton[] tektons = new Tekton[2]; // Fixed size array for exactly 2 Tektons

    public MushroomYarn(Tekton tekton1, Tekton tekton2) {
        System.out.println("MushroomYarn.MushroomYarn(Tekton, Tekton) called");
        if (tekton1 == null || tekton2 == null) {
            throw new IllegalArgumentException("Both Tektons must be non-null.");
        }
        this.tektons[0] = tekton1;
        this.tektons[1] = tekton2;
        System.out.println("MushroomYarn.MushroomYarn(Tekton, Tekton) returned");
    }

    public Tekton[] getTektons() {
        System.out.println("MushroomYarn.getTektons() called");
        System.out.println("MushroomYarn.getTektons() returned Tekton[]");
        return Arrays.copyOf(tektons, tektons.length); // Return a copy to prevent external modification
    }

    public void updateTektons(Tekton tekton1, Tekton tekton2) {
        System.out.println("MushroomYarn.updateTektons(Tekton, Tekton) called");
        if (tekton1 == null || tekton2 == null) {
            throw new IllegalArgumentException("Both Tektons must be non-null.");
        }
        this.tektons[0] = tekton1;
        this.tektons[1] = tekton2;
        System.out.println("MushroomYarn.updateTektons(Tekton, Tekton) returned");
    }

    public void decay() {
        System.out.println("MushroomYarn.decay() called");
        System.out.println("MushroomYarn.decay() returned");
    }

    public boolean cut() {
        System.out.println("MushroomYarn.cut() called");
        if(!(tektons[0].canCut() && tektons[1].canCut())){
            System.out.println("MushroomYarn.cut() returned false");
            return false;
        }
        boolean m1 = tektons[0].getMushroom().hasMushroomBody();
        boolean m2= tektons[1].getMushroom().hasMushroomBody();

        if (!m1 && !m2) {
            System.out.println("MushroomYarn.cut() returned false");
            return false;
        }

        System.out.println("MushroomYarn.cut() returned true");
        return true;
    }

    public void update() {
        System.out.println("MushroomYarn.update() called");
        System.out.println("MushroomYarn.update() returned");
    }
}
