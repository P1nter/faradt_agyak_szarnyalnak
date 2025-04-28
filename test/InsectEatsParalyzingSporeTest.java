import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InsectEatsParalyzingSporeTest {
    private Mushroomer mushroomer;
    private Insecter  insecter;
    private Tekton    tekton1, tekton2, tekton3, tekton4;
    private Insect    insect1, insect2;
    private Spore     spore;
    private Game      game;

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

        // 3) Insects (they register themselves on their Tektons and owners)
        insect1 = new Insect(tekton1, insecter, 1);
        insect2 = new Insect(tekton2, insecter, 2);

        // 4) Build adjacency graph
        tekton1.addAdjacentTekton(tekton2);
        tekton1.addAdjacentTekton(tekton3);
        tekton1.addAdjacentTekton(tekton4);
        tekton2.addAdjacentTekton(tekton3);
        tekton2.addAdjacentTekton(tekton4);
        tekton3.addAdjacentTekton(tekton4);

        // 5) Populate Mushrooms with bodies (so list() will show them if needed)
        tekton1.getMushroomNoPrint().addMushroomBody(new MushroomBody(tekton1, mushroomer,1));
        tekton3.getMushroomNoPrint().addMushroomBody(new MushroomBody(tekton3, mushroomer,3));

        // 6) Create & attach a ParalyzingSpore on tekton1’s mushroom
        spore = new ParalyzingSpore(tekton1,mushroomer, 1);
        mushroomer.addSpore(spore);
        tekton1.getMushroomNoPrint().addSpore(spore);

        // 7) Initialize game with all Tektons and both players
        game = new Game(
                List.of(tekton1, tekton2, tekton3, tekton4),
                List.of(mushroomer, insecter)
        );
    }

    @Test
    void testParalyzingSporeGivesParalysis() {
        // Insect eats the spore
        game.list();
        insect1.consumeSpore(spore);

        // The spore should be removed from both player and mushroom
        assertTrue(mushroomer.getSpores().isEmpty());
        assertTrue(tekton1.getMushroomNoPrint().getSpores().isEmpty());

        // And insect1 should now have a non-zero 'paralyzed' effect
        assertTrue(insect1.getEffects()[1] > 0);
        game.list();
    }
}
