public class Insect {
    private int score;
    //how many actions can the insect make in a turn
    private int action;
    private Tekton tekton;

    private Insecter owner;

    public Insecter getOwner() {
        return owner;
    }

    public void setOwner(Insecter owner) {
        this.owner = owner;
    }
    //cutDisabled, paralyzed, spedUp, slowedDown
    private int[] effects = {0, 0, 0, 0};

    public Insect(Tekton tekton) {
        System.out.println("Insect.Insect() called");
        this.score = 0;
        this.action = 3;
        this.tekton = tekton;
        System.out.println("Insect.Insect() returned");
    }

    public int[] getEffects() {
        return effects;
    }

    public int getScore() {
        System.out.println("Insect.getScore() called");
        System.out.println("Insect.getScore() returned");
        return score;
    }

    public int getAction(){
        System.out.println("Insect.getAction() called");
        System.out.println("Insect.getAction() returned");
        return action;

    }
    public void setTekton(Tekton tekton){
        System.out.println("Insect.setTekton() called");
        this.tekton = tekton;
        System.out.println("Insect.setTekton() returned");
    }

    public Tekton getTekton(){
        System.out.println("Insect.getTekton() called");
        System.out.println("Insect.getTekton() returned");
        return tekton;
    }

    public void effectedByCutDisablingSpore() {
        System.out.println("Insect.effectedByCutDisablingSpore() called");
        this.effects[0] = 3;
        System.out.println("Insect.effectedByCutDisablingSpore() returned");
    }

    public void effectedByParalyzingSpore() {
        System.out.println("Insect.effectedByParalyzingSpore() called");
        this.action = 0;
        this.effects[1] = 3;
        System.out.println("Insect.effectedByParalyzingSpore() returned");
    }

    public void effectedBySpeedingSpore() {
        System.out.println("Insect.effectedBySpeedingSpore() called");
        this.action = this.action *2;
        this.effects[2] = 3;
        System.out.println("Insect.effectedBySpeedingSpore() returned");
    }
    public void effectedBySlowingSpore() {
        System.out.println("Insect.effectedBySlowingSpore() called");
        this.action = this.action /2;
        this.effects[3] = 3;
        System.out.println("Insect.effectedBySlowingSpore() returned");
    }
    public void effectedByDuplicatingSpore() {
        System.out.println("Insect.effectByDuplicatingSpore() called");
        Insect insect = new Insect(this.tekton);
        tekton.addNewInsect(insect);
        insect.setOwner(this.owner);
        insect.getOwner().addInsect(insect);
        System.out.println("Insect.effectByDuplicatingSpore() returned");
    }

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

        for (int i : effects) {
            if(i!=0) i = i-1;
        }
        System.out.println("Insect.nextTurn() returned");
    }

    public void setAction(int action) {
        System.out.println("Insect.setSpeed(int) called");
        this.action = action;
        System.out.println("Insect.setSpeed(int) returned");
    }


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
        } else {
            this.tekton = path.getTektons()[0];
        }
        System.out.println("Insect.move(path) returned");
        action--;
        return true;
    }
    public boolean consumeSpore(Spore spore) {
        System.out.println("Insect.consumeSpore() called");
        if(action == 0 || effects[1]!=0){
            System.out.println("Insect.cut(yarn) returned");
            return false;
        }
        spore.affectInsect(this);
        System.out.println("Insect.consumeSpore() returned");
        action--;
        return true;
    }
    public boolean cut(MushroomYarn yarn) {
        System.out.println("Insect.cut(yarn) called");
        if(action == 0 || effects[1]!=0 || effects[0]!=0) {
            System.out.println("Insect.cut(yarn) returned");
            return false;
        }
        yarn.cut();
        System.out.println("Insect.cut(yarn) returned");
        action--;
        return true;
    }
}
