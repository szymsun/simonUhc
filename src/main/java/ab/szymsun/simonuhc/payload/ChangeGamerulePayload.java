package ab.szymsun.simonuhc.payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ChangeGamerulePayload(String gameruleName, int intValue, boolean isBoolean) implements CustomPayload {
    public static final Id<ChangeGamerulePayload> ID = new Id<>(Identifier.of("uhcrules", "change_gamerule"));

    public static final PacketCodec<RegistryByteBuf, ChangeGamerulePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, ChangeGamerulePayload::gameruleName,
            PacketCodecs.INTEGER, ChangeGamerulePayload::intValue,
            PacketCodecs.BOOLEAN, ChangeGamerulePayload::isBoolean,
            ChangeGamerulePayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}