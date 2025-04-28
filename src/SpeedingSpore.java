/**
 * Represents a spore that speeds up insects upon contact.
 * <p>
 * This class extends the {@code Spore} class and implements the specific
 * behavior of affecting insects by increasing their available actions per turn.
 * Each instance of {@code SpeedingSpore} is associated with a {@code Tekton}
 * and has a fixed nutrition value of 5.
 * </p>
 *
 * @see Spore
 * @see Tekton
 * @see Insect#effectedBySpeedingSpore()
 * @since 1.0
 */
public class SpeedingSpore extends Spore {

    private int ID;
    /**
     * Constructs a {@code SpeedingSpore} linked to the specified {@code Tekton}.
     *
     * @param tekton The {@code Tekton} associated with this spore, potentially indicating its origin or area of effect.
     */
    public SpeedingSpore(Tekton tekton, Mushroomer mushroomer) {
        this.nutrition = 5;
        this.ID = 0;
        this.tekton = tekton;
        this.setOwner(mushroomer);
    }
    public SpeedingSpore(Tekton tekton, Mushroomer mushroomer, int ID) {
        this.nutrition = 5;
        this.ID = ID;
        this.tekton = tekton;
        this.setOwner(mushroomer);
    }

    /**
     * Affects the given insect by applying a speed-enhancing effect.
     * <p>
     * This method calls the {@code effectedBySpeedingSpore()} method of the
     * {@code Insect}, which typically doubles the insect's action points for a
     * certain duration.
     * </p>
     *
     * @param insect The {@code Insect} to be affected by this speeding spore.
     */
    @Override
    public void affectInsect(Insect insect) {
        System.out.println("SpeedingSpore.affectInsect(insect) called");
        insect.effectedBySpeedingSpore();
        System.out.println("SpeedingSpore.affectInsect(insect) returned");
    }
}