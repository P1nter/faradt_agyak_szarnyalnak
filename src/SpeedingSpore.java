public class SpeedingSpore extends Spore {
    public SpeedingSpore(Tekton onTekton, Mushroomer owner, int id) {
        super(SporeType.SPEEDING, onTekton, owner, id, 5);
    }
    public SpeedingSpore(Tekton tekton, Mushroomer mushroomer) { // ID will be auto-generated
        super(SporeType.SPEEDING, tekton, mushroomer, 0, 5);
    }
    @Override public void affectInsect(Insect insect) { insect.effectedBySpeedingSpore(); }
}