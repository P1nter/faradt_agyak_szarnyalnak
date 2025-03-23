import java.io.*;
import java.util.*;

public class Main {
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
                System.out.println("Hupszi");
                break;
            case 7:
                defaultMushroom.spread();
                break;
            case 8:
                defaultMushroom.spread();
                break;
            case 9:
                break;
            case 10:
                defaultInsect.move(defaultMushroomYarn);
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
            case 15:
                break;
            case 16:
                break;
            case 17:
                break;
            default:
                break;
        }


        in.close();
    }
}