/**
 * A specialized {@code Tekton} that cannot be cut.
 * <p>
 * This class extends the {@code Tekton} interface and overrides the
 * {@code canCut()} method to always return {@code false}, indicating
 * that this type of Tekton is resistant to being cut or severed. This might
 * represent a particularly resilient or vital part of the game environment.
 * </p>
 *
 * @see Tekton
 * @since 1.0
 */
public class LifeTekton extends Tekton{

    /**
     * Constructs a new {@code LifeTekton}.
     * <p>
     * This constructor initializes an instance of {@code LifeTekton},
     * inherently possessing the property of not being able to be cut.
     * </p>
     */
    private int ID;
    public LifeTekton() {
        System.out.println("FastGrowthTekton.FastGrowthTekton() called");
        Mushroom mushroom = new Mushroom();
        this.mushrooms = mushroom;
        this.ID = 0;
        System.out.println("FastGrowthTekton.FastGrowthTekton() returned");
    }

    public LifeTekton(int ID) {
        super(ID);
        System.out.println("FastGrowthTekton.FastGrowthTekton() called");
        Mushroom mushroom = new Mushroom();
        this.mushrooms = mushroom;

        System.out.println("FastGrowthTekton.FastGrowthTekton() returned");
    }
    /**
     * Overrides the {@code canCut()} method to always return {@code false}.
     * <p>
     * This implementation ensures that any attempt to cut this {@code Tekton}
     * will be unsuccessful.
     * </p>
     *
     * @return {@code false}, indicating that this Tekton cannot be cut.
     */
    @Override
    public boolean canCut() {
        System.out.println("Tekton.canCut() called");
        System.out.println("Tekton.canCut() returned");
        return false;
    }
}