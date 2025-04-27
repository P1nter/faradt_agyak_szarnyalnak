import java.util.ArrayList;
import java.util.List;

public class MushroomBody {
    private Tekton tekton;

    public void releaseSpore() {
        System.out.println("MushroomBody.releaseSpore called");

        //setTektons(tektons);

        System.out.println("MushroomBody.releaseSpore returned");
    }
    public MushroomBody(Tekton tekton){
        System.out.println("MushroomBody.MushroomBody() called");
        this.tekton = tekton;
        System.out.println("MushroomBody.MushroomBody() returned");
    }

    public void addTekton(List<Tekton> tektons) {
        System.out.println("MushroomBody.setTektons() called");

        System.out.println("MushroomBody.setTektons() returned");
    }

    public Tekton getTektons() {
        System.out.println("MushroomBody.getTektons() called");

        System.out.println("MushroomBody.getTektons() returned List<ITekton>");
        return tekton;
    }

    public boolean destroyBody(){
        System.out.println("MushroomBody.destroyBody() called");
        System.out.println("MushroomBody.destroyBody() returned true");
        return true;
    }

}
