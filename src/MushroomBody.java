import java.util.ArrayList;
import java.util.List;

public class MushroomBody {
    private List<ITekton> tektons;

    //Teszteléshez
    ISpore spore;

    public void releaseSpore() {
        System.out.println("MushroomBody.releaseSpore called");

        spore = new ParalyzingSpore();
        setTektons(tektons);

        System.out.println("MushroomBody.releaseSpore returned");
    }
    public MushroomBody(){
        System.out.println("MushroomBody.MushroomBody() called");
        setTektons(new ArrayList<ITekton>());
        System.out.println("MushroomBody.MushroomBody() returned");
    }

    public void setTektons(List<ITekton> tektons) {
        System.out.println("MushroomBody.setTektons() called");

        System.out.println("MushroomBody.setTektons() returned");
    }

    public List<ITekton> getTektons() {
        System.out.println("MushroomBody.getTektons() called");

        System.out.println("MushroomBody.getTektons() returned List<ITekton>");
        return tektons;
    }
}
