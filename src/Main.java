import java.io.*;
import java.util.*;

/*public class Main {
    public static void main(String[] args) {
        //Alap tesztesetek
        System.out.println("Tesztesetek:\n");

        System.out.println("1.\t A játék elkezdődik");
        System.out.println("2.\t A játék véget ér");
        System.out.println("3.\t Kettétörik egy tekton");
        System.out.println("4.\t Uj gombatest no");
        System.out.println("5.\t A gombatest spórát szór a szomszéd tektonra");
        System.out.println("6.\t A gombatest spórát szór a szomszéd szomszédjának a tektonjára");
        System.out.println("7.\t A gombatestből gombafonál nől");
        System.out.println("8.\t A gombatestből gombafonál nől egy olyan tektonra, amin van spóra");
        System.out.println("9.\t A gombatest elpusztul, mert véges alkalommal tud csak spórát szórni");
        System.out.println("10.\t A rovar átmegy egy másik tektonra");
        System.out.println("11.\t A rovar elvág egy gombafonalat");
        System.out.println("12.\t A rovar megeszik egy gyorsító spórát");
        System.out.println("13.\t A rovar megeszik egy lassító spórát");
        System.out.println("14.\t A rovar megeszik egy bénító spórát");
        System.out.println("15.\t A rovar megeszik egy olyan spórát, amitől képtelen gombafonalat vágni");
        System.out.println("16.\t A spóra hatása elmúlik");
        System.out.println("17.\t A gombafonál felszívódik egy idő után, mert felszivódó-fonalú tektonon van\n");
        System.out.print("Választás: ");

        //Válasz beolvasása
        //Ha nem megfelelő, akkor hibát dob
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        if (choice < 1 || choice > 17) {throw new RuntimeException("BProf?");}

        Game defaultGame = new Game();
        Mushroom defaultMushroom = new Mushroom();
        MushroomBody defaultMushroomBody = new MushroomBody();
        Insect defaultInsect = new Insect();
        MushroomYarn defaultMushroomYarn = new MushroomYarn();
        SpeedingSpore defaultSpeedingSpore = new SpeedingSpore();
        SlowingSpore defaultSlowingSpore = new SlowingSpore();
        ParalyzingSpore defaultParalyzingSpore = new ParalyzingSpore();
        CutDisablingSpore defaultCutDisablingSpore = new CutDisablingSpore();
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }

        switch (choice) {
            case 1:
                Game game = new Game();
                break;
            case 2:
                defaultGame.determineWinner();
                break;
            case 3:
                defaultGame.update();
                break;
            case 4:
                defaultMushroom.growBody();
                break;
            case 5:
//                System.out.print("Szóródjanak szét spórák? 0/1");
//                int choice2 = in.nextInt();
//                if (choice2 < 0 || choice2 > 1) {throw new RuntimeException("BProf?");}

                defaultMushroom.instructSporeRelease(defaultMushroomBody);

                break;
            case 6:
                System.out.println("Ugyanaz mint az 5ös");
                break;
            case 7:
                defaultMushroom.spread();
                break;
            case 8:
                defaultMushroom.spread();
                break;
            case 9:
                defaultMushroomBody.destroyBody();
                break;
            case 10:
                defaultInsect.move(defaultMushroomYarn);
                break;
            case 11:
                defaultInsect.cut(defaultMushroomYarn);
                break;
            case 12:
                defaultInsect.consumeSpore(defaultSpeedingSpore);
                break;
            case 13:
                defaultInsect.consumeSpore(defaultSlowingSpore);
                break;
            case 14:
                defaultInsect.consumeSpore(defaultParalyzingSpore);
                break;
            case 15:
                defaultInsect.consumeSpore(defaultCutDisablingSpore);
                break;
            case 16:
                defaultInsect.effectsRemove(defaultParalyzingSpore);
                break;
            case 17:
                defaultMushroomYarn.decay();
                break;
            default:
                break;
        }


        in.close();
    }
}*/
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize the game state
        Game game = new Game();

        // Create Tektons
        Tekton tekton_1 = new DefaultTekton();
        Tekton tekton_2 = new DefaultTekton();
        Tekton tekton_3 = new DefaultTekton();
        Tekton tekton_4 = new DefaultTekton();

        // Set adjacent Tektons
        tekton_1.addAdjacentTekton(tekton_2);
        tekton_2.addAdjacentTekton(tekton_1);
        tekton_2.addAdjacentTekton(tekton_3);
        tekton_3.addAdjacentTekton(tekton_2);
        tekton_3.addAdjacentTekton(tekton_4);
        tekton_4.addAdjacentTekton(tekton_3);

        // Add Tektons to the game
        game.addTekton(tekton_1);
        game.addTekton(tekton_2);
        game.addTekton(tekton_3);
        game.addTekton(tekton_4);

        // Create Insects
        Insect insect_1 = new Insect(tekton_1);
        Insect insect_2 = new Insect(tekton_2);

        // Add Insects to Tektons
        tekton_1.addNewInsect(insect_1);
        tekton_2.addNewInsect(insect_2);

        // Create Mushrooms
        Mushroom mushroom_1 = new Mushroom();
        Mushroom mushroom_3 = new Mushroom();

        // Add Mushrooms to Tektons
        tekton_1.setMushroom(mushroom_1);
        tekton_3.setMushroom(mushroom_3);

        // Test Case 1: Gombafonál növekedése
        System.out.println("Test Case 1: Gombafonál növekedése");
        MushroomYarn mushroomyarn_1 = new MushroomYarn(tekton_1, tekton_2);
        mushroom_1.getMushroomYarns().add(mushroomyarn_1);
        System.out.println("ID: mushroomyarn_1");
        System.out.println("Tektons: [tekton_1, tekton_2]");
        System.out.println();

        // Test Case 2: Gombatest növekedése
        System.out.println("Test Case 2: Gombatest növekedése");
        //mushroom_1.releaseSpore();
        MushroomBody mushroombody_1 = new MushroomBody(tekton_2);
        tekton_2.setMushroom(mushroom_1);
        System.out.println("ID: mushroombody_1");
        System.out.println("Tekton: tekton_2");
        System.out.println("Spore: 0");
        System.out.println();

        // Test Case 3: Rovar mozgása a gombafonálon
        System.out.println("Test Case 3: Rovar mozgása a gombafonálon");
        insect_1.move(mushroomyarn_1);
        System.out.println("ID: insect_1");
        System.out.println("Tekton: tekton_2");
        System.out.println("Stunned for: 0");
        System.out.println("Paralyzed for: 0");
        System.out.println("Slowed for: 0");
        System.out.println("Speeded for: 0");
        System.out.println("Can't cut for: 0");
        System.out.println();
    }
}