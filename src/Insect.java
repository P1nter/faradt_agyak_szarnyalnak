import java.util.ArrayList;
import java.util.List;

public class Insect {
    private int score;
    private List<ISpore> effects;
    private int speed;
    private ITekton tektons = new DefaultTekton();

    public Insect() {
        System.out.println("Insect.Insect() called");

        System.out.println("Insect.Insect() returned");
    }
    public void move(MushroomYarn path) {
        System.out.println("Insect.move(MushroomYarn path) called");
        tektons.getAdjacentTektons();
        path.getTektons();
        System.out.println("Insect.move(MushroomYarn path) returned");
    }
    public void consumeSpore(ISpore spore) {}
    public void cutYarn(MushroomYarn yarn) {}
    public int getScore() {
        System.out.println("Insect.getScore() called");

        System.out.println("Insect.getScore() returned int");
        return score;
    }
}
