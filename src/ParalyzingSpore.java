public class ParalyzingSpore extends Spore {
    public ParalyzingSpore(Tekton onTekton, Mushroomer owner, int id) {
        super(SporeType.PARALYZING, onTekton, owner, id, 7); // Example nutrition 7
    }
    @Override public void affectInsect(Insect insect) { insect.effectedByParalyzingSpore(); }
}