import java.util.List;

/**
 * A specialized {@code Tekton} that exhibits fast growth characteristics.
 * <p>
 * This class extends the {@code Tekton} interface and overrides the
 * {@code isFastTekton()} method to always return {@code true}, indicating
 * that any entity associated with this {@code Tekton} experiences accelerated
 * growth. This could represent a nutrient-rich environment or a specific
 * biological trait.
 * </p>
 *
 * @see Tekton
 * @since 1.0
 */
public class FastGrowthTekton extends Tekton {

    /**
     * Constructs a new {@code FastGrowthTekton}.
     * <p>
     * This constructor initializes an instance of {@code FastGrowthTekton},
     * inherently marked as having fast growth capabilities.
     * </p>
     */
    public FastGrowthTekton() {
        System.out.println("FastGrowthTekton.FastGrowthTekton() called");
        System.out.println("FastGrowthTekton.FastGrowthTekton() returned");
    }

    /**
     * Overrides the {@code isFastTekton()} method to always return {@code true}.
     * <p>
     * This implementation signifies that this {@code Tekton} instance promotes
     * rapid growth in any associated biological entities.
     * </p>
     *
     * @return {@code true}, indicating that this Tekton is associated with fast growth.
     */
    @Override
    public boolean isFastTekton(){
        System.out.println("Tekton.isFastTekton() called");
        System.out.println("Tekton.isFastTekton() returned");
        return true;
    }
}