import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InsectEatsCutDisablingSporeTest {
    private Mushroomer mushroomer;
    private Insecter insecter;
    private Tekton tekton1;
    private Tekton tekton2;
    private Tekton tekton3;
    private Tekton tekton4;
    private Insect insect1;
    private Insect insect2;
    private MushroomBody mushroomBody1;
    private MushroomBody mushroomBody3;
    private Spore spore;
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

        // 3) Insects (owning player and ID)
        insect1 = new Insect(tekton1, insecter, 1);
        insect2 = new Insect(tekton2, insecter, 2);

        // 4) Adjacencies
        tekton1.addAdjacentTekton(tekton2);
        tekton1.addAdjacentTekton(tekton3);
        tekton1.addAdjacentTekton(tekton4);
        tekton2.addAdjacentTekton(tekton3);
        tekton2.addAdjacentTekton(tekton4);
        tekton3.addAdjacentTekton(tekton4);

        // 5) Populate bodies on existing Mushrooms
        mushroomBody1 = new MushroomBody(tekton1, mushroomer, 1);
        tekton1.getMushroomNoPrint().addMushroomBody(mushroomBody1);
        mushroomBody3 = new MushroomBody(tekton3, mushroomer, 3);
        tekton3.getMushroomNoPrint().addMushroomBody(mushroomBody3);

        // 6) Create and assign spore to tekton1's mushroom and to the player
        spore = new CutDisablingSpore(tekton1, mushroomer, 1);
        mushroomer.addSpore(spore);
        tekton1.getMushroomNoPrint().addSpore(spore);

        // 7) Build game with all Tektons and both players
        List<Tekton> allTektons   = List.of(tekton1, tekton2, tekton3, tekton4);
        List<Player>  allPlayers  = List.of(mushroomer, insecter);
        game = new Game(allTektons, allPlayers);
    }

    @Test
    void TestInsectEatsCutDisablingSporeSporeAndGetsSlowed() {
        game.list();
        insect1.consumeSpore(spore);
        assertTrue(mushroomer.getSpores().isEmpty());
        assertTrue(tekton1.getMushroomNoPrint().getSpores().isEmpty());
        assertTrue(insect1.getEffects()[0] != 0);
        game.list();
    }
}
