import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<Tekton> whereCanIGrowMushroomBodies() {
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
        Set<Tekton> uniqueTektons = new HashSet<>(tektonWhereICanGrow);
        tektonWhereICanGrow.clear();
        tektonWhereICanGrow.addAll(uniqueTektons);
        //write to console where the player can grow mushroom bodies
        for (Tekton tekton : tektonWhereICanGrow) {
            System.out.println("Mushroomer.whereCanIGrowMushroomBodies() tekton: " + tekton);
        }
        System.out.println("Mushroomer.whereCanIGrowMushroomBodies() returned");
        return tektonWhereICanGrow;
    }
    public void GrowBody(Tekton tekton){
        if (whereCanIGrowMushroomBodies().contains(tekton)) {
            System.out.println("Mushroomer.Grow(tekton) called");

            MushroomBody mushroomBody = tekton.getMushroom().growBody(tekton);
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
    public List<Tekton> fromWhereCanIGrowYarns() {
        System.out.println("Mushroomer.callSpread(tekton) called");
        List<Tekton> whereHasYarns = new ArrayList<Tekton>();
        for (MushroomYarn mushroomYarns: this.mushroomYarns) {
           for(Tekton tekton: mushroomYarns.getTektons()){
               whereHasYarns.add(tekton);
           }
        }
        for(MushroomBody mushroombodies: this.mushroomsBodies){
            for(Tekton tekton: mushroombodies.getTektons()){
                whereHasYarns.add(tekton);
            }
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
        //make sure that every tektons are unique in tektonWhereICanGrow
        Set<Tekton> uniqueTektons = new HashSet<>(tektonWhereICanGrow);
        tektonWhereICanGrow.clear();
        tektonWhereICanGrow.addAll(uniqueTektons);
        //write to console where the player can grow mushroom bodies
        for (Tekton tekton : tektonWhereICanGrow) {
            System.out.println("Mushroomer.whereCanIGrowYarns() tekton: " + tekton);
        }
        System.out.println("Mushroomer.whereCanIGrowYarns() returned");
        return tektonWhereICanGrow;
    }
    public List<Tekton> whereCanIGrowYarnsFromThisTekton(Tekton tekton) {
        System.out.println("Mushroomer.whereCanIGrowYarnsFromThisTekton(tekton) called");
        List<Tekton> tektonWhereICanGrow = new ArrayList<Tekton>();
        for (Tekton tekton1 : tekton.getAdjacentTektons()) {
            if (tekton1.canGrowYarn()) {
                tektonWhereICanGrow.add(tekton1);
            }
        }
        //no duplicate
        Set<Tekton> uniqueTektons = new HashSet<>(tektonWhereICanGrow);
        tektonWhereICanGrow.clear();
        tektonWhereICanGrow.addAll(uniqueTektons);
        for (Tekton tekton1 : tektonWhereICanGrow) {
            System.out.println("Mushroomer.whereCanIGrowYarnsFromThisTekton(tekton) tekton: " + tekton1);
        }
        System.out.println("Mushroomer.whereCanIGrowYarnsFromThisTekton(tekton) returned");
        return tektonWhereICanGrow;
    }
    public void GrowYarn(Tekton honnan, Tekton hova){
        System.out.println("Mushroomer.GrowYarn() called");
        if(fromWhereCanIGrowYarns().contains(honnan) && whereCanIGrowYarnsFromThisTekton(honnan).contains(hova))
        mushroomYarns.add(honnan.getMushroom().spread(honnan, hova));
        System.out.println("Mushroomer.GrowYarn() returned");
    }
}
