public class SlowingSpore extends Spore {
    public SlowingSpore(Tekton tekton) {
        this.nutrition = 5;
        this.tekton = tekton;
    }

    @Override
    public void affectInsect(Insect insect) {
        System.out.println("SlowingSpore.affectInsect(insect) called");
        insect.effectedBySlowingSpore();
        System.out.println("SlowingSpore.affectInsect(insect) returned");
    }
}
