import java.util.*;

public class Mushroom {
    private MushroomBody mushroomBody = null;
    private List<MushroomYarn> mushroomYarns = new ArrayList<>();
    private List<Spore> spores = new ArrayList<>();
    private Tekton tekton = null;

    public void addMushroomYarn(MushroomYarn mushroomYarn) {
        System.out.println("Mushroom.addMushroomYarn(MushroomYarn) called");
        mushroomYarns.add(mushroomYarn);
        //megnézni, h disappearingyarn tektonon van e
        if (mushroomYarn.getTektons().)
        System.out.println("Mushroom.addMushroomYarn(MushroomYarn) returned");
    }

    public Mushroom() {
        System.out.println("Mushroom.Mushroom() called");
        this.mushroomBody = null;
        mushroomYarns = new ArrayList<>();
        spores = new ArrayList<>();
        System.out.println("Mushroom.Mushroom() returned");
    }

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
        System.out.println("Mushroom.isThereMushroomYarn() returned boolean");
        return result;
    }
    public void instructSporeRelease(MushroomBody body) {
        System.out.println("Mushroom.instructSporeRelease() called");
        body.releaseSpore();
        System.out.println("Mushroom.instructSporeRelease() returned");
    }
    public void spread() {
        System.out.println("Mushroom.spread() called");

        mushroomYarn = new MushroomYarn();

        System.out.println("Mushroom.spread() returned");
    }
    public void growBody(Tekton tekton) {
        System.out.println("Mushroom.growBody() called");
        //check if tekton allows
        if (this.mushroomBody != null) {
            System.out.println("Mushroom.growBody() returned: there is already a mushroom body");
            return;
        }
        if (tekton.canGrow()) {
            System.out.println("Mushroom.growBody() tekton.canGrow() returned");
            //check if tekton has spore and mushroomyarn
            if(this.tekton.hasSpore && this.tekton.hasMushroomYarn) {
                System.out.println("Mushroom.growBody() tekton.hasSpore && tekton.hasMushroomYarn returned");
                //create mushroom body
            mushroomBody = new MushroomBody(tekton);
        } else {
            System.out.println("Mushroom.growBody() tekton.canGrow() returned");
            return;
        }

        System.out.println("Mushroom.growBody() returned");
    }
    public void update() {
        System.out.println("Mushroom.update() called");
        System.out.println("MushroomYarn adding for testing started");
        mushroomYarns.add(new MushroomYarn());
        System.out.println("MushroomYarn adding for testing finished");
        for (MushroomYarn m : mushroomYarns) {
            m.update();
        }


        System.out.println("Mushroom.update() returned");

    }
    public int getScore() {
        System.out.println("Mushroom.getScore() called");

        System.out.println("Mushroom.getScore() returned int");
        return score;
    }
    public void destroyMushroomBody(MushroomBody body) {
        System.out.println("Mushroom.destroyMushroomBody() called");
        if (mushroomBody != null) {
            mushroomBody = null;
            System.out.println("Mushroom.destroyMushroomBody() returned");
            return;
        }
        System.out.println("Mushroom.getScore() returned");
    }
    public boolean hasMushroomBody() {
        System.out.println("Mushroom.hasMushroomBody() called");
        boolean result = mushroomBody != null;
        System.out.println("Mushroom.hasMushroomBody() returned boolean");
        return result;
    }
}

