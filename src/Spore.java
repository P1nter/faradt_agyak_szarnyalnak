import java.util.ArrayList;
import java.util.List;

abstract class Spore {
    int nutrition = 0;
    List<Tekton> tekton = new ArrayList<>();
    public void affectInsect(Insect insect){}

    public void setTekton(List<Tekton> tekton) {
        System.out.println("Spore.setTekton() called");
        this.tekton = tekton;
        System.out.println("Spore.setTekton() returned");    }
}