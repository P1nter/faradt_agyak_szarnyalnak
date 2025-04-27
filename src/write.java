import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class write {
    private BufferedWriter writer;

    /**
     * Constructs a persistent writer that writes game details to a file.
     *
     * @param filePath The path of the file to write to.
     * @throws IOException If an I/O error occurs.
     */
    public write(String filePath) throws IOException {
        writer = new BufferedWriter(new FileWriter(filePath, true)); // Append mode
    }

    /**
     * Saves the entire game state to the file.
     *
     * @param game The Game object to save.
     * @throws IOException If an I/O error occurs.
     */
    public void save(Game game) throws IOException {
        writer.write("GAME STATE:");
        writer.newLine();

        // Log all players
        writer.write("Players:");
        writer.newLine();
        for (Player player : game.getPlayers()) {
            writer.write("  PLAYER: " + player.getName());
            writer.newLine();
            writer.write("    Type: " + (player instanceof Insecter ? "Insecter" : "Mushroomer"));
            writer.newLine();
            writer.write("    Score: " + player.getScore());
            writer.newLine();
            writer.write("    Actions: " + player.getAction());
            writer.newLine();

            if (player instanceof Insecter) {
                Insecter insecter = (Insecter) player;
                writer.write("    Insects:");
                writer.newLine();
                for (Insect insect : insecter.getInsects()) {
                    writer.write("      Insect:");
                    writer.newLine();
                    writer.write("        Location: " + (insect.getTekton() != null ? insect.getTekton().getClass().getSimpleName() : "None"));
                    writer.newLine();
                    writer.write("        Score: " + insect.getScore());
                    writer.newLine();
                    writer.write("        Actions: " + insect.getAction());
                    writer.newLine();
                    writer.write("        Effects: " + java.util.Arrays.toString(insect.getEffects()));
                    writer.newLine();
                }
            }
        }

        // Log all Tektons
        writer.write("Tektons:");
        writer.newLine();
        for (Tekton tekton : game.getTektons()) {
            writer.write("  TEKTON: " + tekton.getClass().getSimpleName());
            writer.newLine();
            writer.write("    Adjacent Tektons: " + tekton.getAdjacentTektons().size());
            writer.newLine();
            writer.write("    Insects: " + tekton.getInsects().size());
            writer.newLine();
            writer.write("    Mushroom: " + (tekton.getMushroom() != null ? "Present" : "None"));
            writer.newLine();

            if (tekton.getMushroom() != null) {
                Mushroom mushroom = tekton.getMushroom();
                writer.write("    Mushroom Details:");
                writer.newLine();
                writer.write("      Has Body: " + mushroom.hasMushroomBody());
                writer.newLine();
                writer.write("      Yarns: " + mushroom.getMushroomYarns().size());
                writer.newLine();
                writer.write("      Spores: " + mushroom.getSpores().size());
                writer.newLine();
            }
        }

        writer.newLine();
        writer.flush();
    }

    /**
     * Closes the writer.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void close() throws IOException {
        writer.close();
    }
}