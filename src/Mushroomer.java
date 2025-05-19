// Mushroomer.java
import java.util.*;
// import java.util.stream.Collectors; // If used

public class Mushroomer extends Player {
    private List<MushroomBody> mushroomBodies = new ArrayList<>();
    private List<MushroomYarn> mushroomYarns = new ArrayList<>();
    private List<Spore> sporesOwned = new ArrayList<>();

    public Mushroomer(String name) {
        super(name);
        setPlayerType(PlayerType.MUSHROOMER);
    }

    public void addMushroomBody(MushroomBody body) { /* ... same as before ... */
        if (body != null && !this.mushroomBodies.contains(body)) {
            this.mushroomBodies.add(body);
        }
    }
    public void removeMushroomBody(MushroomBody body) { this.mushroomBodies.remove(body); }
    public List<MushroomBody> getMushroomBodies() { return new ArrayList<>(mushroomBodies); }

    public void addMushroomYarn(MushroomYarn yarn) { /* ... same as before ... */
        if (yarn != null && !this.mushroomYarns.contains(yarn)) {
            this.mushroomYarns.add(yarn);
            if (yarn.getOwner() != this) yarn.setOwner(this);
        }
    }
    public void removeMushroomYarn(MushroomYarn yarn) { this.mushroomYarns.remove(yarn); }
    public List<MushroomYarn> getMushroomYarns() { return new ArrayList<>(mushroomYarns); }

    public void addOwnedSpore(Spore spore) { /* ... same as before ... */
        if (spore != null && !this.sporesOwned.contains(spore)) {
            this.sporesOwned.add(spore);
            if(spore.getOwner() != this) spore.setOwner(this);
        }
    }
    public void removeOwnedSpore(Spore spore) { this.sporesOwned.remove(spore); }
    public List<Spore> getSporesOwned() { return new ArrayList<>(sporesOwned); }
    @Deprecated public List<Spore> getSpores() { return getSporesOwned(); }
    @Deprecated public void addSpore(Spore spore) { addOwnedSpore(spore); }

    public boolean GrowYarn(Tekton fromTekton, Tekton toTekton) { /* ... same as before ... */
        System.out.println(getName() + " attempts to grow yarn from T" + fromTekton.getIDNoPrint() + " to T" + toTekton.getIDNoPrint());
        boolean canGrowFrom = false;
        for(MushroomBody mb : this.mushroomBodies) if(mb.getTektonNoPrint() == fromTekton) canGrowFrom = true;
        if(!canGrowFrom){
            for(MushroomYarn my : this.mushroomYarns) {
                Tekton[] ends = my.getTektonsNoPrint();
                if(ends[0] == fromTekton || ends[1] == fromTekton) { canGrowFrom = true; break; }
            }
        }
        if(!canGrowFrom) { System.out.println(" GrowYarn Fail: No influence on source Tekton " + fromTekton.getIDNoPrint()); return false; }
        if(!fromTekton.getAdjacentTektonsNoPrint().contains(toTekton)) { System.out.println(" GrowYarn Fail: Tektons not adjacent."); return false; }
        if(!toTekton.canGrowYarn()) { System.out.println(" GrowYarn Fail: Target Tekton " + toTekton.getIDNoPrint() + " cannot grow yarn."); return false; }
        for(MushroomYarn existingYarn : this.mushroomYarns){
            Tekton[] ends = existingYarn.getTektonsNoPrint();
            if((ends[0] == fromTekton && ends[1] == toTekton) || (ends[0] == toTekton && ends[1] == fromTekton)){
                System.out.println(" GrowYarn Fail: Yarn already exists."); return false;
            }
        }
        int nextYarnId = 1;
        if (!this.mushroomYarns.isEmpty()) nextYarnId = this.mushroomYarns.stream().mapToInt(MushroomYarn::getIDNoPrint).max().orElse(0) + 1;
        MushroomYarn newYarn = new MushroomYarn(fromTekton, toTekton, this, nextYarnId); // Constructor should set owner
        // this.addMushroomYarn(newYarn); // Constructor of MushroomYarn now adds to owner
        fromTekton.getMushroomNoPrint().addMushroomYarn(newYarn);
        toTekton.getMushroomNoPrint().addMushroomYarn(newYarn);
        System.out.println("Mushroomer " + getName() + ": Yarn grown successfully between T" + fromTekton.getIDNoPrint() + " and T" + toTekton.getIDNoPrint());
        return true;
    }

