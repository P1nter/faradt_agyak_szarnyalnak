import java.util.List;

public class Game {
    private List<ITekton> tektons;

    //Teszteléshez
    DefaultTekton defaultTekton;
    Insect insect;
    Mushroom mushroom;
    public Mushroom getMushroom() {return mushroom;}

    public Game() {
        System.out.println("Game.Game() called");

       defaultTekton = new DefaultTekton();
        insect = new Insect();
        mushroom = new Mushroom();

        System.out.println("Game.Game() returned");
    }
    public void determineWinner() {
        System.out.println("Game.DetermineWinner() called");

        insect.getScore();
        mushroom.getScore();

        System.out.println("Game.DetermineWinner() returned");
    }

    public void update() {
        System.out.println("Game.update() called");

        defaultTekton.split();

        System.out.println("Game.update() returned");
    }
}