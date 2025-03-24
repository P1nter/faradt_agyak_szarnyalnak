public class CutDisablingSpore extends Spore {
    @Override
    public void affectInsect(Insect insect) {
        System.out.println("CutDisablingSpore.affectInsect(insect) called");
        insect.effectsAdd(this);
        System.out.println("CutDisablingSpore.affectInsect(insect) returned");
    }
}
