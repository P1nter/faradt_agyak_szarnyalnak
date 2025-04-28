/**
 * Represents a spore that triggers a duplication effect on insects.
 * <p>
 * This class extends the {@code Spore} class and defines the specific behavior
 * of causing an insect to duplicate in some manner upon being affected by this spore.
 * Each {@code DuplicatingSpore} is associated with a specific {@code Tekton}
 * and has a fixed nutrition value of 5.
 * </p>
 *
 * @see Spore
 * @see Tekton
 * @since 1.0
 */
public class DuplicatingSpore extends Spore {

    private int ID;
    /**
     * Constructs a {@code DuplicatingSpore} associated with the specified {@code Tekton}.
     *
     * @param tekton The {@code Tekton} linked to this spore, potentially influencing its behavior or origin.
     */
    public DuplicatingSpore(Tekton tekton) {
        this.nutrition = 5;
        this.ID = 0;
        this.tekton = tekton;
    }
    public DuplicatingSpore(Tekton tekton, Mushroomer Owner,int ID) {
        this.setOwner(Owner);
        this.nutrition = 5;
        this.ID = ID;
        this.tekton = tekton;
        this.getOwner().addSpore(this);
        this.tekton.getMushroom().addSpore(this);
    }

    /**
     * Affects the provided insect by initiating a duplication process.
     * <p>
     * The exact nature of the duplication is determined by the {@code Insect}
     * implementation's {@code effectedByDuplicatingSpore()} method.
     * </p>
     *
     * @param insect The {@code Insect} that will be affected by this spore and undergo duplication.
     */
    @Override
    public void affectInsect(Insect insect) {
        System.out.println("DuplicatingSpore.affectInsect(insect) called");
        insect.effectedByDuplicatingSpore();
        System.out.println("DuplicatingSpore.affectInsect(insect) returned");
    }
}