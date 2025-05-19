// Game.java
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Game {
    private List<Tekton> tektons;
    private List<Player> players;
    private Player activePlayer;
    private final List<GameListener> listeners = new ArrayList<>();

    private Spore.SporeType sporeTypeToSpreadContext = null;

    public enum GameActionType { NONE, INSECT_MOVE, INSECT_EAT_SPORE, INSECT_CUT_YARN, MUSHROOMER_SPREAD_SPORES, MUSHROOMER_GROW_YARN, MUSHROOMER_GROW_BODY, MUSHROOMER_EAT }
    public enum InteractionStep { IDLE, SELECT_ACTOR, SELECT_FIRST_TARGET, SELECT_SECOND_TARGET, AWAITING_FINAL_TARGET }

    private GameActionType currentActionType = GameActionType.NONE;
    private InteractionStep currentInteractionStep = InteractionStep.IDLE;
    private Object selectedActor = null;
    private Tekton firstTektonTarget = null;


    public Game(List<Tekton> tektons, List<Player> players) {
        this.tektons = tektons != null ? tektons : new ArrayList<>();
        this.players = players != null ? players : new ArrayList<>();
        if (!this.players.isEmpty()) {
            this.activePlayer = this.players.get(0);
            if (activePlayer != null) activePlayer.resetActionPoints();
        }
    }

    public void addListener(GameListener l) { if (!listeners.contains(l)) listeners.add(l); }
    public void removeListener(GameListener l) { listeners.remove(l); }
    private void fireMapChanged() { System.out.println("GAME_EVENT: Firing Map Changed"); for (GameListener l : new ArrayList<>(listeners)) l.onMapChanged(); }
    private void fireStateChanged() {
        System.out.println("GAME_EVENT: Firing State Changed (Player: " + (activePlayer!=null?activePlayer.getName():"N/A") +
                ", ActionsLeft: " + (activePlayer!=null?activePlayer.getAction():"N/A") +
                ", Mode=" + currentInteractionStep + ", ActionType=" + currentActionType +
                ", SelectedActor=" + (selectedActor != null ? selectedActor.getClass().getSimpleName() + "(" + getEntityID(selectedActor) + ")" : "null") + ")");
        for (GameListener l : new ArrayList<>(listeners)) l.onStateChanged();
    }

    public GameActionType getCurrentActionType() { return currentActionType; }
    public InteractionStep getCurrentInteractionStep() { return currentInteractionStep; }
    public Object getSelectedActor() { return selectedActor; }
    public Tekton getFirstTektonTarget() { return firstTektonTarget; }
    public Player getActivePlayer() { return activePlayer; }
    public List<Tekton> getTektons() { return tektons; }
    public List<Player> getPlayers() { return players; }

    public void setContextForSpreadAction(Spore.SporeType type) { this.sporeTypeToSpreadContext = type; }

    public void startAction(GameActionType actionType) {
        if (currentInteractionStep != InteractionStep.IDLE) {
            System.out.println("Game: Cannot start new action " + actionType + " while " + currentActionType + " ("+currentInteractionStep+") is in progress. Cancel first.");
            return;
        }
        if (activePlayer == null || !activePlayer.hasActionsLeft()) {
            System.out.println("Game: No actions left for " + (activePlayer != null ? activePlayer.getName() : "N/A") + " or no active player.");
            JOptionPane.showMessageDialog(null, "No actions remaining this turn!", "Out of Actions", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (actionType != GameActionType.MUSHROOMER_SPREAD_SPORES) this.sporeTypeToSpreadContext = null;
        this.currentActionType = actionType;
        this.selectedActor = null; this.firstTektonTarget = null; // Clear previous selections for a new action type
        System.out.println("Game: Starting action " + actionType + (actionType == GameActionType.MUSHROOMER_SPREAD_SPORES ? " with type " + sporeTypeToSpreadContext : ""));

        switch (actionType) {
            case INSECT_MOVE: case INSECT_EAT_SPORE: case INSECT_CUT_YARN:
                this.currentInteractionStep = InteractionStep.SELECT_ACTOR; System.out.println("  > Please select an Insect."); break;
            case MUSHROOMER_SPREAD_SPORES:
                if (sporeTypeToSpreadContext == null && activePlayer instanceof Mushroomer) {
                    this.currentActionType = GameActionType.NONE;
                    JOptionPane.showMessageDialog(null, "Select spore type from Action Panel first for Spread.", "Spore Spread", JOptionPane.INFORMATION_MESSAGE);
                    fireStateChanged(); return; // Fire state to update UI from aborted action start
                }
                this.currentInteractionStep = InteractionStep.SELECT_ACTOR; System.out.println("  > Please select one of your Mushroom Bodies (or its Tekton) to spread from."); break;
            case MUSHROOMER_GROW_BODY:
                this.currentInteractionStep = InteractionStep.SELECT_FIRST_TARGET; this.selectedActor = activePlayer; System.out.println("  > Please select a Tekton to Grow Body on."); break;
            case MUSHROOMER_GROW_YARN:
                this.currentInteractionStep = InteractionStep.SELECT_FIRST_TARGET; this.selectedActor = activePlayer; System.out.println("  > Please select Tekton to Grow Yarn FROM."); break;
            case MUSHROOMER_EAT:
                this.currentInteractionStep = InteractionStep.SELECT_FIRST_TARGET; this.selectedActor = activePlayer; System.out.println("  > Please select target for Mushroomer Eat action."); break;
            default: this.currentActionType = GameActionType.NONE; break;
        }
        fireStateChanged();
    }

    public void cancelAction() {
        String prevAction = currentActionType.toString(); InteractionStep prevStep = currentInteractionStep;
        currentActionType = GameActionType.NONE; currentInteractionStep = InteractionStep.IDLE;
        selectedActor = null; firstTektonTarget = null; sporeTypeToSpreadContext = null;
        System.out.println("Game: Action " + prevAction + " (was in step " + prevStep + ") cancelled.");
        fireStateChanged();
    }

    public void mapInsectClicked(Insect clickedInsect) {
        System.out.println("Game: mapInsectClicked - Insect " + getEntityID(clickedInsect) + ". Step: " + currentInteractionStep);
        if (currentInteractionStep == InteractionStep.IDLE) {
            this.selectedActor = clickedInsect;
            System.out.println("  > Insect " + getEntityID(clickedInsect) + " set as selectedActor (for info/context).");
        } else if (currentInteractionStep == InteractionStep.SELECT_ACTOR) {
            if (activePlayer == clickedInsect.getOwnerNoPrint()) {
                if (currentActionType == GameActionType.INSECT_MOVE || currentActionType == GameActionType.INSECT_EAT_SPORE || currentActionType == GameActionType.INSECT_CUT_YARN) {
                    this.selectedActor = clickedInsect;
                    this.currentInteractionStep = InteractionStep.AWAITING_FINAL_TARGET;
                    System.out.println("  > Insect " + getEntityID(clickedInsect) + " confirmed as actor for " + currentActionType + ". Awaiting final target.");
                } else { System.out.println("  > Clicked Insect, but current action " + currentActionType + " does not use Insect as primary actor.");}
            } else System.out.println("  > Cannot select other player's insect as actor.");
        } else { System.out.println("  > Insect clicked in unexpected step: " + currentInteractionStep + ". No action taken.");}
        fireStateChanged();
    }

    public void mapTektonClicked(Tekton clickedTekton) {
        System.out.println("Game: mapTektonClicked - Tekton " + getEntityID(clickedTekton) + ". Step: " + currentInteractionStep + ", Action: " + currentActionType);
        boolean actionLogicAttempted = false;
        boolean stepChanged = false;

        switch (currentInteractionStep) {
            case IDLE:
                this.selectedActor = clickedTekton;
                System.out.println("  > Tekton " + getEntityID(clickedTekton) + " set as selectedActor (context).");
                break;
            case SELECT_ACTOR:
                if (currentActionType == GameActionType.MUSHROOMER_SPREAD_SPORES && activePlayer instanceof Mushroomer) {
                    MushroomBody body = clickedTekton.getMushroomNoPrint().getMushroomBodyNoPrint();
                    if (body != null && body.getOwner() == activePlayer) {
                        this.selectedActor = body; this.currentInteractionStep = InteractionStep.AWAITING_FINAL_TARGET; stepChanged = true;
                        System.out.println("  > MushroomBody on T" + getEntityID(clickedTekton) + " selected as source for SPREAD. Awaiting target Tekton.");
                    } else System.out.println("  > Tekton " + getEntityID(clickedTekton) + " has no body owned by " + activePlayer.getName() + " for spread.");
                } else if (activePlayer instanceof Insecter && (currentActionType == GameActionType.INSECT_MOVE || currentActionType == GameActionType.INSECT_EAT_SPORE || currentActionType == GameActionType.INSECT_CUT_YARN)) {
                    boolean foundInsect = false;
                    for(Insect i : clickedTekton.getInsectsNoPrint()){ if(i.getOwnerNoPrint() == activePlayer) { mapInsectClicked(i); foundInsect = true; return; }} // mapInsectClicked fires state
                    if (!foundInsect) System.out.println("  > No insect of " + activePlayer.getName() + " on T" + getEntityID(clickedTekton) + " to select for " + currentActionType);
                } else { System.out.println("  > Clicked Tekton in SELECT_ACTOR step, but current action/player type mismatch. Expected specific actor type."); }
                break;
            case SELECT_FIRST_TARGET:
                if (activePlayer instanceof Mushroomer) {
                    if (currentActionType == GameActionType.MUSHROOMER_GROW_BODY) actionLogicAttempted = tryGrowBody(clickedTekton);
                    else if (currentActionType == GameActionType.MUSHROOMER_GROW_YARN) { this.firstTektonTarget = clickedTekton; this.currentInteractionStep = InteractionStep.SELECT_SECOND_TARGET; stepChanged = true; System.out.println("  > Grow Yarn FROM T" + getEntityID(clickedTekton) + ". Select destination.");}
                    else if (currentActionType == GameActionType.MUSHROOMER_EAT) actionLogicAttempted = tryMushroomerEat(clickedTekton);
                    else { System.out.println("  > Clicked Tekton in SELECT_FIRST_TARGET for unhandled Mushroomer action: " + currentActionType); }
                } else { System.out.println("  > Clicked Tekton in SELECT_FIRST_TARGET, but active player is not Mushroomer."); }
                break;
            case SELECT_SECOND_TARGET:
                if (currentActionType == GameActionType.MUSHROOMER_GROW_YARN && firstTektonTarget != null && activePlayer instanceof Mushroomer) {
                    actionLogicAttempted = tryGrowYarn(firstTektonTarget, clickedTekton);
                } else { System.out.println("  > Clicked Tekton in SELECT_SECOND_TARGET for unhandled action/state."); }
                break;
            case AWAITING_FINAL_TARGET:
                if (selectedActor == null) { System.out.println("  > No actor selected for AWAITING_FINAL_TARGET."); cancelAction(); return; }
                actionLogicAttempted = performFinalTargetAction(clickedTekton, null);
                break;
            default: System.out.println("  > Tekton clicked in unhandled step: " + currentInteractionStep); break;
        }
        if (stepChanged || !actionLogicAttempted) { // If only a step changed, or no model action was attempted (e.g. just context selection)
            fireStateChanged();
        }
    }

    public void mapYarnClicked(MushroomYarn clickedYarn) {
        System.out.println("Game: mapYarnClicked - Yarn " + getEntityID(clickedYarn) + ". Step: " + currentInteractionStep);
        boolean actionLogicAttempted = false;
        if (currentInteractionStep == InteractionStep.AWAITING_FINAL_TARGET && selectedActor instanceof Insect) {
            if (currentActionType == GameActionType.INSECT_CUT_YARN) actionLogicAttempted = tryCutYarn((Insect) selectedActor, clickedYarn);
            else if (currentActionType == GameActionType.INSECT_MOVE) actionLogicAttempted = performFinalTargetAction(null, clickedYarn);
            else System.out.println("  > Yarn clicked in AWAITING_FINAL_TARGET, but action " + currentActionType + " does not use yarn target for Insect.");
        } else {
            System.out.println("  > Yarn clicked in invalid step (" + currentInteractionStep + ") or with invalid/no actor for yarn interaction.");
            // Provide feedback: "Please select an Insect first" or "This action doesn't target yarns"
            if (currentInteractionStep == InteractionStep.SELECT_ACTOR && currentActionType.toString().startsWith("INSECT_")) {
                JOptionPane.showMessageDialog(null, "Please select an Insect first, not a yarn.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (!actionLogicAttempted) fireStateChanged(); // Update UI if state didn't change via completeAction/cancelAction
    }

    private boolean tryGrowBody(Tekton onTekton) { /* ... same ... */
        if (!activePlayer.hasActionsLeft()) { noActionsLeft(); cancelAction(); return false; }
        if (activePlayer instanceof Mushroomer) {
            Mushroomer m = (Mushroomer) activePlayer; MushroomBody body = onTekton.getMushroomNoPrint().growBody(onTekton, m);
            if (body != null) { activePlayer.decrementActionPoints(); completeAction(); return true; }
            else { System.out.println("Game: Grow Body failed in tryGrowBody (e.g., cannot grow or body exists)."); /* Don't cancel, let user select another tekton */ fireStateChanged(); return false; }
        }
        cancelAction(); return false;
    }
    private boolean tryGrowYarn(Tekton from, Tekton to) { /* ... same ... */
        if (!activePlayer.hasActionsLeft()) { noActionsLeft(); cancelAction(); return false; }
        if (activePlayer instanceof Mushroomer) {
            Mushroomer m = (Mushroomer) activePlayer; boolean success = m.GrowYarn(from, to);
            if (success) { activePlayer.decrementActionPoints(); completeAction(); return true; }
            else { System.out.println("Game: Grow Yarn failed (e.g., not adjacent, already exists)."); /* Don't cancel, let user select another TO tekton */ firstTektonTarget = from; currentInteractionStep = InteractionStep.SELECT_SECOND_TARGET; fireStateChanged(); return false; }
        }
        cancelAction(); return false;
    }

    public boolean tryMushroomerEat(Tekton target) {
        Player player = getActivePlayer();
        if (!(player instanceof Mushroomer mushroomer)) {
            System.out.println("Current player is not a Mushroomer.");
            return false;
        }

        if (target == null) {
            System.out.println("No target Tekton selected for eating.");
            return false;
        }

        // Delegate the eat logic to the Mushroomer object.
        boolean eatSuccess = mushroomer.eatInsect(target);
        if (eatSuccess) {
            System.out.println("Mushroomer successfully ate an insect on Tekton " + target.getIDNoPrint());
            completeAction(); // Ensure actions are only deducted on success
        } else {
            JOptionPane.showMessageDialog(null, "Failed to eat an insect. Ensure there's a paralyzed insect and yarn on the Tekton.", 
                                      "Action Failed", JOptionPane.WARNING_MESSAGE);
            System.out.println("Mushroomer failed to eat the insect on Tekton " + target.getIDNoPrint());
        }

        fireStateChanged(); // Reflect changes to the UI regardless of success
        return eatSuccess;
    }

    private boolean tryCutYarn(Insect byInsect, MushroomYarn yarnToCut) { /* ... same ... */
        if (!activePlayer.hasActionsLeft()) { noActionsLeft(); cancelAction(); return false; }
        boolean success = byInsect.cut(yarnToCut);
        if(success) { activePlayer.decrementActionPoints(); completeAction(); return true; }
        else { System.out.println("Game: Cut Yarn failed (e.g., already cut, cannot cut)."); /* Don't cancel, let user select another yarn */ fireStateChanged(); return false;}
    }

    private boolean performFinalTargetAction(Tekton targetTekton, MushroomYarn targetYarn) { /* ... same ... */
        if (!activePlayer.hasActionsLeft() && currentActionType != GameActionType.NONE) { noActionsLeft(); if(currentInteractionStep != InteractionStep.IDLE) cancelAction(); return false; }
        System.out.println("Game: Performing final target action: " + currentActionType + " with actor: " + (selectedActor != null ? selectedActor.getClass().getSimpleName() + "(" + getEntityID(selectedActor) + ")" : "null"));
        boolean actionTaken = false;
        switch (currentActionType) {
            case INSECT_MOVE: if (selectedActor instanceof Insect) { Insect i = (Insect)selectedActor; if (targetYarn != null) actionTaken = i.move(targetYarn); } break;
            case INSECT_EAT_SPORE: if (selectedActor instanceof Insect && targetTekton != null) { Insect i = (Insect)selectedActor; if (targetTekton == i.getTektonNoPrint()) { Mushroom mgr = targetTekton.getMushroomNoPrint(); if (mgr!=null && !mgr.getSporesNoPrint().isEmpty()) actionTaken = i.consumeSpore(mgr.getSporesNoPrint().get(0));} else System.out.println("Game: Insect must be on Tekton to eat spores.");} break;
            case MUSHROOMER_SPREAD_SPORES:
                if (activePlayer instanceof Mushroomer && targetTekton != null && sporeTypeToSpreadContext != null && selectedActor instanceof MushroomBody) {
                    Mushroomer m = (Mushroomer) activePlayer; MushroomBody sb = (MushroomBody)selectedActor; Tekton st = sb.getTektonNoPrint();
                    if (sb.getOwner() == m) { int id = m.getSporesOwned().size() + (st!=null?st.getIDNoPrint()*1000:0) + targetTekton.getIDNoPrint()*100 +1; Spore sp = st.getMushroomNoPrint().spreadSporeTo(targetTekton, m, sporeTypeToSpreadContext, id); actionTaken = (sp != null); }
                    else System.out.println("Game: Source MushroomBody not owned by active player.");
                } else System.out.println("Game: Conditions not met for SPREAD_SPORES. Actor: " + selectedActor + ", SporeType: " + sporeTypeToSpreadContext);
                sporeTypeToSpreadContext = null; break;
        }
        if (actionTaken) { activePlayer.decrementActionPoints(); System.out.println("Game: Action " + currentActionType + " successful. Decremented AP."); completeAction(); return true; }
        else { System.out.println("Game: Action " + currentActionType + " FAILED in model or conditions unmet."); if (currentActionType != GameActionType.NONE) { /* Don't always cancel; let user retry target for current actor/action */ currentInteractionStep = InteractionStep.AWAITING_FINAL_TARGET; fireStateChanged(); } else fireStateChanged(); return false; }
    }
    private void completeAction() { /* ... same ... */
        System.out.println("Game: Completing action " + currentActionType + ". Resetting to IDLE.");
        currentActionType = GameActionType.NONE; currentInteractionStep = InteractionStep.IDLE;
        firstTektonTarget = null; // Clear two-step target
        // Keep selectedActor for info panel, cleared by nextPlayer or new startAction
        fireStateChanged();
    }
    private void noActionsLeft(){ /* ... same ... */
        System.out.println("Game: No actions left for " + (activePlayer != null ? activePlayer.getName() : "N/A"));
        JOptionPane.showMessageDialog(null, "No actions remaining this turn!", "Out of Actions", JOptionPane.INFORMATION_MESSAGE);
    }
    public void nextPlayer() { /* ... same, calls activePlayer.resetActionPoints() ... */
        if (players.isEmpty()) return; int index = players.indexOf(activePlayer);
        activePlayer = players.get((index + 1) % players.size());
        if(activePlayer != null) activePlayer.resetActionPoints();
        System.out.println("Game: Next player is " + (activePlayer != null ? activePlayer.getName() : "None") + ". Turn begins.");
        cancelAction(); this.selectedActor = null; this.firstTektonTarget = null; this.sporeTypeToSpreadContext = null;
        if (index == players.size() - 1 && players.size() > 0) updateGameRound();
        fireStateChanged();
    }

    public void updateGameRound() { /* ... same, including checkForRandomTektonSplit() ... */
        System.out.println("Game: Updating game round (effects, etc.).");
        boolean mapMightHaveChanged = false;
        for (Tekton tekton : new ArrayList<>(tektons)) {
            for (Insect insect : new ArrayList<>(tekton.getInsectsNoPrint())) insect.nextTurn();
            Mushroom mushroomManager = tekton.getMushroomNoPrint();
            if (mushroomManager != null) {
                List<MushroomYarn> toRemove = mushroomManager.Update();
                if (toRemove != null && !toRemove.isEmpty()) {
                    for(MushroomYarn yarn : new HashSet<>(toRemove)){
                        if(yarn.getOwner() != null) yarn.getOwner().removeMushroomYarn(yarn);
                        Tekton[] yarnTektons = yarn.getTektonsNoPrint();
                        if(yarnTektons != null && yarnTektons.length == 2){
                            if(yarnTektons[0] != null && yarnTektons[0].getMushroomNoPrint() != null) yarnTektons[0].getMushroomNoPrint().removeMushroomYarn(yarn);
                            if(yarnTektons[1] != null && yarnTektons[1].getMushroomNoPrint() != null) yarnTektons[1].getMushroomNoPrint().removeMushroomYarn(yarn);
                        }
                    }
                    mapMightHaveChanged = true;
                }
            }
        }
        checkForRandomTektonSplit();
        if (mapMightHaveChanged) fireMapChanged();
        fireStateChanged();
        System.out.println("Game: Game round update finished.");
    }

    private void checkForRandomTektonSplit() {
        Random rnd = new Random();
        if (tektons == null || tektons.isEmpty() || tektons.size() < 2) return; // Need at least 2 tektons to split one and have remaining.
        if (rnd.nextInt(100) < 5) { // 5% chance each round
            Tekton tektonToSplit = tektons.get(rnd.nextInt(tektons.size()));
            System.out.println("GAME EVENT: Attempting to randomly split Tekton " + getEntityID(tektonToSplit));
            splitTekton(tektonToSplit);
        }
    }

    public void splitTekton(Tekton tektonToSplit) { /* ... same robust splitTekton logic ... */
        if (tektonToSplit == null || !tektons.contains(tektonToSplit) || tektons.size() < 1) { System.out.println("Game.splitTekton: Cannot split. Invalid conditions."); return; }
        System.out.println("Game.splitTekton: Splitting Tekton " + getEntityID(tektonToSplit));
        // Create a new Tekton of a random type, or default
        Random rnd = new Random();
        int newTektonID = 0; // Let Tekton constructor assign ID
        Tekton newTekton;
        // For simplicity, new split part is Default, or copy type of original
        if (rnd.nextBoolean()) newTekton = new DefaultTekton(newTektonID);
        else { try { newTekton = tektonToSplit.getClass().getDeclaredConstructor().newInstance(); newTekton.setID(newTektonID); } catch (Exception ex) { newTekton = new DefaultTekton(newTektonID); } }

        List<Tekton> originalNeighbors = new ArrayList<>(tektonToSplit.getAdjacentTektonsNoPrint());
        int halfNeighbors = originalNeighbors.size() / 2;
        for (int i = 0; i < halfNeighbors; i++) {
            Tekton neighborToMove = originalNeighbors.get(i);
            tektonToSplit.removeAdjacentTekton(neighborToMove); newTekton.addAdjacentTekton(neighborToMove);
        }
        tektonToSplit.addAdjacentTekton(newTekton);

        List<Insect> insectsToMove = new ArrayList<>(); int c=0;
        for(Insect insect : new ArrayList<>(tektonToSplit.getInsectsNoPrint())) if (c++ % 2 == 0) insectsToMove.add(insect);
        for(Insect insect : insectsToMove) insect.setTekton(newTekton);

        Mushroom originalMushroomManager = tektonToSplit.getMushroomNoPrint();
        if (originalMushroomManager != null) originalMushroomManager.removeMushroomBody(); // Original loses body
        // New tekton has its own fresh mushroomManager from its constructor. Yarns remain for now.
        this.tektons.add(newTekton);
        System.out.println("Game.splitTekton: T" + getEntityID(tektonToSplit) + " split. New Tekton: " + getEntityID(newTekton));
        fireMapChanged(); fireStateChanged();
    }

    public List<Object> getHighlightableEntities() { /* ... same as previous full version ... */
        List<Object> highlights = new ArrayList<>(); if (activePlayer == null) return highlights;
        // System.out.println("GET_HIGHLIGHTS: Player=" + activePlayer.getName() + ", Step=" + currentInteractionStep + // (verbose log)
        switch (currentInteractionStep) {
            case SELECT_ACTOR:
                if (currentActionType == GameActionType.MUSHROOMER_SPREAD_SPORES && activePlayer instanceof Mushroomer) highlights.addAll(((Mushroomer)activePlayer).getMushroomBodies());
                else if (activePlayer instanceof Insecter && (currentActionType == GameActionType.INSECT_MOVE || currentActionType == GameActionType.INSECT_EAT_SPORE || currentActionType == GameActionType.INSECT_CUT_YARN)) highlights.addAll(((Insecter) activePlayer).getInsects());
                break;
            case SELECT_FIRST_TARGET:
                if (activePlayer instanceof Mushroomer) { Mushroomer m = (Mushroomer) activePlayer;
                    if (currentActionType == GameActionType.MUSHROOMER_GROW_BODY) highlights.addAll(m.whereCanIGrowMushroomBodies(tektons));
                    else if (currentActionType == GameActionType.MUSHROOMER_GROW_YARN) highlights.addAll(m.fromWhereCanIGrowYarns());
                    else if (currentActionType == GameActionType.MUSHROOMER_EAT) highlights.addAll(tektons); } break;
            case SELECT_SECOND_TARGET:
                if (currentActionType == GameActionType.MUSHROOMER_GROW_YARN && firstTektonTarget != null && activePlayer instanceof Mushroomer) highlights.addAll(((Mushroomer)activePlayer).whereCanIGrowYarnsFromThisTekton(firstTektonTarget));
                break;
            case AWAITING_FINAL_TARGET:
                if (selectedActor instanceof Insect) {
                    Insect insect = (Insect) selectedActor; Tekton currentInsectTekton = insect.getTektonNoPrint(); if (currentInsectTekton == null) break;
                    if (currentActionType == GameActionType.INSECT_MOVE) { for(Player pOwn : players) { if(pOwn instanceof Mushroomer) { for(MushroomYarn yarn : ((Mushroomer)pOwn).getMushroomYarns()){ if(!yarn.getIsCut()){ Tekton t1=yarn.getTektonsNoPrint()[0],t2=yarn.getTektonsNoPrint()[1]; if(t1==currentInsectTekton&&t2!=null){highlights.add(yarn);highlights.add(t2);} else if(t2==currentInsectTekton&&t1!=null){highlights.add(yarn);highlights.add(t1);}}}}}}
                    else if (currentActionType == GameActionType.INSECT_EAT_SPORE) { Mushroom mMan = currentInsectTekton.getMushroomNoPrint(); if (mMan != null && !mMan.getSporesNoPrint().isEmpty()) highlights.add(currentInsectTekton); }
                    else if (currentActionType == GameActionType.INSECT_CUT_YARN) { for(Player pOwn : players) { if(pOwn instanceof Mushroomer){ for(MushroomYarn yarn : ((Mushroomer)pOwn).getMushroomYarns()){ if(!yarn.getIsCut() && (yarn.getTektonsNoPrint()[0] == currentInsectTekton || yarn.getTektonsNoPrint()[1] == currentInsectTekton)) highlights.add(yarn);}}}}
                } else if (selectedActor instanceof MushroomBody && currentActionType == GameActionType.MUSHROOMER_SPREAD_SPORES && activePlayer instanceof Mushroomer) {
                    MushroomBody sourceBody = (MushroomBody) selectedActor;
                    highlights.addAll(((Mushroomer)activePlayer).whereCanISpreadSpores(sourceBody.getTektonNoPrint(), sporeTypeToSpreadContext, tektons)); // Pass all tektons if method needs it
                } break;
        }
        // System.out.println("Game: Highlighting " + highlights.size() + " entities: " + highlights.stream().map(this::getEntityID).collect(Collectors.joining(", ")));
        return highlights;
    }
    private String getEntityID(Object entity) { /* ... same as before ... */
        if (entity == null) return "null_entity";
        if (entity instanceof Tekton) return "T" + ((Tekton) entity).getIDNoPrint();
        if (entity instanceof Insect) return "I" + ((Insect) entity).getIDNoPrint();
        if (entity instanceof MushroomBody) return "MB" + ((MushroomBody) entity).getIDNoPrint();
        if (entity instanceof MushroomYarn) return "MY" + ((MushroomYarn) entity).getIDNoPrint();
        if (entity instanceof Spore) return "S" + ((Spore) entity).getIDNoPrint();
        return entity.getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(entity));
    }
}