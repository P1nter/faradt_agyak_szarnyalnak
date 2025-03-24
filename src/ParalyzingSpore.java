public class ParalyzingSpore implements ISpore {
    public ParalyzingSpore() {
        System.out.println("ParalyzingSpore.ParalyzingSpore() called");

        System.out.println("ParalyzingSpore.ParalyzingSpore() returned");
    }
    @Override
    public void affectInsect(Insect insect) {
        System.out.println("ParalyzingSpore.affectInsect(insect) called");
        insect.effectsAdd(this);
        System.out.println("ParalyzinhSpore.affectInsect(insect) returned");
    }
}
