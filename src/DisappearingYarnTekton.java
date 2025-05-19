/**
 * A Tekton variant whose yarn disappears after use.
 * Overrides disappearing behavior to always return true.
 */
public class DisappearingYarnTekton extends Tekton {

    /**
     * Constructs a DisappearingYarnTekton with the specified unique identifier.
     *
     * @param id the unique identifier for this Tekton
     */
    public DisappearingYarnTekton(int id) {
        super(id);
    }

    /**
     * Constructs a DisappearingYarnTekton with an auto-generated unique identifier.
     */
    public DisappearingYarnTekton() {
        super();
    }

    /**
     * Indicates whether this Tekton's yarn disappears after use. Always true for this subclass.
     *
     * @return true, since yarn disappearing is enabled
     */
    @Override
    public boolean isDisappearing() {
        return true;
    }
}
