/**
 * A spore that causes an insect to duplicate when consumed.
 * Inherits base spore properties (type, location, owner, id, nutrition) from the Spore class.
 */
public class DuplicatingSpore extends Spore {

    /**
     * Constructs a DuplicatingSpore at the specified location with the given owner and ID.
     *
     * @param onTekton the Tekton on which this spore is placed
     * @param owner    the Mushroomer player who owns this spore
     * @param id       the unique identifier for this spore instance
     */
    public DuplicatingSpore(Tekton onTekton, Mushroomer owner, int id) {
        super(SporeType.DUPLICATING, onTekton, owner, id, 10);
    }

    /**
     * Applies the duplicating effect to the specified insect.
     *
     * @param insect the insect being affected by this spore
     */
    @Override
    public void affectInsect(Insect insect) {
        insect.effectedByDuplicatingSpore();
    }
}
