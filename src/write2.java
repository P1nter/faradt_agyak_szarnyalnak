import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Write {

    public static void saveGame(Game game, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            // Tektonok
            writer.write("[Tektons]\n");
            for (Tekton tekton : game.getTektons()) {
                writer.write(tektonInfo(tekton) + "\n");
            }

            // Gombatestek
            writer.write("\n[MushroomBodies]\n");
            for (Tekton tekton : game.getTektons()) {
                MushroomBody body = tekton.getMushroom().getMushroomBody();
                if (body != null) {
                    writer.write(mushroomBodyInfo(body) + "\n");
                }
            }

            // Gombafonalak
            writer.write("\n[MushroomYarns]\n");
            for (Tekton tekton : game.getTektons()) {
                for (MushroomYarn yarn : tekton.getMushroom().getMushroomYarns()) {
                    writer.write(mushroomYarnInfo(yarn) + "\n");
                }
            }

            // Spórák
            writer.write("\n[Spores]\n");
            for (Tekton tekton : game.getTektons()) {
                for (Spore spore : tekton.getMushroom().getSpores()) {
                    writer.write(sporeInfo(spore) + "\n");
                }
            }

            // Rovarok
            writer.write("\n[Insects]\n");
            for (Tekton tekton : game.getTektons()) {
                for (Insect insect : tekton.getInsects()) {
                    writer.write(insectInfo(insect) + "\n");
                }
            }

            System.out.println("\nJátékállás mentése sikeres volt: " + filename);

        } catch (IOException e) {
            System.err.println("Hiba a játék mentése közben: " + e.getMessage());
        }
    }

    private static String tektonInfo(Tekton tekton) {
        StringBuilder builder = new StringBuilder();
        builder.append("TektonID=").append(System.identityHashCode(tekton));
        builder.append(",Type=").append(tekton.getClass().getSimpleName());
        builder.append(",MushroomExists=").append(tekton.getMushroom() != null);
        builder.append(",AdjacentTektons=");
        for (Tekton adj : tekton.getAdjacentTektons()) {
            builder.append(System.identityHashCode(adj)).append(" ");
        }
        return builder.toString().trim();
    }

    private static String mushroomBodyInfo(MushroomBody body) {
        return "MushroomBodyID=" + System.identityHashCode(body) +
                ",TektonID=" + System.identityHashCode(body.getTektons());
    }

    private static String mushroomYarnInfo(MushroomYarn yarn) {
        return "MushroomYarnID=" + System.identityHashCode(yarn) +
                ",FromTekton=" + System.identityHashCode(yarn.getTektons()[0]) +
                ",ToTekton=" + System.identityHashCode(yarn.getTektons()[1]) +
                ",IsCut=" + yarn.getIsCut() +
                ",TimeBack=" + yarn.getTimeBack();
    }

    private static String sporeInfo(Spore spore) {
        return "SporeID=" + System.identityHashCode(spore) +
                ",Type=" + spore.getClass().getSimpleName() +
                ",TektonID=" + System.identityHashCode(spore.tekton) +
                ",Nutrition=" + spore.nutrition;
    }

    private static String insectInfo(Insect insect) {
        int[] effects = insect.getEffects();
        return "InsectID=" + System.identityHashCode(insect) +
                ",TektonID=" + System.identityHashCode(insect.getTekton()) +
                ",Score=" + insect.getScore() +
                ",Action=" + insect.getAction() +
                ",CutDisabled=" + effects[0] +
                ",Paralyzed=" + effects[1] +
                ",SpedUp=" + effects[2] +
                ",SlowedDown=" + effects[3];
    }
}
