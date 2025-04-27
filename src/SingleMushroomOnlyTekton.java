import java.util.List;

public class SingleMushroomOnlyTekton extends Tekton {
    public SingleMushroomOnlyTekton() {
        System.out.println("SingleMushroomOnlyTekton.SingleMushroomOnlyTekton() called");
        System.out.println("SingleMushroomOnlyTekton.SingleMushroomOnlyTekton() returned");
    }
    @Override
    public boolean canGrowYarn() {
        System.out.println("SingleMushroomOnlyTekton.canGrowYarn() called");
        System.out.println("SingleMushroomOnlyTekton.canGrowYarn() returned");
        if(this.getMushroom().hasYarns()){
            System.out.println("SingleMushroomOnlyTekton.canGrowYarn() returned true");
            return false;
        }
        return true;
    }
}
