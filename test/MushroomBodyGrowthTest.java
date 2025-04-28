import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MushroomBodyGrowthTest {
    private Mushroomer mushroomer;
    private Tekton tekton1, tekton2, tekton3, tekton4;
    private Spore spore;
    private Game game;

    @BeforeEach
    void setUp() {
        // 1) Player
        mushroomer = new Mushroomer("Mushroomer");

        // 2) Tektons
        tekton1 = new DefaultTekton(1);
        tekton2 = new DefaultTekton(2);
        tekton3 = new DefaultTekton(3);
        tekton4 = new DefaultTekton(4);

        // 3) Adjacency
        tekton1.addAdjacentTekton(tekton2);
        tekton1.addAdjacentTekton(tekton3);
        tekton1.addAdjacentTekton(tekton4);
        tekton2.addAdjacentTekton(tekton3);
        tekton2.addAdjacentTekton(tekton4);
        tekton3.addAdjacentTekton(tekton4);

        game = new Game(
                List.of(tekton1, tekton2, tekton3, tekton4),
                List.of(mushroomer)
        );
    }

    @Test
    void TestGrowMushroomBodyNoSporeNoYarn() {
        // No spore, no yarn → cannot grow
        mushroomer.growBody(tekton2);
        assertTrue(tekton2.getMushroomNoPrint().getMushroomBodyNoPrint()==null);
    }

    @Test
    void TestGrowMushroomBodyYesSporeNoYarn() {
        // Spore present, but no connecting yarn → still cannot grow
        spore = new SlowingSpore(tekton2, mushroomer,1);

        mushroomer.growBody(tekton2);
        assertTrue(tekton2.getMushroomNoPrint().getMushroomBodyNoPrint()==null);
    }

    @Test
    void TestGrowMushroomBodyNoSporeYesYarn() {
        // Yarn present, but no spore → cannot grow
        MushroomYarn yarn = new MushroomYarn(tekton1, tekton2, mushroomer,1);


        mushroomer.growBody(tekton2);
        assertNull(tekton2.getMushroomNoPrint().getMushroomBodyNoPrint());
    }

    @Test
    void TestGrowMushroomBodyYesSporeYesYarnButAlreadyBody() {
        // Spore + yarn, but body already exists → no new growth
        spore = new SlowingSpore(tekton2,mushroomer, 1);

        MushroomYarn yarn = new MushroomYarn(tekton1, tekton2,mushroomer, 1);

        // Pre-add a body so growBody should detect “already there”
        tekton2.getMushroomNoPrint().addMushroomBody(
                new MushroomBody(tekton2, mushroomer,2)
        );

        mushroomer.growBody(tekton2);
        // Still only the original body
        assertNotNull(tekton2.getMushroomNoPrint().getMushroomBodyNoPrint());
    }

    @Test
    void TestGrowMushroomBodyYesSporeYesYarnAndNoBody() {

        // Spore + yarn, no existing body → should grow exactly one
        spore = new SlowingSpore(tekton2, mushroomer,1);
        MushroomYarn yarn = new MushroomYarn(tekton1, tekton2,mushroomer, 1);
        mushroomer.growBody(tekton2);
        assertNotNull(tekton2.getMushroomNoPrint().getMushroomBodyNoPrint());
    }

    @Test
    void TestGrowMushroomOnNoMushroomBodyTekton() {
        // A Tekton subclass that forbids body growth
        Tekton noBody = new NoMushroomBodyTekton(5);
        noBody.addAdjacentTekton(tekton1);

        mushroomer.growBody(noBody);
        assertNull(noBody.getMushroomNoPrint().getMushroomBodyNoPrint());
    }
}
