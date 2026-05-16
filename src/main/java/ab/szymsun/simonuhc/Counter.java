package ab.szymsun.simonuhc;

import java.beans.EventHandler;

public class Counter implements ITickable {

    private int ticks = -1;
    private boolean finished = false;



    public Counter(int seconds) {
        ticks = seconds * 20;
    }

    @Override
    public void tick() {
        ticks -= 1;

        if (ticks <= 0) onFinish();
        else UhcData.setCurrentCountdown(ticks / 20);
    }

    private void onFinish() {
        UhcData.setIsUhcRunning(false);
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
