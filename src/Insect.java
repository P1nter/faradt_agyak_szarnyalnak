/**
 * Represents an insect within the game world, capable of movement, interaction, and being affected by spores.
 * <p>
 * Each insect has a score, a number of actions it can perform in a turn, and a current location represented by a {@code Tekton}.
 * Insects can be owned by an {@code Insecter} (likely a player or AI). They can also be affected by various spores,
 * leading to temporary status effects such as being unable to cut, being paralyzed, moving faster, or moving slower.
 * </p>
 *
 * @see Tekton
 * @see Insecter
 * @see Spore
 * @see MushroomYarn
 * @since 1.0
 */
public class Insect {
    private int ID;
    private int score;
    //how many actions can the insect make in a turn
    private int action;
    private Tekton tekton;

    private Insecter owner;

    /**
     * Gets the owner of this insect.
     *
     * @return The {@code Insecter} that owns this insect.
     */
    public Insecter getOwner() {
        return owner;
    }

    /**
     * Sets the owner of this insect.
     *
     * @param owner The {@code Insecter} to set as the owner of this insect.
     */
    public void setOwner(Insecter owner) {
        this.owner = owner;
    }

    /**
     * An array representing the current status effects on the insect.
     * The indices correspond to:
     * <ul>
     * <li>0: cutDisabled (number of turns remaining)</li>
     * <li>1: paralyzed (number of turns remaining)</li>
     * <li>2: spedUp (number of turns remaining)</li>
     * <li>3: slowedDown (number of turns remaining)</li>
     * </ul>
     */
    private int[] effects = {0, 0, 0, 0};

    /**
     * Constructs a new {@code Insect} located on the specified {@code Tekton}.
     * <p>
     * Initializes the insect with a score of 0, 3 actions per turn, and sets its initial
     * location to the provided {@code Tekton}.
     * </p>
     *
     * @param tekton The initial {@code Tekton} where this insect is located.
     */
    public Insect(Tekton tekton, Insecter insecter, int ID) {
        System.out.println("Insect.Insect() called");
        this.ID = ID;
        this.score = 0;
        this.action = 3;
        this.tekton = tekton;
        tekton.addNewInsect(this);
        insecter.addInsect(this);
        System.out.println("Insect.Insect() returned");
    }
    public Insect(Tekton tekton, Insecter insecter) {
        System.out.println("Insect.Insect() called");
        this.score = 0;
        this.ID = 0;
        this.action = 3;
        this.tekton = tekton;
        System.out.println("Insect.Insect() returned");
    }

    public int getID(){
        System.out.println("Insect.getID() called");
        try {
            return ID;
        } catch(NullPointerException e){
            System.out.println("ID is null");
        }
        System.out.println("Insect.getID() returned ");
        return 0;

    }

    /**
     * Gets the current status effects applied to this insect.
     *
     * @return An integer array representing the active effects and their remaining durations.
     * The indices correspond to cutDisabled, paralyzed, spedUp, and slowedDown respectively.
     */
    public int[] getEffects() {
        return effects;
    }

    /**
     * Gets the current score of this insect.
     *
     * @return The current score of the insect.
     */
    public int getScore() {
        System.out.println("Insect.getScore() called");
        System.out.println("Insect.getScore() returned");
        return score;
    }

    /**
     * Gets the number of actions this insect can perform in the current turn.
     *
     * @return The number of remaining actions for this insect.
     */
    public int getAction(){
        System.out.println("Insect.getAction() called");
        System.out.println("Insect.getAction() returned");
        return action;
    }

    /**
     * Sets the current {@code Tekton} where this insect is located.
     *
     * @param tekton The new {@code Tekton} for this insect.
     */
    public void setTekton(Tekton tekton){
        System.out.println("Insect.setTekton() called");
        this.tekton = tekton;
        System.out.println("Insect.setTekton() returned");
    }

    /**
     * Gets the current {@code Tekton} where this insect is located.
     *
     * @return The current {@code Tekton} of this insect.
     */
    public Tekton getTekton(){
        System.out.println("Insect.getTekton() called");
        System.out.println("Insect.getTekton() returned");
        return tekton;
    }

    /**
     * Applies the effect of a cut-disabling spore to this insect.
     * <p>
     * Sets the 'cutDisabled' effect for 3 turns, preventing the insect from cutting yarn.
     * </p>
     */
    public void effectedByCutDisablingSpore() {
        System.out.println("Insect.effectedByCutDisablingSpore() called");
        this.effects[0] = 3;
        System.out.println("Insect.effectedByCutDisablingSpore() returned");
    }

    /**
     * Applies the effect of a paralyzing spore to this insect.
     * <p>
     * Sets the 'paralyzed' effect for 3 turns, reducing the insect's available actions to 0.
     * </p>
     */
    public void effectedByParalyzingSpore() {
        System.out.println("Insect.effectedByParalyzingSpore() called");
        this.action = 0;
        this.effects[1] = 3;
        System.out.println("Insect.effectedByParalyzingSpore() returned");
    }

    /**
     * Applies the effect of a speeding spore to this insect.
     * <p>
     * Doubles the insect's available actions for 3 turns.
     * </p>
     */
    public void effectedBySpeedingSpore() {
        System.out.println("Insect.effectedBySpeedingSpore() called");
        this.action = this.action *2;
        this.effects[2] = 3;
        System.out.println("Insect.effectedBySpeedingSpore() returned");
    }

    /**
     * Applies the effect of a slowing spore to this insect.
     * <p>
     * Halves the insect's available actions for 3 turns.
     * </p>
     */
    public void effectedBySlowingSpore() {
        System.out.println("Insect.effectedBySlowingSpore() called");
        this.action = this.action /2;
        this.effects[3] = 3;
        System.out.println("Insect.effectedBySlowingSpore() returned");
    }

