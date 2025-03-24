import java.util.ArrayList;
import java.util.List;

public class MushroomYarn {
    private List<Tekton> tektons = new ArrayList<>();



    public MushroomYarn() {
        System.out.println();
        System.out.println("MushroomBody creating for testing started");
        MushroomBody mushroomBody = new MushroomBody();
        System.out.println("MushroomBody creating for testing finished");
        System.out.println();
        System.out.println("MushroomYarn.MushroomYarn called");

        tektons = mushroomBody.getTektons();
        System.out.println("MushroomYarn.MushroomYarn returned");
    }
    public void decay() {
        System.out.println("MushroomYarn.decay() called");

        System.out.println("MushroomYarn.decay() returned");

    }
    public void cut() {
        System.out.println("MushroomYarn.cut() called");
        System.out.println("MushroomYarn.cut() returned");
    }
    public void update() {
        System.out.println("MushroomYarn.update() called");

        System.out.println("MushroomYarn.update() returned");
    }
    public List<Tekton> getTektons() {
        System.out.println("MushroomYarn.getTektons() called");
        System.out.println("MushroomYarn.getTektons() returned ArrayList<ITekton>");
        return tektons;
    }
}
