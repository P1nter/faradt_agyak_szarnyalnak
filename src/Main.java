import javax.swing.*;
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

public class Main {
    private static Game game;
    private static Map<String, Tekton> tektons = new HashMap<>();
    private static Map<String, Insect> insects = new HashMap<>();
    private static Map<String, Mushroom> mushrooms = new HashMap<>();
    private static Map<String, MushroomYarn> mushroomYarns = new HashMap<>();

    public static void main(String[] args) {
        // Swing komponensek biztonságos inicializálása
        SwingUtilities.invokeLater(() -> {
            GameMenu mainMenu = new GameMenu();
            mainMenu.setVisible(true);  // Menü megjelenítése
            mainMenu.setLocationRelativeTo(null);  // Ablak középre igazítása

            //help me start the menu
            
        });
    }

}