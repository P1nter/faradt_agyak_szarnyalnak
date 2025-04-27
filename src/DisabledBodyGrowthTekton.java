import java.util.List;

public class DisabledBodyGrowthTekton extends Tekton {
    public DisabledBodyGrowthTekton() {
        System.out.println("DisabledBodyGrowthTekton.DisabledBodyGrowthTekton() called");
        System.out.println("DisabledBodyGrowthTekton.DisabledBodyGrowthTekton() returned");
    }
    @Override
    public boolean canGrow() {
        System.out.println("DisabledBodyGrowthTekton.canGrowBody() called");
        System.out.println("DisabledBodyGrowthTekton.canGrowBody() returned");
        return false;
    }
}
