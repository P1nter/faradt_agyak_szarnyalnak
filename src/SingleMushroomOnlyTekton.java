public class SingleMushroomOnlyTekton extends Tekton {
    public SingleMushroomOnlyTekton(int id) { super(id); }
    public SingleMushroomOnlyTekton() { super(); }

    @Override
    public boolean canGrowYarn() {
        // A Tekton of this type can only grow a yarn if its mushroomManager
        // currently has no yarns connected to it.
        if (this.getMushroomNoPrint() != null && !this.getMushroomNoPrint().getMushroomYarnsNoPrint().isEmpty()) {
            // System.out.println("SingleMushroomOnlyTekton " + getIDNoPrint() + ": cannot grow yarn, already has one.");
            return false;
        }
        // System.out.println("SingleMushroomOnlyTekton " + getIDNoPrint() + ": can grow yarn.");
        return true;
    }
}