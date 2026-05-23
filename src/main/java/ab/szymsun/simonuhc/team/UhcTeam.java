package ab.szymsun.simonuhc.team;
import java.util.Random;
import java.util.UUID;
import java.util.random.RandomGenerator;


public class UhcTeam {
    private String name;
    private final UUID leaderUUID;
    private int color;

    private final String prefix;

    public UhcTeam(UUID leaderUUID,String name, int color) {
        this.prefix = name.substring(0,2);
        this.leaderUUID = leaderUUID;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public boolean isLeader(UUID uuid) {
        return leaderUUID.equals(uuid);
    }

    public String getPrefix() {
        return prefix;
    }

    public UUID getLeaderUUID() {
        return leaderUUID;
    }
}
