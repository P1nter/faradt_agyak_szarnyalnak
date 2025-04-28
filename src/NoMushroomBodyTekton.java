import java.util.List;

/**
 * A specialized {@code Tekton} that does not allow the growth of a mushroom body.
 * <p>
 * This class extends the {@code Tekton} interface and overrides the
 * {@code canGrow()} method to always return {@code false}, preventing any
 * mushroom from forming a body on this type of Tekton. This might represent
 * an inhospitable or restricted area in the game world.
 * </p>
 *
 * @see Tekton
 * @since 1.0
 */
public class NoMushroomBodyTekton extends Tekton {

    /**
     * Constructs a new {@code NoMushroomBodyTekton}.
     * <p>
     * This constructor initializes an instance of {@code NoMushroomBodyTekton},
     * inherently disallowing mushroom body growth.
     * </p>
     */
    private int ID;
    public NoMushroomBodyTekton() {
        System.out.println("NoMushroomBodyTekton.NoMushroomBodyTekton() called");
        Mushroom mushroom = new Mushroom();
        this.mushrooms = mushroom;
        this.ID = 0;
        System.out.println("NoMushroomBodyTekton.NoMushroomBodyTekton() returned");
    }
    public NoMushroomBodyTekton(int ID) {
        System.out.println("NoMushroomBodyTekton.NoMushroomBodyTekton() called");
        Mushroom mushroom = new Mushroom();
        this.mushrooms = mushroom;
        this.ID = ID;
        System.out.println("NoMushroomBodyTekton.NoMushroomBodyTekton() returned");
    }

    /**
     * Overrides the {@code canGrow()} method to always return {@code false}.
     * <p>
     * This implementation ensures that no mushroom body can be grown on this
     * specific {@code Tekton}.
     * </p>
     *
     * @return {@code false}, indicating that a mushroom body cannot grow on this Tekton.
     */
    @Override
    public boolean canGrow() {
        System.out.println("NoMushroomBodyTekton.isMushroomBody() called");
        System.out.println("NoMushroomBodyTekton.isMushroomBody() returned");
        return false;
    }
}