import java.util.ArrayList;
import java.util.List;

abstract class Tekton {
    List<Tekton> adjacentTektons = new ArrayList<>();
    Mushroom mushrooms = null;
    List<Insect> insects = new ArrayList<>();

    public Tekton() {
        System.out.println("Tekton.Tekton() called");

        System.out.println("Tekton.Tekton() returned");
    }
    List<Tekton> split() {
        //ezgecibonyi
        return adjacentTektons;
    }
    List<Tekton> getAdjacentTektons() {
        System.out.println("Tekton.getAdjacentTektons() called");
        System.out.println("Tekton.getAdjacentTektons() returned");
        return adjacentTektons;
    }
    public void addNewInsect(Insect insect) {
        System.out.println("Tekton.addNewInsect(Insect) called");
        insects.add(insect);
        System.out.println("Tekton.addNewInsect(Insect) returned");
    }
    public void removeInsect(Insect insect) {
        System.out.println("Tekton.removeInsect(Insect) called");
        insects.remove(insect);
        System.out.println("Tekton.removeInsect(Insect) returned");
    }
    public void addAdjacentTekton(Tekton tekton) {
        System.out.println("Tekton.addAdjacentTekton(Tekton) called");
        adjacentTektons.add(tekton);
        System.out.println("Tekton.addAdjacentTekton(Tekton) returned");
    }
    public void removeAdjacentTekton(Tekton tekton) {
        System.out.println("Tekton.removeAdjacentTekton(Tekton) called");
        adjacentTektons.remove(tekton);
        System.out.println("Tekton.removeAdjacentTekton(Tekton) returned");
    }
    public void setMushroom(Mushroom mushroom) {
        System.out.println("Tekton.setMushroom(Mushroom) called");
        this.mushrooms = mushroom;
        System.out.println("Tekton.setMushroom(Mushroom) returned");
    }
    public Mushroom getMushroom() {
        System.out.println("Tekton.getMushroom() called");
        System.out.println("Tekton.getMushroom() returned");
        return mushrooms;
    }
    public List<Insect> getInsects(){
        return insects;
    }
    public boolean canCut() {
        System.out.println("Tekton.canCut() called");
        System.out.println("Tekton.canCut() returned");
        return true;
    }
    public boolean canGrow() {
        System.out.println("Tekton.canGrow() called");
        System.out.println("Tekton.canGrow() returned");
        return true;
    }
    public boolean canGrowYarn(){
        System.out.println("Tekton.canGrowYarn() called");
        System.out.println("Tekton.canGrowYarn() returned");
        return true;
    }
}
