package ab.szymsun.simonuhc.uhc.tickable;

import net.minecraft.server.MinecraftServer;

public interface ITickable {
    void tick(MinecraftServer server);

    void onFinish(MinecraftServer server);
    boolean isFinished();
}
