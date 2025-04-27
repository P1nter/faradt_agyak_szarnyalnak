public class LifeTekton extends Tekton{
    public LifeTekton() {
        System.out.println("FastGrowthTekton.FastGrowthTekton() called");
        System.out.println("FastGrowthTekton.FastGrowthTekton() returned");

    }
    @Override
    public boolean canCut() {
        System.out.println("Tekton.canCut() called");
        System.out.println("Tekton.canCut() returned");
        return false;
    }
}
