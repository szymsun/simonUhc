package ab.szymsun.simonuhc;

import net.minecraft.server.MinecraftServer;

public interface ITickable {
    void tick();
    boolean isFinished();
}
