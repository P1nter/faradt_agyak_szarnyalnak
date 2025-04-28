import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a player in the game who specializes in managing and growing mushrooms.
 * <p>
 * This class extends the {@code Player} class and provides specific functionality
 * for a player focused on cultivating mushrooms. It maintains lists of mushroom bodies,
 * mushroom yarns, and spores owned by the player. It also includes methods to determine
 * where the player can grow new mushroom bodies and yarns, and to perform these growth actions.
 * </p>
 *
 * @see Player
 * @see MushroomBody
 * @see MushroomYarn
 * @see Spore
 * @see Tekton
 * @since 1.0
 */
public class Mushroomer extends Player {
    private List<MushroomBody> mushroomsBodies = new ArrayList<MushroomBody>();
    private List<MushroomYarn> mushroomYarns = new ArrayList<MushroomYarn>();
    private List<Spore> spores = new ArrayList<Spore>();

    /**
     * Gets the list of mushroom yarns owned by this player.
     *
     * @return A {@code List} containing the {@code MushroomYarn} objects owned by the player.
     */
    public List<MushroomYarn> getMushroomYarns() {
        System.out.println("Mushroomer.getMushroomYarns() called");
        System.out.println("Mushroomer.getMushroomYarns() returned");
        return mushroomYarns;
    }

    /**
     * Adds a mushroom yarn to this player's collection.
     *
     * @param mushroomYarn The {@code MushroomYarn} object to be added.
     */
    public void addMushroomYarn(MushroomYarn mushroomYarn) {
        System.out.println("Mushroomer.addMushroomYarn(MushroomYarn) called");
        mushroomYarns.add(mushroomYarn);
        System.out.println("Mushroomer.addMushroomYarn(MushroomYarn) returned");
    }

    /**
     * Gets the list of mushroom bodies owned by this player.
     *
     * @return A {@code List} containing the {@code MushroomBody} objects owned by the player.
     */
    public List<MushroomBody> getMushroomBodies() {
        System.out.println("Mushroomer.getMushrooms() called");
        System.out.println("Mushroomer.getMushrooms() returned");
        return mushroomsBodies;
    }

    /**
     * Adds a mushroom body to this player's collection.
     *
     * @param mushroomBody The {@code MushroomBody} object to be added.
     */
    public void addMushroomBody(MushroomBody mushroomBody) {
        System.out.println("Mushroomer.addMushroom(Mushroom) called");
        mushroomsBodies.add(mushroomBody);
        System.out.println("Mushroomer.addMushroom(Mushroom) returned");
    }

    /**
     * Constructs a new {@code Mushroomer} with the given name.
     *
     * @param name The name of the mushroom controlling player.
     */
    public Mushroomer(String name) {
        super(name);
        System.out.println("Mushroomer.Mushroomer(name) called");
        setPlayerType(PlayerType.MUSHROOMER);
        System.out.println("Mushroomer.Mushroomer(name) returned");
    }

    /**
     * Determines the list of Tektons where this player can grow new mushroom bodies.
     * <p>
     * A player can grow a mushroom body on a Tekton if a mushroom yarn owned by the player
     * is connected to that Tekton, and if the spores present on that Tekton's mushroom
     * are also in the player's collection of spores. The returned list contains unique Tektons.
     * </p>
     *
     * @return A {@code List} of {@code Tekton} objects where the player can grow mushroom bodies.
     */
    public void addYarn(MushroomYarn mushroomYarn){
        System.out.println("Mushroomer.addYarn(MushroomYarn) called");
        this.mushroomYarns.add(mushroomYarn);
        System.out.println("Mushroomer.addYarn(MushroomYarn) returned");
    }
    public List<Tekton> whereCanIGrowMushroomBodies() {
        System.out.println("Mushroomer.whereCanIGrowMushroomBodies() called");
        List<Tekton> tektonWhereICanGrow = new ArrayList<Tekton>();
        for (MushroomYarn mushroomYarn : mushroomYarns) {
            for (Tekton tekton : mushroomYarn.getTektons()) {
                for (Spore spore : tekton.getMushroom().getSpores()){
                    if (this.spores.contains (spore)) {
                        tektonWhereICanGrow.add(tekton);
                    }
                }
            }
        }
        Set<Tekton> uniqueTektons = new HashSet<>(tektonWhereICanGrow);
        tektonWhereICanGrow.clear();
        tektonWhereICanGrow.addAll(uniqueTektons);
        for (Tekton tekton : tektonWhereICanGrow) {
            System.out.println("Mushroomer.whereCanIGrowMushroomBodies() tekton: " + tekton);
        }
        System.out.println("Mushroomer.whereCanIGrowMushroomBodies() returned");
        return tektonWhereICanGrow;
    }

