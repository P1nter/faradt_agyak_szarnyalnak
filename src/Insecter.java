import java.util.ArrayList;
import java.util.List;

public class Insecter extends Player{
    private List<Insect> insects = new ArrayList<Insect>();
    public Insecter(String name) {
        super(name);
        System.out.println("Insecter.Insecter(name) called");
        System.out.println("Insecter.Insecter(name) returned");
    }
}
