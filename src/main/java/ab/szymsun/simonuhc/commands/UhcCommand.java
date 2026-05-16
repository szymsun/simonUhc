package ab.szymsun.simonuhc.commands;

import ab.szymsun.simonuhc.uhc.tickable.GracePeriodCounter;
import ab.szymsun.simonuhc.SimonUhcInit;
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

import java.util.List;
import java.util.Objects;

public class UhcCommand implements ICommand {
    
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                CommandManager.literal("uhc")
                    .then(CommandManager.literal("countdown")
                        .executes(UhcCommand::getCountDownCallback)
                        .then(CommandManager.argument("seconds", IntegerArgumentType.integer(1)) // Requires at least 1 second
                            .executes(UhcCommand::setCountdownCallback)))

                    .then(CommandManager.literal("borders")
                        .executes(UhcCommand::getBorderSizeCallback)
                        .then(CommandManager.argument("blocks", IntegerArgumentType.integer(1))
                            .executes(UhcCommand::setBorderSizeCallback)))

                    .then(CommandManager.literal("init").executes(UhcCommand::initCallback))

            );
        });
    }

    private static int getCountDownCallback(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() ->
            Text.literal("Current countdown is set to: " + UhcData.getCountdownSeconds() + " seconds."), false);
        return 1;
    }

    private static int setCountdownCallback(CommandContext<ServerCommandSource> context) {

        if (UhcData.getGameState() != UhcGameState.PRE_GAME) {
            context.getSource().sendError(Text.literal("UHC is already running!"));
            return 0;
        }

        int seconds = IntegerArgumentType.getInteger(context, "seconds");
        UhcData.setCountdownSeconds(seconds);

        context.getSource().sendFeedback(() ->
                Text.literal("Countdown updated! New value: " + UhcData.getCountdownSeconds() + " seconds."), true);
        return 1;
    }

    private static int getBorderSizeCallback(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.literal("border size is set to: " + UhcData.getBorderSize()),false);
        return 1;
    }

    private static int setBorderSizeCallback(CommandContext<ServerCommandSource> context) {

        if (UhcData.getGameState() != UhcGameState.PRE_GAME) {
            context.getSource().sendError(Text.literal("UHC is already running!"));
            return 0;
        }

        int size = IntegerArgumentType.getInteger(context, "blocks");
        UhcData.setBorderSize(size);
        context.getSource().sendFeedback(() -> Text.literal("border size updated! New value: " + UhcData.getBorderSize()),true);
        return 1;
    }

    private static int initCallback(CommandContext<ServerCommandSource> context) {

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

        int spreadDistance = UhcData.getBorderSize() / 10 * server.getCurrentPlayerCount();

        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();

        for (ServerPlayerEntity player : players) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 10*20,10));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10*20,255));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 5*20,255));
        }

        server.getSpawnWorld().getWorldBorder().setSize(UhcData.getBorderSize());
        server.getCommandManager().parseAndExecute(server.getCommandSource(),"spreadplayers 0 0 " + spreadDistance + " " + UhcData.getBorderSize() / 2 + " false @a[gamemode=survival]");
        server.getCommandManager().parseAndExecute(server.getCommandSource(),"execute at @a[gamemode=survival] run spawnpoint @p ~ ~ ~");

        GracePeriodCounter gracePeriodCounter = new GracePeriodCounter(UhcData.getCountdownSeconds());

        UhcData.setCurrentGameState(UhcGameState.PRE_GAME);
        TickableUtil.registerTickable(gracePeriodCounter);

        return 1;
    }


}
