import java.util.ArrayList;
import java.util.List;

public class Mushroomer extends Player {
    List<MushroomBody> mushroomsBodies = new ArrayList<MushroomBody>();
    List<MushroomYarn> mushroomYarns = new ArrayList<MushroomYarn>();
    List<Spore> spores = new ArrayList<Spore>();

    public List<MushroomYarn> getMushroomYarns() {
        System.out.println("Mushroomer.getMushroomYarns() called");
        System.out.println("Mushroomer.getMushroomYarns() returned");
        return mushroomYarns;
    }
    public void addMushroomYarn(MushroomYarn mushroomYarn) {
        System.out.println("Mushroomer.addMushroomYarn(MushroomYarn) called");
        mushroomYarns.add(mushroomYarn);
        System.out.println("Mushroomer.addMushroomYarn(MushroomYarn) returned");
    }

    public List<MushroomBody> getMushroomBodies() {
        System.out.println("Mushroomer.getMushrooms() called");
        System.out.println("Mushroomer.getMushrooms() returned");
        return mushroomsBodies;
    }
    public void addMushroomBody(MushroomBody mushroomBody) {
        System.out.println("Mushroomer.addMushroom(Mushroom) called");
        mushroomsBodies.add(mushroomBody);
        System.out.println("Mushroomer.addMushroom(Mushroom) returned");
    }
    public Mushroomer (String name) {
        super(name);
        System.out.println("Mushroomer.Mushroomer(name) called");
        //this.addMushroomBody(new MushroomBody());
        System.out.println("Mushroomer.Mushroomer(name) returned");
    }
    public void whereCanIGrowMushroomBodies() {
        System.out.println("Mushroomer.whereCanIGrowMushroomBodies() called");
        List<Tekton> tektonWhereICanGrow = new ArrayList<Tekton>();
        for (MushroomYarn mushroomYarn : mushroomYarns) {
            for (Tekton tekton : mushroomYarn.getTektons()) {
                //check if the tekton'smushroom's spores have the same as the player
                for (Spore spore : tekton.getMushroom().getSpores()){
                    if (this.spores.contains (spore)) {
                        System.out.println("Mushroomer.whereCanIGrowMushroomBodies() returned");
                        tektonWhereICanGrow.add(tekton);
                    }
                }
            }
        }
        //make sure that every tektons are unique in tektonWhereICanGrow
        Set<>Tekton> uniqueTektons = new HashSet<>(tektonWhereICanGrow);
        tektonWhereICanGrow.clear();
        tektonWhereICanGrow.addAll(uniqueTektons);
        System.out.println("Mushroomer.whereCanIGrowMushroomBodies() returned");
    }

}
