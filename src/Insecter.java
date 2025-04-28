import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game who controls a collection of insects.
 * <p>
 * This class extends the {@code Player} class and specifically manages a list
 * of {@code Insect} objects owned by this player. It provides functionality
 * to add and retrieve these insects.
 * </p>
 *
 * @see Player
 * @see Insect
 * @since 1.0
 */
public class Insecter extends Player{
    private List<Insect> insects = new ArrayList<Insect>();

    /**
     * Constructs a new {@code Insecter} with the given name.
     *
     * @param name The name of the insect controlling player.
     */
    public Insecter(String name) {
        super(name);
        System.out.println("Insecter.Insecter(name) called");
        System.out.println("Insecter.Insecter(name) returned");
    }

    /**
     * Attempts to have one of this player's insects consume a spore.
     * <p>
     * Note: The current implementation of this method does not specify
     * *which* insect consumes the spore. This might need to be refined
     * in future implementations to allow the player to choose an insect.
     * </p>
     *
     * @param spore The {@code Spore} to be consumed by one of the player's insects.
     */
    public void ConsumSpore(Spore spore) {
        System.out.println("Insecter.ConsumSpore(spore) called");
        // TODO: Implement logic to select an insect to consume the spore.
        // For example, iterate through the insects and allow the player to choose.
        System.out.println("Insecter.ConsumSpore(spore) returned");
    }

    /**
     * Adds an insect to this player's collection of controlled insects.
     *
     * @param insect The {@code Insect} object to be added to the player's insects.
     */
    public void addInsect(Insect insect) {
        System.out.println("Insecter.AddInsect(insect) called");
        insects.add(insect);
        System.out.println("Insecter.AddInsect(insect) returned");
    }

    /**
     * Retrieves the list of insects controlled by this player.
     *
     * @return A {@code List} containing all the {@code Insect} objects owned by this player.
     */
    public List<Insect> getInsects() {
        System.out.println("Insecter.getInsects() called");
        System.out.println("Insecter.getInsects() returned");
        return insects;
    }
}