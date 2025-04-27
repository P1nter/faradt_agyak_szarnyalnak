public class DuplicatingSpore extends Spore{
    public DuplicatingSpore(Tekton tekton) {
        this.nutrition = 5;
        this.tekton = tekton;
    }

    @Override
    public void affectInsect(Insect insect) {
        System.out.println("DuplicatingSpore.affectInsect(insect) called");
        //nem létezik ez a függvény
        //TODO
        //insect.effectedByDuplicatingSpore();
        System.out.println("DuplicatingSpore.affectInsect(insect) returned");
    }
}
