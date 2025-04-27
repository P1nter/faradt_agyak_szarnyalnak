import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Tekton> tektons;
    private List<Player> players;
    private List<Insect> insects;
    private List<Mushroom> mushrooms;
    private Player activePlayer;

    //generál egy standart mappot, vagy betölt egyet ha úgy inditod el, játékosokat hoz létre és kontorllálja h kinek a köre van

    public Game() {

        tektons = new ArrayList<>();
        insects = new ArrayList<>();
        mushrooms = new ArrayList<>();
        players = new ArrayList<>();

    }

    public void addPlayer(Player player){
        players.add(player);
    }
    public void addTekton(Tekton tekton) {
        tektons.add(tekton);
    }
    public void addInsect(Insect insect) {
        insects.add(insect);
    }
    public void addMushroom(Mushroom mushroom) {
        mushrooms.add(mushroom);
    }
    public void removeTekton(Tekton tekton) {
        tektons.remove(tekton);
    }
    public void removeInsect(Insect insect) {
        insects.remove(insect);
    }
    public void removeMushroom(Mushroom mushroom) {
        mushrooms.remove(mushroom);
    }
    public void removePlayer(Player player){
        players.remove(player);
    }
    public List<Tekton> getTektons() {
        return tektons;
    }
    public List<Insect> getInsects() {
        return insects;
    }
    public List<Mushroom> getMushrooms() {
        return mushrooms;
    }
    public List<Player> getPlayers(){
        return players;
    }

    public void nextPlayer(){
        int index = players.indexOf(activePlayer);

        if(index == players.size()-1){
            activePlayer = players.get(0);
            this.update();
        } else{ activePlayer = players.get(index++); }
    }

    public void determineWinner(){
        Player winner = players.get(0);
        for(Player player:players){
            if(player.getScore() > winner.getScore()) winner = player;
        }

        System.out.println("The winner is: "+winner);
    }
    public void update(){
        //update all insects and mushrooms
        for (Tekton tekton : tektons) {
            for(Insect insect : tekton.getInsects()){
                insect.nextTurn();
            }

            tekton.getMushroom().update();
        }
    }

}