/*import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class read2 {

    public static Game loadGame(String filename) {
        Game game = new Game();
        Map<Integer, Tekton> tektonMap = new HashMap<>();
        Map<Integer, MushroomBody> bodyMap = new HashMap<>();
        Map<Integer, MushroomYarn> yarnMap = new HashMap<>();
        Map<Integer, Spore> sporeMap = new HashMap<>();
        Map<Integer, Insect> insectMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String section = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.startsWith("[")) {
                    section = line;
                    continue;
                }

                switch (section) {
                    case "[Tektons]":
                        Tekton tekton = parseTekton(line);
                        tektonMap.put(System.identityHashCode(tekton), tekton);
                        game.addTekton(tekton);
                        break;
                    case "[MushroomBodies]":
                        MushroomBody body = parseMushroomBody(line, tektonMap);
                        bodyMap.put(System.identityHashCode(body), body);
                        break;
                    case "[MushroomYarns]":
                        MushroomYarn yarn = parseMushroomYarn(line, tektonMap);
                        yarnMap.put(System.identityHashCode(yarn), yarn);
                        break;
                    case "[Spores]":
                        Spore spore = parseSpore(line, tektonMap);
                        sporeMap.put(System.identityHashCode(spore), spore);
                        break;
                    case "[Insects]":
                        Insect insect = parseInsect(line, tektonMap);
                        insectMap.put(System.identityHashCode(insect), insect);
                        break;
                }
            }

        } catch (IOException e) {
            System.err.println("Hiba a játék beolvasása közben: " + e.getMessage());
        }

        return game;
    }

    private static Tekton parseTekton(String line) {
        String[] parts = line.split(",");
        String type = parts[1].split("=")[1];
        Tekton tekton = createTektonByType(type);
        // Mushroom alapból lesz később hozzáadva, ha kell
        return tekton;
    }

    private static Tekton createTektonByType(String type) {
        switch (type) {
            case "DefaultTekton": return new DefaultTekton();
            case "FastGrowthTekton": return new FastGrowthTekton();
            case "LifeTekton": return new LifeTekton();
            case "DisappearingYarnTekton": return new DisappearingYarnTekton();
            case "DisabledBodyGrowthTekton": return new DisabledBodyGrowthTekton();
            case "SingleMushroomOnlyTekton": return new SingleMushroomOnlyTekton();
            case "NoMushroomBodyTekton": return new NoMushroomBodyTekton();
            default: throw new IllegalArgumentException("Ismeretlen Tekton típus: " + type);
        }
    }

    private static MushroomBody parseMushroomBody(String line, Map<Integer, Tekton> tektonMap) {
        int tektonId = Integer.parseInt(line.split(",")[1].split("=")[1]);
        Tekton tekton = tektonMap.get(tektonId);
        MushroomBody body = new MushroomBody(tekton);
        tekton.getMushroom().setMushroomBody(body);
        return body;
    }

    private static MushroomYarn parseMushroomYarn(String line, Map<Integer, Tekton> tektonMap) {
        String[] parts = line.split(",");
        int fromId = Integer.parseInt(parts[1].split("=")[1]);
        int toId = Integer.parseInt(parts[2].split("=")[1]);
        boolean isCut = Boolean.parseBoolean(parts[3].split("=")[1]);
        int timeBack = Integer.parseInt(parts[4].split("=")[1]);

        MushroomYarn yarn = new MushroomYarn(tektonMap.get(fromId), tektonMap.get(toId));
        if (isCut) yarn.setCut();
        yarn.setTimeBack(timeBack);

        tektonMap.get(fromId).getMushroom().getMushroomYarns().add(yarn);
        tektonMap.get(toId).getMushroom().getMushroomYarns().add(yarn);

        return yarn;
    }

    private static Spore parseSpore(String line, Map<Integer, Tekton> tektonMap) {
        String[] parts = line.split(",");
        String type = parts[1].split("=")[1];
        int tektonId = Integer.parseInt(parts[2].split("=")[1]);
        int nutrition = Integer.parseInt(parts[3].split("=")[1]);

        Spore spore = createSporeByType(type, tektonMap.get(tektonId));
        spore.nutrition = nutrition;
        tektonMap.get(tektonId).getMushroom().getSpores().add(spore);

        return spore;
    }

    private static Spore createSporeByType(String type, Tekton tekton) {
        switch (type) {
            case "DuplicatingSpore": return new DuplicatingSpore(tekton);
            case "ParalyzingSpore": return new ParalyzingSpore(tekton);
            case "SlowingSpore": return new SlowingSpore(tekton);
            case "SpeedingSpore": return new SpeedingSpore(tekton);
            default: throw new IllegalArgumentException("Ismeretlen Spore típus: " + type);
        }
    }

    private static Insect parseInsect(String line, Map<Integer, Tekton> tektonMap) {
        String[] parts = line.split(",");
        int tektonId = Integer.parseInt(parts[1].split("=")[1]);
        int score = Integer.parseInt(parts[2].split("=")[1]);
        int action = Integer.parseInt(parts[3].split("=")[1]);
        int cutDisabled = Integer.parseInt(parts[4].split("=")[1]);
        int paralyzed = Integer.parseInt(parts[5].split("=")[1]);
        int spedUp = Integer.parseInt(parts[6].split("=")[1]);
        int slowedDown = Integer.parseInt(parts[7].split("=")[1]);

        Insect insect = new Insect(tektonMap.get(tektonId));
        insect.setAction(action);
        insect.getEffects()[0] = cutDisabled;
        insect.getEffects()[1] = paralyzed;
        insect.getEffects()[2] = spedUp;
        insect.getEffects()[3] = slowedDown;

        tektonMap.get(tektonId).addNewInsect(insect);

        return insect;
    }
}
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class read2 {

    public static Game loadGame(String filename) {
        Game game = new Game();
        List<Tekton> tektons = new ArrayList<>();
        String section = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.startsWith("[")) {
                    section = line;
                    continue;
                }

                switch (section) {
                    case "[Tektons]":
                        Tekton tekton = createTektonFromType(line);
                        tektons.add(tekton);
                        game.addTekton(tekton);
                        break;

                    case "[MushroomBodies]":
                        for (Tekton t : tektons) {
                            if (t.getClass().getSimpleName().equals(line)) {
                                t.setMushroom(new Mushroom());
                                t.getMushroom().setMushroomBody(new MushroomBody(t));
                            }
                        }
                        break;

                    case "[MushroomYarns]":
                        String[] parts = line.split(" ");
                        for (Tekton t1 : tektons) {
                            for (Tekton t2 : tektons) {
                                if (t1.getClass().getSimpleName().equals(parts[0]) &&
                                        t2.getClass().getSimpleName().equals(parts[1])) {
                                    MushroomYarn yarn = new MushroomYarn(t1, t2);
                                    if (Boolean.parseBoolean(parts[2])) yarn.setCut();
                                    yarn.setTimeBack(Integer.parseInt(parts[3]));
                                    if (t1.getMushroom() == null) t1.setMushroom(new Mushroom());
                                    t1.getMushroom().getMushroomYarns().add(yarn);
                                    if (t2.getMushroom() == null) t2.setMushroom(new Mushroom());
                                    t2.getMushroom().getMushroomYarns().add(yarn);
                                }
                            }
                        }
                        break;

                    case "[Spores]":
                        String[] sporeParts = line.split(" ");
                        for (Tekton t : tektons) {
                            if (t.getClass().getSimpleName().equals(sporeParts[1])) {
                                if (t.getMushroom() == null) t.setMushroom(new Mushroom());
                                t.getMushroom().getSpores().add(createSporeFromType(sporeParts[0], t));
                            }
                        }
                        break;

                    case "[Insects]":
                        String[] insectParts = line.split(" ");
                        for (Tekton t : tektons) {
                            if (t.getClass().getSimpleName().equals(insectParts[0])) {
                                Insect insect = new Insect(t);
                                // NEM állítunk be Score-t, mert nincs setScore()!
                                insect.setAction(Integer.parseInt(insectParts[1]));
                                t.addNewInsect(insect);
                            }
                        }
                        break;
                }
            }

        } catch (IOException e) {
            System.err.println("Hiba a játék betöltése közben: " + e.getMessage());
        }

        return game;
    }

    private static Tekton createTektonFromType(String type) {
        switch (type) {
            case "DefaultTekton": return new DefaultTekton();
            case "FastGrowthTekton": return new FastGrowthTekton();
            case "LifeTekton": return new LifeTekton();
            case "DisappearingYarnTekton": return new DisappearingYarnTekton();
            case "DisabledBodyGrowthTekton": return new DisabledBodyGrowthTekton();
            case "SingleMushroomOnlyTekton": return new SingleMushroomOnlyTekton();
            case "NoMushroomBodyTekton": return new NoMushroomBodyTekton();
            default: throw new IllegalArgumentException("Ismeretlen Tekton típus: " + type);
        }
    }

    private static Spore createSporeFromType(String type, Tekton tekton) {
        switch (type) {
            case "SlowingSpore": return new SlowingSpore(tekton);
            case "DuplicatingSpore": return new DuplicatingSpore(tekton);
            case "ParalyzingSpore": return new ParalyzingSpore(tekton);
            case "SpeedingSpore": return new SpeedingSpore(tekton);
            default: throw new IllegalArgumentException("Ismeretlen Spore típus: " + type);
        }
    }
}*//*
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class read2 {

    public static Game loadGame(String filename) {
        Game game = new Game();
        Map<String, Tekton> tektonMap = new HashMap<>();
        String section = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            Tekton currentTekton = null;
            Mushroom currentMushroom = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.startsWith("[")) {
                    section = line;
                    continue;
                }

                switch (section) {
                    case "[Tektons]":
                        if (line.startsWith("Type=")) {
                            String type = line.split("=")[1];
                            currentTekton = createTektonByType(type);
                            tektonMap.put(type, currentTekton);
                            game.addTekton(currentTekton);
                        } else if (line.startsWith("AdjacentTektons=")) {
                            String[] neighbors = line.substring("AdjacentTektons=".length()).trim().split(" ");
                            for (String neighborType : neighbors) {
                                if (!neighborType.isEmpty()) {
                                    Tekton neighbor = tektonMap.get(neighborType);
                                    if (neighbor != null) currentTekton.addAdjacentTekton(neighbor);
                                }
                            }
                        } else if (line.startsWith("HasMushroom=")) {
                            if (line.contains("true")) {
                                currentTekton.setMushroom(new Mushroom());
                            }
                        } else if (line.startsWith("Insects=")) {
                            // nem kell semmit, várjuk a következő sort
                        } else if (line.startsWith("-Action=")) {
                            int action = Integer.parseInt(line.split(" ")[0].split("=")[1]);
                            String effectsPart = line.substring(line.indexOf("[") + 1, line.indexOf("]")).trim();
                            String[] effectStrings = effectsPart.split(" ");
                            Insect insect = new Insect(currentTekton, new Insecter("alma"));
                            insect.setAction(action);

                            int[] effects = insect.getEffects();
                            for (int i = 0; i < Math.min(effects.length, effectStrings.length); i++) {
                                effects[i] = Integer.parseInt(effectStrings[i]);
                            }

                            currentTekton.addNewInsect(insect);
                        }


                        break;

                    case "[Mushrooms]":
                        if (line.startsWith("OnTekton=")) {
                            String tektonType = line.split("=")[1];
                            currentTekton = tektonMap.get(tektonType);
                            currentMushroom = new Mushroom();
                            currentTekton.setMushroom(currentMushroom);
                        } else if (line.startsWith("HasBody=")) {
                            if (line.contains("true")) {
                                currentMushroom.setMushroomBody(new MushroomBody(currentTekton));
                            }
                        } else if (line.startsWith("Spores=")) {
                            // semmi, várunk
                        } else if (line.startsWith("-Type=")) {
                            String sporeType = line.split("=")[1];
                            Spore spore = createSporeByType(sporeType, currentTekton);
                            currentMushroom.getSpores().add(spore);
                        } else if (line.startsWith("Yarns=")) {
                            // semmi, várunk
                        } else if (line.startsWith("-Between=")) {
                            String[] parts = line.split(" ");
                            Tekton t1 = tektonMap.get(parts[0].split("=")[1]);
                            Tekton t2 = tektonMap.get(parts[1]);
                            boolean isCut = Boolean.parseBoolean(parts[2].split("=")[1]);
                            int timeBack = Integer.parseInt(parts[3].split("=")[1]); // parts[3], NEM parts[4]!

                            MushroomYarn yarn = new MushroomYarn(t1, t2);
                            if (isCut) yarn.setCut();
                            yarn.setTimeBack(timeBack);

                            if (t1.getMushroom() == null) t1.setMushroom(new Mushroom());
                            if (t2.getMushroom() == null) t2.setMushroom(new Mushroom());
                            t1.getMushroom().getMushroomYarns().add(yarn);
                            t2.getMushroom().getMushroomYarns().add(yarn);
                        }

                        break;
                }
            }

        } catch (IOException e) {
            System.err.println("Hiba beolvasáskor: " + e.getMessage());
        }

        return game;
    }

    private static Tekton createTektonByType(String type) {
        switch (type) {
            case "DefaultTekton": return new DefaultTekton();
            case "FastGrowthTekton": return new FastGrowthTekton();
            case "LifeTekton": return new LifeTekton();
            case "DisappearingYarnTekton": return new DisappearingYarnTekton();
            case "DisabledBodyGrowthTekton": return new DisabledBodyGrowthTekton();
            case "SingleMushroomOnlyTekton": return new SingleMushroomOnlyTekton();
            case "NoMushroomBodyTekton": return new NoMushroomBodyTekton();
            default: throw new IllegalArgumentException("Ismeretlen Tekton típus: " + type);
        }
    }

    private static Spore createSporeByType(String type, Tekton tekton) {
        switch (type) {
            case "SlowingSpore": return new SlowingSpore(tekton);
            case "DuplicatingSpore": return new DuplicatingSpore(tekton);
            case "ParalyzingSpore": return new ParalyzingSpore(tekton);
            case "SpeedingSpore": return new SpeedingSpore(tekton);
            default: throw new IllegalArgumentException("Ismeretlen Spore típus: " + type);
        }
    }
}
*/

