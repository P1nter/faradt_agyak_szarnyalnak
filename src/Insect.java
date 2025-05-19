import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
// import java.util.Observable; // Legacy

public class Insect /* extends Observable */ { // Consider PropertyChangeSupport if events needed
    private int ID;
    private int score;
    private int action;
    private Tekton tekton;
    private Insecter owner;
    private int[] effects = {0, 0, 0, 0}; // 0:cutDisabled, 1:paralyzed, 2:spedUp, 3:slowedDown

    private static int nextIDCounter = 1; // For unique default IDs

    // PropertyChangeSupport for modern observable pattern (optional)
    // private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    // public void addPropertyChangeListener(PropertyChangeListener listener) { this.pcs.addPropertyChangeListener(listener); }
    // public void removePropertyChangeListener(PropertyChangeListener listener) { this.pcs.removePropertyChangeListener(listener); }

    public Insect(Tekton location, Insecter owner) {
        this(location, owner, 0); // Call main constructor with default ID
    }

    public Insect(Tekton location, Insecter owner, int id) {
        if (location == null || owner == null) {
            throw new IllegalArgumentException("Insect must have a valid Tekton location and an Insecter owner.");
        }
        if (id <= 0) {
            this.ID = nextIDCounter++;
        } else {
            this.ID = id;
            if (id >= nextIDCounter) nextIDCounter = id + 1;
        }
        this.tekton = location;
        this.owner = owner;
        this.score = 0;
        this.action = Player.DEFAULT_ACTIONS_PER_TURN; // Get from Player class

        // CRITICAL: Ensure the insect is registered with its location and owner
        this.owner.addInsect(this);
        this.tekton.addNewInsect(this);

        System.out.println("Insect CONSTRUCTOR: ID " + this.ID + " created for " + owner.getName() + " on Tekton " + location.getIDNoPrint());
    }

    public Insecter getOwnerNoPrint() { return owner; }
    public Insecter getOwner() { return owner; }
    public void setOwner(Insecter owner) { this.owner = owner; } // Should typically be set at construction

    public Tekton getTektonNoPrint() { return tekton; }
    public Tekton getTekton() { return tekton; }
    public void setTekton(Tekton newTekton) { // Called when insect moves
        if (this.tekton != null) {
            this.tekton.removeInsect(this);
        }
        this.tekton = newTekton;
        if (this.tekton != null) {
            this.tekton.addNewInsect(this);
        }
        // System.out.println("Insect " + ID + " moved to Tekton " + (newTekton != null ? newTekton.getIDNoPrint() : "null"));
    }


    public int getIDNoPrint() { return ID; }
    public int getID() { return ID; }
    public int[] getEffectsNoPrint() { return effects; }
    public int[] getEffects() { return effects; }
    public int getScoreNoPrint() { return score; }
    public int getScore() { return score; }
    public int getActionNoPrint() { return action; }
    public int getAction() { return action; }
    public void setActionForTurn(int action) { this.action = action; } // For effects like paralyze/speed


    public void effectedByCutDisablingSpore() { this.effects[0] = 3; System.out.println("Insect " + ID + " effected by CutDisablingSpore."); }
    public void effectedByParalyzingSpore() { this.effects[1] = 3; this.action = 0; System.out.println("Insect " + ID + " effected by ParalyzingSpore. Actions set to 0."); }
    public void effectedBySpeedingSpore() { this.effects[2] = 3; this.action *= 2; System.out.println("Insect " + ID + " effected by SpeedingSpore. Actions doubled to " + this.action); }
    public void effectedBySlowingSpore() { this.effects[3] = 3; this.action /= 2; System.out.println("Insect " + ID + " effected by SlowingSpore. Actions halved to " + this.action); }
    public void effectedByDuplicatingSpore() {
        System.out.println("Insect " + ID + " effected by DuplicatingSpore.");
        // Create a new insect with a new ID
        Insect newInsect = new Insect(this.tekton, this.owner, 0); // Let constructor assign new ID
        // Note: The new insect is automatically added to owner and tekton by its constructor.
        System.out.println("  New Insect ID " + newInsect.getIDNoPrint() + " created for " + this.owner.getName() + " on Tekton " + this.tekton.getIDNoPrint());
    }

    public void nextTurn() {
        // System.out.println("Insect " + ID + ": nextTurn called. Effects before: " + java.util.Arrays.toString(effects));
        // Handle effects wearing off
        if (effects[1] == 1) { // Paralyze wears off
            this.action = Player.DEFAULT_ACTIONS_PER_TURN; // Reset actions
            System.out.println("  Insect " + ID + ": Paralyze worn off. Actions reset to " + this.action);
        }
        if (effects[2] == 1) { // Speed up wears off
            this.action /= 2; // Revert speed effect
            System.out.println("  Insect " + ID + ": SpeedUp worn off. Actions reverted to " + this.action);
        }
        if (effects[3] == 1) { // Slow down wears off
            this.action *= 2; // Revert slow effect
            System.out.println("  Insect " + ID + ": SlowDown worn off. Actions reverted to " + this.action);
        }

        for (int i = 0; i < effects.length; i++) {
            if (effects[i] > 0) {
                effects[i]--;
            }
        }
        // System.out.println("  Insect " + ID + ": Effects after: " + java.util.Arrays.toString(effects));
    }

