import java.util.ArrayList;

/**
 * Represents a spore that paralyzes insects upon contact.
 * <p>
 * This class extends the {@code Spore} class and implements the specific
 * behavior of affecting insects by temporarily paralyzing them, thus
 * preventing them from taking actions. Each instance of
 * {@code ParalyzingSpore} is associated with a {@code Tekton} and has a
 * fixed nutrition value of 5.
 * </p>
 *
 * @see Spore
 * @see Tekton
 * @see Insect#effectedByParalyzingSpore()
 * @since 1.0
 */
public class ParalyzingSpore extends Spore {

    private int ID;
    /**
     * Constructs a {@code ParalyzingSpore} associated with the specified {@code Tekton}.
     *
     * @param tekton The {@code Tekton} linked to this spore, potentially influencing its origin or distribution.
     */
    public ParalyzingSpore(Tekton tekton) {
        this.nutrition = 5;
        this.ID = 0;
        this.tekton = tekton;
    }
    public ParalyzingSpore(Tekton tekton, Mushroomer Owner,int ID) {
        this.setOwner(Owner);
        this.nutrition = 5;
        this.ID = ID;
        this.tekton = tekton;
        this.getOwner().addSpore(this);
        this.tekton.getMushroom().addSpore(this);
    }

    /**
     * Affects the specified insect by applying a paralyzing effect.
     * <p>
     * This method calls the {@code effectedByParalyzingSpore()} method of the
     * {@code Insect} to apply the paralysis, which typically reduces the insect's
     * available actions to zero for a certain duration.
     * </p>
     *
     * @param insect The {@code Insect} to be affected by this paralyzing spore.
     */
    @Override
    public void affectInsect(Insect insect) {
        insect.effectedByParalyzingSpore();
    }
}