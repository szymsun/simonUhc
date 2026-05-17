package ab.szymsun.simonuhc;

import ab.szymsun.simonuhc.commands.SpectatorCommand;
import ab.szymsun.simonuhc.commands.UhcCommand;

import ab.szymsun.simonuhc.uhc.UhcServerEventsHandler;
import ab.szymsun.simonuhc.uhc.tickable.TickableUtil;
import net.fabricmc.api.ModInitializer;


import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// word()
// literal("foo")
// argument("bar", word())
// Import everything in the CommandManager


public class SimonUhcInit implements ModInitializer {
	public static final String MOD_ID = "SimonUHC";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		registerEvents();
		registerCommands();

		LOGGER.info("SimonUHC main Loaded. made by szymsun");
	}

	void registerEvents() {
		ServerTickEvents.END_SERVER_TICK.register(TickableUtil::registerITickableEvents);
		ServerPlayerEvents.AFTER_RESPAWN.register(UhcServerEventsHandler::registerServerPlayerAfterRespawnEvents);
		ServerLifecycleEvents.SERVER_STOPPING.register(UhcServerEventsHandler::registerServerStopEvents);
	}

	void registerCommands() {
		UhcCommand.register();
		SpectatorCommand.register();
	}
}