    public boolean move(MushroomYarn path) {
        System.out.println("Insect " + ID + ": Attempting move via Yarn " + path.getIDNoPrint());
        if (action <= 0) { System.out.println("  Move Fail: No actions left."); return false; }
        if (effects[1] > 0) { System.out.println("  Move Fail: Paralyzed."); return false; } // Paralyzed

        Tekton[] pathTektons = path.getTektonsNoPrint();
        Tekton destinationTekton = null;
        if (pathTektons[0] == this.tekton) {
            destinationTekton = pathTektons[1];
        } else if (pathTektons[1] == this.tekton) {
            destinationTekton = pathTektons[0];
        }

        if (destinationTekton == null) {
            System.out.println("  Move Fail: Yarn " + path.getIDNoPrint() + " not connected to current Tekton " + this.tekton.getIDNoPrint());
            return false;
        }
        if (path.getIsCut()) {
            System.out.println("  Move Fail: Yarn " + path.getIDNoPrint() + " is cut.");
            return false;
        }

        // Successfully moving
        Tekton oldTekton = this.tekton;
        setTekton(destinationTekton); // This will update lists in old and new Tekton

        // action--; // Decrementing action points is now handled by Game.java after successful model update
        System.out.println("  Move SUCCESS: Insect " + ID + " moved from T" + oldTekton.getIDNoPrint() + " to T" + destinationTekton.getIDNoPrint() + " via Yarn " + path.getIDNoPrint());
        return true;
    }

    public boolean consumeSpore(Spore spore) {
        System.out.println("Insect " + ID + ": Attempting to consume Spore " + spore.getIDNoPrint() + " of type " + spore.getSporeType());
        if (action <= 0) { System.out.println("  Consume Fail: No actions left."); return false; }
        if (effects[1] > 0) { System.out.println("  Consume Fail: Paralyzed."); return false; }

        if (spore.getTektonNoPrint() != this.tekton) {
            System.out.println("  Consume Fail: Spore is on T" + spore.getTektonNoPrint().getIDNoPrint() + ", Insect is on T" + this.tekton.getIDNoPrint());
            return false;
        }

        // add score to Insect's score
        this.score += spore.getNutrition();
        spore.affectInsect(this); // Apply effect

        // Spore is consumed, remove it from game model
        // The Spore's owner (Mushroomer) and the Tekton's Mushroom manager should remove it.
        // Spore.destroySpore() handles this.
        spore.destroySpore();

        // action--; // Game.java handles action point decrement
        System.out.println("  Consume SUCCESS: Insect " + ID + " consumed Spore " + spore.getIDNoPrint());
        return true;
    }

    public boolean cut(MushroomYarn yarn) {
        System.out.println("Insect " + ID + ": Attempting to cut Yarn " + yarn.getIDNoPrint());
        if (action <= 0) { System.out.println("  Cut Fail: No actions left."); return false; }
        if (effects[1] > 0) { System.out.println("  Cut Fail: Paralyzed."); return false; } // Paralyzed
        if (effects[0] > 0) { System.out.println("  Cut Fail: Cutting disabled effect active."); return false; } // Cut disabled

        Tekton[] yarnTektons = yarn.getTektonsNoPrint();
        if (yarnTektons[0] != this.tekton && yarnTektons[1] != this.tekton) {
            System.out.println("  Cut Fail: Yarn " + yarn.getIDNoPrint() + " not connected to current Tekton " + this.tekton.getIDNoPrint());
            return false;
        }

        if (yarn.getIsCut()) {
            System.out.println("  Cut Fail: Yarn " + yarn.getIDNoPrint() + " is already cut.");
            return false; // Or maybe true if "attempting to cut an already cut yarn" is fine
        }

        boolean cutSuccess = yarn.cut(); // This method in MushroomYarn should set its state and return true
        if (cutSuccess) {
            // action--; // Game.java handles action point decrement
            // Cutting yarn → 1 point
            this.score += 1;
            System.out.println("  Cut SUCCESS: Insect " + ID + " cut Yarn " + yarn.getIDNoPrint());
            return true;
        } else {
            System.out.println("  Cut FAILED: Yarn.cut() returned false (e.g., Tektons don't allow cutting it).");
            return false;
        }
    }

    public void disappear() {
        System.out.println("Insect " + ID + " disappearing from Tekton " + (tekton != null ? tekton.getIDNoPrint() : "null"));
        if (this.tekton != null) {
            this.tekton.removeInsect(this);
        }
        if (this.owner != null) {
            this.owner.removeInsect(this);
        }
        this.tekton = null; // Nullify reference
    }
}