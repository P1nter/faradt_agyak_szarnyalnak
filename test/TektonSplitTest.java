import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TektonSplitTest {
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

    private MushroomYarn mushroomYarn1;
    private Mushroom mushroom2;
    private Game game;
    @BeforeEach
    void setUp() {
        // 1) Player
        mushroomer = new Mushroomer("Mushroomer");
        insecter = new Insecter("Insecter");
        // 2) Tektons
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

        mushroomYarn1 = new MushroomYarn(tekton3, tekton4, mushroomer, 1);
        List<Tekton> all = new ArrayList<>();
        all.add(tekton1);
        all.add(tekton2);
        all.add(tekton3);
        all.add(tekton4);

        List<Player> players = new ArrayList<>();
        players.add(mushroomer);
        players.add(insecter);
        game = new Game(all, players);
    }
    @Test
    void TestDuplicatestPerfectly(){
        game.split(tekton1);
        assertTrue(tekton1.getMushroom().getMushroomYarns().isEmpty());
        assertEquals(5, game.getTektons().size());
        game.list();
        assertEquals(3, tekton1.adjacentTektons.size());
        assertFalse(tekton1.getAdjacentTektons().contains(tekton2));
        assertTrue(tekton1.getAdjacentTektons().contains(game.getTektons().get(4)));
        assertTrue(game.getTektons().get(4).getAdjacentTektons().contains(tekton1));
        assertTrue(game.getTektons().get(4).getAdjacentTektons().contains(tekton2));
        assertFalse(game.getTektons().get(4).getAdjacentTektons().contains(tekton4));
    }
}
