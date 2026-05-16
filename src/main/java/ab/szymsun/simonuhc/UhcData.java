package ab.szymsun.simonuhc;

public class UhcData {

    private static int x,y,z;
    private static int currentCountdown = -1;
    private static int countdownSeconds = 10;
    private static int borderSize = 5000;

    private static boolean isUhcRunning = false;


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

    public static boolean isIsUhcRunning() {
        return isUhcRunning;
    }

    public static void setIsUhcRunning(boolean isUhcRunning) {
        UhcData.isUhcRunning = isUhcRunning;
    }
}
