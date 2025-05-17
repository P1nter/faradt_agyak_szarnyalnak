// Tekton.java
import java.util.ArrayList;
import java.util.List;
// import java.util.Observable; // Observable is legacy

public abstract class Tekton /* extends Observable */ { // Consider PropertyChangeSupport if needed
    protected List<Tekton> adjacentTektons = new ArrayList<>();
    protected Mushroom mushroomManager; // Each Tekton has its own Mushroom manager instance
    protected int ID;
    private static int nextIDCounter = 1; // For unique default IDs
    protected List<Insect> insects = new ArrayList<>();

    public Tekton(int id) {
        if (id <= 0) {
            this.ID = nextIDCounter++;
            // System.out.println("Tekton Constructor: ID was <= 0, assigned new ID: " + this.ID);
        } else {
            this.ID = id;
            if (id >= nextIDCounter) { // Ensure nextIDCounter stays ahead if IDs are manually set
                nextIDCounter = id + 1;
            }
            // System.out.println("Tekton Constructor: Assigned provided ID: " + this.ID);
        }
        // CRUCIAL: Each Tekton creates its Mushroom manager and tells the manager which Tekton it belongs to.
        this.mushroomManager = new Mushroom(this);
    }

    public Tekton() {
        this(0); // Call the constructor that handles ID assignment
    }

    // --- Getters and Setters ---
    public Mushroom getMushroomNoPrint() {
        return mushroomManager;
    }

    public Mushroom getMushroom() {
        // System.out.println("Tekton " + getIDNoPrint() + ": getMushroom() called.");
        return mushroomManager;
    }

    // Setting the mushroom manager post-construction should be rare if Tekton creates its own.
    // Could be used if a Tekton's fungal properties fundamentally change.
    public void setMushroom(Mushroom mushroomManager) {
        // System.out.println("Tekton " + getIDNoPrint() + ": setMushroom() called.");
        this.mushroomManager = mushroomManager;
        if (this.mushroomManager != null && this.mushroomManager.getTektonNoPrint() != this) {
            this.mushroomManager.setTekton(this); // Ensure the Mushroom manager knows its Tekton
        }
    }

    public int getIDNoPrint() {
        return ID;
    }

    public int getID() {
        // System.out.println("Tekton.getID() called for ID " + this.ID);
        return ID;
    }

    public void setID(int id) { // Useful if IDs are assigned after object creation (e.g. loading)
        this.ID = id;
        if (id >= nextIDCounter) {
            nextIDCounter = id + 1;
        }
    }


    public List<Tekton> getAdjacentTektons() {
        // System.out.println("Tekton " + getIDNoPrint() + ": getAdjacentTektons() called (logged version).");
        return new ArrayList<>(adjacentTektons); // Return a defensive copy
    }

    public List<Tekton> getAdjacentTektonsNoPrint() {
        return adjacentTektons; // Internal access to the modifiable list
    }

    public void addAdjacentTekton(Tekton tekton) {
        if (tekton == null || tekton == this) {
            return; // Cannot add null or self as adjacent
        }
        // System.out.println("Tekton " + getIDNoPrint() + ": Attempting to add adjacent Tekton " + tekton.getIDNoPrint());
        if (!this.adjacentTektons.contains(tekton)) {
            this.adjacentTektons.add(tekton);
        }
        // Ensure symmetry by adding this tekton to the other's list,
        // using its NoPrint version to avoid potential logging loops during this operation.
        if (!tekton.getAdjacentTektonsNoPrint().contains(this)) {
            tekton.getAdjacentTektonsNoPrint().add(this);
        }
    }

    public void removeAdjacentTekton(Tekton tekton) {
        if (tekton == null) return;
        // System.out.println("Tekton " + getIDNoPrint() + ": Attempting to remove adjacent Tekton " + tekton.getIDNoPrint());
        boolean removed1 = this.adjacentTektons.remove(tekton);
        boolean removed2 = tekton.getAdjacentTektonsNoPrint().remove(this); // Use NoPrint for the other side
        // if (removed1 || removed2) System.out.println("Adjacency removed between " + getIDNoPrint() + " and " + tekton.getIDNoPrint());
    }

    public List<Insect> getInsects() {
        return new ArrayList<>(insects); // Return a defensive copy
    }

    public List<Insect> getInsectsNoPrint() {
        return insects;
    }

    public void addNewInsect(Insect insect) {
        if (insect != null && !this.insects.contains(insect)) {
            this.insects.add(insect);
            // System.out.println("Tekton " + getIDNoPrint() + ": Insect " + insect.getIDNoPrint() + " added.");
        }
    }

    public void removeInsect(Insect insect) {
        boolean removed = this.insects.remove(insect);
        // if (removed) System.out.println("Tekton " + getIDNoPrint() + ": Insect " + insect.getIDNoPrint() + " removed.");
    }

    // --- Gameplay relevant methods (to be overridden by subclasses) ---
    public boolean canGrow() {
        // System.out.println("Tekton " + getIDNoPrint() + ": canGrow() called, returning true (default).");
        return true;
    }

    public boolean canGrowYarn() {
        // System.out.println("Tekton " + getIDNoPrint() + ": canGrowYarn() called, returning true (default).");
        return true;
    }

    public boolean canCut() {
        // System.out.println("Tekton " + getIDNoPrint() + ": canCut() called, returning true (default).");
        return true;
    }

    public boolean isDisappearing() {
        // System.out.println("Tekton " + getIDNoPrint() + ": isDisappearing() called, returning false (default).");
        return false;
    }

    public boolean isFastTekton() {
        // System.out.println("Tekton " + getIDNoPrint() + ": isFastTekton() called, returning false (default).");
        return false;
    }
}