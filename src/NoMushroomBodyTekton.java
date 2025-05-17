public class NoMushroomBodyTekton extends Tekton {
    public NoMushroomBodyTekton(int id) { super(id); }
    public NoMushroomBodyTekton() { super(); }
    @Override public boolean canGrow() { return false; }
}