    // Corrected signature to match call from Game.java
    public List<Tekton> whereCanISpreadSpores(Tekton fromSourceBodyTekton, Spore.SporeType type, /* Potentially: , List<Tekton> allGameTektons */List<Tekton> tektons) {
        Set<Tekton> validTargets = new HashSet<>();
        List<Tekton> sourcesToConsider = new ArrayList<>();

        if (fromSourceBodyTekton != null &&
                fromSourceBodyTekton.getMushroomNoPrint() != null &&
                fromSourceBodyTekton.getMushroomNoPrint().hasMushroomBody() &&
                fromSourceBodyTekton.getMushroomNoPrint().getMushroomBodyNoPrint().getOwner() == this) {
            sourcesToConsider.add(fromSourceBodyTekton);
        } else { // If no specific source, consider all player's bodies (this part might be removed if SELECT_ACTOR step requires a body to be selected first)
            System.out.println(getName() + ": No specific source Tekton for spread, considering all owned bodies.");
            for (MushroomBody mb : this.mushroomBodies) {
                sourcesToConsider.add(mb.getTektonNoPrint());
            }
        }

        if(sourcesToConsider.isEmpty()){
            System.out.println(getName() + " has no suitable mushroom bodies to spread spores from for this action.");
            return new ArrayList<>();
        }

        for (Tekton source : sourcesToConsider) {
            Mushroom sourceMushroomManager = source.getMushroomNoPrint();
            if (sourceMushroomManager == null || !sourceMushroomManager.hasMushroomBody()) continue;

            // Rule: Adjacent Tektons are primary targets
            for (Tekton adj : source.getAdjacentTektonsNoPrint()) {
                validTargets.add(adj);
            }

            // Rule: If mushroom body is old enough (e.g., age > 1), can spread to Tektons 2 steps away
            if (sourceMushroomManager.getHowOldNoPrint() > 1) { // Example: age > 1 allows 2-step
                for (Tekton adj1 : source.getAdjacentTektonsNoPrint()) {
                    for (Tekton adj2 : adj1.getAdjacentTektonsNoPrint()) {
                        if (adj2 != source) { // Don't target the source Tekton itself
                            validTargets.add(adj2);
                        }
                    }
                }
            }
        }
        // Player cannot spread spores onto Tektons where they already have a body.
        for(MushroomBody mb : this.mushroomBodies){
            validTargets.remove(mb.getTektonNoPrint());
        }
        System.out.println(getName() + ": Found " + validTargets.size() + " valid targets for spreading " + type + " from considered sources.");
        return new ArrayList<>(validTargets);
    }


