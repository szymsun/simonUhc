package ab.szymsun.simonuhc.uhc.tickable;

import ab.szymsun.simonuhc.uhc.UhcData;
import ab.szymsun.simonuhc.uhc.UhcGameState;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class GameStartCounter implements ITickable{

    boolean f = false;

    int startCooldown = 10;
    int seconds;
    int ticks = startCooldown * 20;

    @Override
    public void tick(MinecraftServer server) {
        ticks -= 1;

        seconds = ticks / 20;

        if (ticks / 20 < 11 && ticks % 20 == 0) {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
                player.networkHandler.sendPacket(new TitleS2CPacket(Text.literal(String.valueOf(seconds)).formatted(Formatting.BLUE).formatted(Formatting.BOLD)));
            }
        }

        if (ticks <= 0) {
            onFinish(server);
        }
    }

    @Override
    public void onFinish(MinecraftServer server) {
        f = true;

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            player.networkHandler.sendPacket(new TitleS2CPacket(Text.literal("GOGOGOGOOOO").formatted(Formatting.GREEN).formatted(Formatting.BOLD)));
        }

        GracePeriodCounter gracePeriodCounter = new GracePeriodCounter(UhcData.getCountdownSeconds());

        UhcData.setCurrentGameState(UhcGameState.PRE_GAME);
        TickableUtil.registerTickable(gracePeriodCounter);
    }

    @Override
    public boolean isFinished() {
        return f;
    }
}
