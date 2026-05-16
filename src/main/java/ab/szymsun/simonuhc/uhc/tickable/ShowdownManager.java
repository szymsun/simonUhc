package ab.szymsun.simonuhc.uhc.tickable;

import ab.szymsun.simonuhc.uhc.UhcData;
import ab.szymsun.simonuhc.uhc.UhcGameState;
import ab.szymsun.simonuhc.uhc.UhcManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;

import java.util.ArrayList;
import java.util.List;

public class ShowdownManager implements ITickable {

    boolean finished = false;

    @Override
    public void tick(MinecraftServer server) {
        List<ServerPlayerEntity> alivePlayers = new ArrayList<>();

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            if (player.getGameMode() == GameMode.SURVIVAL){
                alivePlayers.add(player);
            }
        }

        if (alivePlayers.size() == 1){
            onFinish(server);
            UhcManager.OnGameEnd(server, alivePlayers.getFirst());
        }

    }

    @Override
    public void onFinish(MinecraftServer server) {
        UhcData.setCurrentGameState(UhcGameState.POST_GAME);
        finished = true;

    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
