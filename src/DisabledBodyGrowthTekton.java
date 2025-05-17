public class DisabledBodyGrowthTekton extends Tekton {
    public DisabledBodyGrowthTekton(int id) { super(id); }
    public DisabledBodyGrowthTekton() { super(); }
    @Override public boolean canGrow() { return false; }
}