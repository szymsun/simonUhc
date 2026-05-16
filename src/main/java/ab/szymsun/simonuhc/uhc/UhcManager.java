package ab.szymsun.simonuhc.uhc;

import ab.szymsun.simonuhc.SimonUhcInit;
import ab.szymsun.simonuhc.uhc.tickable.ShowdownManager;
import ab.szymsun.simonuhc.uhc.tickable.TickableUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class UhcManager {
    public static void OnFinalShowdown(MinecraftServer server) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();

        for (ServerPlayerEntity player : players) {

            player.setSpawnPoint(null,false);
            player.sendMessage(Text.literal("Final showdown! Good luck!").formatted(Formatting.RED));
        }

        server.getCommandManager().parseAndExecute(server.getCommandSource(),"spreadplayers 0 0 " + 10 + " " + 50 + " false @a[gamemode=survival]");
        server.getSpawnWorld().getWorldBorder().setSize(100);

        ShowdownManager showdownManager = new ShowdownManager();
        TickableUtil.registerTickable(showdownManager);
    }

    public static void OnGameEnd(MinecraftServer server, ServerPlayerEntity winner) {

    }
}
