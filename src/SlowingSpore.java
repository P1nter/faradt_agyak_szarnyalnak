public class SlowingSpore implements ISpore {
    @Override
    public void affectInsect(Insect insect) {
        System.out.println("SlowingSpore.affectInsect(insect) called");
        insect.effectsAdd(this);
        System.out.println("SlowingSpore.affectInsect(insect) returned");
    }
}
