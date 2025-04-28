import java.util.List;

/**
 * A specialized {@code Tekton} that prevents body growth.
 * <p>
 * This class extends the {@code Tekton} interface and overrides the
 * {@code canGrow()} method to always return {@code false}, effectively
 * disabling the ability of any associated organism to grow its body.
 * This type of {@code Tekton} might represent a specific environmental
 * condition or a deliberate biological constraint.
 * </p>
 *
 * @see Tekton
 * @since 1.0
 */
public class DisabledBodyGrowthTekton extends Tekton {

    /**
     * Constructs a new {@code DisabledBodyGrowthTekton}.
     * <p>
     * This constructor initializes an instance of {@code DisabledBodyGrowthTekton}
     * with its body growth capability inherently disabled.
     * </p>
     */
    public DisabledBodyGrowthTekton() {
        System.out.println("DisabledBodyGrowthTekton.DisabledBodyGrowthTekton() called");
        System.out.println("DisabledBodyGrowthTekton.DisabledBodyGrowthTekton() returned");
    }

    /**
     * Overrides the {@code canGrow()} method to always return {@code false}.
     * <p>
     * This implementation ensures that any entity associated with a
     * {@code DisabledBodyGrowthTekton} will not be able to grow its body.
     * </p>
     *
     * @return {@code false}, indicating that body growth is disabled for this Tekton.
     */
    @Override
    public boolean canGrow() {
        System.out.println("DisabledBodyGrowthTekton.canGrowBody() called");
        System.out.println("DisabledBodyGrowthTekton.canGrowBody() returned");
        return false;
    }
}