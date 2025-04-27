import java.util.List;

public class FastGrowthTekton extends Tekton {
    public FastGrowthTekton() {
        System.out.println("FastGrowthTekton.FastGrowthTekton() called");
        System.out.println("FastGrowthTekton.FastGrowthTekton() returned");

    }
    @Override
    public boolean isFastTekton(){
        System.out.println("Tekton.isFastTekton() called");
        System.out.println("Tekton.isFastTekton() returned");
        return true;
    }
}
