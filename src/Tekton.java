import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An abstract base class representing a location or area within the game world.
 * <p>
 * {@code Tekton} objects can be interconnected, host mushrooms and insects,
 * and have properties that influence gameplay, such as whether they can be cut,
 * support mushroom growth, or allow the spread of mushroom yarns. Concrete
 * subclasses will define specific types of locations with unique characteristics.
 * </p>
 *
 * @see Mushroom
 * @see Insect
 * @since 1.0
 */
abstract class Tekton {
    /**
     * A list of adjacent Tektons connected to this Tekton.
     */
    List<Tekton> adjacentTektons = new ArrayList<>();

    /**
     * The mushroom associated with this Tekton.
     */
    Mushroom mushrooms = null;
    private int ID;

    /**
     * A list of insects currently located on this Tekton.
     */
    List<Insect> insects = new ArrayList<>();

    /**
     * Constructs a new {@code Tekton} with no initial adjacent Tektons or associated mushroom.
     */
    public Tekton() {
        System.out.println("Tekton.Tekton() called");
        Mushroom mushroom = new Mushroom();
        this.ID = 0;
        this.mushrooms = mushroom;
        System.out.println("Tekton.Tekton() returned");
    }
    public Tekton(int ID) {
        System.out.println("Tekton.Tekton() called");
        Mushroom mushroom = new Mushroom();
        this.ID = ID;
        this.mushrooms = mushroom;
        System.out.println("Tekton.Tekton() returned");
    }

    public int getID(){
        return ID;
    }

    /**
     * Constructs a new {@code Tekton} with a specified list of adjacent Tektons.
     *
     * @param adjacentTektonss The initial list of Tektons adjacent to this one.
     */
    public Tekton(List<Tekton> adjacentTektonss) {
        System.out.println("Tekton.Tekton() called");
        this.adjacentTektons = adjacentTektonss;
        Mushroom mushroom = new Mushroom();
        this.mushrooms = mushroom;
        System.out.println("Tekton.Tekton() returned");
    }

    /**
     * Gets the list of Tektons adjacent to this one.
     *
     * @return A {@code List} containing the adjacent {@code Tekton} objects.
     */
    public List<Tekton> getAdjacentTektons() {
        System.out.println("Tekton.getAdjacentTektons() called");
        System.out.println("Tekton.getAdjacentTektons() returned");
        return adjacentTektons;
    }

    /**
     * Adds a new insect to this Tekton.
     *
     * @param insect The {@code Insect} to be added to this Tekton.
     */
    public void addNewInsect(Insect insect) {
        System.out.println("Tekton.addNewInsect(Insect) called");
        insects.add(insect);
        System.out.println("Tekton.addNewInsect(Insect) returned");
    }

    /**
     * Removes an insect from this Tekton.
     *
     * @param insect The {@code Insect} to be removed from this Tekton.
     */
    public void removeInsect(Insect insect) {
        System.out.println("Tekton.removeInsect(Insect) called");
        insects.remove(insect);
        System.out.println("Tekton.removeInsect(Insect) returned");
    }

    /**
     * Adds a Tekton to the list of Tektons adjacent to this one.
     *
     * @param tekton The {@code Tekton} to be added as an adjacent Tekton.
     */
    public void addAdjacentTekton(Tekton tekton) {
        System.out.println("Tekton.addAdjacentTekton(Tekton) called");
        adjacentTektons.add(tekton);
        tekton.getAdjacentTektons().add(this);
        System.out.println("Tekton.addAdjacentTekton(Tekton) returned");
    }

    /**
     * Removes a Tekton from the list of Tektons adjacent to this one.
     *
     * @param tekton The {@code Tekton} to be removed from the adjacent Tektons.
     */
    public void removeAdjacentTekton(Tekton tekton) {
        System.out.println("Tekton.removeAdjacentTekton(Tekton) called");
        adjacentTektons.remove(tekton);
        System.out.println("Tekton.removeAdjacentTekton(Tekton) returned");
    }

