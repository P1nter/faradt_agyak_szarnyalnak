public class CutDisablingSpore extends Spore {
    // Removed redundant ID field, inherited from Spore.
    // Removed redundant tekton field, inherited from Spore (as currentTekton).
    // Removed redundant Owner field, inherited from Spore.

    // Constructor for when a player creates this spore
    public CutDisablingSpore(Tekton onTekton, Mushroomer owner, int id) {
        super(SporeType.CUT_DISABLING, onTekton, owner, id, 5); // Type, location, owner, id, nutrition
        // System.out.println("CutDisablingSpore ID " + this.ID + " created.");
    }

    // Diagram also showed a constructor with just Tekton. This is problematic for ownership.
    // If such a constructor is needed (e.g. for neutral spores not owned by a player),
    // the 'owner' field in Spore would need to be nullable, or use a placeholder owner.
    // For now, assuming all game-relevant spores have a Mushroomer owner.
    /*
    public CutDisablingSpore(Tekton tekton) {
        // This constructor is problematic as it doesn't set an owner.
        // Spores in this game design seem to need an owner.
        // super(SporeType.CUT_DISABLING, tekton, ??? OWNER ???, 0, 5);
        System.err.println("CutDisablingSpore constructor (Tekton only) is problematic for ownership.");
    }
    */

    @Override
    public void affectInsect(Insect insect) {
        // System.out.println("CutDisablingSpore " + getIDNoPrint() + " affecting Insect " + insect.getIDNoPrint());
        insect.effectedByCutDisablingSpore();
    }
}