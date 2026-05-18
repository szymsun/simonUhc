package ab.szymsun.simonuhc.commands;

import ab.szymsun.simonuhc.SimonUHCGameRules;
import ab.szymsun.simonuhc.uhc.tickable.GameStartCounter;
import ab.szymsun.simonuhc.uhc.UhcData;
import ab.szymsun.simonuhc.uhc.UhcGameState;
import ab.szymsun.simonuhc.uhc.tickable.TickableUtil;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.MinecraftServer;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRules;

import java.util.List;
import java.util.Objects;

public class UhcCommand implements ICommand {
    
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
            CommandManager.literal("uhc")
                    .then(CommandManager.literal("init").executes(UhcCommand::initCallback))

        ));
    }

    private static int initCallback(CommandContext<ServerCommandSource> context) {

        if(context.getSource().getServer().isSingleplayer() ) {
            if (!context.getSource().getServer().isRemote()){
                context.getSource().sendError(Text.literal("This command is not available in singleplayer mode!"));
                return 0;
            }
        }

        if (UhcData.getGameState() != UhcGameState.PRE_GAME) {
            context.getSource().sendError(Text.literal("UHC is already running!"));
            return 0;
        }



        ServerCommandSource source = context.getSource();

        if(!source.getServer().isDedicated()) {
            if (!source.getServer().isHost(Objects.requireNonNull(source.getPlayer()).getPlayerConfigEntry())) {
                context.getSource().sendError(Text.literal("You are not the host!"));
                return 0;
            }
        }

        MinecraftServer server = source.getServer();

        int borderSize = server.getSpawnWorld().getGameRules().getValue(SimonUHCGameRules.GAME_BORDER_GAMERULE);

        int spreadDistance = borderSize / 10 * server.getCurrentPlayerCount();

        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();

        for (ServerPlayerEntity player : players) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 10*20,10));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10*20,255));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 10*20,255));
        }

        server.getSpawnWorld().getWorldBorder().setSize(borderSize);
        server.getCommandManager().parseAndExecute(server.getCommandSource(),"spreadplayers 0 0 " + spreadDistance + " " + borderSize / 2 + " false @a[gamemode=survival]");
        server.getCommandManager().parseAndExecute(server.getCommandSource(),"execute at @a[gamemode=survival] run spawnpoint @p ~ ~ ~");

        GameStartCounter gameStartCounter = new GameStartCounter();
        TickableUtil.registerTickable(gameStartCounter);

        return 1;
    }


}
