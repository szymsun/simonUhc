package ab.szymsun.simonuhc.uhc;

public class UhcData {
    private static int currentCountdown = -1;
    private static int countdownSeconds = 10;
    private static int borderSize = 5000;

    private static UhcGameState currentGameState = UhcGameState.PRE_GAME;

    public static int getCurrentCountdown() {
        return currentCountdown;
    }

    public static void setCurrentCountdown(int currentCountdown) {
        UhcData.currentCountdown = currentCountdown;
    }

    public static int getCountdownSeconds() {
        return countdownSeconds;
    }

    public static void setCountdownSeconds(int countdownSeconds) {
        UhcData.countdownSeconds = countdownSeconds;
    }

    public static int getBorderSize() {
        return borderSize;
    }

    public static void setBorderSize(int borderSize) {
        UhcData.borderSize = borderSize;
    }

    public static UhcGameState getGameState() {
        return currentGameState;
    }

    public static void setCurrentGameState(UhcGameState gameState) {
        currentGameState = gameState;
    }
}