    /**
     * Attempts to grow a mushroom body on the specified Tekton.
     * <p>
     * Growth is only possible if the Tekton is in the list of locations where the player can grow
     * mushroom bodies (determined by {@link #whereCanIGrowMushroomBodies()}). If the growth is successful,
     * the new mushroom body is added to the player's collection. If the Tekton is a fast-growing Tekton,
     * its mushroom is updated immediately after body growth.
     * </p>
     *
     * @param tekton The {@code Tekton} on which to attempt to grow a mushroom body.
     */
    public void growBody(Tekton tekton){
        if (whereCanIGrowMushroomBodies().contains(tekton)) {
            System.out.println("Mushroomer.Grow(tekton) called");
            MushroomBody mushroomBody = tekton.getMushroom().growBody(tekton, this);
            if(mushroomBody != null && mushroomBody.getTektons().isFastTekton()){
                tekton.getMushroom().Update();
            }
            if (mushroomBody == null) {
                System.out.println("Mushroomer.Grow(tekton) returned: mushroom body is null");
                return;
            }
            this.addMushroomBody(mushroomBody);
            System.out.println("Mushroomer.Grow(tekton) returned");
        } else {
            System.out.println("Mushroomer.Grow(tekton) tekton is not in whereCanIGrowMushroomBodies()");
        }
    }

    /**
     * Determines the list of Tektons from which this player can grow new mushroom yarns.
     * <p>
     * A player can grow a yarn from a Tekton if the player owns a mushroom yarn connected to that Tekton
     * or if the player owns a mushroom body located on that Tekton. The returned list contains unique Tektons.
     * </p>
     *
     * @return A {@code List} of {@code Tekton} objects from which the player can grow mushroom yarns.
     */
    public List<Tekton> fromWhereCanIGrowYarns() {
        System.out.println("Mushroomer.callSpread(tekton) called");
        List<Tekton> whereHasYarns = new ArrayList<Tekton>();
        for (MushroomYarn mushroomYarns: this.mushroomYarns) {
            for(Tekton tekton: mushroomYarns.getTektons()){
                whereHasYarns.add(tekton);
            }
        }
        for(MushroomBody mushroombodies: this.mushroomsBodies){
            whereHasYarns.add(mushroombodies.getTektons());
        }
        Set<Tekton> uniqueTektons = new HashSet<>(whereHasYarns);
        whereHasYarns.clear();
        whereHasYarns.addAll(uniqueTektons);
        for (Tekton tekton: whereHasYarns){
            System.out.println("Mushroomer.callSpread(tekton) tekton: " + tekton);
        }
        System.out.println("Mushroomer.callSpread(tekton) returned");
        return whereHasYarns;
    }

    /**
     * Determines the list of adjacent Tektons where this player can grow new mushroom yarns.
     * <p>
     * A player can grow a yarn to an adjacent Tekton if that Tekton allows yarn growth
     * (determined by its {@code canGrowYarn()} method) and if the player has a connection
     * (either a mushroom yarn or a mushroom body) to the originating Tekton. The returned
     * list contains unique adjacent Tektons.
     * </p>
     *
     * @return A {@code List} of adjacent {@code Tekton} objects where the player can grow mushroom yarns.
     */
    public List<Tekton> whereCanIGrowYarns() {
        System.out.println("Mushroomer.whereCanIGrowYarns() called");
        List<Tekton> tektonWhereICanGrow = new ArrayList<Tekton>();
        for(Tekton tekton: fromWhereCanIGrowYarns()){
            for(Tekton tekton2: tekton.getAdjacentTektons()){
                if(tekton2.canGrowYarn()){
                    tektonWhereICanGrow.add(tekton2);
                }
            }
        }
        Set<Tekton> uniqueTektons = new HashSet<>(tektonWhereICanGrow);
        tektonWhereICanGrow.clear();
        tektonWhereICanGrow.addAll(uniqueTektons);
        for (Tekton tekton : tektonWhereICanGrow) {
            System.out.println("Mushroomer.whereCanIGrowYarns() tekton: " + tekton);
        }
        System.out.println("Mushroomer.whereCanIGrowYarns() returned");
        return tektonWhereICanGrow;
    }
    public void eatInsect(Insect insect){
        System.out.println("Mushroomer.eatInsect() called");
        // Implement logic to eat an insect
        if(insect.getEffects()[1] > 0){
            for (MushroomYarn mushroomYarn: mushroomYarns){
                if(mushroomYarn.getTektons()[0] == insect.getTekton() || mushroomYarn.getTektons()[1] == insect.getTekton()){
                    insect.getTekton().removeInsect(insect);
                    insect.getOwner().getInsects().remove(insect);
                }
            }
        }
        System.out.println("Mushroomer.eatInsect() returned");
    }

