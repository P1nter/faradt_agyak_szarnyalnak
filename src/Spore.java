import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
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
//make it observable
//import java.util.Observable;
abstract class Spore  extends Observable {
    /**
     * The nutritional value of the spore.
     */
    int nutrition = 5;
    private Mushroomer Owner;

    private int ID;
    /**
     * The {@code Tekton} associated with this spore.
     */
    Tekton tekton;



    public void setOwner(Mushroomer mushroomer){
        System.out.println("Spore.setOwner() called");
        this.Owner = mushroomer;
        System.out.println("Spore.setOwner() returned");
    }
    public Mushroomer getOwner() {
        System.out.println("Spore.getOwner() called");
        System.out.println("Spore.getOwner() returned");
        return Owner; }
    /**
     * Protected default constructor for subclasses.
     */
    protected Spore(){}

    /**
     * Protected constructor to create a spore associated with a specific {@code Tekton}.
     *
     * @param tekton The {@code Tekton} associated with this spore.
     */
    protected Spore(Tekton tekton, Mushroomer mushroomer) {
        System.out.println("Spore(tekton) called");
        this.tekton = tekton;
        this.ID = 0;
        System.out.println("Spore(tekton) returned");
    }
    protected Spore(Tekton tekton, Mushroomer mushroomer, int ID) {
        System.out.println("Spore(tekton) called");
        this.tekton = tekton;
        this.ID = ID;
        System.out.println("Spore(tekton) returned");
    }

    public Tekton getTekton() {
        System.out.println("Spore.getTekton() called");
        System.out.println("Spore.getTekton() returned");
        return this.tekton; }

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
        System.out.println("Spore.destroySpore() called");
        if (tekton != null && tekton.getMushroom() != null) {
            tekton.getMushroom().getSpores().remove(this);
        }
        System.out.println("Spore.destroySpore() returned");
    }
    public void setTekton(Tekton tekton) {
        System.out.println("Spore.setTekton() called");
        this.tekton = tekton;
        System.out.println("Spore.setTekton() returned");
    }

    public int getIDNoPrint() {
        return ID;
    }
}