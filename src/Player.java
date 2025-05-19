// Player.java
public abstract class Player {
    private String name;
    private int action;
    public static final int DEFAULT_ACTIONS_PER_TURN = 3; // Default actions per turn

    protected enum PlayerType { INSECTER, MUSHROOMER }
    private PlayerType playerType;

    /**
     * Construct a new player with the given name.
     */
    public Player(String name) {
        this.name = name;
        this.action = DEFAULT_ACTIONS_PER_TURN;
    }

    /**
     * Get the player's display name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the number of actions remaining this turn.
     */
    public int getAction() {
        return action;
    }

    /**
     * Reset this player's actions to the default for a new turn.
     */
    public void resetActionPoints() {
        this.action = DEFAULT_ACTIONS_PER_TURN;
        System.out.println("Player " + name + ": Action points reset to " + this.action);
    }

    /**
     * Check if the player has any actions left this turn.
     */
    public boolean hasActionsLeft() {
        return this.action > 0;
    }

    /**
     * Decrement the player's remaining actions by one.
     */
    public void decrementActionPoints() {
        if (this.action > 0) {
            this.action--;
            System.out.println("Player " + name + ": Action points decremented. Remaining: " + this.action);
        } else {
            System.out.println("Player " + name + ": Warning - Tried to decrement action points when already 0.");
        }
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    protected void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    /**
     * Calculate the player's current score according to role-specific rules.
     * Must be implemented by subclasses.
     * @return total points for this player
     */
    public abstract int getPoints();
}