    /**
     * Determines the list of adjacent Tektons reachable from a specific Tekton where this player can grow new mushroom yarns.
     * <p>
     * This method checks the adjacent Tektons of the given Tekton and returns those that allow yarn growth
     * (determined by their {@code canGrowYarn()} method). The returned list contains unique adjacent Tektons.
     * </p>
     *
     * @param tekton The originating {@code Tekton} from which to check for growable yarns.
     * @return A {@code List} of adjacent {@code Tekton} objects where the player can grow mushroom yarns.
     */
    public List<Tekton> whereCanIGrowYarnsFromThisTekton(Tekton tekton) {
        System.out.println("Mushroomer.whereCanIGrowYarnsFromThisTekton(tekton) called");
        List<Tekton> tektonWhereICanGrow = new ArrayList<Tekton>();
        for (Tekton tekton1 : tekton.getAdjacentTektons()) {
            if (tekton1.canGrowYarn()) {
                tektonWhereICanGrow.add(tekton1);
            }
        }
        Set<Tekton> uniqueTektons = new HashSet<>(tektonWhereICanGrow);
        tektonWhereICanGrow.clear();
        tektonWhereICanGrow.addAll(uniqueTektons);
        for (Tekton tekton1 : tektonWhereICanGrow) {
            System.out.println("Mushroomer.whereCanIGrowYarnsFromThisTekton(tekton) tekton: " + tekton1);
        }
        System.out.println("Mushroomer.whereCanIGrowYarnsFromThisTekton(tekton) returned");
        return tektonWhereICanGrow;
    }

    /**
     * Attempts to grow a mushroom yarn between two specified Tektons.
     * <p>
     * Growth is only possible if the originating Tekton is one from which the player can grow yarns
     * (determined by {@link #fromWhereCanIGrowYarns()}) and if the destination Tekton is an adjacent
     * Tekton where a yarn can be grown from the originating Tekton
     * (determined by {@link #whereCanIGrowYarnsFromThisTekton(Tekton)}). If the growth is successful,
     * the new mushroom yarn is added to the player's collection.
     * </p>
     *
     * @param honnan The originating {@code Tekton} for the new mushroom yarn.
     * @param hova   The destination {@code Tekton} for the new mushroom yarn.
     */
    public void GrowYarn(Tekton honnan, Tekton hova){
        System.out.println("Mushroomer.GrowYarn() called");
        if(fromWhereCanIGrowYarns().contains(honnan) && whereCanIGrowYarnsFromThisTekton(honnan).contains(hova) ||
                fromWhereCanIGrowYarns().contains(hova) && whereCanIGrowYarnsFromThisTekton(hova).contains(honnan)){
            MushroomYarn newYarn = honnan.getMushroom().spread(honnan, hova);
            if (newYarn != null) {
                mushroomYarns.add(newYarn);
                honnan.getMushroom().getMushroomYarns().add(newYarn);
                hova.getMushroom().getMushroomYarns().add(newYarn);
            }
        }
        System.out.println("Mushroomer.GrowYarn() returned");
    }
    public void addSpore(Spore spore){
        System.out.println("Mushroomer.addSpore(Spore) called");
        this.spores.add(spore);
        System.out.println("Mushroomer.addSpore(Spore) returned");
    }
    public List<Spore> getSpores(){
        System.out.println("Mushroomer.getSpores() called");
        System.out.println("Mushroomer.getSpores() returned");
        return this.spores;
    }
    public void releaseSpore(MushroomBody mushroomBody, Spore spore, Tekton tekton){
        System.out.println("Mushroomer.releaseSpore() called");
        if (mushroomBody.getTektons().getMushroom().getHowOld() < 1)
            System.out.println("This mushroom body is too young to release spores");
        if (mushroomBody.getTektons().getMushroom().getHowOld() > 0 && mushroomBody.getTektons().getMushroom().getHowOld() < 3) {
            if (mushroomBody.getTektons().getAdjacentTektons().contains(tekton)){
                //logic to release spore
                spore.setTekton(tekton);
                this.spores.add(spore);
                //megkell nézni van e ott mushroom << legyen mindenhol mushroom alapértelmezetten
                if (tekton.getMushroom() != null) {
                    tekton.getMushroom().getSpores().add(spore);
                }
                System.out.println("Spore released successfully");
            } else {
                System.out.println("This mushroom body is not adjacent to the specified tekton");
            }
        }
        if (mushroomBody.getTektons().getMushroom().getHowOld() > 3){
            for (Tekton tekton1: mushroomBody.getTektons().getAdjacentTektons()) {
                if (mushroomBody.getTektons().getAdjacentTektons().contains(tekton) ||
                        tekton1.getAdjacentTektons().contains(tekton)) {
                    //logic to release spore
                    spore.setTekton(tekton);
                    this.spores.add(spore);
                    if (tekton.getMushroom() != null) {
                        tekton.getMushroom().getSpores().add(spore);
                    }
                    System.out.println("Spore released successfully");
                } else {
                    System.out.println("This mushroombody cant release spores there");
                }
            }
        }
        System.out.println("Mushroomer.releaseSpore() returned");
    }
}