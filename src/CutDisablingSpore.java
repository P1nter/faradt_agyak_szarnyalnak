/**
 * This class models a specialized spore designed to impair the cutting mechanisms of insects upon contact.
 * <p>This class extends the {@code Spore} class and provides specific behavior
 * for affecting insects by disabling their cutting ability.</p>
 *
 * <p>Each instance of {@code CutDisablingSpore} is linked to a specific {@code Tekton},
 * potentially influencing its distribution or effectiveness. It also has a fixed nutrition value of 5.</p>
 *
 * @see Spore
 * @see Tekton
 * @since 1.0
 */
public class CutDisablingSpore extends Spore {

    private int ID;
    /**
     * Constructs a {@code CutDisablingSpore} associated with the provided {@code Tekton} instance.
     * This association might determine the origin or target of the spore.
     *
     * @param tekton The {@code Tekton} associated with this spore.
     */
    public CutDisablingSpore(Tekton tekton) {
        this.nutrition = 5;
        this.tekton = tekton;
        this.ID = 0;
    }
    public CutDisablingSpore(Tekton tekton, Mushroomer Owner,int ID) {
        this.setOwner(Owner);
        this.nutrition = 5;
        this.tekton = tekton;
        this.ID = ID;
        this.getOwner().addSpore(this);
        this.tekton.getMushroom().addSpore(this);
    }

    /**
     * Affects the specified insect by disabling its cutting ability.
     *
     * @param insect The {@code Insect} to be affected by this spore, causing its cutting ability to be disabled.
     */
    @Override
    public void affectInsect(Insect insect) {
        System.out.println("CutDisablingSpore.affectInsect(insect) called");
        insect.effectedByCutDisablingSpore();

        System.out.println("CutDisablingSpore.affectInsect(insect) returned");
    }
}