import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WinningInsecterTest {
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
    private Insecter insecter2;
    private Game game;
    @BeforeEach
    void setUp() {
        // 1) Player
        mushroomer = new Mushroomer("Mushroomer");
        insecter = new Insecter("Insecter");
        insecter2 = new Insecter("Insecter2");
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
    void testWinningInsecter() {
        game.list();
        insecter.setScore(15);
        insecter2.setScore(10);
        assertEquals(insecter, game.determineInsecterWinner());
        game.list();
    }
}
