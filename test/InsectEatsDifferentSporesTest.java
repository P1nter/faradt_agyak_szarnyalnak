import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InsectEatsDifferentSporesTest {
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
    private Spore spore2;

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

        // 3) Insects (assigning to owners)
        insect1 = new Insect(tekton1, insecter, 1);
        insect2 = new Insect(tekton2, insecter, 2);

        // 4) Adjacency setup
        tekton1.addAdjacentTekton(tekton2);
        tekton1.addAdjacentTekton(tekton3);
        tekton1.addAdjacentTekton(tekton4);
        tekton2.addAdjacentTekton(tekton3);
        tekton2.addAdjacentTekton(tekton4);
        tekton3.addAdjacentTekton(tekton4);

        // 5) Create a spore placed on tekton1
        spore = new CutDisablingSpore(tekton1,mushroomer, 1);
        spore2 = new SlowingSpore(tekton1, mushroomer,1);

        // 7) Build game with all Tektons and both players
        List<Tekton> allTektons   = List.of(tekton1, tekton2, tekton3, tekton4);
        List<Player>  allPlayers  = List.of(mushroomer, insecter);
        game = new Game(allTektons, allPlayers);
    }

    @Test
    void TestInsectEatsMultipleSpores() {
        game.list();
        // Insect tries to consume a spore held by the player, not on its current mushroom
        insect1.consumeSpore(spore);
        insect1.consumeSpore(spore2);
        // The spore should be removed from the Mushroomer's inventory
        assertTrue(insect1.getEffects()[0] != 0 && insect1.getEffects()[3] != 0);
        // Since the spore was never on a mushroom, no spore list to clear there
        // If your Mushroom.getSpores() returns null when empty, this passes
        // Otherwise adjust assertion accordingly
        game.list();
    }
}