/**
 * A Tekton variant that cannot have its yarn cut by insects.
 * Overrides cutting behavior to always return false.
 */
public class LifeTekton extends Tekton {

    /**
     * Constructs a LifeTekton with the specified unique identifier.
     *
     * @param id the unique identifier for this Tekton
     */
    public LifeTekton(int id) {
        super(id);
    }

    /**
     * Constructs a LifeTekton with an auto-generated unique identifier.
     */
    public LifeTekton() {
        super();
    }

    /**
     * Indicates whether this Tekton's yarn can be cut. Always false for this subclass.
     *
     * @return false, since cutting is disabled
     */
    @Override
    public boolean canCut() {
        return false;
    }
}