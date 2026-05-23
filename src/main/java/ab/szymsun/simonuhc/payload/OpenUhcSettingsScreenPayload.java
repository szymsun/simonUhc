package ab.szymsun.simonuhc.payload;

import ab.szymsun.simonuhc.SimonUhcInit;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record OpenUhcSettingsScreenPayload() implements CustomPayload {
    public static final Id<OpenUhcSettingsScreenPayload> ID = new Id<>(Identifier.of(SimonUhcInit.MOD_ID, "open_uhc_settings"));

    public static final PacketCodec<RegistryByteBuf, OpenUhcSettingsScreenPayload> CODEC = CustomPayload.codecOf(
            (value, buf) -> {},
            buf -> new OpenUhcSettingsScreenPayload()
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
