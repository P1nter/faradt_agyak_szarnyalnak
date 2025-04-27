public class SpeedingSpore extends Spore {
    @Override
    public void affectInsect(Insect insect) {
        System.out.println("SpeedingSpore.affectInsect(insect) called");
        insect.effectedBySpeedingSpore();
        System.out.println("SpeedingSpore.affectInsect(insect) returned");
    }
}
