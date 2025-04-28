/**
 * An abstract base class representing a player in the game.
 * <p>
 * This class provides fundamental properties and methods common to all types of players,
 * such as a name, a score, and available actions per turn. Concrete player implementations
 * will extend this class to add specific behaviors and attributes relevant to their role
 * in the game.
 * </p>
 *
 * @since 1.0
 */
public abstract class Player {
    private String Name;
    private int Score;
    private int Action;

    /**
     * Constructs a new {@code Player} with the given name.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.Name = name;
        this.Score = 0;
        this.Action = 3;
    }

    /**
     * Gets the name of this player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return Name;
    }

    /**
     * Gets the current score of this player.
     *
     * @return The current score of the player.
     */
    public int getScore() {
        return Score;
    }

    /**
     * Gets the number of actions this player can perform in the current turn.
     *
     * @return The number of available actions for the player.
     */
    public int getAction() {
        return Action;
    }

    /**
     * Sets the name of this player.
     *
     * @param name The new name for the player.
     */
    public void setName(String name) {
        this.Name = name;
    }

    /**
     * Sets the current score of this player.
     *
     * @param score The new score for the player.
     */
    public void setScore(int score) {
        this.Score = score;
    }

    /**
     * Sets the number of actions this player has for the current turn.
     *
     * @param action The new number of actions for the player.
     */
    public void setAction(int action) {
        this.Action = action;
    }
}