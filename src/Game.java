import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Tekton> tektons;
    private List<Player> players;

    private Player activePlayer;

    //generál egy standart mappot, vagy betölt egyet ha úgy inditod el, játékosokat hoz létre és kontorllálja h kinek a köre van

    public Game() {

        tektons = new ArrayList<>();
        players = new ArrayList<>();

    }

    public void split(Tekton tekton){
        //split the tekton into two tektons
        int half = tekton.getAdjacentTektons().size()/2;

        List<Tekton> newadjacentTektons = new ArrayList<>();
        for(int i = 0; i < half; i++){
            newadjacentTektons.add(tekton.getAdjacentTektons().get(i));
            tekton.getAdjacentTektons().remove(i);
        }

        Tekton newTekton = new DefaultTekton(newadjacentTektons);

        int halfInsects = tekton.getInsects().size()/2;
        for(int i = 0; i < halfInsects; i++){
            tekton.getInsects().get(i).setTekton(newTekton);
            tekton.getInsects().remove(i);
            newTekton.addNewInsect(tekton.getInsects().get(i));
        }
        tekton.getMushroom().removeMushroomBody();
        //delete all yarn from tekton
        for(MushroomYarn mushroomYarn : tekton.getMushroom().getMushroomYarns()){
            mushroomYarn.getTektons()[0].getMushroom().getMushroomYarns().remove(mushroomYarn);
            mushroomYarn.getTektons()[1].getMushroom().getMushroomYarns().remove(mushroomYarn);
        }
        newadjacentTektons.add(tekton);
        tekton.addAdjacentTekton(newTekton);
        Mushroom newMushroom = new Mushroom();
        newTekton.setMushroom(newMushroom);

        //add the new tekton to the game
        tektons.add(newTekton);
    }
    public void addPlayer(Player player){
        players.add(player);
    }
    public void addTekton(Tekton tekton) {
        tektons.add(tekton);
    }
    public void removeTekton(Tekton tekton) {
        tektons.remove(tekton);
    }
    public void removePlayer(Player player){
        players.remove(player);
    }
    public List<Tekton> getTektons() {
        return tektons;
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
            tekton.getMushroom().Update();
        }
    }

}