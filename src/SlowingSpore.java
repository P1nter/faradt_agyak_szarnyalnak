public class SlowingSpore extends Spore {
    public SlowingSpore(Tekton onTekton, Mushroomer owner, int id) {
        super(SporeType.SLOWING, onTekton, owner, id, 5);
    }
    // The diagram shows a constructor (Tekton, Mushroomer) - this implies ID is auto-generated or 0.
    // The Spore base class now handles auto-ID if 0 is passed.
    public SlowingSpore(Tekton tekton, Mushroomer mushroomer) { // ID will be auto-generated
        super(SporeType.SLOWING, tekton, mushroomer, 0, 5);
    }
    @Override public void affectInsect(Insect insect) { insect.effectedBySlowingSpore(); }
}