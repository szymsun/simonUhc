package ab.szymsun.simonuhc.client;

import ab.szymsun.simonuhc.payload.OpenUhcSettingsScreenPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class SimonUhcClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		registerRecievers();

		registerKeybinds();
	}

	private void registerRecievers() {
		ClientPlayNetworking.registerGlobalReceiver(OpenUhcSettingsScreenPayload.ID, SimonUhcClientPayloadCallbacks::payloadRecievedCallback);
	}

	private void registerKeybinds() {
		KeyBinding.Category UHC_CATEGORY = KeyBinding.Category.create(
				Identifier.of("simon_uhc", "simonuhc")
		);

		KeyBinding openUhcSettingsKey = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.simonuhc.open_uhc_settings", // The translation key for the key mapping.
						InputUtil.Type.KEYSYM, // // The type of the keybinding; KEYSYM for keyboard, MOUSE for mouse.
						GLFW.GLFW_KEY_Y, // The GLFW keycode of the key.
						UHC_CATEGORY // The category of the mapping.
				));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openUhcSettingsKey.wasPressed()) {
				if(client.player != null){
					Objects.requireNonNull(client.getNetworkHandler()).sendChatCommand("uhc setup");
				}
			}
		});
	}

}