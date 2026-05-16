package ab.szymsun.simonuhc.commands;

import ab.szymsun.simonuhc.SimonUhcInit;
import ab.szymsun.simonuhc.uhc.UhcData;
import ab.szymsun.simonuhc.uhc.UhcGameState;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

import java.util.Objects;

public class SpectatorCommand implements ICommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("spectators").executes(SpectatorCommand::spectatorDefaultCallback)
                    .then(CommandManager.literal("join").executes(SpectatorCommand::spectatorJoinCallback))
                    .then(CommandManager.literal("leave").executes(SpectatorCommand::spectatorLeaveCallback))
            );
        });
    }

    private static int spectatorDefaultCallback(CommandContext<ServerCommandSource> context) {
        StringBuilder msg = new StringBuilder();

        msg.append("Spectators: ");

        for (var player : context.getSource().getServer().getPlayerManager().getPlayerList()) {
            msg.append(player.isSpectator() ? player.getName().getString() + ", " : "");
        }

        context.getSource().sendFeedback(() -> Text.literal(msg.toString()), false);

        return 1;
    }

    private static int spectatorJoinCallback(CommandContext<ServerCommandSource> context) {

        if (context.getSource().getPlayer() == null) {

            context.getSource().sendError(Text.literal("Bro why do you want to spectate from cli? get a job."));

            return 0;
        }

        if (context.getSource().getPlayer().isSpectator()) {
            context.getSource().sendError(Text.literal("you are already a spectator"));
            return 0;
        }

        if (UhcData.getGameState() != UhcGameState.PRE_GAME) {
            context.getSource().sendError(Text.literal("uhc is already running. you cannot switch"));
            return 1;
        }

        ServerPlayerEntity player = context.getSource().getPlayer();

        player.changeGameMode(GameMode.SPECTATOR);
        context.getSource().sendFeedback(() -> Text.literal("You are now spectating!"), false);
        SimonUhcInit.LOGGER.info("{} joined spectators", Objects.requireNonNull(context.getSource().getPlayer()).getName().getString());
        return 1;
    }

    private static int spectatorLeaveCallback(CommandContext<ServerCommandSource> context) {

        if (context.getSource().getPlayer() == null) {
            context.getSource().sendError(Text.literal("you cant even spectate here"));
            return 0;
        }

        if (!context.getSource().getPlayer().isSpectator()) {
            context.getSource().sendError(Text.literal("you are not a spectator"));
            return 0;
        }

        if (UhcData.getGameState() != UhcGameState.PRE_GAME) {
            context.getSource().sendError(Text.literal("uhc is already running. you cannot switch"));
            return 0;
        }

        ServerPlayerEntity player = context.getSource().getPlayer();

        player.changeGameMode(GameMode.SURVIVAL);
        context.getSource().sendFeedback(() -> Text.literal("You are now in survival mode!"), false);
        SimonUhcInit.LOGGER.info("{} left spectators", Objects.requireNonNull(context.getSource().getPlayer()).getName().getString());

        return 1;
    }
}
