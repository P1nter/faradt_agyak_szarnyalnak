// Player.java
public abstract class Player {
    private String Name;
    private int Score;
    private int Action; // Current actions remaining this turn
    public static final int DEFAULT_ACTIONS_PER_TURN = 3; // Define default actions

    protected enum PlayerType { INSECTER, MUSHROOMER }
    private PlayerType playerType;

    public Player(String name) {
        this.Name = name;
        this.Score = 0;
        this.Action = DEFAULT_ACTIONS_PER_TURN; // Initialize with default
    }

    public String getName() { return Name; }
    public int getScore() { return Score; }
    public int getAction() { return Action; } // Returns actions *remaining*

    public void setName(String name) { this.Name = name; }
    public void setScore(int score) { this.Score = score; }

    // This might be used if max actions can change, but decrementActionPoints is for using them up.
    // public void setAction(int action) { this.Action = action; }

    public void resetActionPoints() {
        this.Action = DEFAULT_ACTIONS_PER_TURN;
        System.out.println("Player " + Name + ": Action points reset to " + this.Action);
    }

    public boolean hasActionsLeft() {
        return this.Action > 0;
    }

    public void decrementActionPoints() {
        if (this.Action > 0) {
            this.Action--;
            System.out.println("Player " + Name + ": Action points decremented. Remaining: " + this.Action);
        } else {
            System.out.println("Player " + Name + ": Warning - Tried to decrement action points when already 0.");
        }
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    protected void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
}