package ab.szymsun.simonuhc.uhc.tickable;

import ab.szymsun.simonuhc.uhc.UhcData;
import ab.szymsun.simonuhc.uhc.UhcGameState;
import ab.szymsun.simonuhc.uhc.UhcManager;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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

        msg.append("Time left: ");
        if (minutes < 10) msg.append("0");
        msg.append(minutes).append(":");

        if (1 + (seconds % 60) < 10) msg.append("0");
        msg.append(1 + (seconds % 60));

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.sendMessage(Text.literal(msg.toString()), true);

            if(seconds % 60 < 11 && minutes == 0 && ticks % 20 == 0){
                player.networkHandler.sendPacket(new TitleS2CPacket(Text.literal("Grace period ends in:").formatted(Formatting.RED).formatted(Formatting.BOLD)));
                player.networkHandler.sendPacket(new SubtitleS2CPacket(Text.literal(String.valueOf(seconds)).formatted(Formatting.YELLOW).formatted(Formatting.BOLD)));
            }
        }

        if (ticks <= 0) finished = true;
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
