import java.util.List;

/**
 * A specialized {@code Tekton} that represents a disappearing yarn.
 * <p>
 * This class extends the {@code Tekton} interface and overrides the
 * {@code isDisappearing()} method to always return {@code true}, indicating
 * that this type of Tekton has the characteristic of disappearing. This could
 * represent a temporary environmental factor or a specific type of resource.
 * </p>
 *
 * @see Tekton
 * @since 1.0
 */
public class DisappearingYarnTekton extends Tekton {

    private int ID;
    /**
     * Constructs a new {@code DisappearingYarnTekton}.
     * <p>
     * This constructor initializes an instance of {@code DisappearingYarnTekton},
     * inherently marked as disappearing.
     * </p>
     */
    public DisappearingYarnTekton() {
        System.out.println("DisappearingYarnTekton.DisappearingYarnTekton() called");
        Mushroom mushroom = new Mushroom();
        this.ID = 0;
        this.mushrooms = mushroom;
        System.out.println("DisappearingYarnTekton.DisappearingYarnTekton() returned");
    }
    public DisappearingYarnTekton(int ID) {
        super(ID);
        System.out.println("DisappearingYarnTekton.DisappearingYarnTekton() called");
        Mushroom mushroom = new Mushroom();

        this.mushrooms = mushroom;
        System.out.println("DisappearingYarnTekton.DisappearingYarnTekton() returned");
    }

    /**
     * Overrides the {@code isDisappearing()} method to always return {@code true}.
     * <p>
     * This implementation signifies that this {@code Tekton} instance has the
     * property of disappearing over time or under certain conditions.
     * </p>
     *
     * @return {@code true}, indicating that this Tekton is disappearing.
     */
    @Override
    public boolean isDisappearing() {
        System.out.println("DisappearingYarnTekton.isDisappearingYarn() called");
        System.out.println("DisappearingYarnTekton.isDisappearingYarn() returned");
        return true;
    }
}