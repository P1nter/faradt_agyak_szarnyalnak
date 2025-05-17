public class FastGrowthTekton extends Tekton {
    // Your diagram showed "fastTekton: boolean" - similar to DisappearingYarnTekton,
    // if it's a fixed property, the override is enough.
    public FastGrowthTekton(int id) { super(id); }
    public FastGrowthTekton() { super(); }
    @Override public boolean isFastTekton() { return true; }
}