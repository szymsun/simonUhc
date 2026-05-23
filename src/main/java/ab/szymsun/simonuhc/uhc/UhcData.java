package ab.szymsun.simonuhc.uhc;

public class UhcData {
    private static UhcGameState currentGameState = UhcGameState.PRE_GAME;

    public static UhcGameState getGameState() {
        return currentGameState;
    }

    public static void setCurrentGameState(UhcGameState gameState) {
        currentGameState = gameState;
    }
}
