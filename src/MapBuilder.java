// MapBuilder.java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapBuilder {
    public static List<Tekton> build(int count, int maxNeighbors) {
        System.out.println("MAP_BUILDER: build START - Count: " + count + ", MaxNeighbors: " + maxNeighbors);
        List<Tekton> map = new ArrayList<>();
        if (count <= 0) return map; // Handle edge case

        // 1) instantiate tektons
        for (int i = 0; i < count; i++) {
            // DefaultTekton now needs a unique ID if Tekton.getIDNoPrint() is used for sorting/Map keys.
            // Let's assume Tekton or DefaultTekton constructor handles ID assignment (e.g., static counter).
            // If not, you need to assign IDs here. For now, I'll assume they get unique IDs.
            map.add(new DefaultTekton(i + 1)); // Pass ID to constructor
        }
        System.out.println("MAP_BUILDER: Tektons instantiated.");

        if (count == 1 || maxNeighbors == 0) { // No links needed if only one tekton or maxNeighbors is 0
            System.out.println("MAP_BUILDER: build END - No links to create.");
            return map;
        }

        Random rnd = new Random();
        // 2) for each tekton, establish random links
        for (Tekton t : map) {
            // System.out.println("MAP_BUILDER: Processing Tekton ID: " + t.getIDNoPrint());
            // Ensure maxNeighbors is not more than other available tektons
            int actualMaxNeighbors = Math.min(maxNeighbors, count - 1);

            while (t.getAdjacentTektonsNoPrint().size() < actualMaxNeighbors) { // Use NoPrint here for the loop condition
                int attempts = 0; // To prevent truly infinite loop if logic is stuck
                Tekton candidate = map.get(rnd.nextInt(count));

                // Basic validation for candidate
                if (candidate == t || t.getAdjacentTektonsNoPrint().contains(candidate)) {
                    attempts++;
                    if (attempts > count * 2 && count > 1) { // Safety break if stuck finding a new candidate
                        // System.out.println("MAP_BUILDER: Stuck finding new candidate for Tekton " + t.getIDNoPrint() + ", breaking inner loop.");
                        break;
                    }
                    continue;
                }

                // Check if candidate can accept more neighbors
                if (candidate.getAdjacentTektonsNoPrint().size() >= actualMaxNeighbors) {
                    attempts++;
                    if (attempts > count * 2 && count > 1) {
                        // System.out.println("MAP_BUILDER: Stuck finding candidate with space for Tekton " + t.getIDNoPrint() + ", breaking inner loop.");
                        break;
                    }
                    continue;
                }

                // Link them (this should handle symmetry)
                // System.out.println("MAP_BUILDER: Linking " + t.getIDNoPrint() + " and " + candidate.getIDNoPrint());
                t.addAdjacentTekton(candidate); // This one call should make them adjacent to each other

                // No need for: candidate.addAdjacentTekton(t); as it's handled by t.addAdjacentTekton

                // Break early if this tekton t is fully linked
                if (t.getAdjacentTektonsNoPrint().size() >= actualMaxNeighbors) {
                    break;
                }
            }
            // System.out.println("MAP_BUILDER: Finished processing Tekton ID: " + t.getIDNoPrint() + ", Adjacents: " + t.getAdjacentTektonsNoPrint().size());
        }
        System.out.println("MAP_BUILDER: build END - Links established.");
        return map;
    }
}