    public List<Tekton> whereCanIGrowMushroomBodies(List<Tekton> allGameTektons) { // Added allGameTektons if rule 2 needs it
        Set<Tekton> tektonWhereICanGrow = new HashSet<>();
        // Rule 1: Can grow on a Tekton connected by player's yarn, if that Tekton has a spore owned by player
        for (MushroomYarn my : mushroomYarns) {
            for (Tekton tekton : my.getTektonsNoPrint()) {
                if (tekton.canGrow() && (tekton.getMushroomNoPrint() == null || !tekton.getMushroomNoPrint().hasMushroomBody())) {
                    Mushroom manager = tekton.getMushroomNoPrint();
                    if (manager != null) {
                        for (Spore sporeOnTekton : manager.getSporesNoPrint()) {
                            if (sporeOnTekton.getOwner() == this) {
                                tektonWhereICanGrow.add(tekton);
                                break;
                            }
                        }
                    }
                }
            }
        }
        // Rule 2: Can grow on any Tekton that has a spore owned by the player,
        // if you want this rule, you need access to all tektons.
        if (allGameTektons != null) {
            for (Tekton tekton : allGameTektons ) {
                if (tekton.canGrow() && (tekton.getMushroomNoPrint() == null || !tekton.getMushroomNoPrint().hasMushroomBody())) {
                    Mushroom manager = tekton.getMushroomNoPrint();
                    if (manager != null) {
                        for (Spore sporeOnTekton : manager.getSporesNoPrint()) {
                            if (sporeOnTekton.getOwner() == this) {
                                tektonWhereICanGrow.add(tekton);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<>(tektonWhereICanGrow);
    }

    public List<Tekton> fromWhereCanIGrowYarns() { /* ... same as before ... */
        Set<Tekton> possibleSources = new HashSet<>();
        for(MushroomBody mb : this.mushroomBodies) possibleSources.add(mb.getTektonNoPrint());
        for(MushroomYarn my : this.mushroomYarns) {
            possibleSources.add(my.getTektonsNoPrint()[0]);
            possibleSources.add(my.getTektonsNoPrint()[1]);
        }
        return new ArrayList<>(possibleSources);
    }
    public List<Tekton> whereCanIGrowYarnsFromThisTekton(Tekton t) { /* ... same as before ... */
        List<Tekton> validTargets = new ArrayList<>();
        if (t == null) return validTargets;
        for(Tekton neighbor : t.getAdjacentTektonsNoPrint()){
            if(neighbor.canGrowYarn()) {
                boolean yarnExists = false;
                for(MushroomYarn my : this.mushroomYarns){
                    Tekton[] ends = my.getTektonsNoPrint();
                    if((ends[0] == t && ends[1] == neighbor) || (ends[0] == neighbor && ends[1] == t)){
                        yarnExists = true; break;
                    }
                }
                if(!yarnExists) validTargets.add(neighbor);
            }
        }
        return validTargets;
    }
    public void growBody(Tekton tekton){ /* ... same as before ... */
        if (tekton.canGrow() && (tekton.getMushroomNoPrint() == null || !tekton.getMushroomNoPrint().hasMushroomBody())) {
            MushroomBody newBody = tekton.getMushroomNoPrint().growBody(tekton, this);
            // Game.tryGrowBody will call completeAction which fires state change
        } else {
            System.out.println(getName() + ": growBody preconditions not met for T" + tekton.getIDNoPrint());
        }
    }

    public boolean eatInsect(Tekton targetTekton) {
        if (targetTekton == null) {
            System.out.println(getName() + ": Target Tekton is null. Cannot eat insect.");
            return false;
        }

        // Check if there is a paralyzed insect on the target Tekton.
        Insect targetInsect = targetTekton.getInsects().stream()
                .filter(insect -> insect.getEffectsNoPrint()[1] > 0)  // Check if the insect is paralyzed.
                .findFirst()
                .orElse(null);

        if (targetInsect == null) {
            System.out.println(getName() + ": No paralyzed insect found on Tekton " + targetTekton.getIDNoPrint());
            return false;
        }

        // Check if the Mushroomer has a yarn on the target Tekton.
        boolean hasYarnOnTekton = mushroomYarns.stream()
                .anyMatch(yarn -> Arrays.asList(yarn.getTektons()).contains(targetTekton));

        if (!hasYarnOnTekton) {
            System.out.println(getName() + ": No yarn on Tekton " + targetTekton.getIDNoPrint() + ". Cannot eat insect.");
            return false;
        }

        // Eat the insect (remove it from the game).
        targetInsect.disappear();
        System.out.println(getName() + ": Successfully ate insect ID " + targetInsect.getIDNoPrint() + " on Tekton " + targetTekton.getIDNoPrint());
        return true;
    }

    public void addYarn(MushroomYarn my) { addMushroomYarn(my); } // Alias from diagram
    public void releaseSpore(MushroomBody fromBody, Spore.SporeType type, Tekton toTekton){ /* ... same as before ... */
        if (fromBody == null || fromBody.getOwner() != this || toTekton == null) { return; }
        Tekton sourceTekton = fromBody.getTektonNoPrint();
        Mushroom sourceMushroomManager = sourceTekton.getMushroomNoPrint();
        int nextSporeId = this.sporesOwned.size() + 1;
        sourceMushroomManager.spreadSporeTo(toTekton, this, type, nextSporeId);
    }
}