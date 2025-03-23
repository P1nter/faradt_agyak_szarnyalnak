public class ParalyzingSpore implements ISpore {
    public ParalyzingSpore() {
        System.out.println("ParalyzingSpore.ParalyzingSpore() called");

        System.out.println("ParalyzingSpore.ParalyzingSpore() returned");
    }
    public void affectInsect(Insect insect) {}
}
