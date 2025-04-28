import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InsectEatsDuplicatingSporeTest {
    private Mushroomer mushroomer;
    private Insecter insecter;
    private Tekton tekton1;
    private Tekton tekton2;
    private Tekton tekton3;
    private Tekton tekton4;
    private Insect insect1;
    private Insect insect2;
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

        // 3) Insects (each registers itself with its tekton and owner)
        insect1 = new Insect(tekton1, insecter, 1);
        insect2 = new Insect(tekton2, insecter, 2);

        // 4) Build adjacency graph
        tekton1.addAdjacentTekton(tekton2);
        tekton1.addAdjacentTekton(tekton3);
        tekton1.addAdjacentTekton(tekton4);
        tekton2.addAdjacentTekton(tekton3);
        tekton2.addAdjacentTekton(tekton4);
        tekton3.addAdjacentTekton(tekton4);

        // 5) Create a duplicating spore on tekton1
        spore = new DuplicatingSpore(tekton1, mushroomer, 1);
        mushroomer.addSpore(spore);
        // attach it to the mushroom that tekton1 already has
        tekton1.getMushroomNoPrint().addSpore(spore);

        // 6) Initialize game context (so duplicating can deposit a *new* insect into both tekton1 and the Insecter)
        List<Tekton> all = List.of(tekton1, tekton2, tekton3, tekton4);
        List<Player> players = List.of(mushroomer, insecter);
        game = new Game(all, players);

    }

    @Test
    void testDuplicatingSporeProducesExtraInsect() {
        game.list();
        // Before eating: only one insect on tekton1, and one owned by insecter

        // Insect1 eats the duplicating spore
        insect1.consumeSpore(spore);

        // The spore is removed from the Mushroomer and from the mushroom
        assertTrue(mushroomer.getSpores().isEmpty());
        assertTrue(tekton1.getMushroomNoPrint().getSpores().isEmpty());

        // After eating: there should now be two insects on tekton1 and two in insecter's roster
        assertEquals(2, tekton1.getInsects().size());

        game.list();
    }
}
