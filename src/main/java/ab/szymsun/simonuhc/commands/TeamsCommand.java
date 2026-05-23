package ab.szymsun.simonuhc.commands;

import ab.szymsun.simonuhc.team.UhcTeam;
import ab.szymsun.simonuhc.team.UhcTeamManager;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;
import java.util.Random;

public class TeamsCommand implements ICommand{
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
           dispatcher.register(CommandManager.literal("teams")
                   .then(CommandManager.literal("create").then(CommandManager.argument("team_name", StringArgumentType.string()).executes(TeamsCommand::createTeamCallback)))
                   .then(CommandManager.literal("add").then(CommandManager.argument("player_name", EntityArgumentType.player()).executes(TeamsCommand::addPlayerToTeamCallback)))
                   .then(CommandManager.literal("remove").then(CommandManager.argument("player_name", EntityArgumentType.player()).executes(TeamsCommand::removePlayerFromTeamCallback))));
        });
    }

    private static int addPlayerToTeamCallback(CommandContext<ServerCommandSource> context) {
        if (context.getSource().getPlayer() == null) {
            context.getSource().sendError(Text.literal("you are not a player"));
            return 0;
        }
        if (context.getSource().getPlayer().isSpectator()) {
            context.getSource().sendError(Text.literal("you are a spectator"));
            return 0;
        }

        if (UhcTeamManager.playerToTeamName.get(context.getSource().getPlayer().getUuid()).isEmpty()){
            context.getSource().sendError(Text.literal("you are not in a team"));
            return 0;
        }


        return 1;
    }

    private static int removePlayerFromTeamCallback(CommandContext<ServerCommandSource> context) {
        if (context.getSource().getPlayer() == null) {
            context.getSource().sendError(Text.literal("you are not a player"));
            return 0;
        }
        if (context.getSource().getPlayer().isSpectator()) {
            context.getSource().sendError(Text.literal("you are a spectator"));
            return 0;
        }
        if (UhcTeamManager.playerToTeamName.get(context.getSource().getPlayer().getUuid()).isEmpty()){
            context.getSource().sendError(Text.literal("you are not in a team"));
            return 0;
        }


        return 1;

    }

    private static int createTeamCallback(CommandContext<ServerCommandSource> context) {
        if (context.getSource().getPlayer() == null) {
            context.getSource().sendError(Text.literal("you are not a player"));
            return 0;
        }
        if (context.getSource().getPlayer().isSpectator()) {
            context.getSource().sendError(Text.literal("you are a spectator"));
            return 0;
        }

        if (!UhcTeamManager.playerToTeamName.get(context.getSource().getPlayer().getUuid()).isEmpty()){
            context.getSource().sendError(Text.literal("you are already in a team"));
            return 0;
        }

        UhcTeam newTeam = new UhcTeam(Objects.requireNonNull(context.getSource().getPlayer()).getUuid(),StringArgumentType.getString(context,"team_name"),new Random().nextInt(16));

        UhcTeamManager.teams.put(newTeam.getName(),newTeam);
        UhcTeamManager.playerToTeamName.put(context.getSource().getPlayer().getUuid(),newTeam.getName());

        context.getSource().sendFeedback(() -> {

            var color = Formatting.byColorIndex(newTeam.getColor());
            assert color != null;
            return Text.literal("Created new team: ").append(Text.literal(newTeam.getName()).formatted(color));
        }, false);

        return 1;
    }
}
