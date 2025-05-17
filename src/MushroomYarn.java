// MushroomYarn.java
import java.util.Arrays;
// import java.util.Observable; // Legacy

public class MushroomYarn /* extends Observable */ {
    private Tekton[] tektons = new Tekton[2];
    private boolean isCut = false;
    private int timeBack = 0; // Time until reappears if it's a disappearing type that was cut
    private final int ID;
    private Mushroomer owner;
    private static int nextIDCounter = 1;

    public MushroomYarn(Tekton tekton1, Tekton tekton2, Mushroomer owner, int id) {
        if (tekton1 == null || tekton2 == null || owner == null) {
            throw new IllegalArgumentException("Tektons and Owner must be non-null for MushroomYarn.");
        }
        if (id <= 0) this.ID = nextIDCounter++; else this.ID = id;
        if (id >= nextIDCounter) nextIDCounter = id + 1;

        this.tektons[0] = tekton1;
        this.tektons[1] = tekton2;
        this.owner = owner;
        this.owner.addMushroomYarn(this); // Add to owner's list

        // Tekton's mushroom manager should also know about this yarn
        // This was previously in constructor, but should be done by GrowYarn method to ensure symmetry and avoid issues
        // tekton1.getMushroomNoPrint().addMushroomYarn(this);
        // tekton2.getMushroomNoPrint().addMushroomYarn(this);

        // Example: disappearing yarn logic could be here if Tekton type dictates it
        if (tektons[0].isDisappearing() || tektons[1].isDisappearing()) {
            // this.timeBack = 5; // Yarn on such tektons might start with a timer
        }
        // System.out.println("MushroomYarn CONSTRUCTOR: ID " + this.ID + " created between T" + tekton1.getIDNoPrint() + " and T" + tekton2.getIDNoPrint() + " for " + owner.getName());
    }

    // Simpler constructor if ID is auto-generated and owner set later, or for neutral yarns
    public MushroomYarn(Tekton tekton1, Tekton tekton2) {
        this(tekton1, tekton2, null, 0); // Owner can be set later
    }


    public boolean getIsCut() { return isCut; }
    public boolean getIsCutNoPrint() { return isCut; }
    // public void setCutStatus(boolean cut) { this.isCut = cut; } // Internal use if needed

    public Mushroomer getOwner() { return owner; }
    public void setOwner(Mushroomer owner) { this.owner = owner; }

    public int getTimeBackNoPrint() { return timeBack; }
    public int getTimeBack() { return timeBack; }
    public void setTimeBack(int timeBack) { this.timeBack = timeBack; }


    public boolean Update() { // Called each game round
        if (timeBack > 0) {
            timeBack--;
            if (timeBack == 0) {
                System.out.println("MushroomYarn ID " + ID + ": Timer up. It might reappear or be fully removed.");
                // If it was cut and timer is up, it could become uncut or just be removed.
                // For a disappearing yarn type, this means it's gone.
                if (this.isCut) { // If it was cut and timer is up, assume it's gone for good
                    return true; // Signal to Game to fully remove it
                } else {
                    // If it was a disappearing type of yarn (not necessarily cut)
                    // and its timer is up, signal for removal
                    if (tektons[0].isDisappearing() || tektons[1].isDisappearing()){
                        // This yarn type might inherently disappear
                        // return true; // This needs specific game rule implementation
                    }
                }
            }
        }
        return false; // Not disappeared this turn due to timer
    }

    public int getIDNoPrint() { return ID; }
    public int getID() { return ID; }

    public Tekton[] getTektonsNoPrint() { return tektons; } // Return direct array for internal checks
    public Tekton[] getTektons() { return Arrays.copyOf(tektons, tektons.length); } // Defensive copy

    // public void updateTektons(Tekton t1, Tekton t2) { /* ... if yarns can be moved ... */ }

    public boolean cut() {
        System.out.println("MushroomYarn ID " + ID + ": cut() method called.");
        if (isCut) {
            System.out.println("  Yarn " + ID + " is already cut.");
            return false; // Cannot cut an already cut yarn again by this logic
        }
        // Check if connected Tektons allow cutting.
        // Your Tekton subtypes (like LifeTekton) override canCut().
        if (!(tektons[0].canCut() && tektons[1].canCut())) {
            System.out.println("  Yarn " + ID + " cannot be cut; one or both Tektons (T" + tektons[0].getIDNoPrint() + " type: " + tektons[0].getClass().getSimpleName() +
                    ", T" + tektons[1].getIDNoPrint() + " type: " + tektons[1].getClass().getSimpleName() + ") prevent it.");
            return false;
        }

        // Additional game rule: Can only cut if one of the Tektons has a MushroomBody?
        // boolean bodyAtEnd1 = tektons[0].getMushroomNoPrint() != null && tektons[0].getMushroomNoPrint().hasMushroomBody();
        // boolean bodyAtEnd2 = tektons[1].getMushroomNoPrint() != null && tektons[1].getMushroomNoPrint().hasMushroomBody();
        // if (!bodyAtEnd1 && !bodyAtEnd2) {
        //    System.out.println("  Yarn " + ID + " cannot be cut; no mushroom body at either end.");
        //    return false;
        // }

        this.isCut = true;
        this.timeBack = 3; // Example: Cut yarn disappears for 3 turns then is gone for good (if Update handles it)
        System.out.println("  Yarn " + ID + " successfully cut. Will disappear in " + this.timeBack + " turns.");
        return true;
    }

    // public void eatInsect(Insect insect) { /* ... if yarns can eat insects ... */ }
}