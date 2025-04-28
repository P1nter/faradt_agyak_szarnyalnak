import java.util.List;

/**
 * A specialized {@code Tekton} that allows the growth of only a single mushroom yarn.
 * <p>
 * This class extends the {@code Tekton} interface and overrides the
 * {@code canGrowYarn()} method to control the number of mushroom yarns that
 * can originate from its associated mushroom. Once the mushroom on this
 * Tekton has at least one yarn, subsequent attempts to grow more yarns will fail.
 * </p>
 *
 * @see Tekton
 * @see Mushroom
 * @see MushroomYarn
 * @since 1.0
 */
public class SingleMushroomOnlyTekton extends Tekton {

    /**
     * Constructs a new {@code SingleMushroomOnlyTekton}.
     * <p>
     * This constructor initializes an instance of {@code SingleMushroomOnlyTekton}
     * with the specific behavior of allowing only one mushroom yarn to be grown.
     * </p>
     */
    private int ID;
    public SingleMushroomOnlyTekton() {
        System.out.println("SingleMushroomOnlyTekton.SingleMushroomOnlyTekton() called");
        Mushroom mushroom = new Mushroom();
        this.mushrooms = mushroom;
        this.ID = 0;
        System.out.println("SingleMushroomOnlyTekton.SingleMushroomOnlyTekton() returned");
    }
    public SingleMushroomOnlyTekton(int ID) {
        super(ID);
        System.out.println("SingleMushroomOnlyTekton.SingleMushroomOnlyTekton() called");
        Mushroom mushroom = new Mushroom();
        this.mushrooms = mushroom;
        System.out.println("SingleMushroomOnlyTekton.SingleMushroomOnlyTekton() returned");
    }

    /**
     * Overrides the {@code canGrowYarn()} method to limit yarn growth to a single instance.
     * <p>
     * This implementation checks if the mushroom associated with this Tekton already
     * has any yarns. If it does, this method returns {@code false}, preventing further
     * yarn growth from this Tekton's mushroom. Otherwise, it returns {@code true},
     * allowing the first yarn to be grown.
     * </p>
     *
     * @return {@code true} if the associated mushroom has no yarns yet, {@code false} otherwise.
     */
    @Override
    public boolean canGrowYarn() {
        System.out.println("SingleMushroomOnlyTekton.canGrowYarn() called");
        System.out.println("SingleMushroomOnlyTekton.canGrowYarn() returned");
        if(this.getMushroom().hasYarns()){
            System.out.println("SingleMushroomOnlyTekton.canGrowYarn() returned false");
            return false;
        }
        return true;
    }
}