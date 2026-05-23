package ab.szymsun.simonuhc.team;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.UUID;

public class UhcTeamManager {
    public static Map<String,UhcTeam> teams;
    public static Map<UUID,String> playerToTeamName;

    public static int addPlayerToTeam(ServerPlayerEntity player, String teamName) {
        if (teams.get(teamName) == null) return -1;
        if (playerToTeamName.get(player.getUuid()) == null) return -2;

        playerToTeamName.put(player.getUuid(),teamName);
        return 0;
    }

    public static int clearPlayerTeam(ServerPlayerEntity player) {
        if (playerToTeamName.get(player.getUuid()) == null) return -1;

        playerToTeamName.put(player.getUuid(),"");
        return 0;
    }
}
