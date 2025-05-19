/**
 * A spore that paralyzes an insect when consumed, preventing it from acting for a duration.
 * Inherits base spore attributes (type, location, owner, id, nutrition) from the Spore class.
 */
public class ParalyzingSpore extends Spore {

    /**
     * Constructs a ParalyzingSpore at the specified location with the given owner and ID.
     *
     * @param onTekton the Tekton on which this spore is placed
     * @param owner    the Mushroomer player who owns this spore
     * @param id       the unique identifier for this spore instance
     */
    public ParalyzingSpore(Tekton onTekton, Mushroomer owner, int id) {
        super(SporeType.PARALYZING, onTekton, owner, id, 7);
    }

    /**
     * Applies the paralyzing effect to the specified insect.
     *
     * @param insect the insect being affected by this spore
     */
    @Override
    public void affectInsect(Insect insect) {
        insect.effectedByParalyzingSpore();
    }
}
