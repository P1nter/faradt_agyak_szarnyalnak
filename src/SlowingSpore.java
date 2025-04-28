/**
 * Represents a spore that slows down insects upon contact.
 * <p>
 * This class extends the {@code Spore} class and provides the specific
 * behavior of affecting insects by reducing their available actions per turn.
 * Each instance of {@code SlowingSpore} is associated with a {@code Tekton}
 * and has a fixed nutrition value of 5.
 * </p>
 *
 * @see Spore
 * @see Tekton
 * @see Insect#effectedBySlowingSpore()
 * @since 1.0
 */
public class SlowingSpore extends Spore {

    private int ID;
    /**
     * Constructs a {@code SlowingSpore} linked to the specified {@code Tekton}.
     *
     * @param tekton The {@code Tekton} associated with this spore, potentially indicating its origin or area of effect.
     */
    public SlowingSpore(Tekton tekton, Mushroomer mushroomer) {
        this.nutrition = 5;
        this.ID = 0;
        this.tekton = tekton;
        this.setOwner(mushroomer);
    }
    public SlowingSpore(Tekton tekton, Mushroomer mushroomer, int ID) {
        this.nutrition = 5;
        this.ID = ID;
        this.tekton = tekton;
        this.setOwner(mushroomer);
    }

    /**
     * Affects the given insect by applying a slowing effect.
     * <p>
     * This method calls the {@code effectedBySlowingSpore()} method of the
     * {@code Insect}, which typically halves the insect's action points for a
     * certain duration.
     * </p>
     *
     * @param insect The {@code Insect} to be affected by this slowing spore.
     */
    @Override
    public void affectInsect(Insect insect) {
        System.out.println("SlowingSpore.affectInsect(insect) called");
        insect.effectedBySlowingSpore();
        System.out.println("SlowingSpore.affectInsect(insect) returned");
    }
}