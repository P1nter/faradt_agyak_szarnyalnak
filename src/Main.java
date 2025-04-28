import java.io.*;
import java.util.*;

public class Main {
    private static Game game;
    private static Map<String, Tekton> tektons = new HashMap<>();
    private static Map<String, Insect> insects = new HashMap<>();
    private static Map<String, Mushroom> mushrooms = new HashMap<>();
    private static Map<String, MushroomYarn> mushroomYarns = new HashMap<>();

    public static void main(String[] args) {

        System.out.println("Tesztesetek:\n");

        System.out.println("1.\t Rovar fonalat vág és eltűnik a fonál");
        System.out.println("2.\t Rovar olyan fonalat vág, ami nem szomszéod, így nem tűnik el");
        System.out.println("3.\t Rovar megeszik egy CutDisabling spórát");
        System.out.println("4.\t Rovar megeszik több spórát, megkapja a hatásait");
        System.out.println("5.\t Rovar megeszik duplicating spórát, duplikálódik");
        System.out.println("6.\t Rovar megeszik paralízáló spórát, paralizálódik");
        System.out.println("7.\t Rovar megeszik lassító spórát, lassul");
        System.out.println("8.\t Rovar megeszik gyorsító spórát, gyorsul");
        System.out.println("9.\t Rovar átlép szomszédos tektonrra");
        System.out.println("10.\t Rovar nem tud átlépni szomszédos tektonra mert nincs fonál");
        System.out.println("11.\t Gombatest növesztés, de nincs spóra se fonál");
        System.out.println("12.\t Gombatest növesztés, van spóra, de nincs fonál");
        System.out.println("13.\t Gombatest növesztés, van fonál, de nincs spóra");
        System.out.println("14.\t Gombatest növesztése, van spóra és fonál, de már van gombatest");
        System.out.println("15.\t Gombatest növesztése, van spóra van fonál de nincs gombatest");
        System.out.println("16.\t Gombatest növesztése, de NoMushroomBodyTekton");
        System.out.println("17.\t Fonál növesztőkészség tesztelése\n");
        System.out.println("18.\t DisAppearingYarnTektonon eltűnik a Yarn\n");
        System.out.println("19.\t Yarn tud nőni ahova Body nem\n");
        System.out.println("20.\t Gombafonál növesztés\n");
        System.out.println("21.\t Life tekton életben tartja a fonalat\n");
        System.out.println("22.\t Tekton split\n");
        System.out.println("23.\t Insecter győztes\n");
        System.out.println("24.\t Mushroomer győzte\n");
        System.out.println("25.\t Fonál megeszi a paralyzed rovart\n");
        System.out.print("Választás: ");

        //Válasz beolvasása
        //Ha nem megfelelő, akkor hibát dob
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();

        InsectCutsYarnTest test1 = new InsectCutsYarnTest();
        InsectEatsCutDisablingSporeTest test2 = new InsectEatsCutDisablingSporeTest();
        InsectEatsDifferentSporesTest test3 = new InsectEatsDifferentSporesTest();
        InsectEatsDuplicatingSporeTest test4 = new InsectEatsDuplicatingSporeTest();
        InsectEatsParalyzingSporeTest test5 = new InsectEatsParalyzingSporeTest();
        InsectEatsSlowingSporeTest test6 = new InsectEatsSlowingSporeTest();
        InsectEatsSpeedingSporeTest test7 = new InsectEatsSpeedingSporeTest();
        InsectMovementTest test8 = new InsectMovementTest();
        MushroomBodyGrowthTest test9 = new MushroomBodyGrowthTest();
        MushroomYarnGrowthTest test10 = new MushroomYarnGrowthTest();
        TektonKeepsYarnAliveTest test11 = new TektonKeepsYarnAliveTest();
        TektonSplitTest test12 = new TektonSplitTest();
        WinningInsecterTest test13 = new WinningInsecterTest();
        WinningMushroomerTest test14 = new WinningMushroomerTest();
        YarnEatsInsectTest test15 = new YarnEatsInsectTest();


        switch (choice) {
            case 1:
                test1.setUp();
                test1.TestCutsYarnAndItDisappears();
                break;
            case 2:
                test1.setUp();
                test1.TestCantCutThroughTimeAndSpace();
                break;
            case 3:
                test2.setUp();
                test2.TestInsectEatsCutDisablingSporeSporeAndGetsSlowed();
                break;
            case 4:
                test3.setUp();
                test3.TestInsectEatsMultipleSpores();
                break;
            case 5:
                test4.setUp();
                test4.testDuplicatingSporeProducesExtraInsect();
                break;
            case 6:
                test5.setUp();
                test5.testParalyzingSporeGivesParalysis();
                break;
            case 7:
                test6.setUp();
                test6.testSlowingSporeAppliesSlowEffect();
                break;
            case 8:
                test7.setUp();
                test7.testSpeedingSporeAppliesSpeedEffect();
                break;
            case 9:
                test8.setUp();
                test8.TestCanMoveToAdjacentTekton();
                break;
            case 10:
                test8.setUp();
                test8.TestYarnDoesntExistOnSameTekton();
                break;
            case 11:
                test9.setUp();
                test9.TestGrowMushroomBodyNoSporeNoYarn();
                break;
            case 12:
                test9.setUp();
                test9.TestGrowMushroomBodyYesSporeNoYarn();
                break;
            case 13:
                test9.setUp();
                test9.TestGrowMushroomBodyNoSporeYesYarn();
                break;
            case 14:
                test9.setUp();
                test9.TestGrowMushroomBodyYesSporeYesYarnButAlreadyBody();
                break;
            case 15:
                test9.setUp();
                test9.TestGrowMushroomBodyYesSporeYesYarnAndNoBody();
                break;
            case 16:
                test9.setUp();
                test9.TestGrowMushroomOnNoMushroomBodyTekton();
                break;
            case 17:
                test10.setUp();
                test10.testWhereYarnDisappearsOnDis();
                break;
            case 18:
                test10.setUp();
                test10.testWhereYarnDisappearsOnDis();
                break;
            case 19:
                test10.setUp();
                test10.TestYarnCanGrowWhereBodyCant();
                break;
            case 20:
                test10.setUp();
                test10.testMushroomYarnGrowth();
                break;
            case 21:
                test11.setUp();
                test11.TestLifeTektonKeepsYarnAlive();
                break;
            case 22:
                test12.setUp();
                test12.TestDuplicatestPerfectly();
                break;
            case 23:
                test13.setUp();
                test13.testWinningInsecter();
                break;
            case 24:
                test14.setUp();
                test14.testWinningMushroomer();
                break;
            case 25:
                test15.setUp();
                test15.TestYarnEatsInsect();
                break;
            default:
                throw new RuntimeException("BProf?");
        }
    }
}