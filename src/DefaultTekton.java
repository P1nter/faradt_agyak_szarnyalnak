import java.util.List;
import java.util.ArrayList;

public class DefaultTekton implements ITekton {
    private List<ITekton> adjacentTektons = new ArrayList<>();
    public DefaultTekton() {
        System.out.println("DefaultTekton.DefaultTekton() called");

        System.out.println("DefaultTekton.DefaultTekton() returned");
    }

    public List<ITekton> split() {
        System.out.println("DefaultTekton.split() called");

        System.out.println("DefaultTekton.split() returned");
        return null;
    }
    public List<ITekton> getAdjacentTektons(){
        System.out.println("DefaultTekton.getAdjacentTektons() called");
        System.out.println("DefaultTekton.getAdjacentTektons() returned ArrayList<DefaultTekton>");
        return adjacentTektons;
    }
}
