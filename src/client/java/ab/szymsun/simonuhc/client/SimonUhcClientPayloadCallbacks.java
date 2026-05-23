package ab.szymsun.simonuhc.client;

import ab.szymsun.simonuhc.client.screen.GameruleScreen;
import ab.szymsun.simonuhc.payload.OpenUhcSettingsScreenPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class SimonUhcClientPayloadCallbacks {
    public static void payloadRecievedCallback(OpenUhcSettingsScreenPayload payload, ClientPlayNetworking.Context context) {
        MinecraftClient client = context.client();

        client.execute(() -> {
            client.setScreen(new GameruleScreen(Text.literal("Game Rules Manager")));
        });
    }
}
