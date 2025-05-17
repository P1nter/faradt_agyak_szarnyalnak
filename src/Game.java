import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Game {
    private List<Tekton> tektons;
    private List<Player> players;
    private Player activePlayer;

    private final List<GameListener> listeners = new ArrayList<>();

    public enum GameActionType {
        NONE,
        INSECT_MOVE,
        INSECT_EAT_SPORE,
        INSECT_CUT_YARN,
        MUSHROOMER_SPREAD_SPORES, // Special: direct to target selection
        MUSHROOMER_GROW_YARN,
        MUSHROOMER_GROW_BODY,
        MUSHROOMER_EAT // Define what they eat
    }

    public enum InteractionStep {
        IDLE,
        SELECT_ACTOR,           // e.g., select an Insect or a Tekton to act from
        SELECT_FIRST_TARGET,    // e.g., select Tekton for Grow Body, or first Tekton for Grow Yarn
        SELECT_SECOND_TARGET,   // e.g., select second Tekton for Grow Yarn
        AWAITING_FINAL_TARGET   // Actor/first target selected, now highlight and await click on a final target
    }

    private GameActionType currentActionType = GameActionType.NONE;
    private InteractionStep currentInteractionStep = InteractionStep.IDLE;

    private Object selectedActor = null;       // Primary entity initiating action (e.g., Insect, or starting Tekton for Mushroomer)
    private Tekton firstTektonTarget = null; // For two-step Tekton targeting like Grow Yarn

    public Game() {
        tektons = new ArrayList<>();
        players = new ArrayList<>();
    }

    public Game(List<Tekton> tektons, List<Player> players) {
        this.tektons = tektons;
        this.players = players;
        if (players != null && !players.isEmpty()) {
            this.activePlayer = players.get(0);
            // Assuming Player class has setActionsRemaining() or similar
            // if (this.activePlayer != null) this.activePlayer.resetActions();
        }
    }

    public void addListener(GameListener l) { listeners.add(l); }
    public void removeListener(GameListener l) { listeners.remove(l); }
    private void fireMapChanged() { for (GameListener l : listeners) l.onMapChanged(); }
    private void fireStateChanged() { for (GameListener l : listeners) l.onStateChanged(); }

    // --- Accessors for UI State ---
    public GameActionType getCurrentActionType() { return currentActionType; }
    public InteractionStep getCurrentInteractionStep() { return currentInteractionStep; }
    public Object getSelectedActor() { return selectedActor; }
    public Tekton getFirstTektonTarget() { return firstTektonTarget; }

    // --- Action Initiation from ActionPanel ---
    public void startAction(GameActionType actionType) {
        if (currentInteractionStep != InteractionStep.IDLE) {
            System.out.println("Game: Cannot start new action while another is in progress. Cancel first.");
            return;
        }
        if (activePlayer == null /*|| activePlayer.getActionsRemaining() <= 0*/) {
            System.out.println("Game: No actions left or no active player.");
            return;
        }

        this.currentActionType = actionType;
        System.out.println("Game: Starting action " + actionType);

        switch (actionType) {
            case INSECT_MOVE:
            case INSECT_EAT_SPORE:
            case INSECT_CUT_YARN:
                this.currentInteractionStep = InteractionStep.SELECT_ACTOR;
                this.selectedActor = null; // Clear previous actor
                System.out.println("Game: Please select an Insect.");
                break;
            case MUSHROOMER_SPREAD_SPORES:
                this.currentInteractionStep = InteractionStep.AWAITING_FINAL_TARGET; // Special case
                this.selectedActor = activePlayer; // Mushroomer is the "actor"
                System.out.println("Game: Highlight valid Tektons for Spore Spread.");
                break;
            case MUSHROOMER_GROW_BODY:
                this.currentInteractionStep = InteractionStep.SELECT_FIRST_TARGET; // Select Tekton to grow on
                this.selectedActor = activePlayer;
                System.out.println("Game: Please select a Tekton to Grow Body on.");
                break;
            case MUSHROOMER_GROW_YARN:
                this.currentInteractionStep = InteractionStep.SELECT_FIRST_TARGET; // Select Tekton to grow FROM
                this.selectedActor = activePlayer;
                System.out.println("Game: Please select Tekton to Grow Yarn FROM.");
                break;
            case MUSHROOMER_EAT: // Define what mushroomers eat and how
                this.currentInteractionStep = InteractionStep.SELECT_FIRST_TARGET; // Example
                this.selectedActor = activePlayer;
                System.out.println("Game: Please select target for Mushroomer Eat action.");
                break;
            default:
                this.currentActionType = GameActionType.NONE;
                System.out.println("Game: Unknown action type.");
                break;
        }
        fireStateChanged();
    }

    public void cancelAction() {
        System.out.println("Game: Action " + currentActionType + " cancelled.");
        currentActionType = GameActionType.NONE;
        currentInteractionStep = InteractionStep.IDLE;
        selectedActor = null;
        firstTektonTarget = null;
        fireStateChanged();
    }

    // --- Click Handlers from MapPanel ---
    public void mapInsectClicked(Insect clickedInsect) {
        System.out.println("Game: Insect " + clickedInsect.getIDNoPrint() + " clicked.");
        if (activePlayer != clickedInsect.getOwnerNoPrint()) {
            System.out.println("Game: Cannot select other player's insect.");
            // Allow selecting own insect even if an enemy insect is clicked over it for info?
            if (currentInteractionStep == InteractionStep.IDLE) this.selectedActor = clickedInsect; // Show info
            fireStateChanged();
            return;
        }

        if (currentInteractionStep == InteractionStep.SELECT_ACTOR) {
            if (currentActionType == GameActionType.INSECT_MOVE ||
                    currentActionType == GameActionType.INSECT_EAT_SPORE ||
                    currentActionType == GameActionType.INSECT_CUT_YARN) {
                this.selectedActor = clickedInsect;
                this.currentInteractionStep = InteractionStep.AWAITING_FINAL_TARGET;
                System.out.println("Game: Insect " + clickedInsect.getIDNoPrint() + " selected for " + currentActionType + ". Highlight targets.");
            }
        } else if (currentInteractionStep == InteractionStep.IDLE) {
            this.selectedActor = clickedInsect; // Select for info display or context
            System.out.println("Game: Insect " + clickedInsect.getIDNoPrint() + " selected for info.");
        } else {
            // Clicked an insect when not expecting to select one as actor.
            // Could be an "eat insect" target for a Mushroomer, if that's a game mechanic.
            System.out.println("Game: Insect clicked in unexpected step: " + currentInteractionStep);
        }
        fireStateChanged();
    }

    public void mapTektonClicked(Tekton clickedTekton) {
        System.out.println("Game: Tekton " + clickedTekton.getIDNoPrint() + " clicked in step " + currentInteractionStep + " for action " + currentActionType);

        switch (currentInteractionStep) {
            case IDLE:
                // If a Mushroomer is active, clicking a Tekton could select it as an "actor" for some actions.
                if (activePlayer instanceof Mushroomer) {
                    // This is context-dependent. For now, just log.
                    // Potentially set selectedActor = clickedTekton if it's a valid starting point for some default action.
                } else {
                    // If an insect is on this tekton and belongs to player, select the insect?
                    List<Insect> playerInsectsOnTekton = clickedTekton.getInsectsNoPrint().stream()
                            .filter(i -> i.getOwnerNoPrint() == activePlayer).collect(Collectors.toList());
                    if(!playerInsectsOnTekton.isEmpty()){
                        mapInsectClicked(playerInsectsOnTekton.get(0)); // Select the first one
                        return;
                    }
                }
                System.out.println("Game: Tekton clicked while IDLE. Action depends on context.");
                break;

            case SELECT_FIRST_TARGET: // Mushroomer actions: Grow Body, Grow Yarn (FROM)
                if (currentActionType == GameActionType.MUSHROOMER_GROW_BODY) {
                    // TODO: Validate if clickedTekton is valid for growing body
                    System.out.println("Game: Attempting Grow Body on Tekton " + clickedTekton.getIDNoPrint());
                    // ((Mushroomer)activePlayer).growBody(clickedTekton);
                    // activePlayer.decrementActionsRemaining();
                    completeActionAndPotentiallyEndTurn();
                } else if (currentActionType == GameActionType.MUSHROOMER_GROW_YARN) {
                    // TODO: Validate if clickedTekton is valid to grow yarn FROM
                    this.firstTektonTarget = clickedTekton;
                    this.currentInteractionStep = InteractionStep.SELECT_SECOND_TARGET;
                    System.out.println("Game: Grow Yarn FROM " + clickedTekton.getIDNoPrint() + ". Select destination Tekton.");
                } else if (currentActionType == GameActionType.MUSHROOMER_EAT) {
                    // TODO: Validate if clickedTekton is valid target for Mushroomer eat
                    System.out.println("Game: Mushroomer eating at Tekton " + clickedTekton.getIDNoPrint());
                    // ((Mushroomer)activePlayer).eatAt(clickedTekton);
                    // activePlayer.decrementActionsRemaining();
                    completeActionAndPotentiallyEndTurn();
                }
                break;

            case SELECT_SECOND_TARGET: // Mushroomer: Grow Yarn (TO)
                if (currentActionType == GameActionType.MUSHROOMER_GROW_YARN && firstTektonTarget != null) {
                    // TODO: Validate if clickedTekton is valid to grow yarn TO, from firstTektonTarget
                    System.out.println("Game: Attempting Grow Yarn from " + firstTektonTarget.getIDNoPrint() + " TO " + clickedTekton.getIDNoPrint());
                    // ((Mushroomer)activePlayer).GrowYarn(firstTektonTarget, clickedTekton);
                    // activePlayer.decrementActionsRemaining();
                    completeActionAndPotentiallyEndTurn();
                }
                break;

            case AWAITING_FINAL_TARGET:
                if (selectedActor == null && currentActionType != GameActionType.MUSHROOMER_SPREAD_SPORES) {
                    System.out.println("Game: Actor not selected for action " + currentActionType);
                    cancelAction(); return;
                }
                performFinalTargetAction(clickedTekton, null);
                break;
            default:
                System.out.println("Game: Tekton clicked in unhandled step: " + currentInteractionStep);
                break;
        }
        fireStateChanged();
    }

    public void mapYarnClicked(MushroomYarn clickedYarn) {
        System.out.println("Game: Yarn (ID: " + clickedYarn.getIDNoPrint() + ") clicked in step " + currentInteractionStep + " for action " + currentActionType);
        if (currentInteractionStep == InteractionStep.AWAITING_FINAL_TARGET &&
                currentActionType == GameActionType.INSECT_CUT_YARN && selectedActor instanceof Insect) {
            // TODO: Validate if this yarn can be cut by the selectedInsect
            System.out.println("Game: Insect " + ((Insect)selectedActor).getIDNoPrint() + " attempts to CUT yarn " + clickedYarn.getIDNoPrint());
            // boolean success = ((Insect)selectedActor).cut(clickedYarn);
            // if (success) { activePlayer.decrementActionsRemaining(); completeActionAndPotentiallyEndTurn(); }
            // else { System.out.println("Cut failed."); cancelAction(); }
            completeActionAndPotentiallyEndTurn(); // Placeholder for successful cut
        } else if (currentInteractionStep == InteractionStep.AWAITING_FINAL_TARGET &&
                currentActionType == GameActionType.INSECT_MOVE && selectedActor instanceof Insect) {
            performFinalTargetAction(null, clickedYarn); // Pass yarn to the common handler
        } else {
            System.out.println("Game: Yarn clicked in unexpected state.");
        }
        fireStateChanged();
    }

    private void performFinalTargetAction(Tekton targetTekton, MushroomYarn targetYarn) {
        boolean actionTaken = false;
        switch (currentActionType) {
            case INSECT_MOVE:
                Insect movingInsect = (Insect) selectedActor;
                // TODO: Validate if targetYarn (or targetTekton if move is Tekton to Tekton) is a valid move
                if (targetYarn != null) { // Assuming move along yarn
                    System.out.println("Game: Insect " + movingInsect.getIDNoPrint() + " attempts to MOVE along Yarn " + targetYarn.getIDNoPrint());
                    // boolean success = movingInsect.move(targetYarn);
                    // if (success) actionTaken = true; else System.out.println("Move failed.");
                    actionTaken = true; // Placeholder
                } else if (targetTekton != null) { // Placeholder if move is Tekton to Tekton
                    System.out.println("Game: Insect " + movingInsect.getIDNoPrint() + " attempts to MOVE to Tekton " + targetTekton.getIDNoPrint());
                    // Tekton currentLoc = movingInsect.getTektonNoPrint();
                    // boolean success = movingInsect.move(findPath(currentLoc, targetTekton)); // You'd need pathfinding
                    // if (success) actionTaken = true; else System.out.println("Move failed.");
                    actionTaken = true; // Placeholder
                }
                break;
            case INSECT_EAT_SPORE:
                Insect eatingInsect = (Insect) selectedActor;
                // TODO: Spores are on Tektons. Target must be the Tekton the insect is on.
                if (targetTekton == eatingInsect.getTektonNoPrint()) {
                    System.out.println("Game: Insect " + eatingInsect.getIDNoPrint() + " EATS SPORE on Tekton " + targetTekton.getIDNoPrint());
                    // List<Spore> sporesOnTekton = targetTekton.getMushroomNoPrint().getSporesNoPrint();
                    // if(!sporesOnTekton.isEmpty()){ boolean success = eatingInsect.consumeSpore(sporesOnTekton.get(0)); if(success) actionTaken=true;}
                    actionTaken = true; // Placeholder
                } else {
                    System.out.println("Game: Insect can only eat spores on its current Tekton.");
                }
                break;
            // INSECT_CUT_YARN is handled by mapYarnClicked directly for now
            case MUSHROOMER_SPREAD_SPORES:
                // TODO: Validate if targetTekton is valid for spreading spores
                System.out.println("Game: Mushroomer SPREADS SPORES to Tekton " + targetTekton.getIDNoPrint());
                // ((Mushroomer)activePlayer).releaseSpore(targetTekton); // Assuming a method like this
                actionTaken = true; // Placeholder
                break;
            default:
                System.out.println("Game: Final target action for " + currentActionType + " not fully defined.");
                break;
        }

        if (actionTaken) {
            // activePlayer.decrementActionsRemaining();
            completeActionAndPotentiallyEndTurn();
        } else {
            cancelAction(); // If action failed or target was invalid
        }
    }


    private void completeActionAndPotentiallyEndTurn() {
        System.out.println("Game: Action " + currentActionType + " completed.");
        GameActionType completedAction = currentActionType; // Store before reset
        currentActionType = GameActionType.NONE;
        currentInteractionStep = InteractionStep.IDLE;
        // selectedActor = null; // Keep selectedActor for info panel, clear on new action or turn end
        firstTektonTarget = null;

        // TODO: Check activePlayer.getActionsRemaining(). If 0, or player chose to end turn:
        // For now, assume one action ends turn for simplicity in GUI hookup
        // if (activePlayer.getActionsRemaining() <= 0 || completedAction == GameActionType.MUSHROOMER_SPREAD_SPORES /* etc */) {
        //    nextPlayer();
        // } else {
        //    fireStateChanged(); // Update UI (e.g. actions remaining)
        // }
        fireStateChanged(); // Always fire state change to update UI
        // Decide if turn ends based on game rules / player choice / actions remaining
        // For this example, let's not auto-end turn here. Player uses "End Turn" button.
    }


    // --- Methods for MapPanel Highlighting ---
    public List<Object> getHighlightableEntities() {
        List<Object> highlights = new ArrayList<>();
        if (activePlayer == null) return highlights;

        switch (currentInteractionStep) {
            case SELECT_ACTOR:
                if (currentActionType == GameActionType.INSECT_MOVE || currentActionType == GameActionType.INSECT_EAT_SPORE || currentActionType == GameActionType.INSECT_CUT_YARN) {
                    if (activePlayer instanceof Insecter) {
                        highlights.addAll(((Insecter) activePlayer).getInsects());
                    }
                }
                break;
            case SELECT_FIRST_TARGET:
                if (currentActionType == GameActionType.MUSHROOMER_GROW_BODY) {
                    // highlights.addAll(getValidGrowBodyTargets((Mushroomer)activePlayer));
                    highlights.addAll(tektons); // Placeholder: all tektons
                } else if (currentActionType == GameActionType.MUSHROOMER_GROW_YARN) {
                    // highlights.addAll(getValidGrowYarnFromTargets((Mushroomer)activePlayer));
                    highlights.addAll(tektons); // Placeholder: all tektons
                }
                break;
            case SELECT_SECOND_TARGET:
                if (currentActionType == GameActionType.MUSHROOMER_GROW_YARN && firstTektonTarget != null) {
                    // highlights.addAll(getValidGrowYarnToTargets((Mushroomer)activePlayer, firstTektonTarget));
                    for(Tekton t : firstTektonTarget.getAdjacentTektonsNoPrint()){ // Placeholder
                        if(t != firstTektonTarget) highlights.add(t);
                    }
                }
                break;
            case AWAITING_FINAL_TARGET:
                if (selectedActor instanceof Insect) {
                    Insect insect = (Insect) selectedActor;
                    if (currentActionType == GameActionType.INSECT_MOVE) {
                        // highlights.addAll(getValidMoveTargetsForInsect(insect)); // Should return List<MushroomYarn> or List<Tekton>
                        // Placeholder: all adjacent yarns
                        for(Player p : players) {
                            if(p instanceof Mushroomer) {
                                for(MushroomYarn yarn : ((Mushroomer)p).getMushroomYarns()){
                                    if(!yarn.getIsCut() && (yarn.getTektonsNoPrint()[0] == insect.getTektonNoPrint() || yarn.getTektonsNoPrint()[1] == insect.getTektonNoPrint())){
                                        highlights.add(yarn);
                                    }
                                }
                            }
                        }
                    } else if (currentActionType == GameActionType.INSECT_EAT_SPORE) {
                        if (insect.getTektonNoPrint().getMushroomNoPrint() != null &&
                                !insect.getTektonNoPrint().getMushroomNoPrint().getSporesNoPrint().isEmpty()) {
                            highlights.add(insect.getTektonNoPrint()); // Highlight the Tekton insect is on
                        }
                    } else if (currentActionType == GameActionType.INSECT_CUT_YARN) {
                        // highlights.addAll(getValidCutYarnTargetsForInsect(insect));
                        for(Player p : players) { // Placeholder: all nearby yarns
                            if(p instanceof Mushroomer) {
                                for(MushroomYarn yarn : ((Mushroomer)p).getMushroomYarns()){
                                    if(!yarn.getIsCut() && (yarn.getTektonsNoPrint()[0] == insect.getTektonNoPrint() || yarn.getTektonsNoPrint()[1] == insect.getTektonNoPrint())){
                                        highlights.add(yarn);
                                    }
                                }
                            }
                        }
                    }
                } else if (currentActionType == GameActionType.MUSHROOMER_SPREAD_SPORES) {
                    // highlights.addAll(getValidSpreadSporesToTargets((Mushroomer)activePlayer));
                    for(Tekton t : tektons){ // Placeholder: some tektons
                        if(Math.random() > 0.5) highlights.add(t);
                    }
                }
                break;
        }
        return highlights;
    }


    // --- Standard Game Methods (simplified, ensure your actual logic is robust) ---
    public void setTektons(List<Tekton> tektons) { this.tektons = tektons; fireMapChanged(); }
    public void setPlayers(List<Player> players) {
        this.players = players;
        if (activePlayer == null && !players.isEmpty()) {
            activePlayer = players.get(0);
            // if(activePlayer != null) activePlayer.resetActions();
        }
        fireStateChanged();
    }
    public List<Tekton> getTektons() { return tektons; }
    public List<Player> getPlayers() { return players; }
    public Player getActivePlayer() { return activePlayer; }

    public void nextPlayer() {
        if (players.isEmpty()) return;
        int index = players.indexOf(activePlayer);
        activePlayer = players.get((index + 1) % players.size());
        // if(activePlayer != null) activePlayer.resetActions(); // Reset actions for the new player

        System.out.println("Next player: " + (activePlayer != null ? activePlayer.getName() : "None") + " Turn begins.");
        cancelAction(); // Resets interaction state for the new player
        this.selectedActor = null; // Clear selected actor from previous turn for info panel

        if (index == players.size() - 1) { // If wrapped around to the first player
            this.updateGameRound(); // Perform end-of-round updates
        }
        fireStateChanged(); // Also fires map change if needed via listeners
    }

    public void updateGameRound() { // Renamed from 'update' to be more specific
        System.out.println("Game.updateGameRound() called (effects, etc.)");
        // ... your existing update logic for insects, mushrooms, yarns ...
        for (Tekton tekton : tektons) {
            for(Insect insect : new ArrayList<>(tekton.getInsectsNoPrint())){ // Iterate copy
                insect.nextTurn();
            }
            if(tekton.getMushroomNoPrint() != null){
                List<MushroomYarn> toRemove = tekton.getMushroomNoPrint().Update();
                // TODO: Handle yarn removal properly (from player lists too)
            }
        }
        System.out.println("Game.updateGameRound() finished.");
    }

    // ... other methods like determineWinner, split, add/remove entities ...
    // Make sure they call fireMapChanged() or fireStateChanged() appropriately.
}