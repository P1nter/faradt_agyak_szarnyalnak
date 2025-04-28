import java.util.*;

/**
 * Represents a mushroom entity within the game, capable of growing a body,
 * spreading via mushroom yarns, and releasing spores.
 * <p>
 * Each mushroom can have an optional {@code MushroomBody}, a collection of
 * {@code MushroomYarn} connecting it to other Tektons, and a set of {@code Spore}
 * it can release. The mushroom is also associated with a specific {@code Tekton}
 * and has an age that increments each game update.
 * </p>
 *
 * @see MushroomBody
 * @see MushroomYarn
 * @see Spore
 * @see Tekton
 * @since 1.0
 */
public class Mushroom {
    private int ID;
    private MushroomBody mushroomBody = null;
    private List<MushroomYarn> mushroomYarns = new ArrayList<>();
    private List<Spore> spores = new ArrayList<>();
    private Tekton tekton = null;
    int howOld = 0;

    /**
     * Constructs a new {@code Mushroom} instance.
     * <p>
     * Initializes the mushroom with no body, an empty list of mushroom yarns,
     * an empty list of spores, and an initial age of 0.
     * </p>
     */
    public Mushroom() {
        System.out.println("Mushroom.Mushroom() called");
        this.mushroomBody = null;
        this.ID = 0;
        mushroomYarns = new ArrayList<>();
        spores = new ArrayList<>();
        howOld = 0;
        System.out.println("Mushroom.Mushroom() returned");
    }
    public Mushroom(int ID) {
        System.out.println("Mushroom.Mushroom() called");
        this.mushroomBody = null;
        this.ID = ID;
        mushroomYarns = new ArrayList<>();
        spores = new ArrayList<>();
        howOld = 0;
        System.out.println("Mushroom.Mushroom() returned");
    }

    /**
     * Checks if a mushroom yarn exists between two specified Tektons connected to this mushroom.
     *
     * @param tekton1 The first {@code Tekton} to check the connection for.
     * @param tekton2 The second {@code Tekton} to check the connection for.
     * @return {@code true} if a mushroom yarn connects the two Tektons, {@code false} otherwise.
     */
    public boolean isThereMushroomYarn(Tekton tekton1, Tekton tekton2) {
        System.out.println("Mushroom.isThereMushroomYarn() called");
        boolean result = false;
        for (MushroomYarn mushroomYarn : mushroomYarns) {
            Tekton[] tektons = mushroomYarn.getTektons();
            if ((tektons[0] == tekton1 && tektons[1] == tekton2) || (tektons[0] == tekton2 && tektons[1] == tekton1)) {
                result = true;
                break;
            }
        }
        System.out.println("Mushroom.isThereMushroomYarn() returned "+result);
        return result;
    }

    /**
     * Increments the age of this mushroom by one.
     */
    public void updateAge() {
        System.out.println("Mushroom.updateAge() called");
        howOld++;
        System.out.println("Mushroom.updateAge() returned");
    }

    /**
     * Instructs the mushroom body to release a spore.
     *
     * @param body The {@code MushroomBody} to instruct for spore release.
     */
    public void instructSporeRelease(MushroomBody body) {
        System.out.println("Mushroom.instructSporeRelease() called");
        if (howOld > 0 && howOld < 2){

        }
        System.out.println("Mushroom.instructSporeRelease() returned");
    }

    public int getHowOld() {
        System.out.println("Mushroom.getHowOld() called");
        System.out.println("Mushroom.getHowOld() returned int");
        return howOld;
    }
    /**
     * Updates the state of the mushroom for the current game turn.
     * <p>
     * This method iterates through the mushroom's yarns and updates them. If a yarn's
     * update returns true (indicating it should be removed), it is removed from the list.
     * The mushroom's age is also incremented.
     * </p>
     */
    public void Update() {
        System.out.println("Mushroom.Update() called");
        Iterator<MushroomYarn> iterator = mushroomYarns.iterator();
        while (iterator.hasNext()) {
            MushroomYarn mushroomYarn = iterator.next();
            if (mushroomYarn.Update()) {
                iterator.remove();
            }
        }
        updateAge();
        System.out.println("Mushroom.Update() returned");
    }

    /**
     * Spreads a new mushroom yarn from one Tekton to another.
     * <p>
     * Creates a new {@code MushroomYarn} connecting the two specified Tektons.
     * If either of the Tektons is marked as disappearing, the new yarn's time
     * until disappearance is set to 5.
     * </p>
     *
     * @param honnan The starting {@code Tekton} for the new mushroom yarn.
     * @param hova   The destination {@code Tekton} for the new mushroom yarn.
     * @return The newly created {@code MushroomYarn}.
     */
    public MushroomYarn spread(Tekton honnan, Tekton hova) {
        System.out.println("Mushroom.spread() called");
        //grows a mushroomyarn
        MushroomYarn mushroomYarn = new MushroomYarn(honnan, hova);
        if(hova.isDisappearing() || honnan.isDisappearing()){
            mushroomYarn.setTimeBack(5);
        }
        this.mushroomYarns.add(mushroomYarn);
        System.out.println("Mushroom.spread() returned");
        return mushroomYarn;
    }

