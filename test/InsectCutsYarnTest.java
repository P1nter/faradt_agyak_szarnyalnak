import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InsectCutsYarnTest {
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
    private MushroomBody mushroomBody2;
    private MushroomYarn mushroomYarn1;
    private MushroomYarn mushroomYarn4;
    private Mushroom mushroom4;
    private Game game;
    @BeforeEach
    void setUp() {
        // 1) Players
        mushroomer = new Mushroomer("Mushroomer");
        insecter   = new Insecter("Insecter");

        // 2) Tektons
        tekton1 = new DefaultTekton(1);
        tekton2 = new DefaultTekton(2);
        tekton3 = new DefaultTekton(3);
        tekton4 = new DefaultTekton(4);

        // 3) Insects (constructor registers them with each Tekton)
        insect1 = new Insect(tekton1, insecter, 1);
        insect2 = new Insect(tekton2, insecter, 2);

        // 4) Adjacencies
        tekton1.addAdjacentTekton(tekton2);
        tekton1.addAdjacentTekton(tekton3);
        tekton1.addAdjacentTekton(tekton4);
        tekton2.addAdjacentTekton(tekton3);
        tekton2.addAdjacentTekton(tekton4);
        tekton3.addAdjacentTekton(tekton4);

        // 5) Create & attach Mushrooms
        //    We’ll build fresh Mushrooms with bodies & yarns, then call setMushroom(...)
        mushroom1 = new Mushroom(1);
        mushroom2 = new Mushroom(2);
        mushroom3 = new Mushroom(3);
        mushroom4 = new Mushroom(4);

        // 6) MushroomBodies
        mushroomBody1 = new MushroomBody(tekton1, mushroomer, 1);
        mushroomBody3 = new MushroomBody(tekton3, mushroomer, 3);
        mushroom1.addMushroomBody(mushroomBody1);
        mushroom3.addMushroomBody(mushroomBody3);

        // 7) MushroomYarn between tekton1 ↔ tekton2
        mushroomYarn1 = new MushroomYarn(tekton1, tekton2,mushroomer, 1);


        // 8) Now replace each Tekton’s default Mushroom with ours
        tekton1.setMushroom(mushroom1);
        tekton2.setMushroom(mushroom2);
        tekton3.setMushroom(mushroom3);
        tekton4.setMushroom(mushroom4);

        // 9) Finally, build the Game with all four Tektons + both players
        List<Tekton> allTektons   = List.of(tekton1, tekton2, tekton3, tekton4);
        List<Player>  allPlayers  = List.of(mushroomer, insecter);
        game = new Game(allTektons, allPlayers);
    }
    @Test
    void TestCutsYarnAndItDisappears(){
        game.list();
        insect1.cut(mushroomYarn1);
        game.update();
        game.update();
        game.update();
        assertFalse(mushroom1.isThereMushroomYarn(tekton1, tekton2));
        game.list();
    }
    @Test
    void TestCantCutThroughTimeAndSpace(){
        game.list();
        mushroomYarn4 = new MushroomYarn(tekton3,tekton4,mushroomer,4);
        insect1.cut(mushroomYarn4);
        assertTrue(mushroom4.isThereMushroomYarn(tekton4, tekton3));
        game.list();
    }
}
