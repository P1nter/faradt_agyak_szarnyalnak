public class LifeTekton extends Tekton {
    public LifeTekton(int id) { super(id); }
    public LifeTekton() { super(); }
    @Override public boolean canCut() { return false; }
}