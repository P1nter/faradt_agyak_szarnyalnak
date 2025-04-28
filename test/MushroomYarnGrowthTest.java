import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MushroomYarnGrowthTest {
    private Mushroomer mushroomer;
    private Insecter insecter;
    private Tekton tekton1;
    private Tekton tekton2;
    private Tekton tekton3;
    private Tekton tekton4;
    private Insect insect1;
    private Insect insect2;
    private Mushroom mushroom1;
    private Mushroom mushroom3;
    private MushroomBody mushroomBody1;
    private MushroomBody mushroomBody3;
    private Spore spore;
    private Mushroom mushroom2;
    private MushroomYarn mushroomYarn1;
    private Game game;

    @BeforeEach
    void setUp() {
        mushroomer = new Mushroomer("Mushroomer");
        insecter = new Insecter("Insecter");
        tekton1 = new DefaultTekton(1);
        tekton2 = new DefaultTekton(2);
        tekton3 = new DefaultTekton(3);
        tekton4 = new DefaultTekton(4);
        insect1 = new Insect(tekton1, insecter,1);
        insect2 = new Insect(tekton2,insecter,2);
        tekton1.addAdjacentTekton(tekton2);
        tekton1.addAdjacentTekton(tekton3);
        tekton1.addAdjacentTekton(tekton4);
        tekton2.addAdjacentTekton(tekton3);
        tekton2.addAdjacentTekton(tekton4);
        tekton3.addAdjacentTekton(tekton4);
        mushroomBody1 = new MushroomBody(tekton1,mushroomer,1);
        mushroomBody3 = new MushroomBody(tekton3,mushroomer,3);
        mushroom1 = new Mushroom(1);
        mushroom1.addMushroomBody(mushroomBody1);
        mushroom3 = new Mushroom(3);
        mushroom3.addMushroomBody(mushroomBody3);
        Spore spore = new SlowingSpore(tekton1,mushroomer,1);
        mushroomer.addSpore(spore);
        mushroom1.addSpore(spore);
    }

    @Test
    void testWhereCanIGrowMushroomYarnNotAdjacent() {
        assertTrue(mushroomer.whereCanIGrowYarnsFromThisTekton(tekton1).isEmpty());
    }

    @Test
    void testWhereCanIGrowMushroomYarnAdjacent() {
        tekton1.adjacentTektons.add(tekton2);
        assertEquals(mushroomer.whereCanIGrowYarnsFromThisTekton(tekton1).getFirst(), tekton2);
    }

    @Test
    void testWhereCanIGrowMushroomYarnSingleMushroomOnlyHasYarn() {
        tekton1 = new SingleMushroomOnlyTekton(1);
        tekton1.addAdjacentTekton(tekton2);
        tekton1.addAdjacentTekton(tekton3);
        tekton1.addAdjacentTekton(tekton4);
        mushroom2 = new Mushroom(2);
        mushroomYarn1 = new MushroomYarn(tekton1,tekton2,1);
        mushroom2.addMushroomYarn(mushroomYarn1);
        mushroom1.addMushroomYarn(mushroomYarn1);
        //ha hibás akk azért, mert magunkat nem csekkoljuk csak a másik tektont
        assertTrue(mushroomer.whereCanIGrowYarnsFromThisTekton(tekton1).isEmpty());
    }

    @Test
    void testWhereYarnDisappearsOnDis(){
        tekton1 = new DisappearingYarnTekton(1);
        tekton1.addAdjacentTekton(tekton2);
        tekton1.addAdjacentTekton(tekton3);
        tekton1.addAdjacentTekton(tekton4);
        mushroom2 = new Mushroom(2);
        mushroomYarn1 = new MushroomYarn(tekton1,tekton2,1);
        mushroom2.addMushroomYarn(mushroomYarn1);
        mushroom1.addMushroomYarn(mushroomYarn1);
        //ha hibás akk azért, mert magunkat nem csekkoljuk csak a másik tektont
        List<Tekton> temptektons = new ArrayList<>(tekton1.getAdjacentTektons());
        List<Player> tempplayers = new ArrayList<>();
        tempplayers.add(mushroomer);
        tempplayers.add(insecter);
        game = new Game(temptektons, tempplayers);
        game.update();
        game.update();
        assertFalse(mushroomer.whereCanIGrowYarnsFromThisTekton(tekton1).isEmpty());
        game.update();
        assertTrue(mushroomer.whereCanIGrowYarnsFromThisTekton(tekton1).isEmpty());

    }
    @Test
    void TestYarnCanGrowWhereBodyCant(){
        tekton4 = new NoMushroomBodyTekton(1);
        tekton4.addAdjacentTekton(tekton1);
        tekton4.addAdjacentTekton(tekton3);
        tekton4.addAdjacentTekton(tekton2);
        mushroom2 = new Mushroom(2);
        mushroomer.growBody(tekton4);
        assertTrue(tekton4.getMushroom().getMushroomBody() == null);
        mushroomer.GrowYarn(tekton2, tekton4);
        assertTrue(mushroom2.isThereMushroomYarn(tekton2, tekton4));
    }
    @Test
    void testMushroomYarnGrowth() {
        int initTekton1YarnSize = tekton1.getMushroom().getMushroomYarns().size();
        int initTekton2YarnSize = tekton2.getMushroom().getMushroomYarns().size();

        tekton1.adjacentTektons.add(tekton2);
        mushroomer.GrowYarn(tekton1, tekton2);

        assertEquals(initTekton1YarnSize + 1, tekton1.getMushroom().getMushroomYarns().size());
        assertEquals(initTekton2YarnSize + 1, tekton2.getMushroom().getMushroomYarns().size());
    }
}