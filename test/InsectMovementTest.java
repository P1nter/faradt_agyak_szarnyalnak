import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InsectMovementTest {
    private Mushroomer mushroomer;
    private Insecter insecter;
    private Tekton tekton1;
    private Tekton tekton2;
    private Tekton tekton3;
    private Tekton tekton4;
    private Insect insect1;
    private Insect insect2;
    private Mushroom mushroom1;
    private Mushroom mushroom2;
    private MushroomBody mushroomBody1;
    private MushroomBody mushroomBody2;
    private Spore spore;

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
        mushroomBody2 = new MushroomBody(tekton3);
        mushroom1 = new Mushroom();
        mushroom1.addMushroomBody(mushroomBody1);
        mushroom2 = new Mushroom();
        mushroom2.addMushroomBody(mushroomBody2);
        Spore spore = new SlowingSpore(tekton1);
        mushroomer.addSpore(spore);
        mushroom1.addSpore(spore);
    }


}