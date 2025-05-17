// MapBuilder.java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapBuilder {
    public static List<Tekton> build(int count, int maxNeighbors) {
        System.out.println("MapBuilder: Building map - Count: " + count + ", MaxNeighbors: " + maxNeighbors);
        List<Tekton> map = new ArrayList<>();
        if (count <= 0) {
            System.err.println("MapBuilder: count cannot be <= 0.");
            return map;
        }

        Random rnd = new Random();
        // 1) Instantiate Tektons of different types
        for (int i = 0; i < count; i++) {
            int tektonID = i + 1; // Using 1-based indexing for IDs from builder
            Tekton newTekton;
            int typeRoll = rnd.nextInt(100); // For probability distribution

            // Example distribution of Tekton types
            if (typeRoll < 40) { // 40% DefaultTekton
                newTekton = new DefaultTekton(tektonID);
            } else if (typeRoll < 50) { // 10% DisabledBodyGrowthTekton
                newTekton = new DisabledBodyGrowthTekton(tektonID);
            } else if (typeRoll < 60) { // 10% DisappearingYarnTekton
                newTekton = new DisappearingYarnTekton(tektonID);
            } else if (typeRoll < 70) { // 10% FastGrowthTekton
                newTekton = new FastGrowthTekton(tektonID);
            } else if (typeRoll < 80) { // 10% LifeTekton
                newTekton = new LifeTekton(tektonID);
            } else if (typeRoll < 90) { // 10% NoMushroomBodyTekton
                newTekton = new NoMushroomBodyTekton(tektonID);
            } else { // 10% SingleMushroomOnlyTekton
                newTekton = new SingleMushroomOnlyTekton(tektonID);
            }
            map.add(newTekton);
        }
        System.out.println("MapBuilder: Tektons instantiated with types. Count: " + map.size());

        if (count == 1 || maxNeighbors == 0) {
            System.out.println("MapBuilder: No links to create for " + count + " tekton(s) or maxNeighbors=0.");
            return map;
        }

        // 2) For each tekton, establish random links
        for (Tekton t : map) {
            // Ensure maxNeighbors is not more than other available tektons
            int actualMaxNeighbors = Math.min(maxNeighbors, count - 1);
            int attemptsToFindNewCandidate = 0;
            int maxAttemptsForThisTekton = count * 3; // Safety break

            while (t.getAdjacentTektonsNoPrint().size() < actualMaxNeighbors && attemptsToFindNewCandidate < maxAttemptsForThisTekton) {
                Tekton candidate = map.get(rnd.nextInt(count));
                attemptsToFindNewCandidate++;

                if (candidate == t || // Cannot link to self
                        t.getAdjacentTektonsNoPrint().contains(candidate) || // Already linked
                        candidate.getAdjacentTektonsNoPrint().size() >= Math.min(maxNeighbors, count - 1) // Candidate is full (respecting overall maxNeighbors)
                ) {
                    continue;
                }
                t.addAdjacentTekton(candidate); // This method should handle symmetry
                // System.out.println("MapBuilder: Linked Tekton " + t.getIDNoPrint() + " and Tekton " + candidate.getIDNoPrint());
            }
        }
        System.out.println("MapBuilder: Links established.");
        return map;
    }
}