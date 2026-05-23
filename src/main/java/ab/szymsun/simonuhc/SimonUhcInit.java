package ab.szymsun.simonuhc;

import ab.szymsun.simonuhc.commands.SpectatorCommand;
import ab.szymsun.simonuhc.commands.TeamsCommand;
import ab.szymsun.simonuhc.commands.UhcCommand;

import ab.szymsun.simonuhc.uhc.UhcServerEventsHandler;
import ab.szymsun.simonuhc.uhc.tickable.TickableUtil;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;


import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Identifier;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRuleCategory;
import net.minecraft.world.rule.GameRuleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ab.szymsun.simonuhc.SimonUHCGameRules.UHC;
// word()
// literal("foo")
// argument("bar", word())
// Import everything in the CommandManager


public class SimonUhcInit implements ModInitializer {
	public static final String MOD_ID = "SimonUHC";
	public static final String IDENTIFIER = "simonuhc";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		registerEvents();
		LOGGER.info("registered events");
		registerGameRules();
		LOGGER.info("registered GameRules");
		registerCommands();
		LOGGER.info("registered Commands");

		LOGGER.info("SimonUHC main Loaded. made by szymsun");
	}

	void registerEvents() {
		ServerTickEvents.END_SERVER_TICK.register(TickableUtil::registerITickableEvents);
		ServerLifecycleEvents.SERVER_STARTED.register(UhcServerEventsHandler::registerServerStartedEvents);
		ServerPlayerEvents.AFTER_RESPAWN.register(UhcServerEventsHandler::registerServerPlayerAfterRespawnEvents);
		ServerLifecycleEvents.SERVER_STOPPED.register(UhcServerEventsHandler::registerServerStopEvents);
	}

	void registerGameRules() {
		SimonUHCGameRules.register();
	}

	void registerCommands() {
		UhcCommand.register();
		SpectatorCommand.register();
		TeamsCommand.register();
	}
}