public class SpeedingSpore extends Spore {
    @Override
    public void affectInsect(Insect insect) {
        System.out.println("SpeedingSpore.affectInsect(insect) called");
        insect.effectsAdd(this);
        System.out.println("SpeedingSpore.affectInsect(insect) returned");
    }
}
