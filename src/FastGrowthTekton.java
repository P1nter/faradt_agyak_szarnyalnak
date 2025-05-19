/**
 * A Tekton variant with accelerated growth capabilities.
 * Overrides fast growth behavior to always return true.
 */
public class FastGrowthTekton extends Tekton {

    /**
     * Constructs a FastGrowthTekton with the specified unique identifier.
     *
     * @param id the unique identifier for this Tekton
     */
    public FastGrowthTekton(int id) {
        super(id);
    }

    /**
     * Constructs a FastGrowthTekton with an auto-generated unique identifier.
     */
    public FastGrowthTekton() {
        super();
    }

    /**
     * Indicates whether this Tekton grows faster than normal. Always true for this subclass.
     *
     * @return true, since fast growth is enabled
     */
    @Override
    public boolean isFastTekton() {
        return true;
    }
}