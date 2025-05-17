// MapPanel.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage; // Keep for placeholder image creation if needed
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random; // For random placement if needed, though Game/MapBuilder handles it

public class MapPanel extends JPanel implements GameListener {
    private final Game game;
    private final Map<Tekton, Point> positions = new HashMap<>();
    private Image tektonImg, insectImg, mushroomImg, sporeImg;

    private static final int TEKTON_DISPLAY_RADIUS = 35;
    private static final int MUSHROOM_BODY_RADIUS = 12;
    private static final int SPORE_RADIUS = 6;
    private static final int INSECT_WIDTH = 20;
    private static final int INSECT_HEIGHT = 20;
    private static final int YARN_THICKNESS = 4;
    private static final int CUT_YARN_THICKNESS = 2;

    private Map<Player, Color> playerColors = new HashMap<>();
    private final Color[] DEFAULT_PLAYER_COLORS = {
            new Color(0, 150, 0), new Color(30, 30, 30), new Color(0, 0, 200), new Color(200, 0, 0),
            new Color(255, 140, 0), new Color(128, 0, 128), new Color(0, 200, 200), new Color(255, 105, 180)
    };
    private int nextColorIdx = 0;

    // --- Updated Highlight Colors ---
    private final Color HIGHLIGHT_COLOR_VALID_TARGET = new Color(255, 255, 0, 128); // Yellow, semi-transparent
    private final Color HIGHLIGHT_COLOR_SELECTED_ACTOR = new Color(0, 100, 255, 150); // LightSkyBlue, semi-transparent
    private final Color HIGHLIGHT_COLOR_FIRST_TARGET = new Color(255, 165, 0, 150);   // Orange, semi-transparent

