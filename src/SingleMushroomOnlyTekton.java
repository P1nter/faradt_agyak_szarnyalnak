/**
 * A Tekton variant that allows only a single mushroom yarn connection.
 * Overrides yarn growth behavior to enforce at most one active yarn.
 */
public class SingleMushroomOnlyTekton extends Tekton {

    /**
     * Constructs a SingleMushroomOnlyTekton with the specified unique identifier.
     *
     * @param id the unique identifier for this Tekton
     */
    public SingleMushroomOnlyTekton(int id) {
        super(id);
    }

    /**
     * Constructs a SingleMushroomOnlyTekton with an auto-generated unique identifier.
     */
    public SingleMushroomOnlyTekton() {
        super();
    }

    /**
     * Indicates whether this Tekton can grow a new yarn connection.
     * Only allowed if no existing yarns are connected.
     *
     * @return true if no yarns currently connected; false otherwise
     */
    @Override
    public boolean canGrowYarn() {
        var manager = this.getMushroomNoPrint();
        return manager == null || manager.getMushroomYarnsNoPrint().isEmpty();
    }
}