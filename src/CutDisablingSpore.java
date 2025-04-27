/**
 * Represents a spore that disables the cutting ability of insects.
 * <p>This class extends the {@code Spore} class and provides specific behavior
 * for affecting insects by disabling their cutting ability.</p>
 *
 * <p>Each instance of {@code CutDisablingSpore} is associated with a {@code Tekton}
 * and has a fixed nutrition value of 5.</p>
 *
 * @see Spore
 * @see Tekton
 * @since 1.0
 */
public class CutDisablingSpore extends Spore {

    /**
     * Constructs a {@code CutDisablingSpore} with the specified {@code Tekton}.
     *
     * @param tekton The {@code Tekton} associated with this spore.
     */
    public CutDisablingSpore(Tekton tekton) {
        this.nutrition = 5;
        this.tekton = tekton;
    }

    /**
     * Affects the specified insect by disabling its cutting ability.
     *
     * @param insect The {@code Insect} to be affected by this spore.
     */
    @Override
    public void affectInsect(Insect insect) {
        System.out.println("CutDisablingSpore.affectInsect(insect) called");
        insect.effectedByCutDisablingSpore();
        System.out.println("CutDisablingSpore.affectInsect(insect) returned");
    }
}