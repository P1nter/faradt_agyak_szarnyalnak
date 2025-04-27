public abstract class Player {
    private String Name;
    private int Score;
    private int Action;

    public Player(String name) {
        this.Name = name;
        this.Score = 0;
        this.Action = 3;
    }
    public String getName() {
        return Name;
    }
    public int getScore() {
        return Score;
    }
    public int getAction() {
        return Action;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public void setScore(int score) {
        this.Score = score;
    }
    public void setAction(int action) {
        this.Action = action;
    }


}
