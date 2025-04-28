import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MushroomBodyGrowthTest {
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

    @BeforeEach
    void setUp() {
        mushroomer = new Mushroomer("Mushroomer");
        insecter = new Insecter("Insecter");
        tekton1 = new DefaultTekton();
        tekton2 = new DefaultTekton();
        tekton3 = new DefaultTekton();
        tekton4 = new DefaultTekton();
        insect1 = new Insect(tekton1);
        insect2 = new Insect(tekton2);
        mushroomBody1 = new MushroomBody(tekton1);
        mushroomBody3 = new MushroomBody(tekton3);
        mushroom1 = new Mushroom();
        mushroom1.addMushroomBody(mushroomBody1);
        mushroom3 = new Mushroom();
        mushroom3.addMushroomBody(mushroomBody3);
    }
    @Test
    void TestGrowMushroomBodyNoSporeNoYarnOrBody (){
        mushroomer.growBody(tekton2);
        assertNull(tekton2.getMushroom().getMushroomBody());
    }
    @Test
    void TestGrowMushroomBodyYesSporeNoYarnOrBody (){
        Spore spore = new SlowingSpore(tekton2);
        mushroomer.addSpore(spore);
        Mushroom mushroom2 = new Mushroom();
        mushroom2.addSpore(spore);
        mushroomer.growBody(tekton2);
        assertNull(tekton2.getMushroom().getMushroomBody());
    }
    @Test
    void TestGrowMushroomBodyYesSporeYesYarnAndBody (){
        Spore spore = new SlowingSpore(tekton2);
        mushroomer.addSpore(spore);
        Mushroom mushroom2 = new Mushroom();
        mushroom2.addSpore(spore);
        mushroomer.growBody(tekton2);
        assertNotNull(tekton2.getMushroom().getMushroomBody());
    }

}
