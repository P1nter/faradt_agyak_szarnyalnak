public class CutDisablingSpore extends Spore {

    public CutDisablingSpore(Tekton tekton) {
        this.nutrition = 5;
        this.tekton = tekton;
    }
    @Override
    public void affectInsect(Insect insect) {
        System.out.println("CutDisablingSpore.affectInsect(insect) called");
        insect.effectedByCutDisablingSpore();
        System.out.println("CutDisablingSpore.affectInsect(insect) returned");
    }
}
