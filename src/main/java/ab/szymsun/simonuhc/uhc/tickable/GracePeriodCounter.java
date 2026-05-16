package ab.szymsun.simonuhc.uhc.tickable;

import ab.szymsun.simonuhc.uhc.UhcData;
import ab.szymsun.simonuhc.uhc.UhcGameState;
import ab.szymsun.simonuhc.uhc.UhcManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class GracePeriodCounter implements ITickable {

    private int ticks;
    private boolean finished = false;

    public GracePeriodCounter(int seconds) {
        ticks = seconds * 20;
    }

    @Override
    public void tick(MinecraftServer server) {
        ticks -= 1;

        StringBuilder msg = new StringBuilder();

        int seconds = ticks / 20;
        int minutes = seconds / 60;

        msg.append("Time left: ").append(minutes).append(":").append(seconds % 60);

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.sendMessage(Text.literal(msg.toString()), true);
        }

        if (ticks <= 0) finished = true;
        else UhcData.setCurrentCountdown(seconds);
    }

    public void onFinish(MinecraftServer server) {
        UhcData.setCurrentGameState(UhcGameState.SHOWDOWN);
        UhcManager.OnFinalShowdown(server);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
