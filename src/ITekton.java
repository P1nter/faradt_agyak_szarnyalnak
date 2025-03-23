import java.util.List;

public interface ITekton {
    List<ITekton> adjacentTektons = null;
    List<Mushroom> mushrooms = null;
    List<Insect> insects = null;
    List<ITekton> split();
    List<ITekton> getAdjacentTektons();
}
