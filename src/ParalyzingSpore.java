import java.util.ArrayList;

public class ParalyzingSpore extends Spore {
    public ParalyzingSpore() {
        System.out.println("ParalyzingSpore.ParalyzingSpore() called");
        setTekton(new ArrayList<>());
        System.out.println("ParalyzingSpore.ParalyzingSpore() returned");
    }
    @Override
    public void affectInsect(Insect insect) {
        System.out.println("ParalyzingSpore.affectInsect(insect) called");
        insect.effectsAdd(this);
        System.out.println("ParalyzinhSpore.affectInsect(insect) returned");
    }
}
