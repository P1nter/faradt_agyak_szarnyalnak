import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InsectMovementTest {
    private Mushroomer mushroomer;
    private Insecter insecter;
    private Tekton tekton1, tekton2, tekton3, tekton4;
    private Insect insect1, insect2;
    private MushroomYarn mushroomYarn1;
    private Game game;

    @BeforeEach
    void setUp() {
        // 1) Create players
        mushroomer = new Mushroomer("Mushroomer");
        insecter   = new Insecter("Insecter");

        // 2) Create tektons
        tekton1 = new DefaultTekton(1);
        tekton2 = new DefaultTekton(2);
        tekton3 = new DefaultTekton(3);
        tekton4 = new DefaultTekton(4);

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

        // 5) Create a mushroom yarn between tekton1 and tekton2
        mushroomYarn1 = new MushroomYarn(tekton1, tekton2, 1);
        // attach to each tekton's mushroom
        tekton1.getMushroomNoPrint().addMushroomYarn(mushroomYarn1);
        tekton2.getMushroomNoPrint().addMushroomYarn(mushroomYarn1);

        game = new Game(
                List.of(tekton1, tekton2, tekton3, tekton4),
                List.of(mushroomer, insecter)
        );
    }

    @Test
    void TestCanMoveToAdjacentTekton() {
        game.list();
        // Move along the yarn: insect1 starts at tekton1, should end at tekton2
        insect1.move(mushroomYarn1);
        assertEquals(tekton2, insect1.getTekton());
        game.list();
    }

    @Test
    void TestYarnDoesntExistOnSameTekton() {
        game.list();
        // Move via adjacency: insect2 starts at tekton2, moving to tekton3
        insect2.move(mushroomYarn1);
        assertNotEquals(tekton1, insect2.getTekton());
        game.list();
    }
}