    /**
     * Applies the effect of a duplicating spore to this insect.
     * <p>
     * Creates a new insect of the same type on the same {@code Tekton}, owned by the same {@code Insecter}.
     * The new insect is also added to the owner's list of insects.
     * </p>
     */
    public void effectedByDuplicatingSpore() {
        System.out.println("Insect.effectByDuplicatingSpore() called");
        Insect insect = new Insect(this.tekton, this.owner);
        tekton.addNewInsect(insect);
        insect.setOwner(this.owner);
        insect.getOwner().addInsect(insect);
        System.out.println("Insect.effectByDuplicatingSpore() returned");
    }

    /**
     * Updates the insect's state at the beginning of a new turn.
     * <p>
     * This method manages the duration of active status effects. If the 'paralyzed' effect
     * is active and has one turn remaining, the insect's action points are reset to 3.
     * Similarly, if 'spedUp' or 'slowedDown' have one turn remaining, the action points
     * are reverted to their base value (before the effect was applied). Finally, it decrements
     * the duration of all active effects.
     * </p>
     */
    public void nextTurn(){
        System.out.println("Insect.nextTurn() called");
        if(effects[1]==1){
            this.action=3;
        }
        if(effects[2]==1){
            this.action = this.action/2;
        }
        if(effects[3]==1){
            this.action = this.action * 2;
        }

        for (int i = 0; i < effects.length; i++) {
            if(effects[i] > 0) {
                effects[i]--;
            }
        }
        System.out.println("Insect.nextTurn() returned");
    }

    /**
     * Sets the number of actions this insect has for the current turn.
     *
     * @param action The new number of actions for this insect.
     */
    public void setAction(int action) {
        System.out.println("Insect.setSpeed(int) called");
        this.action = action;
        System.out.println("Insect.setSpeed(int) returned");
    }

    /**
     * Attempts to move this insect along a given {@code MushroomYarn} path.
     * <p>
     * The move is successful only if the insect has remaining actions, is not paralyzed,
     * and the target {@code Tekton} is connected to the insect's current {@code Tekton}
     * via the provided {@code MushroomYarn} which is not cut. Upon successful movement,
     * the insect's current {@code Tekton} is updated, and one action point is consumed.
     * </p>
     *
     * @param path The {@code MushroomYarn} representing the path to move along.
     * @return {@code true} if the move was successful, {@code false} otherwise.
     */
    public boolean move(MushroomYarn path) {
        System.out.println("Insect.move(path) called");
        if(action == 0 || effects[1]!=0) return false;
        if(path.getTektons()[1] != this.getTekton() && path.getTektons()[0] != this.getTekton()) {
            System.out.println("Insect.move(path) returned");
            return false;
        }
        if(path.getIsCut()){
            System.out.println("Insect.move(path) returned");
            return false;
        }
        if(path.getTektons()[0] == this.getTekton()) {
            this.tekton = path.getTektons()[1];
            path.getTektons()[0].getInsects().remove(this);
            path.getTektons()[1].getInsects().add(this);
        } else {
            this.tekton = path.getTektons()[0];
            path.getTektons()[1].getInsects().remove(this);
            path.getTektons()[0].getInsects().add(this);
        }
        System.out.println("Insect.move(path) returned");

        action--;
        return true;
    }

    /**
     * Attempts to consume a {@code Spore}.
     * <p>
     * If the insect has remaining actions and is not paralyzed, it consumes the spore,
     * triggering the spore's effect on the insect. Consuming a spore costs one action point.
     * </p>
     *
     * @param spore The {@code Spore} to be consumed.
     * @return {@code true} if the spore was consumed successfully, {@code false} otherwise.
     */
    public boolean consumeSpore(Spore spore) {
        System.out.println("Insect.consumeSpore() called");
        if(action == 0 || effects[1]!=0){
            System.out.println("Insect.cut(yarn) returned");
            return false;
        }
        spore.affectInsect(this);
        this.tekton.getMushroom().getSpores().remove(spore);
        spore.getOwner().getSpores().remove(spore);


        System.out.println("Insect.consumeSpore() returned");
        action--;
        return true;
    }

    /**
     * Attempts to cut a {@code MushroomYarn}.
     * <p>
     * If the insect has remaining actions, is not paralyzed, and is not affected by a
     * cut-disabling spore, it can cut the specified yarn. Cutting a yarn costs one action point.
     * </p>
     *
     * @param yarn The {@code MushroomYarn} to be cut.
     * @return {@code true} if the yarn was cut successfully, {@code false} otherwise.
     */
    public boolean cut(MushroomYarn yarn) {
        System.out.println("Insect.cut(yarn) called");
        if(action == 0 || effects[1]!=0 || effects[0]!=0) {
            System.out.println("Insect.cut(yarn) returned");
            return false;
        }
        if(!yarn.cut()) {
            System.out.println("Insect.cut(yarn) returned false");
            return false;
        }
        System.out.println("Insect.cut(yarn) returned true");
        action--;
        return true;
    }
    public void disappear(){
        System.out.println("Insect.disappear() called");
        this.tekton.removeInsect(this);
        this.getOwner().removeInsect(this);
        this.tekton = null;
        System.out.println("Insect.disappear() returned");
    }
    //write a version for each getter where it doesnt sistem.out
    public int getScoreNoPrint() {
        return score;
    }
    public int getActionNoPrint(){
        return action;
    }
    public int getIDNoPrint(){
        return ID;
    }
    public int[] getEffectsNoPrint() {
        return effects;
    }
    public Tekton getTektonNoPrint(){
        return tekton;
    }
    public Insecter getOwnerNoPrint() {
        return owner;
    }
}