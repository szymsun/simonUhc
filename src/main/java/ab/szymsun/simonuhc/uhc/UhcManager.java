package ab.szymsun.simonuhc.uhc;

import ab.szymsun.simonuhc.uhc.tickable.ShowdownManager;
import ab.szymsun.simonuhc.uhc.tickable.TickableUtil;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import net.minecraft.world.rule.GameRules;

import java.util.List;

public class UhcManager {
    public static void OnFinalShowdown(MinecraftServer server) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();

        for (ServerPlayerEntity player : players) {

            player.setSpawnPoint(null,false);
            player.sendMessage(Text.literal("Final showdown! Good luck!").formatted(Formatting.RED));
        }

        GameRules gameRules = server.getOverworld().getGameRules();

        gameRules.setValue(GameRules.PVP, true, server);

        server.getCommandManager().parseAndExecute(server.getCommandSource(),"spreadplayers 0 0 " + 10 + " " + 50 + " false @a[gamemode=survival]");
        server.getSpawnWorld().getWorldBorder().setSize(100);

        ShowdownManager showdownManager = new ShowdownManager();
        TickableUtil.registerTickable(showdownManager);
    }

    public static void OnGameEnd(MinecraftServer server, ServerPlayerEntity winner) {
        winner.networkHandler.sendPacket(new SubtitleS2CPacket(Text.literal("Congratulations!").formatted(Formatting.YELLOW).formatted(Formatting.BOLD)));
        winner.networkHandler.sendPacket(new TitleS2CPacket(Text.literal("You won!").formatted(Formatting.GREEN).formatted(Formatting.BOLD)));
        winner.changeGameMode(GameMode.CREATIVE);

        server.getSpawnWorld().getWorldBorder().setSize(UhcData.getBorderSize());

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player != winner) {
                player.networkHandler.sendPacket(new SubtitleS2CPacket(Text.literal(winner.getName().getString()+" won.").formatted(Formatting.YELLOW).formatted(Formatting.BOLD)));
                player.networkHandler.sendPacket(new TitleS2CPacket(Text.literal("You lost!").formatted(Formatting.RED).formatted(Formatting.BOLD)));
                player.changeGameMode(GameMode.CREATIVE);
            }
        }
    }
}
