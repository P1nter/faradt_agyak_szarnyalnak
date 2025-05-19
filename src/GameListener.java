/**
 * Listener interface for receiving core game events.
 * Implement this interface to update UI or game components when the map changes,
 * the game state updates, or the game ends.
 */
public interface GameListener {

    /**
     * Called when the game map has changed (e.g., Makings of new Tektons or Yarns).
     * Implement to refresh or repaint map-related UI elements.
     */
    void onMapChanged();

    /**
     * Called when the game state or active player changes (e.g., action points, turn).
     * Implement to update status displays or available actions.
     */
    void onStateChanged();

    /**
     * Called when the game has ended, such as reaching the maximum round limit.
     * Provides a Winners object summarizing the top-scoring players.
     *
     * @param winners the result containing best Insecter and Mushroomer players
     */
    void onGameEnd(Game.Winners winners);
}