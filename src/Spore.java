// Spore.java
// import java.util.Observable; // Legacy

public abstract class Spore /* extends Observable */ {
    public enum SporeType {
        CUT_DISABLING("Cut Disabling"),
        PARALYZING("Paralyzing"),
        DUPLICATING("Duplicating"),
        SLOWING("Slowing"),
        SPEEDING("Speeding");

        private final String displayName;
        SporeType(String displayName) { this.displayName = displayName; }
        @Override public String toString() { return displayName; }
    }

    protected int nutrition; // Subclasses should set this
    protected Tekton currentTekton; // The Tekton this spore is currently on
    protected Mushroomer owner;     // The Mushroomer player who owns/created this spore
    protected final int ID;         // Unique ID for the spore instance
    protected final SporeType type; // The specific type of this spore

    private static int nextIDCounter = 1;

    protected Spore(SporeType type, Tekton onTekton, Mushroomer owner, int id, int nutritionValue) {
        if (type == null || onTekton == null || owner == null) {
            throw new IllegalArgumentException("Spore type, onTekton, and owner cannot be null.");
        }
        this.type = type;
        this.currentTekton = onTekton;
        this.owner = owner;
        this.nutrition = nutritionValue;

        if (id <= 0) {
            this.ID = nextIDCounter++;
        } else {
            this.ID = id;
            if (id >= nextIDCounter) nextIDCounter = id + 1;
        }
        // System.out.println("Spore Created: ID=" + this.ID + ", Type=" + type + ", Owner=" + owner.getName() + ", OnTekton=" + onTekton.getIDNoPrint());
    }

    public abstract void affectInsect(Insect insect);

    public Tekton getTektonNoPrint() { return currentTekton; }
    public Tekton getTekton() { /* System.out.println("Spore.getTekton() called"); */ return currentTekton; }

    public void setTekton(Tekton tekton) { // When spore moves or is placed
        // If spore moves, it should be removed from old Tekton's mushroom manager's list
        // and added to the new one. This logic is better handled by game actions.
        // For now, just update its internal reference.
        // System.out.println("Spore " + ID + " setTekton from " + (this.currentTekton != null ? this.currentTekton.getIDNoPrint() : "null") + " to " + (tekton != null ? tekton.getIDNoPrint() : "null"));
        this.currentTekton = tekton;
    }

    public Mushroomer getOwner() { return owner; }
    // Owner is typically set at creation and shouldn't change.
    public void setOwner(Mushroomer owner) { this.owner = owner; }


    public int getIDNoPrint() { return ID; }
    public int getID() { return ID; }
    public SporeType getSporeType() { return type; }
    public int getNutrition() { return nutrition; }


    public void destroySpore(){
        // System.out.println("Spore ID " + ID + " destroySpore() called.");
        if (currentTekton != null && currentTekton.getMushroomNoPrint() != null) {
            currentTekton.getMushroomNoPrint().removeSpore(this);
        }
        if (owner != null) {
            owner.removeOwnedSpore(this); // Mushroomer needs removeOwnedSpore(Spore s)
        }
        // System.out.println("Spore ID " + ID + " references removed from Tekton's manager and Owner.");
    }
}