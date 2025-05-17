import java.util.List;

public class DefaultTekton extends Tekton {
    public DefaultTekton(int id) {
        super(id);
    }
    public DefaultTekton() {
        super(); // Calls Tekton() which assigns a unique ID
    }
    // This constructor can be useful for specific map setups if MapBuilder doesn't do all linking
    public DefaultTekton(List<Tekton> adjacentTektons, int id) {
        super(id); // Sets ID and initializes mushroomManager
        if (adjacentTektons != null) {
            for (Tekton t : adjacentTektons) {
                this.addAdjacentTekton(t); // Use the safe add method
            }
        }
    }
}