/**
 * A Tekton variant whose body growth capability is permanently disabled.
 * Overrides growth behavior to prevent any increase in size.
 */
public class DisabledBodyGrowthTekton extends Tekton {

    /**
     * Constructs a DisabledBodyGrowthTekton with the specified unique identifier.
     *
     * @param id the unique identifier for this Tekton
     */
    public DisabledBodyGrowthTekton(int id) {
        super(id);
    }

    /**
     * Constructs a DisabledBodyGrowthTekton with an auto-generated unique identifier.
     */
    public DisabledBodyGrowthTekton() {
        super();
    }

    /**
     * Indicates whether this Tekton can grow its body. Always returns false for this subclass.
     *
     * @return false, since body growth is disabled
     */
    @Override
    public boolean canGrow() {
        return false;
    }
}
