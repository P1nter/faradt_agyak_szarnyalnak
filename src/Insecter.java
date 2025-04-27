import java.util.ArrayList;
import java.util.List;

public class Insecter extends Player{
    private List<Insect> insects = new ArrayList<Insect>();
    public Insecter(String name) {
        super(name);
        System.out.println("Insecter.Insecter(name) called");
        System.out.println("Insecter.Insecter(name) returned");
    }
    public void ConsumSpore(Spore spore) {
        System.out.println("Insecter.ConsumSpore(spore) called");

        System.out.println("Insecter.ConsumSpore(spore) returned");
    }
    public void addInsect(Insect insect) {
        System.out.println("Insecter.AddInsect(insect) called");
        insects.add(insect);
        System.out.println("Insecter.AddInsect(insect) returned");
    }
    public List<Insect> getInsects() {
        System.out.println("Insecter.getInsects() called");
        System.out.println("Insecter.getInsects() returned");
        return insects;
    }
}
