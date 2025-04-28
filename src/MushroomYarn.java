import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Represents a connection or pathway of mushroom filaments between two Tektons.
 * <p>
 * A {@code MushroomYarn} links exactly two {@code Tekton} objects. It can be cut,
 * and it has a time-based decay mechanism. The yarn can also temporarily disappear
 * and reappear after a certain duration.
 * </p>
 *
 * @see Tekton
 * @since 1.0
 */
public class MushroomYarn {
    private Tekton[] tektons = new Tekton[2]; // Fixed size array for exactly 2 Tektons
    private boolean isCut = false;
    private int timeBack = 0;

    /**
     * Checks if this mushroom yarn has been cut.
     *
     * @return {@code true} if the yarn is cut, {@code false} otherwise.
     */
    public boolean getIsCut() {
        return isCut;
    }

    /**
     * Gets the remaining time until this yarn reappears if it has disappeared.
     *
     * @return The number of turns remaining before the yarn reappears. A value of 0 indicates the yarn is present.
     */
    public int getTimeBack() {
        return timeBack;
    }

    /**
     * Sets the time until this yarn reappears after disappearing.
     *
     * @param timeBack The number of turns before the yarn reappears.
     */
    public void setTimeBack(int timeBack) {
        this.timeBack = timeBack;
    }

    /**
     * Marks this mushroom yarn as cut.
     */
    public void setCut() {
        isCut = true;
    }

    /**
     * Updates the state of the mushroom yarn for the current game turn.
     * <p>
     * If the {@code timeBack} is greater than 0, it decrements. If {@code timeBack}
     * reaches 0, this method returns {@code true}, indicating that the yarn should
     * potentially be removed or its state updated (e.g., reappearing).
     * </p>
     *
     * @return {@code true} if the yarn's disappearance timer has reached 0, {@code false} otherwise.
     */
    public boolean Update(){
        System.out.println("MushroomYarn.Update() called");
        if (timeBack > 0) {
            timeBack--;
            if (timeBack == 0) {
                return true;
            }
        }
        System.out.println("MushroomYarn.Update() returned");
        return false;
    }

    /**
     * Constructs a new {@code MushroomYarn} connecting the two specified Tektons.
     *
     * @param tekton1 The first {@code Tekton} to be connected by the yarn. Must not be null.
     * @param tekton2 The second {@code Tekton} to be connected by the yarn. Must not be null.
     * @throws IllegalArgumentException If either {@code tekton1} or {@code tekton2} is null.
     */
    public MushroomYarn(Tekton tekton1, Tekton tekton2) {
        System.out.println("MushroomYarn.MushroomYarn(Tekton, Tekton) called");
        if (tekton1 == null || tekton2 == null) {
            throw new IllegalArgumentException("Both Tektons must be non-null.");
        }
        this.tektons[0] = tekton1;
        this.tektons[1] = tekton2;
        System.out.println("MushroomYarn.MushroomYarn(Tekton, Tekton) returned");
    }

    /**
     * Gets the two {@code Tekton} objects connected by this mushroom yarn.
     * <p>
     * Returns a copy of the internal array to prevent external modification.
     * </p>
     *
     * @return An array of two {@code Tekton} objects representing the connection.
     */
    public Tekton[] getTektons() {
        System.out.println("MushroomYarn.getTektons() called");
        System.out.println("MushroomYarn.getTektons() returned Tekton[]");
        return Arrays.copyOf(tektons, tektons.length); // Return a copy to prevent external modification
    }

    /**
     * Updates the two {@code Tekton} objects connected by this mushroom yarn.
     *
     * @param tekton1 The new first {@code Tekton} for the connection. Must not be null.
     * @param tekton2 The new second {@code Tekton} for the connection. Must not be null.
     * @throws IllegalArgumentException If either {@code tekton1} or {@code tekton2} is null.
     */
    public void updateTektons(Tekton tekton1, Tekton tekton2) {
        System.out.println("MushroomYarn.updateTektons(Tekton, Tekton) called");
        if (tekton1 == null || tekton2 == null) {
            throw new IllegalArgumentException("Both Tektons must be non-null.");
        }
        this.tektons[0] = tekton1;
        this.tektons[1] = tekton2;
        System.out.println("MushroomYarn.updateTektons(Tekton, Tekton) returned");
    }

    /**
     * Initiates the decay process of this mushroom yarn.
     * <p>
     * Note: The current implementation does not specify how the yarn decays.
     * This method might be intended for future implementation of a decay mechanic.
     * </p>
     */
    public void decay() {
        System.out.println("MushroomYarn.decay() called");
        // TODO: Implement the decay logic for the mushroom yarn.
        System.out.println("MushroomYarn.decay() returned");
    }

    /**
     * Attempts to cut this mushroom yarn.
     * <p>
     * The yarn can only be cut if both connected Tektons allow cutting ({@code canCut()} returns true)
     * and at least one of the connected Tektons has a mushroom body. If these conditions are met,
     * the yarn is marked as cut, and a timer is set for it to disappear for 3 turns.
     * </p>
     *
     * @return {@code true} if the yarn was successfully cut, {@code false} otherwise.
     */
    public boolean cut() {
        System.out.println("MushroomYarn.cut() called");
        if(!(tektons[0].canCut() && tektons[1].canCut())){
            System.out.println("MushroomYarn.cut() returned false");
            return false;
        }
        boolean m1 = tektons[0].getMushroom().hasMushroomBody();
        boolean m2= tektons[1].getMushroom().hasMushroomBody();

        if (!m1 && !m2) {
            System.out.println("MushroomYarn.cut() returned false");
            return false;
        }
        setCut();
        setTimeBack(3);
        System.out.println("MushroomYarn.cut() returned true");
        return true;
    }
}