    /**
     * Sets the mushroom associated with this Tekton.
     *
     * @param mushroom The {@code Mushroom} to be associated with this Tekton.
     */
    public void setMushroom(Mushroom mushroom) {
        System.out.println("Tekton.setMushroom(Mushroom) called");
        this.mushrooms = mushroom;
        System.out.println("Tekton.setMushroom(Mushroom) returned");
    }

    /**
     * Gets the mushroom associated with this Tekton.
     *
     * @return The {@code Mushroom} associated with this Tekton.
     */
    public Mushroom getMushroom() {
        System.out.println("Tekton.getMushroom() called");
        System.out.println("Tekton.getMushroom() returned");
        return mushrooms;
    }

    /**
     * Gets the list of insects currently on this Tekton.
     *
     * @return A {@code List} containing the {@code Insect} objects on this Tekton.
     */
    public List<Insect> getInsects(){
        return insects;
    }

    /**
     * Sets the list of insects currently on this Tekton.
     *
     * @param insects The new {@code List} of {@code Insect} objects for this Tekton.
     */
    public void setInsects(List<Insect> insects){
        this.insects = insects;
    }

    /**
     * Determines if this Tekton can be cut (e.g., by an insect).
     * <p>
     * Subclasses can override this method to specify whether cutting is allowed.
     * The default implementation allows cutting.
     * </p>
     *
     * @return {@code true} if this Tekton can be cut, {@code false} otherwise.
     */
    public boolean canCut() {
        System.out.println("Tekton.canCut() called");
        System.out.println("Tekton.canCut() returned");
        return true;
    }

    /**
     * Determines if a mushroom body can grow on this Tekton.
     * <p>
     * Subclasses can override this method to specify whether mushroom growth is allowed.
     * The default implementation allows growth.
     * </p>
     *
     * @return {@code true} if a mushroom body can grow here, {@code false} otherwise.
     */
    public boolean canGrow() {
        System.out.println("Tekton.canGrow() called");
        System.out.println("Tekton.canGrow() returned");
        return true;
    }

    /**
     * Determines if a mushroom yarn can grow from the mushroom on this Tekton to an adjacent Tekton.
     * <p>
     * Subclasses can override this method to specify whether yarn growth is allowed.
     * The default implementation allows yarn growth.
     * </p>
     *
     * @return {@code true} if a mushroom yarn can grow from here, {@code false} otherwise.
     */
    public boolean canGrowYarn(){
        System.out.println("Tekton.canGrowYarn() called");
        System.out.println("Tekton.canGrowYarn() returned");
        return true;
    }

    /**
     * Determines if this Tekton has the characteristic of disappearing over time.
     * <p>
     * Subclasses can override this method to indicate if this Tekton is temporary.
     * The default implementation indicates that the Tekton does not disappear.
     * </p>
     *
     * @return {@code true} if this Tekton is disappearing, {@code false} otherwise.
     */
    public boolean isDisappearing() {
        System.out.println("Tekton.isDisappearing() called");
        System.out.println("Tekton.isDisappearing() returned");
        return false;
    }

    /**
     * Determines if this Tekton promotes fast growth of associated elements (e.g., mushroom bodies).
     * <p>
     * Subclasses can override this method to indicate if this Tekton has fast-growth properties.
     * The default implementation indicates normal growth.
     * </p>
     *
     * @return {@code true} if this Tekton promotes fast growth, {@code false} otherwise.
     */
    public boolean isFastTekton(){
        System.out.println("Tekton.isFastTekton() called");
        System.out.println("Tekton.isFastTekton() returned");
        return false;
    }
    public int getIDNoPrint() {
        return ID;
    }

    public List<Tekton> getAdjacentTektonsNoPrint() {
        return adjacentTektons;
    }

    public Mushroom getMushroomNoPrint() {
        return mushrooms;
    }

    public List<Insect> getInsectsNoPrint() {
        return insects;
    }



}