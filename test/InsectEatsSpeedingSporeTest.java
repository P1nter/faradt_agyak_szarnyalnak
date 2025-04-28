import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InsectEatsSpeedingSporeTest {
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

        // 3) Insects (register with their tekton and owner)
        insect1 = new Insect(tekton1, insecter, 1);
        insect2 = new Insect(tekton2, insecter, 2);

        // 4) Build adjacency graph
        tekton1.addAdjacentTekton(tekton2);
        tekton1.addAdjacentTekton(tekton3);
        tekton1.addAdjacentTekton(tekton4);
        tekton2.addAdjacentTekton(tekton3);
        tekton2.addAdjacentTekton(tekton4);
        tekton3.addAdjacentTekton(tekton4);

        // 5) Populate mushroom bodies (optional visualization)
        tekton1.getMushroomNoPrint().addMushroomBody(new MushroomBody(tekton1, mushroomer,1));
        tekton3.getMushroomNoPrint().addMushroomBody(new MushroomBody(tekton3, mushroomer,3));

        // 6) Create & attach a SpeedingSpore on tekton1's mushroom
        spore = new SpeedingSpore(tekton1, mushroomer,1);


        // 7) Initialize Game context with all tektons and players
        game = new Game(
                List.of(tekton1, tekton2, tekton3, tekton4),
                List.of(mushroomer, insecter)
        );
    }

    @Test
    void testSpeedingSporeAppliesSpeedEffect() {
        game.list();
        // Pre-condition: no speed effect yet
        assertEquals(0, insect1.getEffects()[2]);

        // Insect eats the speeding spore
        insect1.consumeSpore(spore);

        // Spore removed from both player and mushroom
        assertTrue(mushroomer.getSpores().isEmpty());
        assertTrue(tekton1.getMushroomNoPrint().getSpores().isEmpty());

        // Insect must now have a non-zero speed effect (effects[2] == sped duration)
        assertTrue(insect1.getEffects()[2] > 0);
        game.list();
    }
}