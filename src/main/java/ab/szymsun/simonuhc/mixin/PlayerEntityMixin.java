package ab.szymsun.simonuhc.mixin;

import ab.szymsun.simonuhc.team.UhcTeamManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void changeNicknameColor(CallbackInfoReturnable<Text> cir) {
        Text originalName = cir.getReturnValue();
        PlayerEntity player = (PlayerEntity) (Object) this;

        String teamName = UhcTeamManager.playerToTeamName.get(player.getUuid());

        if(teamName.isEmpty()) {
            cir.cancel();
        }

        int colorInt = UhcTeamManager.teams.get(teamName).getColor();

        var color = Formatting.byColorIndex(colorInt);
        assert color != null; // just so the compiler doesn't complain

        MutableText coloredName = originalName.copy().formatted(color);


        cir.setReturnValue(coloredName);
    }
}
