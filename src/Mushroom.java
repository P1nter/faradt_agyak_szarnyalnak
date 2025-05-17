// Mushroom.java
import java.util.ArrayList;
import java.util.List;

public class Mushroom {
    private final Tekton onTekton; // The Tekton this Mushroom manager belongs to (final preferred)
    private MushroomBody mushroomBody = null;
    private List<MushroomYarn> yarnsConnectedHere = new ArrayList<>();
    private List<Spore> sporesOnTekton = new ArrayList<>();
    private int howOld = 0;
    private final int ID; // ID for this Mushroom manager instance
    private static int nextIDCounter = 1;

    public Mushroom(Tekton onTekton) {
        if (onTekton == null) {
            throw new IllegalArgumentException("Mushroom manager must be constructed with a non-null Tekton.");
        }
        this.ID = nextIDCounter++;
        this.onTekton = onTekton;
        // System.out.println("Mushroom Manager ID " + this.ID + " created for Tekton ID " + onTekton.getIDNoPrint());
    }

    // If loading from a save, ID might be provided.
    public Mushroom(Tekton onTekton, int id) {
        if (onTekton == null) {
            throw new IllegalArgumentException("Mushroom manager must be constructed with a non-null Tekton.");
        }
        this.ID = id;
        this.onTekton = onTekton;
        if (id >= nextIDCounter) nextIDCounter = id + 1;
    }


    public Tekton getTektonNoPrint() { return onTekton; }
    public Tekton getTekton() { return onTekton; }
    // setTekton should ideally not be needed if onTekton is final and set by constructor
    public void setTekton(Tekton t) {
        // This method might be problematic if onTekton is final.
        // If it's not final, ensure logic is sound when changing the associated Tekton.
        // For now, assuming onTekton is effectively final after construction via Tekton.
        // System.out.println("Mushroom manager ID " + this.ID + ": setTekton called. This is unusual.");
        // this.onTekton = t;
    }


    public boolean hasMushroomBody() { return mushroomBody != null; }
    public MushroomBody getMushroomBodyNoPrint() { return mushroomBody; }
    public int getIDNoPrint() { return ID; }
    public int getHowOldNoPrint() { return howOld; }


    public MushroomBody growBody(Tekton locationTekton, Mushroomer ownerPlayer) {
        if (this.onTekton != locationTekton) {
            System.err.println("Mushroom.growBody ERROR: Called on Mushroom manager of Tekton " +
                    this.onTekton.getIDNoPrint() + " but for location " + locationTekton.getIDNoPrint());
            return null;
        }
        if (this.mushroomBody != null) {
            System.out.println("Mushroom.growBody: Body already exists on Tekton " + onTekton.getIDNoPrint() + " for this mushroom manager.");
            return null;
        }
        if (!onTekton.canGrow()) {
            System.out.println("Mushroom.growBody: Tekton " + onTekton.getIDNoPrint() + " (type: " + onTekton.getClass().getSimpleName() + ") cannot grow a body.");
            return null;
        }

        // Assuming MushroomBody constructor: MushroomBody(Tekton onT, Mushroomer owner, int id)
        // A better ID generation for game entities is needed than just list size.
        int newBodyID = 1; // Placeholder - Needs a global unique ID generator for bodies
        if (ownerPlayer.getMushroomBodies() != null) {
            newBodyID = ownerPlayer.getMushroomBodies().size() + 1 + (this.ID * 1000); // Make it somewhat unique
        }

        MushroomBody newBody = new MushroomBody(locationTekton, ownerPlayer, newBodyID);
        this.mushroomBody = newBody;
        ownerPlayer.addMushroomBody(newBody); // Player keeps track of their bodies

        System.out.println("Mushroom: Successfully grew body for " + ownerPlayer.getName() + " on Tekton " + onTekton.getIDNoPrint());
        this.howOld = 0; // Reset age for new body
        return newBody;
    }

    public void removeMushroomBody() {
        if (this.mushroomBody != null) {
            Mushroomer owner = this.mushroomBody.getOwner();
            if (owner != null) {
                owner.removeMushroomBody(this.mushroomBody);
            }
            System.out.println("Mushroom: Body of " + (owner != null ? owner.getName() : "unknown owner") +
                    " removed from Tekton " + onTekton.getIDNoPrint());
            this.mushroomBody = null;
        }
    }

    public List<Spore> getSporesNoPrint() { return new ArrayList<>(sporesOnTekton); } // Return copy
    public List<Spore> getSpores() { return new ArrayList<>(sporesOnTekton); }

    public void addSpore(Spore spore) {
        if (spore != null && !this.sporesOnTekton.contains(spore)) {
            this.sporesOnTekton.add(spore);
            if (spore.getTektonNoPrint() != this.onTekton) {
                spore.setTekton(this.onTekton); // Ensure spore is on this Tekton
            }
            // System.out.println("Mushroom on Tekton " + onTekton.getIDNoPrint() + ": Spore " + spore.getIDNoPrint() + " added.");
        }
    }
    public void removeSpore(Spore spore) {
        boolean removed = this.sporesOnTekton.remove(spore);
        // if (removed) System.out.println("Mushroom on Tekton " + onTekton.getIDNoPrint() + ": Spore " + spore.getIDNoPrint() + " removed.");
    }


