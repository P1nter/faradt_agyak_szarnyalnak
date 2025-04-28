import java.io.*;
import java.util.*;

public class read {
    private BufferedReader reader;

    /**
     * Constructs a reader to read game state from a file.
     *
     * @param filePath The path of the file to read from.
     * @throws IOException If an I/O error occurs.
     */
    public read(String filePath) throws IOException {
        reader = new BufferedReader(new FileReader(filePath));
    }

    /**
     * Reads the game state from the file and initializes a Game object.
     *
     * @return The initialized Game object.
     * @throws IOException If an I/O error occurs.
     */
    public Game load() throws IOException {
        Game game = new Game();
        Map<String, Tekton> tektonMap = new HashMap<>();
        Map<String, Player> playerMap = new HashMap<>();

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.startsWith("PLAYER:")) {
                String playerName = line.split(":")[1].trim();
                line = reader.readLine().trim();
                String type = line.split(":")[1].trim();
                Player player = type.equals("Insecter") ? new Insecter(playerName) : new Mushroomer(playerName);
                playerMap.put(playerName, player);
                game.addPlayer(player);

                if (player instanceof Insecter) {
                    while ((line = reader.readLine().trim()).startsWith("Insect:")) {
                        Insecter insecter = (Insecter) player;
                        line = reader.readLine().trim();
                        String location = line.split(":")[1].trim();
                        Tekton tekton = tektonMap.get(location);
                        if (tekton == null) {
                            tekton = new DefaultTekton();
                            tektonMap.put(location, tekton);
                            game.addTekton(tekton);
                        }
                        Insect insect = new Insect(tekton,insecter);
                        insect.setOwner(insecter);
                        insecter.addInsect(insect);
                        tekton.addNewInsect(insect);
                    }
                }
            } else if (line.startsWith("TEKTON:")) {
                String tektonName = line.split(":")[1].trim();
                Tekton tekton = tektonMap.getOrDefault(tektonName, new DefaultTekton());
                tektonMap.put(tektonName, tekton);
                game.addTekton(tekton);

                while ((line = reader.readLine().trim()).startsWith("Mushroom Details:")) {
                    Mushroom mushroom = new Mushroom();
                    line = reader.readLine().trim();
                    boolean hasBody = Boolean.parseBoolean(line.split(":")[1].trim());
                    if (hasBody) {
                        mushroom.setMushroomBody(new MushroomBody());
                    }
                    tekton.setMushroom(mushroom);
                }
            }
        }

        return game;
    }

    /**
     * Closes the reader.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void close() throws IOException {
        reader.close();
    }
}