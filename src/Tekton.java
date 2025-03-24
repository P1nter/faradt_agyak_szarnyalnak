import java.util.ArrayList;
import java.util.List;

abstract class Tekton {
    List<Tekton> adjacentTektons = new ArrayList<>();
    List<Mushroom> mushrooms = new ArrayList<>();
    List<Insect> insects = null;
    List<Tekton> split() {return adjacentTektons;}
    List<Tekton> getAdjacentTektons() {return adjacentTektons;}
}