    public List<MushroomYarn> getMushroomYarnsNoPrint() { return new ArrayList<>(yarnsConnectedHere); } // Return copy
    public List<MushroomYarn> getMushroomYarns() { return new ArrayList<>(yarnsConnectedHere); }

    public void addMushroomYarn(MushroomYarn yarn) {
        if (yarn != null && !this.yarnsConnectedHere.contains(yarn)) {
            this.yarnsConnectedHere.add(yarn);
            // System.out.println("Mushroom on Tekton " + onTekton.getIDNoPrint() + ": Yarn " + yarn.getIDNoPrint() + " registered as connected.");
        }
    }
    public void removeMushroomYarn(MushroomYarn yarn) {
        boolean removed = this.yarnsConnectedHere.remove(yarn);
        // if (removed) System.out.println("Mushroom on Tekton " + onTekton.getIDNoPrint() + ": Yarn " + yarn.getIDNoPrint() + " unregistered.");
    }

    public List<MushroomYarn> Update() { // Game round update for this mushroom manager
        howOld++;
        List<MushroomYarn> yarnsThatDisappearedThisTurn = new ArrayList<>();
        for (MushroomYarn yarn : new ArrayList<>(yarnsConnectedHere)) { // Iterate copy
            if (yarn.Update()) { // Yarn.Update() returns true if it disappeared due to its timer
                yarnsThatDisappearedThisTurn.add(yarn);
            }
        }
        // Game.updateGameRound will handle the comprehensive removal of these yarns
        // from player lists and the other Tekton's mushroom manager.
        return yarnsThatDisappearedThisTurn;
    }

    // Method for spreading a specific type of spore FROM this Tekton (if it has a body owned by sporeOwner)
    // TO a target Tekton.
    public Spore spreadSporeTo(Tekton targetTekton, Mushroomer sporeOwner, Spore.SporeType typeToSpread, int sporeID) {
        if (!hasMushroomBody() || mushroomBody.getOwner() != sporeOwner) {
            System.out.println("Mushroom ("+this.ID+ ") on T"+onTekton.getIDNoPrint()+": No body or not owner's body to spread from for "+sporeOwner.getName());
            return null;
        }
        if (targetTekton == null) {
            System.err.println("Mushroom.spreadSporeTo: Target Tekton is null.");
            return null;
        }

        // Adjacency/range check for spreading (example: must be adjacent)
        // This rule can be more complex (e.g. depends on mushroom age or type)
        // boolean canReach = false;
        // if (onTekton.getAdjacentTektonsNoPrint().contains(targetTekton)) canReach = true;
        // if (howOld > 2) { // Older mushrooms might spread further
        //     for (Tekton adj : onTekton.getAdjacentTektonsNoPrint()) {
        //         if (adj.getAdjacentTektonsNoPrint().contains(targetTekton)) canReach = true;
        //     }
        // }
        // if (!canReach) {
        //    System.out.println("Mushroom on T"+onTekton.getIDNoPrint()+": Cannot spread spore to T" + targetTekton.getIDNoPrint() + " (out of range).");
        //    return null;
        // }


        Spore newSpore;
        // The spore's constructor should place it on the targetTekton and link to owner
        switch (typeToSpread) {
            case CUT_DISABLING: newSpore = new CutDisablingSpore(targetTekton, sporeOwner, sporeID); break;
            case PARALYZING:    newSpore = new ParalyzingSpore(targetTekton, sporeOwner, sporeID); break;
            case DUPLICATING:   newSpore = new DuplicatingSpore(targetTekton, sporeOwner, sporeID); break;
            case SLOWING:       newSpore = new SlowingSpore(targetTekton, sporeOwner, sporeID); break;
            case SPEEDING:      newSpore = new SpeedingSpore(targetTekton, sporeOwner, sporeID); break;
            default:
                System.err.println("Mushroom.spreadSporeTo: Unknown spore type " + typeToSpread);
                return null;
        }

        // The target Tekton's mushroom manager receives the spore
        Mushroom targetMushroomManager = targetTekton.getMushroomNoPrint();
        // Tekton constructor ensures mushroomManager is not null.
        targetMushroomManager.addSpore(newSpore); // This also sets spore.currentTekton

        // The owner player also tracks the spores they've created/own
        sporeOwner.addOwnedSpore(newSpore);

        System.out.println(sporeOwner.getName() + " spread " + typeToSpread + " spore (ID " + newSpore.getIDNoPrint() + ") from T" + onTekton.getIDNoPrint() + " to T" + targetTekton.getIDNoPrint());
        return newSpore;
    }
}