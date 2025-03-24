import java.util.List;
import java.util.ArrayList;

public class DefaultTekton extends Tekton {
    private List<Tekton> adjacentTektons = new ArrayList<>();
    public DefaultTekton() {

        System.out.println("DefaultTekton.DefaultTekton() called");

        System.out.println("DefaultTekton.DefaultTekton() returned");
    }
    @Override
    public List<Tekton> split() {
        System.out.println("DefaultTekton.split() called");
        System.out.println("Mushroom adding for testing started");
        mushrooms.add(new Mushroom());
        System.out.println("Mushroom adding finished");
        for (Mushroom m : mushrooms) {
            m.update();
        }
        System.out.println("DefaultTekton.split() returned");
        return null;
    }
    public List<Tekton> getAdjacentTektons(){
        System.out.println("DefaultTekton.getAdjacentTektons() called");
        System.out.println("DefaultTekton.getAdjacentTektons() returned ArrayList<DefaultTekton>");
        return adjacentTektons;
    }
}
