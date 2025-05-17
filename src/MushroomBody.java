import javax.print.attribute.standard.MultipleDocumentHandling;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Represents the body of a mushroom, capable of releasing spores.
 * <p>
 * A {@code MushroomBody} is associated with a specific {@code Tekton} and
 * can perform actions such as releasing spores. It can also be destroyed.
 * </p>
 *
 * @see Tekton
 * @see Spore
 * @since 1.0
 */
public class MushroomBody extends Observable {
    private Tekton tekton;
    private Mushroomer owner;
    private int ID;

    public int getID() {
        System.out.println("MushroomBody.getID() called");
        try {
            return ID;
        } catch(NullPointerException e){
            System.out.println("ID is null");
        }
        System.out.println("MushroomBody.getID() returned " + ID);
        return 0;
    }
    // Getter without System.out.println for ID
    public int getIDNoPrint() {
        return ID;
    }

    public Mushroomer getOwner(){
        return owner;
    }

    // Getter without System.out.println for Tekton
    public Tekton getTektonNoPrint() {
        return tekton;
    }



    /**
     * Triggers the release of a spore from this mushroom body.
     * <p>
     * The specific type of spore released and the mechanism of release are
     * determined by the implementation of this method. Currently, it only
     * prints messages to the console.
     * </p>
     */


    public MushroomBody() {
        System.out.println("MushroomBody.MushroomBody() called");
        System.out.println("MushroomBody.MushroomBody() returned");
    }

    /**
     * Constructs a new {@code MushroomBody} associated with the specified {@code Tekton}.
     *
     * @param tekton The {@code Tekton} where this mushroom body is located.
     */
    public MushroomBody(Tekton tekton, Mushroomer owner){
        System.out.println("MushroomBody.MushroomBody() called");
        this.tekton = tekton;
        this.owner = owner;
        this.ID = 0;
        System.out.println("MushroomBody.MushroomBody() returned");
    }
    public MushroomBody(Tekton tekton, Mushroomer owner, int ID){
        System.out.println("MushroomBody.MushroomBody() called");
        this.tekton = tekton;
        this.owner = owner;
        this.ID = ID;
        System.out.println("MushroomBody.MushroomBody() returned");
    }

    /**
     * Adds a list of Tektons associated with this mushroom body.
     * <p>
     * Note: The current implementation of this method does not actually store
     * or utilize the provided list of Tektons. This might need to be updated
     * based on the intended functionality.
     * </p>
     *
     * @param tektons A {@code List} of {@code Tekton} objects to be associated with this body.
     */
    public void addTekton(List<Tekton> tektons) {
        System.out.println("MushroomBody.setTektons() called");
        // TODO: Implement logic to store or utilize the provided list of Tektons.
        System.out.println("MushroomBody.setTektons() returned");
    }

    /**
     * Gets the {@code Tekton} associated with this mushroom body.
     *
     * @return The {@code Tekton} where this mushroom body is located.
     */
    public Tekton getTektons() {
        System.out.println("MushroomBody.getTektons() called");
        System.out.println("MushroomBody.getTektons() returned List<ITekton>");
        return tekton;
    }

    /**
     * Marks this mushroom body for destruction.
     * <p>
     * Note: The current implementation simply returns {@code true} and does not
     * handle the actual removal of the body from the game. This logic would
     * typically be handled by a game manager or the {@code Mushroom} class.
     * </p>
     *
     * @return {@code true} indicating that the body is to be destroyed.
     */
    public boolean destroyBody(){
        System.out.println("MushroomBody.destroyBody() called");
        System.out.println("MushroomBody.destroyBody() returned true");
        return true;
    }
}