    public MapPanel(Game game) {
        this.game = game;
        this.game.addListener(this);
        setBackground(new Color(230, 230, 230)); // Slightly off-white background
        loadImages();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();
                Insect clickedInsect = findInsectAt(clickPoint);
                if (clickedInsect != null) {
                    game.mapInsectClicked(clickedInsect);
                    return;
                }
                MushroomYarn clickedYarn = findYarnAt(clickPoint, 8);
                if (clickedYarn != null) {
                    game.mapYarnClicked(clickedYarn);
                    return;
                }
                Tekton clickedTekton = findTektonAt(clickPoint);
                if (clickedTekton != null) {
                    game.mapTektonClicked(clickedTekton);
                    return;
                }
                if (game.getCurrentInteractionStep() != Game.InteractionStep.IDLE) {
                    // game.cancelAction(); // Optional
                }
            }
        });
    }

    private void loadImages() {
        tektonImg   = loadImageResource("images/tekton_dodecagon.png", "T"); // If you have a 12-sided image
        insectImg   = loadImageResource("images/insect.png", "I");
        mushroomImg = loadImageResource("images/mushroom_body.png", "MB");
        sporeImg    = loadImageResource("images/spore.png", "S");
    }

    private Image loadImageResource(String path, String placeholderText) {
        try {
            java.net.URL imgUrl = getClass().getClassLoader().getResource(path);
            if (imgUrl != null) return new ImageIcon(imgUrl).getImage();
        } catch (Exception e) { /* Silently ignore if image not found, will use drawing */ }
        return null; // Return null if image not found, paintComponent will draw shapes
    }

    @Override public void onMapChanged() { generatePositions(); repaint(); }
    @Override public void onStateChanged() { repaint(); }

    private void generatePositions() {
        positions.clear();
        List<Tekton> tektonsOnMap = new ArrayList<>(game.getTektons());
        if (tektonsOnMap.isEmpty()) return;
        tektonsOnMap.sort(Comparator.comparingInt(Tekton::getIDNoPrint));

        int panelWidth = getWidth() > 0 ? getWidth() : getPreferredSize().width;
        int panelHeight = getHeight() > 0 ? getHeight() : getPreferredSize().height;
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;
        int numTektons = tektonsOnMap.size();
        int radius = TEKTON_DISPLAY_RADIUS * 2;
        if (numTektons > 6) { // Adjust for more tektons
            radius = (int) ((TEKTON_DISPLAY_RADIUS * 1.8 * numTektons) / (2 * Math.PI)); // Empirically adjust factor 1.8
        }
        radius = Math.max(radius, TEKTON_DISPLAY_RADIUS * 2 + 10);
        radius = Math.min(radius, Math.min(panelWidth, panelHeight)/2 - TEKTON_DISPLAY_RADIUS - 20); // Ensure margin


        for (int i = 0; i < tektonsOnMap.size(); i++) {
            Tekton t = tektonsOnMap.get(i);
            if (numTektons == 1) { positions.put(t, new Point(centerX, centerY)); }
            else {
                double angle = 2 * Math.PI * i / numTektons;
                positions.put(t, new Point(centerX + (int) (radius * Math.cos(angle)),
                        centerY + (int) (radius * Math.sin(angle))));
            }
        }
    }

    @Override public Dimension getPreferredSize() { return new Dimension(850, 650); } // Slightly larger default

    private Tekton findTektonAt(Point clickPoint) {
        for (Map.Entry<Tekton, Point> entry : positions.entrySet()) {
            if (entry.getValue().distanceSq(clickPoint) < (TEKTON_DISPLAY_RADIUS * TEKTON_DISPLAY_RADIUS)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private Insect findInsectAt(Point clickPoint) {
        List<Player> players = new ArrayList<>(game.getPlayers());
        Collections.reverse(players);
        for (Player p : players) {
            if (p instanceof Insecter) {
                List<Insect> insects = new ArrayList<>(((Insecter) p).getInsects());
                Collections.reverse(insects);
                for (Insect insect : insects) {
                    Tekton host = insect.getTektonNoPrint();
                    if (host != null && positions.containsKey(host)) {
                        Point basePos = positions.get(host);
                        int insectOrderOnTekton = host.getInsectsNoPrint().indexOf(insect);
                        if(insectOrderOnTekton == -1) insectOrderOnTekton = 0; // Fallback

                        int insectsPerRow = 3;
                        int col = insectOrderOnTekton % insectsPerRow;
                        int row = insectOrderOnTekton / insectsPerRow;
                        int offsetX = (col - (insectsPerRow -1)/2) * (INSECT_WIDTH + 3);
                        int offsetY = -TEKTON_DISPLAY_RADIUS / 2 - INSECT_HEIGHT / 2 - (row * (INSECT_HEIGHT + 3)) - 5; // Shift further up

                        Point insectPos = new Point(basePos.x + offsetX, basePos.y + offsetY);
                        Rectangle insectBounds = new Rectangle(insectPos.x - INSECT_WIDTH / 2, insectPos.y - INSECT_HEIGHT / 2, INSECT_WIDTH, INSECT_HEIGHT);
                        if (insectBounds.contains(clickPoint)) {
                            return insect;
                        }
                    }
                }
            }
        }
        return null;
    }
    private MushroomYarn findYarnAt(Point clickPoint, double tolerance) {
        // ... (same as before) ...
        for (Player p : game.getPlayers()) {
            if (p instanceof Mushroomer) {
                for (MushroomYarn yarn : ((Mushroomer) p).getMushroomYarns()) {
                    Tekton[] tektons = yarn.getTektonsNoPrint();
                    if (tektons.length == 2 && positions.containsKey(tektons[0]) && positions.containsKey(tektons[1])) {
                        Point p1 = positions.get(tektons[0]);
                        Point p2 = positions.get(tektons[1]);
                        if (Line2D.ptSegDistSq(p1.x, p1.y, p2.x, p2.y, clickPoint.x, clickPoint.y) < tolerance * tolerance) {
                            return yarn;
                        }
                    }
                }
            }
        }
        return null;
    }

    private Color getPlayerColor(Player player) {
        return playerColors.computeIfAbsent(player, p -> DEFAULT_PLAYER_COLORS[nextColorIdx++ % DEFAULT_PLAYER_COLORS.length]);
    }

    private Polygon createRegularPolygon(Point center, int radius, int numSides, double startAngleOffset) {
        Polygon p = new Polygon();
        for (int i = 0; i < numSides; i++) {
            double angle = startAngleOffset + (i * 2 * Math.PI / numSides);
            p.addPoint((int) (center.x + radius * Math.cos(angle)),
                    (int) (center.y + radius * Math.sin(angle)));
        }
        return p;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        if (positions.size() != game.getTektons().size() && !game.getTektons().isEmpty()) {
            generatePositions();
        }
        if (game.getTektons().isEmpty()) return;

        List<Object> highlightedEntities = game.getHighlightableEntities();
        Object selectedActor = game.getSelectedActor();
        Tekton firstTarget = game.getFirstTektonTarget();
        FontMetrics fm = g2.getFontMetrics();
        Stroke defaultStroke = g2.getStroke();

        // 1. Draw Yarns (ensure they are drawn first so Tektons can be on top)
        for (Player player : game.getPlayers()) {
            if (player instanceof Mushroomer) {
                Color playerColor = getPlayerColor(player);
                for (MushroomYarn yarn : ((Mushroomer) player).getMushroomYarns()) {
                    Tekton[] yarnTektons = yarn.getTektonsNoPrint();
                    if (yarnTektons.length == 2 && positions.containsKey(yarnTektons[0]) && positions.containsKey(yarnTektons[1])) {
                        Point p1 = positions.get(yarnTektons[0]);
                        Point p2 = positions.get(yarnTektons[1]);

                        Stroke yarnStroke;
                        Color yarnColor;

                        if (yarn.getIsCut()) {
                            yarnStroke = new BasicStroke(CUT_YARN_THICKNESS, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{8f, 6f}, 0.0f); // Dashed
                            yarnColor = Color.GRAY.darker();
                        } else {
                            yarnStroke = new BasicStroke(YARN_THICKNESS);
                            yarnColor = playerColor;
                        }

                        if (highlightedEntities.contains(yarn)) {
                            // Use a distinct highlight for yarns, maybe a brighter version of player color or yellow outline
                            g2.setStroke(new BasicStroke(YARN_THICKNESS + 3)); // Outer highlight
                            g2.setColor(HIGHLIGHT_COLOR_VALID_TARGET); // Yellow for target
                            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                        }
                        g2.setStroke(yarnStroke);
                        g2.setColor(yarnColor);
                        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }
            }
        }
        g2.setStroke(defaultStroke);


        // 2. Draw Tektons, Mushrooms, Spores
        for (Tekton t : game.getTektons()) {
            Point p = positions.get(t);
            if (p == null) continue;

            // A. Draw Tekton base (Dodecagon or Image)
            Polygon dodecagon = createRegularPolygon(p, TEKTON_DISPLAY_RADIUS, 12, Math.PI / 12.0); // Offset for flat top if desired

            if (tektonImg != null) {
                g2.drawImage(tektonImg, p.x - TEKTON_DISPLAY_RADIUS, p.y - TEKTON_DISPLAY_RADIUS, TEKTON_DISPLAY_RADIUS*2, TEKTON_DISPLAY_RADIUS*2, null);
            } else {
                g2.setColor(new Color(180, 180, 160)); // Earthy tone for tekton
                g2.fill(dodecagon);
                g2.setColor(Color.DARK_GRAY.darker());
                g2.setStroke(new BasicStroke(2));
                g2.draw(dodecagon);
                g2.setStroke(defaultStroke);
            }

            // B. Highlighting for Tekton (draw on top of the tekton fill, but before contents)
            Stroke highlightStroke = new BasicStroke(5);
            Stroke selectedActorStroke = new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Stroke firstTargetStroke = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10f,7f}, 0.0f);


            if (t == selectedActor && selectedActor instanceof Tekton) {
                g2.setColor(HIGHLIGHT_COLOR_SELECTED_ACTOR);
                g2.setStroke(selectedActorStroke);
                g2.draw(createRegularPolygon(p, TEKTON_DISPLAY_RADIUS + 3, 12, Math.PI / 12.0));
            } else if (t == firstTarget) {
                g2.setColor(HIGHLIGHT_COLOR_FIRST_TARGET);
                g2.setStroke(firstTargetStroke);
                g2.draw(createRegularPolygon(p, TEKTON_DISPLAY_RADIUS + 2, 12, Math.PI / 12.0));
            } else if (highlightedEntities.contains(t)) {
                g2.setColor(HIGHLIGHT_COLOR_VALID_TARGET); // Yellow for valid target
                g2.setStroke(highlightStroke);
                g2.draw(createRegularPolygon(p, TEKTON_DISPLAY_RADIUS + 1, 12, Math.PI / 12.0));
            }
            g2.setStroke(defaultStroke);


            // C. Draw Mushroom Body (centered on Tekton)
            Mushroom m = t.getMushroomNoPrint();
            if (m != null && m.hasMushroomBody()) {
                Point mbPos = p; // Centered
                if (mushroomImg != null) {
                    g2.drawImage(mushroomImg, mbPos.x - MUSHROOM_BODY_RADIUS, mbPos.y - MUSHROOM_BODY_RADIUS, MUSHROOM_BODY_RADIUS*2, MUSHROOM_BODY_RADIUS*2, null);
                } else {
                    g2.setColor(new Color(160, 82, 45)); // Sienna brown
                    g2.fill(new Ellipse2D.Double(mbPos.x - MUSHROOM_BODY_RADIUS, mbPos.y - MUSHROOM_BODY_RADIUS, MUSHROOM_BODY_RADIUS*2, MUSHROOM_BODY_RADIUS*2));
                    g2.setColor(Color.BLACK);
                    g2.draw(new Ellipse2D.Double(mbPos.x - MUSHROOM_BODY_RADIUS, mbPos.y - MUSHROOM_BODY_RADIUS, MUSHROOM_BODY_RADIUS*2, MUSHROOM_BODY_RADIUS*2));
                }
            }

            // D. Draw Spores (e.g., cluster near the top of the Tekton, distinct from mushroom body)
            if (m != null && m.getSporesNoPrint() != null && !m.getSporesNoPrint().isEmpty()) {
                int numSpores = m.getSporesNoPrint().size();
                int displaySpores = Math.min(numSpores, 4); // Display up to 4 individual spores
                int sporeClusterRadius = TEKTON_DISPLAY_RADIUS / 3; // Radius for spore cluster positioning

                for (int i = 0; i < displaySpores; i++) {
                    // Arrange in a small arc/cluster above the center
                    double angle = -Math.PI / 2 + (i - (displaySpores - 1.0) / 2.0) * (Math.PI / 6); // Arc above center
                    int sporeX = p.x + (int)(sporeClusterRadius * Math.cos(angle));
                    int sporeY = p.y + (int)(sporeClusterRadius * Math.sin(angle)) - TEKTON_DISPLAY_RADIUS / 3; // Shift cluster up

                    if (sporeImg != null) {
                        g2.drawImage(sporeImg, sporeX - SPORE_RADIUS, sporeY - SPORE_RADIUS, SPORE_RADIUS*2, SPORE_RADIUS*2, null);
                    } else {
                        g2.setColor(new Color(148, 0, 211)); // DarkViolet for spores
                        g2.fill(new Ellipse2D.Double(sporeX - SPORE_RADIUS, sporeY - SPORE_RADIUS, SPORE_RADIUS*2, SPORE_RADIUS*2));
                    }
                }
                if (numSpores > displaySpores) {
                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("SansSerif", Font.BOLD, 10));
                    g2.drawString("+" + (numSpores - displaySpores), p.x + sporeClusterRadius, p.y - TEKTON_DISPLAY_RADIUS / 2 - SPORE_RADIUS);
                }
            }
        }

        // 3. Draw Insects (on top)
        Map<Tekton, Integer> insectsDrawnOnTekton = new HashMap<>();
        for (Player player : game.getPlayers()) {
            if (player instanceof Insecter) {
                for (Insect insect : ((Insecter) player).getInsects()) {
                    Tekton host = insect.getTektonNoPrint();
                    if (host != null && positions.containsKey(host)) {
                        Point basePos = positions.get(host);
                        int countOnTekton = host.getInsectsNoPrint().indexOf(insect);
                        if(countOnTekton == -1) countOnTekton = insectsDrawnOnTekton.getOrDefault(host,0);


                        int insectsPerRow = 2;
                        int col = countOnTekton % insectsPerRow;
                        int row = countOnTekton / insectsPerRow;

                        // Position insects towards the top edge of the Tekton
                        int initialOffsetY = -TEKTON_DISPLAY_RADIUS + INSECT_HEIGHT; // Start near top edge
                        int offsetX = (int) ((col - (insectsPerRow -1.0)/2.0) * (INSECT_WIDTH + 4));
                        int offsetY = initialOffsetY + (row * (INSECT_HEIGHT + 4));

                        Point insectPos = new Point(basePos.x + (int)offsetX, basePos.y + offsetY);

                        if (insectImg != null) {
                            g2.drawImage(insectImg, insectPos.x - INSECT_WIDTH / 2, insectPos.y - INSECT_HEIGHT / 2, INSECT_WIDTH, INSECT_HEIGHT, null);
                        } else {
                            g2.setColor(Color.DARK_GRAY.brighter());
                            g2.fillRect(insectPos.x - INSECT_WIDTH / 2, insectPos.y - INSECT_HEIGHT / 2, INSECT_WIDTH, INSECT_HEIGHT);
                        }
                        g2.setColor(getPlayerColor(player));
                        g2.fillRect(insectPos.x - INSECT_WIDTH / 2, insectPos.y - INSECT_HEIGHT / 2, INSECT_WIDTH, 4); // Thinner bar

                        if (insect == selectedActor) {
                            g2.setColor(HIGHLIGHT_COLOR_SELECTED_ACTOR);
                            g2.setStroke(new BasicStroke(3));
                            g2.drawRect(insectPos.x - INSECT_WIDTH / 2 - 2, insectPos.y - INSECT_HEIGHT / 2 - 2, INSECT_WIDTH + 4, INSECT_HEIGHT + 4);
                            g2.setStroke(defaultStroke);
                        }
                        insectsDrawnOnTekton.put(host, insectsDrawnOnTekton.getOrDefault(host,0) + 1);
                    }
                }
            }
        }
    }
}