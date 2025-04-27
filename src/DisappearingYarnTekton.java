import java.util.List;

public class DisappearingYarnTekton extends Tekton {
    public DisappearingYarnTekton() {
        System.out.println("DisappearingYarnTekton.DisappearingYarnTekton() called");
        System.out.println("DisappearingYarnTekton.DisappearingYarnTekton() returned");
    }

    @Override
    public boolean isDisappearing() {
        System.out.println("DisappearingYarnTekton.isDisappearingYarn() called");
        System.out.println("DisappearingYarnTekton.isDisappearingYarn() returned");
        return true;
    }
}
