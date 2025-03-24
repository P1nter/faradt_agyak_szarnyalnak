import java.util.ArrayList;
import java.util.List;

public class Insect {
    private int score;
    private List<Spore> effects = new ArrayList<>();

    private int speed;
    private Tekton tektons = new DefaultTekton();
    public List<Spore> getEffects() {
        return effects;
    }
    public void effectsAdd(Spore spore) {
        System.out.println("Insect.effectsadd(Spore) called");
        boolean add =  effects.add(spore);
        System.out.println("Insect.effectsadd(Spore) returned " + add);
    }
    public void effectsRemove(Spore spore) {
        System.out.println("Insect.effectsremove(Spore) called");
        boolean add =  effects.remove(spore);
        System.out.println("Insect.effectsremove(Spore) returned " + add);
    }

    public Insect() {
        System.out.println("Insect.Insect() called");

        System.out.println("Insect.Insect() returned");
    }
    public void move(MushroomYarn path) {
        System.out.println("Insect.move(path) called");
        tektons.getAdjacentTektons();
        path.getTektons();
        System.out.println("Insect.move(path) returned");
    }
    public void consumeSpore(Spore spore) {
        System.out.println("Insect.consumeSpore() called");
        spore.affectInsect(this);
        System.out.println("Insect.consumeSpore() returned");
    }
    public void cut(MushroomYarn yarn) {
        System.out.println("Insect.cut(yarn) called");
        yarn.cut();
        System.out.println("Insect.cut(yarn) returned");
    }
    public int getScore() {
        System.out.println("Insect.getScore() called");

        System.out.println("Insect.getScore() returned int");
        return score;
    }
}