    /**
     * Grows a mushroom body on the associated Tekton.
     * <p>
     * Checks if a mushroom body already exists and if the Tekton allows growth.
     * If both conditions are met, a new {@code MushroomBody} is created and associated
     * with the Tekton.
     * </p>
     *
     * @param tekton The {@code Tekton} on which to grow the mushroom body.
     * @return The newly created {@code MushroomBody}, or {@code null} if a body already exists
     * or the Tekton does not allow growth.
     */
    public MushroomBody growBody(Tekton tekton,Mushroomer mushroomer) {
        System.out.println("Mushroom.growBody() called");
        //check if tekton allows
        if (this.mushroomBody != null) {
            System.out.println("Mushroom.growBody() returned: there is already a mushroom body");
            return null;
        }
        if (tekton.canGrow()) {
            System.out.println("Mushroom.growBody() tekton.canGrow() returned");
            //check if tekton has spore and mushroomyarn
            mushroomBody = new MushroomBody(tekton, mushroomer);
        } else {
            System.out.println("Mushroom.growBody() tekton.canGrow() returned");
            return null;
        }
        System.out.println("Mushroom.growBody() returned");
        return mushroomBody;
    }

    /**
     * Destroys the mushroom body associated with this mushroom.
     *
     * @param body The {@code MushroomBody} to destroy.
     */
    public void destroyMushroomBody (MushroomBody body){
        System.out.println("Mushroom.destroyMushroomBody() called");
        if (mushroomBody != null) {
            mushroomBody = null;
            System.out.println("Mushroom.destroyMushroomBody() returned");
            return;
        }
        System.out.println("Mushroom.getScore() returned");
    }

    /**
     * Checks if this mushroom currently has a mushroom body.
     *
     * @return {@code true} if a mushroom body exists, {@code false} otherwise.
     */
    public boolean hasMushroomBody () {
        System.out.println("Mushroom.hasMushroomBody() called");
        boolean result = mushroomBody != null;
        System.out.println("Mushroom.hasMushroomBody() returned boolean");
        return result;
    }

    /**
     * Gets the list of spores associated with this mushroom.
     *
     * @return A {@code List} containing the {@code Spore} objects.
     */
    public List<Spore> getSpores() {
        System.out.println("Mushroom.getSpores() called");
        System.out.println("Mushroom.getSpores() returned");
        return spores;
    }

    /**
     * Sets the mushroom body for this mushroom.
     *
     * @param mushroomBody The {@code MushroomBody} to associate with this mushroom.
     */
    public void setMushroomBody(MushroomBody mushroomBody) {
        System.out.println("Mushroom.setMushroomBody(MushroomBody) called");
        this.mushroomBody = mushroomBody;
        System.out.println("Mushroom.setMushroomBody(MushroomBody) returned");
    }

    /**
     * Checks if this mushroom has any mushroom yarns connected to it.
     *
     * @return {@code true} if there are one or more mushroom yarns, {@code false} otherwise.
     */
    public boolean hasYarns() {
        System.out.println("Mushroom.hasYarns() called");
        boolean result = mushroomYarns.size() > 0;
        System.out.println("Mushroom.hasYarns() returned");
        return result;
    }

    /**
     * Removes the mushroom body from this mushroom.
     */
    public void removeMushroomBody(){
        System.out.println("Mushroom.removeMushroomBody() called");
        this.mushroomBody = null;
        System.out.println("Mushroom.removeMushroomBody() returned");
    }

    /**
     * Gets the list of mushroom yarns connected to this mushroom.
     *
     * @return A {@code List} containing the {@code MushroomYarn} objects.
     */
    public List<MushroomYarn> getMushroomYarns() {
        System.out.println("Mushroom.getMushroomYarns() called");
        System.out.println("Mushroom.getMushroomYarns() returned");
        return mushroomYarns;
    }

    /**
     * Gets the current mushroom body of this mushroom.
     *
     * @return The {@code MushroomBody} associated with this mushroom, or {@code null} if there is none.
     */
    public MushroomBody getMushroomBody() {
        System.out.println("Mushroom.getMushroomBody() called");
        System.out.println("Mushroom.getMushroomBody() returned");
        return mushroomBody;
    }
    public void addMushroomBody(MushroomBody mushroomBody){
        System.out.println("Mushroom.addMushroomBody(MushroomBody) called");
        this.mushroomBody = mushroomBody;
        System.out.println("Mushroom.addMushroomBody(MushroomBody) returned");
    }
    public void addSpore(Spore spore){
        System.out.println("Mushroom.addSpore(Spore) called");
        this.spores.add(spore);
        System.out.println("Mushroom.addSpore(Spore) returned");
    }
    public void addMushroomYarn(MushroomYarn mushroomYarn){
        System.out.println("Mushroom.addMushroomYarn(MushroomYarn) called");
        this.mushroomYarns.add(mushroomYarn);
        System.out.println("Mushroom.addMushroomYarn(MushroomYarn) returned");
    }
    public int getIDNoPrint() {
        return ID;
    }

    public MushroomBody getMushroomBodyNoPrint() {
        return mushroomBody;
    }

    public List<MushroomYarn> getMushroomYarnsNoPrint() {
        return mushroomYarns;
    }

    public List<Spore> getSporesNoPrint() {
        return spores;
    }

    public Tekton getTektonNoPrint() {
        return tekton;
    }

    public int getHowOldNoPrint() {
        return howOld;
    }
}