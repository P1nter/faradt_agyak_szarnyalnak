import java.util.ArrayList;
import java.util.List;

public class MushroomYarn {
    private List<ITekton> tektons = new ArrayList<>();

    //Teszteléshez
    private MushroomBody mushroomBody = new MushroomBody();

    public MushroomYarn() {
        System.out.println("MushroomYarn.MushroomYarn called");

        tektons = mushroomBody.getTektons();

        System.out.println("MushroomYarn.MushroomYarn returned");
    }
    public void decay() {}
    public void cut() {}
    public void update() {}
    public List<ITekton> getTektons() {
        System.out.println("MushroomYarn.getTektons() called");
        System.out.println("MushroomYarn.getTektons() returned ArrayList<ITekton>");
        return tektons;
    }
}
