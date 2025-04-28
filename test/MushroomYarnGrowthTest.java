import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MushroomYarnGrowthTest {
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

    @Test
    void testWhereCanIGrowMushroomYarnNotAdjacent() {
        assertTrue(mushroomer.whereCanIGrowYarnsFromThisTekton(tekton1).isEmpty());
    }

    @Test
    void testWhereCanIGrowMushroomYarnAdjacent() {
        tekton1.adjacentTektons.add(tekton2);
        assertEquals(mushroomer.whereCanIGrowYarnsFromThisTekton(tekton1).getFirst(), tekton2);
    }

    @Test
    void testWhereCanIGrowMushroomYarnSingleMushroomOnlyHasYarn() {
        tekton1 = new SingleMushroomOnlyTekton();
        tekton1.getMushroom().getMushroomYarns().add(new MushroomYarn(tekton1, tekton2));
        assertTrue(mushroomer.whereCanIGrowYarnsFromThisTekton(tekton1).isEmpty());
    }

    @Test
    void testMushroomYarnGrowth() {
        int initTekton1YarnSize = tekton1.getMushroom().getMushroomYarns().size();
        int initTekton2YarnSize = tekton2.getMushroom().getMushroomYarns().size();

        tekton1.adjacentTektons.add(tekton2);
        mushroomer.GrowYarn(tekton1, tekton2);

        assertEquals(initTekton1YarnSize + 1, tekton1.getMushroom().getMushroomYarns().size());
        assertEquals(initTekton2YarnSize + 1, tekton2.getMushroom().getMushroomYarns().size());
    }
}


public class InsectEatsSlowingSporeTest(){
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
    @Test
    void TestInsectEatsSlowingSporeSporeDoesntDisappear(){
        insect1.consumeSpore(spore);
        assertTrue(tekton1.getMushroom().getSpores().isEmpty());
    }
    @Test
    void TestInsectEatsSlowingSporeSporeButDoesntGetSlowed(){
        insect1.consumeSpore(spore);
        assertTrue(insect1.getEffects()[3] == 0);
    }
    @Test
    void TestInsectEatsSlowingSporeSporeAndGetsSlowed(){
        insect1.consumeSpore(spore);
        assertTrue(insect1.getEffects()[3] != 0);
    }
}
public class InsectEatsSpeedingSporeTest(){
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
        Spore spore = new SpeedingSpore(tekton1);
        mushroomer.addSpore(spore);
        mushroom1.addSpore(spore);
    }
    @Test
    void TestInsectEatsSpeedingSporeSporeDoesntDisappear(){
        insect1.consumeSpore(spore);
        assertTrue(tekton1.getMushroom().getSpores().isEmpty());
    }
    @Test
    void TestInsectEatsSpeedingSporeSporeButDoesntGetSlowed(){
        insect1.consumeSpore(spore);
        assertTrue(insect1.getEffects()[2] == 0);
    }
    @Test
    void TestInsectEatsSpeedingSporeSporeAndGetsSlowed(){
        insect1.consumeSpore(spore);
        assertTrue(insect1.getEffects()[2] != 0);
    }
}
public class InsectEatsCutDisablingSporeTest(){
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
        Spore spore = new CutDisablingSpore(tekton1);
        mushroomer.addSpore(spore);
        mushroom1.addSpore(spore);
    }
    @Test
    void TestInsectEatsCutDisablingSporeSporeDoesntDisappear(){
        insect1.consumeSpore(spore);
        assertTrue(tekton1.getMushroom().getSpores().isEmpty());
    }
    @Test
    void TestInsectEatsCutDisablingSporeSporeButDoesntGetSlowed(){
        insect1.consumeSpore(spore);
        assertTrue(insect1.getEffects()[0] == 0);
    }
    @Test
    void TestInsectEatsCutDisablingSporeSporeAndGetsSlowed(){
        insect1.consumeSpore(spore);
        assertTrue(insect1.getEffects()[0] != 0);
    }
}
public class InsectEatsParalyzingSporeTest(){
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
        Spore spore = new ParalyzingSpore(tekton1);
        mushroomer.addSpore(spore);
        mushroom1.addSpore(spore);
    }
    @Test
    void TestInsectEatsParalyzingSporeSporeDoesntDisappear(){
        insect1.consumeSpore(spore);
        assertTrue(tekton1.getMushroom().getSpores().isEmpty());
    }
    @Test
    void TestInsectEatsParalyzingSporeSporeButDoesntGetSlowed(){
        insect1.consumeSpore(spore);
        assertTrue(insect1.getEffects()[1] == 0);
    }
    @Test
    void TestInsectEatsParalyzingSporeSporeAndGetsSlowed(){
        insect1.consumeSpore(spore);
        assertTrue(insect1.getEffects()[1] != 0);
    }
}
