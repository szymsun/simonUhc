package ab.szymsun.simonuhc.uhc.tickable;

import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class TickableUtil {

    public static final List<ITickable> ALL_TICKABLES = new ArrayList<>();

    public static void registerITickableEvents(MinecraftServer server) {
        for (int i = ALL_TICKABLES.size() - 1; i >= 0; i--) {
            ITickable tickable = ALL_TICKABLES.get(i);
            tickable.tick(server);
            if (tickable.isFinished()) {
                ALL_TICKABLES.get(i).onFinish(server);
                ALL_TICKABLES.remove(i);
            }
        }
    }

    public static void registerTickable(ITickable tickable) {
        ALL_TICKABLES.add(tickable);
    }
}
