package ab.szymsun.simonuhc.uhc;

public class UhcData {

    private static int x,y,z;
    private static int currentCountdown = -1;
    private static int countdownSeconds = 10;
    private static int borderSize = 5000;

    private static boolean running = false;

    private static UhcGameState currentGameState = UhcGameState.PRE_GAME;


    public static int[] getXYZ(){
        return new int[]{x,y,z};
    }

    public static void setXYZ(int x, int y, int z){
        UhcData.x = x;
        UhcData.y = y;
        UhcData.z = z;
    }
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

    public static boolean isUhcRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        UhcData.running = running;
    }

    public static UhcGameState getGameState() {
        return currentGameState;
    }

    public static void setCurrentGameState(UhcGameState gameState) {
        currentGameState = gameState;
    }
}
