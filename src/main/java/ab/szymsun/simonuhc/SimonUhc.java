package ab.szymsun.simonuhc;

import ab.szymsun.simonuhc.commands.UhcCommand;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
// word()
// literal("foo")
// argument("bar", word())
// Import everything in the CommandManager


public class SimonUhc implements ModInitializer {
	public static final String MOD_ID = "SimonUHC";

	public static final List<ITickable> ALL_TICKABLES = new ArrayList<>();

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ServerTickEvents.END_SERVER_TICK.register(this::registerITickableEvents);
		registerCommands();
		LOGGER.info("SimonUHC main Loaded. made by szymsun");
	}


	void registerITickableEvents(MinecraftServer server) {
		for (int i = ALL_TICKABLES.size() - 1; i >= 0; i--) {
			ITickable tickable = ALL_TICKABLES.get(i);
			tickable.tick();
			if (tickable.isFinished()) {
				ALL_TICKABLES.remove(i);
			}
		}
	}

	public static void registerTickable(ITickable tickable) {
		ALL_TICKABLES.add(tickable);
	}

	void registerCommands() {
		UhcCommand.register();
	}
}