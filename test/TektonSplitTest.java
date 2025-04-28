import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        mushroomer = new Mushroomer("Mushroomer");
        insecter = new Insecter("Insecter");
        tekton1 = new DefaultTekton(1);
        tekton2 = new DefaultTekton(2);
        tekton3 = new DefaultTekton(3);
        tekton4 = new DefaultTekton(4);

        tekton1.addAdjacentTekton(tekton2);
        tekton1.addAdjacentTekton(tekton3);
        tekton1.addAdjacentTekton(tekton4);
        tekton2.addAdjacentTekton(tekton3);
        tekton2.addAdjacentTekton(tekton4);
        tekton3.addAdjacentTekton(tekton4);

        insect1 = new Insect(tekton1, insecter,1);
        insect2 = new Insect(tekton2, insecter,2);
        mushroomBody1 = new MushroomBody(tekton1, mushroomer,1);
        mushroomBody3 = new MushroomBody(tekton3, mushroomer,3);
        mushroom1 = new Mushroom(1);
        mushroom1.addMushroomBody(mushroomBody1);
        mushroom3 = new Mushroom(3);
        mushroom3.addMushroomBody(mushroomBody3);

        mushroom2 = new Mushroom(2);
        mushroomYarn1 = new MushroomYarn(tekton1,tekton2,1);
        mushroom2.addMushroomYarn(mushroomYarn1);
        mushroom1.addMushroomYarn(mushroomYarn1);
        List<Tekton> temptektons = new ArrayList<>(tekton1.getAdjacentTektons());
        List<Player> tempplayers = new ArrayList<>();
        tempplayers.add(mushroomer);
        tempplayers.add(insecter);
        game = new Game(temptektons, tempplayers);
    }

    @Test
    void TestDuplicatestPerfectly(){
        game.split(tekton1);
        assertTrue(tekton1.getMushroom().getMushroomYarns().isEmpty());
        assertTrue(game.getTektons().size() == 5);
        assertTrue(tekton1.adjacentTektons.size() == 2);
        assertTrue(tekton1.getAdjacentTektons().contains(tekton2));
        assertTrue(tekton1.getAdjacentTektons().contains(game.getTektons().get(4)));
        assertTrue(game.getTektons().get(4).getAdjacentTektons().contains(tekton1));
        assertTrue(game.getTektons().get(4).getAdjacentTektons().contains(tekton3));
        assertTrue(game.getTektons().get(4).getAdjacentTektons().contains(tekton4));
    }
}
