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
    private MushroomBody mushroomBody2;

    @BeforeEach
    void setUp() {
        // 1) Player
        mushroomer = new Mushroomer("Mushroomer");
        insecter = new Insecter("Insecter");
        // 2) Tektons
        tekton1 = new DisappearingYarnTekton(1);
        tekton2 = new DefaultTekton(2);
        tekton3 = new DefaultTekton(3);
        tekton4 = new NoMushroomBodyTekton(4);

        // 3) Register insects with tektons and owner
        insect1 = new Insect(tekton1, insecter, 1);
        insect2 = new Insect(tekton3, insecter, 2);

        // 4) Build adjacency graph
        tekton1.addAdjacentTekton(tekton2);
        tekton1.addAdjacentTekton(tekton3);
        tekton1.addAdjacentTekton(tekton4);
        tekton2.addAdjacentTekton(tekton3);
        tekton2.addAdjacentTekton(tekton4);
        tekton3.addAdjacentTekton(tekton4);

        game = new Game(
                List.of(tekton1, tekton2, tekton3, tekton4),
                List.of(mushroomer, insecter)
        );
    }

    @Test
    void testWhereCanIGrowMushroomYarnNotAdjacent() {
        game.list();
        assertTrue(mushroomer.whereCanIGrowYarnsFromThisTekton(tekton1).contains(tekton2) &&
                mushroomer.whereCanIGrowYarnsFromThisTekton(tekton1).contains(tekton3) &&
                mushroomer.whereCanIGrowYarnsFromThisTekton(tekton1).contains(tekton4));
    }

    @Test
    void testWhereYarnDisappearsOnDis(){
        game.list();
        mushroomYarn1 = new MushroomYarn(tekton1,tekton2,mushroomer, 1);
        game.update();
        game.update();
        assertTrue(tekton1.getMushroom().isThereMushroomYarn(tekton1, tekton2));
        game.update();
        game.update();
        game.update();
        game.list();
        assertFalse(tekton1.getMushroom().isThereMushroomYarn(tekton1, tekton2));
        game.list();
    }
    @Test
    void TestYarnCanGrowWhereBodyCant(){
        game.list();
        mushroomer.growBody(tekton4);
        assertTrue(tekton4.getMushroom().getMushroomBody() == null);
        MushroomYarn yarn = new MushroomYarn(tekton1, tekton2, mushroomer, 1);
        tekton2.getMushroom().addMushroomBody(mushroomBody2 = new MushroomBody(tekton2, mushroomer, 2));
        mushroomer.GrowYarn(tekton2, tekton4);
        assertTrue(tekton2.getMushroom().isThereMushroomYarn(tekton2, tekton4));
        game.list();
    }
    @Test
    void testMushroomYarnGrowth() {
        game.list();
        MushroomYarn yarn = new MushroomYarn(tekton3, tekton2, mushroomer, 1);
        tekton2.getMushroom().addMushroomBody(mushroomBody2 = new MushroomBody(tekton2, mushroomer, 2));
        mushroomer.GrowYarn(tekton1, tekton2);
        assertTrue(tekton2.getMushroom().isThereMushroomYarn(tekton1, tekton2));
        game.list();
    }
}