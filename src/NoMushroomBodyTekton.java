import java.util.List;

public class NoMushroomBodyTekton extends Tekton {
    public NoMushroomBodyTekton() {
        System.out.println("NoMushroomBodyTekton.NoMushroomBodyTekton() called");
        System.out.println("NoMushroomBodyTekton.NoMushroomBodyTekton() returned");
    }
    @Override
    public boolean canGrow() {
        System.out.println("NoMushroomBodyTekton.isMushroomBody() called");
        System.out.println("NoMushroomBodyTekton.isMushroomBody() returned");
        return false;
    }
}
