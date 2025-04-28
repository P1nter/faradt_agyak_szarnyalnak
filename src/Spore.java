import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class representing a spore within the game.
 * <p>
 * This class defines the common properties and behaviors of all spore types.
 * Each spore has a nutrition value and is associated with a specific {@code Tekton},
 * likely the one from which it originated. Subclasses will implement specific
 * effects that the spore has on insects.
 * </p>
 *
 * @see Insect
 * @see Tekton
 * @see Mushroom
 * @since 1.0
 */
abstract class Spore {
    /**
     * The nutritional value of the spore.
     */
    int nutrition = 5;

    /**
     * The {@code Tekton} associated with this spore.
     */
    Tekton tekton;

    /**
     * Protected default constructor for subclasses.
     */
    protected Spore(){}

    /**
     * Protected constructor to create a spore associated with a specific {@code Tekton}.
     *
     * @param tekton The {@code Tekton} associated with this spore.
     */
    protected Spore(Tekton tekton) {
        this.tekton = tekton;
    }

    public Tekton getTekton() { return this.tekton; }

    /**
     * Abstract method to define how this spore affects an insect.
     * <p>
     * Subclasses must implement this method to apply their specific effects
     * to the given insect. The default implementation does nothing.
     * </p>
     *
     * @param insect The {@code Insect} to be affected by this spore.
     */
    public void affectInsect(Insect insect){
        // Subclasses will implement specific effects here.
    }

    /**
     * Destroys this spore by removing it from the list of spores of its associated mushroom.
     */
    public void destroySpore(){
        if (tekton != null && tekton.getMushroom() != null) {
            tekton.getMushroom().getSpores().remove(this);
        }
    }
}