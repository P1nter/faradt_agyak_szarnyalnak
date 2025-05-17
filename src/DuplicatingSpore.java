public class DuplicatingSpore extends Spore {
    public DuplicatingSpore(Tekton onTekton, Mushroomer owner, int id) {
        super(SporeType.DUPLICATING, onTekton, owner, id, 10); // Example nutrition 10
    }
    @Override public void affectInsect(Insect insect) { insect.effectedByDuplicatingSpore(); }
}