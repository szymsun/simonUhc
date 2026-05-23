 package ab.szymsun.simonuhc.uhc;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import net.minecraft.world.rule.GameRules;

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

    public static void registerServerStartedEvents(MinecraftServer server){
        GameRules gameRules = server.getSpawnWorld().getGameRules();
        gameRules.setValue(GameRules.LOCATOR_BAR, false, server);
        gameRules.setValue(GameRules.PVP, false, server);
        gameRules.setValue(GameRules.DO_IMMEDIATE_RESPAWN,true, server);
    }
}
