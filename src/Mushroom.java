import java.util.*;

public class Mushroom {
    private int score;
    private List<MushroomBody> mushroomBodies = new ArrayList<>();
    private List<MushroomYarn> mushroomYarns = new ArrayList<>();
    private List<Spore> spores = new ArrayList<>();

    public void setMushroomYarn(MushroomYarn mushroomYarn) {
        this.mushroomYarn = mushroomYarn;
    }

    //Teszteléshez
    private MushroomBody mushroomBody;
    private MushroomYarn mushroomYarn;

    public Mushroom() {
        System.out.println("Mushroom.Mushroom() called");

        System.out.println("Mushroom.Mushroom() returned");
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
    public void growBody() {
        System.out.println("Mushroom.growBody() called");

        mushroomBody = new MushroomBody();
        //mushroomYarn = new MushroomYarn();

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
        System.out.println("Mushroom.getScore() returned int");
        mushroomBodies.remove(body);
        System.out.println("Mushroom.getScore() returned int");
    }
}
