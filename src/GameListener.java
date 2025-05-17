public interface GameListener {
    /** Called when the game’s map topology has changed (e.g. new tekton added/split). */
    void onMapChanged();
    /** Called when game state changes (e.g. turn advanced, insect moved). */
    void onStateChanged();
}
