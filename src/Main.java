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

import java.util.*;

import java.util.*;

public class Main {
    private static Game game;
    private static Map<String, Tekton> tektons = new HashMap<>();
    private static Map<String, Insect> insects = new HashMap<>();
    private static Map<String, Mushroom> mushrooms = new HashMap<>();
    private static Map<String, MushroomYarn> mushroomYarns = new HashMap<>();

    public static void main(String[] args) {
        //Game game = read2.loadGame("test1.txt");
        //write2.saveGame(game,"out.txt");

        System.out.println("Tesztesetek:\n");

        System.out.println("1.\t Gombafonál növekedése");
        System.out.println("2.\t Gombatest növekedése");
        System.out.println("3.\t Rovar mozgása a gombafonálon");
        System.out.println("4.\t Rovarok lassító spóra evése");
        System.out.println("5.\t Rovarok gyorsító spóra evése");
        System.out.println("6.\t Rovarok bénító spóra evése");
        System.out.println("7.\t Rovarok paralizáló spóra evése");
        System.out.println("8.\t Rovarok vágásképtelenné-tevő spóra evése");
        System.out.println("9.\t Rovarok osztódó spóra evése");
        System.out.println("10.\t Tekton törése");
        System.out.println("11.\t Rovarok fonál elvágása");
        System.out.println("12.\t Gombafonál növekedése tiltott tektonra");
        System.out.println("13.\t Rovar próbál megenni egy nem létező spórát");
        System.out.println("14.\t Tekton fenntartja a fonalakat");
        System.out.println("15.\t Fonalak rovarokat fogyasztanak");
        System.out.println("16.\t Nyertes gombász meghatározása");
        System.out.println("17.\t Nyertes rovarász meghatározása\n");
        System.out.println("18.\t Csak egy fonal nőhet egy SingleMushroomOnlyTektonon\n");
        System.out.println("19.\t Fonal eltűnik DisappearingYarnTekton-on néhány kör után\n");
        System.out.println("20.\t Gombafonal nőhet olyan tektonra, ahová test nem\n");
        System.out.println("21.\t Spóra LifeTektonra, majd fonal tiltott tektonra\n");
        System.out.println("22.\t Fonal vágása után késleltetett eltűnés\n");
        System.out.println("23.\t Gombatest halála miatt a fonal eltűnik\n");
        System.out.println("24.\t Fonal nem eheti meg a nem bénult rovart\n");
        System.out.println("25.\t A rovar bénítást kap, a fonál megeszi, majd testet növeszt\n");
        System.out.println("26.\t Spóra LifeTektonra, fonal, mozgás, vágás visszafelé\n");
        System.out.println("27.\t Spóra szórás szomszéd szomszédjára fejlett gombatestből, majd gombatest növesztése\n");
        System.out.println("28.\t Rovar visszafordulna, de elvágta maga mögött a fonalat\n");
        System.out.println("29.\t Rovar megeszik két különböző spórát, és a hatásuk összeadódik\n");
        System.out.println("30.\t Duplikáló spóra, majd a másolat vágni próbál, de vágásképtelen spórát evett\n");
        System.out.print("Választás: ");

        //Válasz beolvasása
        //Ha nem megfelelő, akkor hibát dob
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        if (choice < 1 || choice > 30) {throw new RuntimeException("BProf?");}

        /*initializeTestEnvironment();
        printTestMenu();
        handleUserChoice();*/
    }
    /*
    private static void initializeTestEnvironment() {
        // Initialize 4 Tektons
        for (int i = 1; i <= 4; i++) {
            String id = "tekton_" + i;
            tektons.put(id, new DefaultTekton());
        }

        // Set adjacent Tektons
        tektons.get("tekton_1").addAdjacentTekton(tektons.get("tekton_2"));
        tektons.get("tekton_2").addAdjacentTekton(tektons.get("tekton_1"));
        tektons.get("tekton_2").addAdjacentTekton(tektons.get("tekton_3"));
        tektons.get("tekton_3").addAdjacentTekton(tektons.get("tekton_2"));
        tektons.get("tekton_3").addAdjacentTekton(tektons.get("tekton_4"));
        tektons.get("tekton_4").addAdjacentTekton(tektons.get("tekton_3"));

        // Initialize Insects
        insects.put("insect_1", new Insect(tektons.get("tekton_1")));
        insects.put("insect_2", new Insect(tektons.get("tekton_2")));

        // Add Insects to Tektons
        tektons.get("tekton_1").addNewInsect(insects.get("insect_1"));
        tektons.get("tekton_2").addNewInsect(insects.get("insect_2"));

        // Initialize Mushrooms
        mushrooms.put("mushroom_1", new Mushroom());
        mushrooms.put("mushroom_3", new Mushroom());

        // Add Mushrooms to Tektons
        tektons.get("tekton_1").setMushroom(mushrooms.get("mushroom_1"));
        tektons.get("tekton_3").setMushroom(mushrooms.get("mushroom_3"));

        // Initialize Game
        game = new Game();
        for (Tekton tekton : tektons.values()) {
            game.addTekton(tekton);
        }
    }

    private static void printTestMenu() {
        System.out.println("====== Test Cases ======");
        System.out.println("1. Grow Mushroom Yarn");
        System.out.println("2. Grow Mushroom Body");
        System.out.println("3. Insect Movement");
        System.out.print("Choose a test case (1-3): ");
    }

    private static void handleUserChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                testGrowMushroomYarn();
                break;
            case 2:
                testGrowMushroomBody();
                break;
            case 3:
                testInsectMovement();
                break;
            default:
                System.out.println("Invalid choice.");
        }
        scanner.close();
    }

    private static void testGrowMushroomYarn() {
        System.out.println("\n=== Test: Grow Mushroom Yarn ===");
        Mushroom mushroom = mushrooms.get("mushroom_1");
        Tekton tekton1 = tektons.get("tekton_1");
        Tekton tekton2 = tektons.get("tekton_2");

        MushroomYarn yarn = new MushroomYarn(tekton1, tekton2);
        mushroom.getMushroomYarns().add(yarn);
        mushroomYarns.put("mushroomyarn_1", yarn);

        System.out.println("Mushroom Yarn created between Tekton_1 and Tekton_2.");
    }

    private static void testGrowMushroomBody() {
        System.out.println("\n=== Test: Grow Mushroom Body ===");
        Mushroom mushroom = mushrooms.get("mushroom_1");
        Tekton tekton2 = tektons.get("tekton_2");

        MushroomBody body = new MushroomBody(tekton2);
        mushroom.setMushroomBody(body);

        System.out.println("Mushroom Body grown on Tekton_2.");
    }

    private static void testInsectMovement() {
        System.out.println("\n=== Test: Insect Movement ===");
        Insect insect = insects.get("insect_1");
        MushroomYarn yarn = mushroomYarns.get("mushroomyarn_1");

        if (insect.move(yarn)) {
            System.out.println("Insect_1 moved to Tekton_2.");
        } else {
            System.out.println("Insect_1 failed to move.");
        }
    }
    */
}