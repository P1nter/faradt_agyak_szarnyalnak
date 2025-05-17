// MapPanel.java (Continued - Full Class)
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MapPanel extends JPanel implements GameListener {
    private final Game game;
    private final Map<Tekton, Point> positions = new HashMap<>();
    private Image tektonImg, insectImg, mushroomImg, sporeImgIcon;

    private static final int TEKTON_DISPLAY_RADIUS = 38;
    private static final int MUSHROOM_BODY_WIDTH = 20;
    private static final int MUSHROOM_BODY_HEIGHT = 16;
    private static final int SPORE_DRAW_RADIUS = 7;
    private static final int INSECT_RADIUS = 10;
    private static final int YARN_THICKNESS = 3;
    private static final int CUT_YARN_THICKNESS = 2;
    private static final int TEKTON_TYPE_INDICATOR_SIZE = 9;
    private static final int SPORE_TYPE_CHAR_OFFSET_Y = 1;

    private Map<Player, Color> playerColors = new HashMap<>();
    private final Color[] DEFAULT_PLAYER_COLORS = {
            new Color(34, 139, 34, 220), new Color(70, 70, 70, 220), // ForestGreen, DimGray (slightly transparent for fill)
            new Color(0, 100, 200, 220), new Color(200, 50, 50, 220), // SteelBlue, IndianRed
            new Color(255, 140, 0, 220), new Color(138, 43, 226, 220), // DarkOrange, BlueViolet
            new Color(0, 139, 139, 220), new Color(255, 105, 180, 220)  // DarkCyan, HotPink
    };
    private int nextColorIdx = 0;

    private final Color HIGHLIGHT_COLOR_VALID_TARGET = new Color(255, 255, 80, 160); // Brighter Yellow, more opaque
    private final Color HIGHLIGHT_COLOR_SELECTED_ACTOR = new Color(60, 170, 255, 190); // Brighter Blue, more opaque
    private final Color HIGHLIGHT_COLOR_FIRST_TARGET = new Color(255, 160, 60, 190);   // Brighter Orange, more opaque
    private final Color ACTIVE_PLAYER_PIECE_HIGHLIGHT_COLOR = new Color(255, 223, 0, 200); // Gold, distinct

    private boolean drawTektonIDs = true; // Set to true for debugging Tekton IDs

    public MapPanel(Game game) {
        this.game = game;
        this.game.addListener(this);
        setBackground(new Color(230, 235, 245));
        loadImages();
        addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();
                Insect clickedInsect = findInsectAt(clickPoint);
                if (clickedInsect != null) { game.mapInsectClicked(clickedInsect); return; }
                MushroomYarn clickedYarn = findYarnAt(clickPoint, 10);
                if (clickedYarn != null) { game.mapYarnClicked(clickedYarn); return; }
                Tekton clickedTekton = findTektonAt(clickPoint);
                if (clickedTekton != null) { game.mapTektonClicked(clickedTekton); return; }
            }
        });
    }

    private void loadImages() {
        tektonImg   = loadImageResource("images/tekton_dodecagon.png", "T");
        insectImg   = loadImageResource("images/insect_icon.png", "I");
        mushroomImg = loadImageResource("images/mushroom_body_rect.png", "MB");
        sporeImgIcon = loadImageResource("images/spore_generic_icon.png", "S"); // A generic icon for spores
    }

    private Image loadImageResource(String path, String placeholderText) {
        try { java.net.URL imgUrl = getClass().getClassLoader().getResource(path); if (imgUrl != null) return new ImageIcon(imgUrl).getImage(); } catch (Exception e) {} return null;
    }

    @Override public void onMapChanged() { System.out.println("MapPanel: onMapChanged received, regenerating positions."); generatePositions(); repaint(); }
    @Override public void onStateChanged() { /*System.out.println("MapPanel: onStateChanged received, repainting.");*/ repaint(); }

    private void generatePositions() {
        System.out.println("MapPanel: generatePositions START");
        positions.clear(); List<Tekton> tektonsOnMap = new ArrayList<>(game.getTektons());
        if (tektonsOnMap.isEmpty()) { System.out.println("MapPanel: No tektons to position."); return; }
        int panelWidth = getWidth() > 0 ? getWidth() : getPreferredSize().width; int panelHeight = getHeight() > 0 ? getHeight() : getPreferredSize().height;
        int margin = TEKTON_DISPLAY_RADIUS * 2 + 25; Random rnd = new Random(System.currentTimeMillis());
        for (Tekton t : tektonsOnMap) positions.put(t, new Point(margin + rnd.nextInt(Math.max(1, panelWidth - 2 * margin)), margin + rnd.nextInt(Math.max(1, panelHeight - 2 * margin))));
        int iterations = 80 + tektonsOnMap.size() * 8; double pullStrength = 0.02; double pushStrength = 35000.0 / (tektonsOnMap.size()/4.0 + 1); double idealDistance = TEKTON_DISPLAY_RADIUS * 3.0; // Reduce ideal distance a bit
        for (int iter = 0; iter < iterations; iter++) {
            Map<Tekton, Point2D.Double> forces = new HashMap<>(); for (Tekton t : tektonsOnMap) forces.put(t, new Point2D.Double(0, 0));
            for (int i = 0; i < tektonsOnMap.size(); i++) {
                Tekton t1 = tektonsOnMap.get(i); Point p1 = positions.get(t1); if (p1 == null) continue;
                for (int j = 0; j < tektonsOnMap.size(); j++) {
                    if (i == j) continue; Tekton t2 = tektonsOnMap.get(j); Point p2 = positions.get(t2); if (p2 == null) continue;
                    double dx = p1.x - p2.x; double dy = p1.y - p2.y; double distSq = dx * dx + dy * dy; if (distSq < 1) distSq = 1; double dist = Math.sqrt(distSq);
                    double repulsiveF = pushStrength / distSq; Point2D.Double forceT1 = forces.get(t1); forceT1.x += repulsiveF * (dx / dist); forceT1.y += repulsiveF * (dy / dist);
                }
                for (Tekton neighbor : t1.getAdjacentTektonsNoPrint()) {
                    if (!positions.containsKey(neighbor)) continue; Point pN = positions.get(neighbor); if (pN == null) continue;
                    double dx = pN.x - p1.x; double dy = pN.y - p1.y; double dist = Math.sqrt(dx * dx + dy * dy); if (dist < 1) dist = 1;
                    double attractiveF = pullStrength * (dist - idealDistance); Point2D.Double forceT1 = forces.get(t1); forceT1.x += attractiveF * (dx / dist); forceT1.y += attractiveF * (dy / dist);
                }
            }
            double maxDisp = TEKTON_DISPLAY_RADIUS / (iter < iterations*0.75 ? 2.0 : 4.0) ;
            for (Tekton t : tektonsOnMap) {
                Point currPos = positions.get(t); Point2D.Double force = forces.get(t); double forceMag = Math.sqrt(force.x*force.x + force.y*force.y); if (forceMag < 0.01) continue;
                double dX = (force.x / forceMag) * Math.min(forceMag, maxDisp); double dY = (force.y / forceMag) * Math.min(forceMag, maxDisp);
                int nX = Math.max(margin, Math.min((int)(currPos.x + dX), panelWidth - margin)); int nY = Math.max(margin, Math.min((int)(currPos.y + dY), panelHeight - margin));
                positions.put(t, new Point(nX, nY));
            }
        }
        System.out.println("MapPanel: generatePositions END.");
    }
    @Override public Dimension getPreferredSize() { return new Dimension(950, 750); } // Accommodate legend better

    private Tekton findTektonAt(Point clickPoint) {
        for (Map.Entry<Tekton, Point> entry : positions.entrySet()) {
            // Use a slightly smaller click radius for polygons than their visual extent for better feel
            if (entry.getValue().distanceSq(clickPoint) < (TEKTON_DISPLAY_RADIUS * 0.85 * TEKTON_DISPLAY_RADIUS * 0.85)) {
                return entry.getKey();
            }
        }
        return null;
    }
    private Insect findInsectAt(Point clickPoint) {
        List<Player> players = new ArrayList<>(game.getPlayers()); Collections.reverse(players); // Check topmost drawn players first
        for (Player p : players) {
            if (p instanceof Insecter) {
                List<Insect> insects = new ArrayList<>(((Insecter) p).getInsects());
                // To click topmost drawn insect on a tekton, iterate based on how they are drawn (e.g. last in list is top)
                // However, indexOf relies on the original list order, not drawing order if drawn randomly.
                // For now, simple iteration. Consider a list of all drawn entities with their bounds if precision is paramount.
                for (int i = insects.size() - 1; i >= 0; i--) {
                    Insect insect = insects.get(i); Tekton host = insect.getTektonNoPrint();
                    if (host != null && positions.containsKey(host)) {
                        Point basePos = positions.get(host);
                        int insectOrderOnTekton = host.getInsectsNoPrint().indexOf(insect); // Get actual order on Tekton
                        if(insectOrderOnTekton == -1) insectOrderOnTekton = i; // Fallback if not found (should not happen)

                        int insectsPerRow = 3; int col = insectOrderOnTekton % insectsPerRow; int row = insectOrderOnTekton / insectsPerRow;
                        int offsetX = (col - (insectsPerRow - 1) / 2) * (INSECT_RADIUS * 2 + 4);
                        int offsetY = -TEKTON_DISPLAY_RADIUS + INSECT_RADIUS + 5 + (row * (INSECT_RADIUS * 2 + 4));
                        Point insectPos = new Point(basePos.x + offsetX, basePos.y + offsetY);
                        Ellipse2D.Double insectClickShape = new Ellipse2D.Double(insectPos.x - INSECT_RADIUS, insectPos.y - INSECT_RADIUS, INSECT_RADIUS * 2, INSECT_RADIUS * 2);
                        if (insectClickShape.contains(clickPoint)) return insect;
                    }
                }
            }
        }
        return null;
    }
    private MushroomYarn findYarnAt(Point clickPoint, double tolerance) { /* ... same ... */
        for (Player p : game.getPlayers()) {
            if (p instanceof Mushroomer) {
                for (MushroomYarn yarn : ((Mushroomer) p).getMushroomYarns()) {
                    Tekton[] tektons = yarn.getTektonsNoPrint();
                    if (tektons.length == 2 && positions.containsKey(tektons[0]) && positions.containsKey(tektons[1])) {
                        Point p1 = positions.get(tektons[0]); Point p2 = positions.get(tektons[1]);
                        if (Line2D.ptSegDistSq(p1.x, p1.y, p2.x, p2.y, clickPoint.x, clickPoint.y) < tolerance * tolerance) return yarn;
                    }
                }
            }
        }
        return null;
    }
    private Color getPlayerColor(Player player) { /* ... same ... */
        if (player == null) return Color.LIGHT_GRAY;
        return playerColors.computeIfAbsent(player, p -> DEFAULT_PLAYER_COLORS[nextColorIdx++ % DEFAULT_PLAYER_COLORS.length]);
    }
    private Polygon createRegularPolygon(Point center, int radius, int numSides, double startAngleOffset) { /* ... same ... */
        Polygon pShape = new Polygon();
        for (int i = 0; i < numSides; i++) {
            double angle = startAngleOffset + (i * 2 * Math.PI / numSides);
            pShape.addPoint((int) (center.x + radius * Math.cos(angle)), (int) (center.y + radius * Math.sin(angle)));
        }
        return pShape;
    }

    public static Color getTektonTypeColor(String typeName) { /* ... same ... */
        if (typeName.contains("Disabled") || typeName.contains("NoMushroomBody")) return new Color(160, 100, 100);
        if (typeName.contains("Disappearing")) return new Color(100, 100, 160);
        if (typeName.contains("FastGrowth")) return new Color(100, 160, 100);
        if (typeName.contains("Life")) return new Color(210, 210, 100);
        if (typeName.contains("SingleMushroom")) return new Color(160, 100, 160);
        return new Color(190, 195, 200);
    }
    public static Color getSporeEffectColor(Spore.SporeType type) { /* ... same ... */
        if (type == null) return Color.DARK_GRAY;
        switch (type) {
            case CUT_DISABLING: return new Color(230, 60, 60); case PARALYZING: return new Color(60, 60, 230);
            case DUPLICATING: return new Color(60, 230, 60);   case SLOWING: return new Color(230, 230, 60);
            case SPEEDING: return new Color(230, 60, 230);
            default: return Color.GRAY;
        }
    }
    public static String getSporeTypeChar(Spore.SporeType type) { /* ... same ... */
        if (type == null) return "?";
        switch (type) {
            case CUT_DISABLING: return "C"; case PARALYZING: return "P"; case DUPLICATING: return "D";
            case SLOWING: return "S"; case SPEEDING: return "F";
            default: return "?";
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        if (game == null || game.getTektons() == null) { g2.drawString("Game not ready.", 50,50); return; }
        if (positions.isEmpty() && !game.getTektons().isEmpty()) { generatePositions(); }
        if (game.getTektons().isEmpty()) { g2.drawString("No Tektons on map.", 50,50); return; }

        List<Object> highlightedAsTargetEntities = game.getHighlightableEntities();
        Object selectedActorEntity = game.getSelectedActor();
        Tekton firstTargetTekton = game.getFirstTektonTarget();
        Player activeGamePlayer = game.getActivePlayer();
        Stroke defaultStroke = g2.getStroke();
        Font defaultFont = g2.getFont();
        Font smallFont = defaultFont.deriveFont(10f); Font tinyFont = defaultFont.deriveFont(Font.BOLD, 9f);


        // 1. Draw Yarns
        for (Player playerOwner : game.getPlayers()) {
            if (playerOwner instanceof Mushroomer) {
                Mushroomer mushroomer = (Mushroomer) playerOwner; Color playerBaseColor = getPlayerColor(mushroomer);
                for (MushroomYarn yarn : mushroomer.getMushroomYarns()) {
                    Tekton[] yarnTektons = yarn.getTektonsNoPrint();
                    if (yarnTektons.length == 2 && positions.containsKey(yarnTektons[0]) && positions.containsKey(yarnTektons[1])) {
                        Point p1 = positions.get(yarnTektons[0]); Point p2 = positions.get(yarnTektons[1]);
                        Stroke currentYarnStroke; Color currentYarnColor; boolean isActivePlayerYarn = (mushroomer == activeGamePlayer && !yarn.getIsCut());
                        if (yarn.getIsCut()) {
                            currentYarnStroke = new BasicStroke(CUT_YARN_THICKNESS, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{7f, 4f}, 0.0f); currentYarnColor = Color.DARK_GRAY;
                        } else {
                            currentYarnStroke = new BasicStroke(isActivePlayerYarn ? YARN_THICKNESS + 1f : YARN_THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                            currentYarnColor = isActivePlayerYarn ? playerBaseColor : playerBaseColor.darker();
                        }
                        if (highlightedAsTargetEntities.contains(yarn)) {
                            g2.setStroke(new BasicStroke(YARN_THICKNESS + 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); g2.setColor(HIGHLIGHT_COLOR_VALID_TARGET); g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                        }
                        g2.setStroke(currentYarnStroke); g2.setColor(currentYarnColor); g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }
            }
        }
        g2.setStroke(defaultStroke);

        // 2. Draw Tektons, then their contents (Mushrooms, Spores), then Highlights
        for (Tekton t : game.getTektons()) {
            Point p = positions.get(t); if (p == null) continue;
            Polygon dodecagon = createRegularPolygon(p, TEKTON_DISPLAY_RADIUS, 12, Math.PI / 12.0);
            if (tektonImg != null) g2.drawImage(tektonImg, p.x - TEKTON_DISPLAY_RADIUS, p.y - TEKTON_DISPLAY_RADIUS, TEKTON_DISPLAY_RADIUS*2, TEKTON_DISPLAY_RADIUS*2, null);
            else { g2.setColor(getTektonTypeColor(t.getClass().getSimpleName())); g2.fill(dodecagon); g2.setColor(Color.DARK_GRAY); g2.setStroke(new BasicStroke(1.5f)); g2.draw(dodecagon); }
            g2.setStroke(defaultStroke);
            drawTektonTypeIndicator(g2, t, p);

            Mushroom m = t.getMushroomNoPrint();
            if (m != null && m.hasMushroomBody()) {
                MushroomBody gameMB = m.getMushroomBodyNoPrint(); Player owner = gameMB.getOwner(); Color ownerColor = getPlayerColor(owner); Point mbPos = p;
                Rectangle2D.Double bodyRect = new Rectangle2D.Double(mbPos.x - MUSHROOM_BODY_WIDTH / 2.0, mbPos.y - MUSHROOM_BODY_HEIGHT / 2.0, MUSHROOM_BODY_WIDTH, MUSHROOM_BODY_HEIGHT);
                if (mushroomImg != null) g2.drawImage(mushroomImg, (int)bodyRect.x, (int)bodyRect.y, MUSHROOM_BODY_WIDTH, MUSHROOM_BODY_HEIGHT, null);
                else { g2.setColor(ownerColor); g2.fill(bodyRect); g2.setColor(Color.BLACK); g2.draw(bodyRect); }
                if (owner == activeGamePlayer) { g2.setColor(ACTIVE_PLAYER_PIECE_HIGHLIGHT_COLOR); g2.setStroke(new BasicStroke(3f)); g2.draw(new Rectangle2D.Double(bodyRect.x-2,bodyRect.y-2,bodyRect.width+4,bodyRect.height+4));}
            }
            g2.setStroke(defaultStroke);

            if (m != null && !m.getSporesNoPrint().isEmpty()) {
                List<Spore> sporesToDraw = m.getSporesNoPrint(); int sporeCount = sporesToDraw.size(); int maxDraw = 4; float angleStep = (float) (Math.PI*2 / Math.min(sporeCount, 6));
                for (int i = 0; i < Math.min(sporeCount, maxDraw); i++) {
                    Spore spore = sporesToDraw.get(i); Color ownerColor = getPlayerColor(spore.getOwner()); Color effectColor = getSporeEffectColor(spore.getSporeType());
                    double angle = (Math.PI / 6.0) * i - (Math.PI/3); // Arrange in an arc at top
                    int offsetX = (int) ((TEKTON_DISPLAY_RADIUS * 0.65) * Math.cos(angle)); int offsetY = (int) ((TEKTON_DISPLAY_RADIUS * 0.65) * Math.sin(angle));
                    Point sporePos = new Point(p.x + offsetX, p.y + offsetY);
                    Ellipse2D.Double sporeCircle = new Ellipse2D.Double(sporePos.x - SPORE_DRAW_RADIUS, sporePos.y - SPORE_DRAW_RADIUS, SPORE_DRAW_RADIUS * 2, SPORE_DRAW_RADIUS * 2);
                    if (sporeImgIcon != null) g2.drawImage(sporeImgIcon, sporePos.x - SPORE_DRAW_RADIUS, sporePos.y - SPORE_DRAW_RADIUS, SPORE_DRAW_RADIUS*2, SPORE_DRAW_RADIUS*2, null);
                    else { g2.setColor(effectColor); g2.fill(sporeCircle); }
                    g2.setColor(ownerColor); g2.setStroke(new BasicStroke(2f)); g2.draw(sporeCircle);
                    g2.setColor(Color.BLACK); g2.setFont(tinyFont); String typeChar = getSporeTypeChar(spore.getSporeType());
                    g2.drawString(typeChar, sporePos.x - g2.getFontMetrics().stringWidth(typeChar)/2, sporePos.y + g2.getFontMetrics().getAscent()/2 - SPORE_TYPE_CHAR_OFFSET_Y);
                }
                if (sporeCount > maxDraw) { g2.setColor(Color.DARK_GRAY); g2.setFont(smallFont); g2.drawString("+" + (sporeCount - maxDraw), p.x + TEKTON_DISPLAY_RADIUS - 15, p.y - TEKTON_DISPLAY_RADIUS + 12); }
            }
            g2.setFont(defaultFont); g2.setStroke(defaultStroke);

            Polygon highlightPoly = createRegularPolygon(p, TEKTON_DISPLAY_RADIUS, 12, Math.PI/12.0);
            if (t == selectedActorEntity && selectedActorEntity instanceof Tekton) { g2.setColor(HIGHLIGHT_COLOR_SELECTED_ACTOR); g2.setStroke(new BasicStroke(5)); g2.draw(highlightPoly); }
            else if (t == firstTargetTekton) { g2.setColor(HIGHLIGHT_COLOR_FIRST_TARGET); g2.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10f,6f}, 0.0f)); g2.draw(highlightPoly); }
            else if (highlightedAsTargetEntities.contains(t)) { g2.setColor(HIGHLIGHT_COLOR_VALID_TARGET); g2.setStroke(new BasicStroke(4)); g2.draw(highlightPoly); }
            if(drawTektonIDs) { g2.setFont(smallFont); g2.setColor(Color.BLACK); String idStr=String.valueOf(t.getIDNoPrint()); g2.drawString(idStr, p.x - g2.getFontMetrics().stringWidth(idStr)/2, p.y + TEKTON_DISPLAY_RADIUS + g2.getFontMetrics().getAscent() + 2); g2.setFont(defaultFont); }
        }
        g2.setStroke(defaultStroke);

        // 3. Draw Insects
        Map<Tekton, Integer> insectsOnTektonCount = new HashMap<>();
        for (Player playerOwner : game.getPlayers()) {
            if (playerOwner instanceof Insecter) {
                for (Insect insect : ((Insecter) playerOwner).getInsects()) {
                    Tekton host = insect.getTektonNoPrint();
                    if (host != null && positions.containsKey(host)) { /* ... same insect drawing logic ... */
                        Point basePos = positions.get(host); int count = insectsOnTektonCount.getOrDefault(host, 0);
                        int insectsPerRow = 3; int col = count % insectsPerRow; int row = count / insectsPerRow;
                        int offsetX = (col - (insectsPerRow - 1) / 2) * (INSECT_RADIUS * 2 + 4);
                        int offsetY = -TEKTON_DISPLAY_RADIUS + INSECT_RADIUS + 8 + (row * (INSECT_RADIUS * 2 + 4));
                        Point insectPos = new Point(basePos.x + offsetX, basePos.y + offsetY);
                        Color playerColor = getPlayerColor(playerOwner);
                        Shape insectShape = new Ellipse2D.Double(insectPos.x - INSECT_RADIUS, insectPos.y - INSECT_RADIUS, INSECT_RADIUS * 2, INSECT_RADIUS * 2);
                        if (insectImg != null) g2.drawImage(insectImg, insectPos.x - INSECT_RADIUS, insectPos.y - INSECT_RADIUS, INSECT_RADIUS * 2, INSECT_RADIUS * 2, null);
                        else { g2.setColor(playerColor); g2.fill(insectShape); g2.setColor(Color.BLACK); g2.setStroke(new BasicStroke(1f)); g2.draw(insectShape); }
                        if (playerOwner == activeGamePlayer && insect != selectedActorEntity) { g2.setColor(ACTIVE_PLAYER_PIECE_HIGHLIGHT_COLOR); g2.setStroke(new BasicStroke(2.5f)); g2.draw(new Ellipse2D.Double(insectPos.x-INSECT_RADIUS-1, insectPos.y-INSECT_RADIUS-1, INSECT_RADIUS*2+2, INSECT_RADIUS*2+2)); }
                        if (insect == selectedActorEntity) { g2.setColor(HIGHLIGHT_COLOR_SELECTED_ACTOR); g2.setStroke(new BasicStroke(3f)); g2.draw(new Ellipse2D.Double(insectPos.x-INSECT_RADIUS-2, insectPos.y-INSECT_RADIUS-2, INSECT_RADIUS*2+4, INSECT_RADIUS*2+4)); }
                        g2.setStroke(defaultStroke); insectsOnTektonCount.put(host, count + 1);
                    }
                }
            }
        }
    }

    private void drawTektonTypeIndicator(Graphics2D g2, Tekton t, Point center) {
        String typeName = t.getClass().getSimpleName();
        Color indicatorColor = getTektonTypeColor(typeName).darker(); // Use a darker shade of the base for the symbol bg
        char indicatorChar = typeName.toUpperCase().charAt(0); // Default to first char

        if (t instanceof DisabledBodyGrowthTekton || t instanceof NoMushroomBodyTekton) indicatorChar = 'B';
        else if (t instanceof DisappearingYarnTekton) indicatorChar = 'Y';
        else if (t instanceof FastGrowthTekton) indicatorChar = 'F';
        else if (t instanceof LifeTekton) indicatorChar = 'L';
        else if (t instanceof SingleMushroomOnlyTekton) indicatorChar = '1';
        // DefaultTekton will use 'D'

        if (indicatorChar != ' ') {
            int boxX = center.x + TEKTON_DISPLAY_RADIUS - TEKTON_TYPE_INDICATOR_SIZE - 5;
            int boxY = center.y + TEKTON_DISPLAY_RADIUS - TEKTON_TYPE_INDICATOR_SIZE - 5;
            g2.setColor(indicatorColor);
            g2.fillRect(boxX, boxY, TEKTON_TYPE_INDICATOR_SIZE, TEKTON_TYPE_INDICATOR_SIZE);
            g2.setColor(Color.BLACK);
            g2.drawRect(boxX, boxY, TEKTON_TYPE_INDICATOR_SIZE, TEKTON_TYPE_INDICATOR_SIZE);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, TEKTON_TYPE_INDICATOR_SIZE));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(String.valueOf(indicatorChar), boxX + (TEKTON_TYPE_INDICATOR_SIZE - fm.stringWidth(String.valueOf(indicatorChar)))/2, boxY + fm.getAscent() - (fm.getAscent()-fm.getHeight())/2 -1 );
        }
    }
}