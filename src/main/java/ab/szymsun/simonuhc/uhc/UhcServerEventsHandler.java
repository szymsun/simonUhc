package ab.szymsun.simonuhc.uhc;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

public class UhcServerEventsHandler {
    public static void registerServerPlayerAfterRespawnEvents(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        if (UhcData.getGameState() == UhcGameState.SHOWDOWN){
            newPlayer.changeGameMode(GameMode.SPECTATOR);
        }
    }


    public static void registerServerStopEvents(MinecraftServer minecraftServer) {
        if(UhcData.getGameState() != UhcGameState.PRE_GAME){
            UhcData.setCurrentGameState(UhcGameState.PRE_GAME);
        }
    }
}
