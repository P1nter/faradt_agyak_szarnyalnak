import java.util.ArrayList;
import java.util.List;

public class MushroomBody {
    private List<Tekton> tektons;

    //Teszteléshez
    Spore spore;

    public void releaseSpore() {
        System.out.println("MushroomBody.releaseSpore called");

        spore = new ParalyzingSpore();
        //setTektons(tektons);

        System.out.println("MushroomBody.releaseSpore returned");
    }
    public MushroomBody(){
        System.out.println("MushroomBody.MushroomBody() called");
        setTektons(new ArrayList<Tekton>());
        System.out.println("MushroomBody.MushroomBody() returned");
    }

    public void setTektons(List<Tekton> tektons) {
        System.out.println("MushroomBody.setTektons() called");

        System.out.println("MushroomBody.setTektons() returned");
    }

    public List<Tekton> getTektons() {
        System.out.println("MushroomBody.getTektons() called");

        System.out.println("MushroomBody.getTektons() returned List<ITekton>");
        return tektons;
    }

    public boolean destroyBody(){
        System.out.println("MushroomBody.destroyBody() called");
        System.out.println("MushroomBody.destroyBody() returned true");
        return true;
    }
}
