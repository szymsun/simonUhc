package ab.szymsun.simonuhc.mixin;

import ab.szymsun.simonuhc.team.UhcTeamManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(at = @At("RETURN"),method = "getPlayerListName",cancellable = true)
    private void changePlayerListName(CallbackInfoReturnable<Text> cir) {

        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        String teamName = UhcTeamManager.playerToTeamName.get(player.getUuid());

        if (teamName.isEmpty()) cir.cancel();

        int colorInt = UhcTeamManager.teams.get(teamName).getColor();

        var color = Formatting.byColorIndex(colorInt);
        assert color != null;

        MutableText coloredName = cir.getReturnValue().copy().formatted(color);


        cir.setReturnValue(coloredName);
    }

}
